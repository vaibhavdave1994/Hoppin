package com.hoppin.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.TextViewCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class CustomTabStrip extends ViewGroup {
    private ViewPager mPager;

    private final TextView[] mTextViews = new TextView[5];
    private static final int PREV_PREV = 0;
    private static final int PREV = 1;
    private static final int CURR = 2;
    private static final int NEXT = 3;
    private static final int NEXT_NEXT = 4;

    private int mLastKnownCurrentPage = -1;
    private float mLastKnownPositionOffset = -1;
    private int mScaledTextSpacing;
    private int mGravity;

    private boolean mUpdatingText;
    private boolean mUpdatingPositions;

    private final PageListener mPageListener = new PageListener();

    private WeakReference<PagerAdapter> mWatchingAdapter;

    private static final int[] ATTRS = new int[]{
            android.R.attr.textAppearance,
            android.R.attr.textSize,
            android.R.attr.textColor,
            android.R.attr.gravity
    };

    private static final float SIDE_ALPHA = 0.6f;

    // Space between titles
    private static final int TEXT_SPACING = 16; // dp

    private int mNonPrimaryAlpha;
    private int mTextColor;

    // -----------------------------------

    private static final int INDICATOR_HEIGHT = 6; // dp
    private static final int MIN_PADDING_BOTTOM = INDICATOR_HEIGHT + 3; // dp
    private static final int TAB_PADDING = 2; // dp
    private static final int TAB_SPACING = 2; // dp
    private static final int MIN_TEXT_SPACING = TAB_SPACING + TAB_PADDING * 2; // dp
    private static final int MIN_STRIP_HEIGHT = 32; // dp

    private int mIndicatorColor;
    private int mIndicatorHeight;

    private int mMinPaddingBottom;
    private int mMinTextSpacing;
    private int mMinStripHeight;

    private int mTabPadding;

    private final Paint mTabPaint = new Paint();

    private int mTabAlpha = 0xFF;

    private boolean mIgnoreTap;
    private float mInitialMotionX;
    private float mInitialMotionY;
    private int mTouchSlop;

    private final Path mPath = new Path();

    private boolean mIndicatorVisible = true;

    public CustomTabStrip(Context context) {
        this(context, null);
    }

    public CustomTabStrip(Context context, AttributeSet attrs) {
        super(context, attrs);

        final TypedArray a = context.obtainStyledAttributes(attrs, ATTRS);
        final int textAppearance = a.getResourceId(0, 0);
        final int textSize = a.getDimensionPixelSize(1, 0);
        final int textColor = a.getColor(2, 0);
        mGravity = a.getInteger(3, Gravity.BOTTOM);
        a.recycle();

        // TextViews creation + set tags
        createTitles(context, textAppearance, textSize, textColor);

        // Text and indicator color
        mTextColor = mTextViews[CURR].getTextColors().getDefaultColor();
        mIndicatorColor = mTextColor;
        // ???
        setNonPrimaryAlpha(SIDE_ALPHA);

        final float density = context.getResources().getDisplayMetrics().density;

        // Set space between titles
        mScaledTextSpacing = (int) (TEXT_SPACING * density);

        // ----------------INDICATORS-------------------
        if (mIndicatorVisible) {
            setIndicatorParams(context, density);
        }
    }

    private void createTitles(Context context, int textAppearance, int textSize, int textColor) {
        for (int i = 0; i < mTextViews.length; i++) {
            addView(mTextViews[i] = new TextView(context));
            mTextViews[i].setTag(i);
        }

        for (TextView textView : mTextViews) {
            // Set textAppearance
            if (textAppearance != 0) {
                TextViewCompat.setTextAppearance(textView, textAppearance);
            }
            // Set textSize
            if (textSize != 0) {
                setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            }
            // Set textColor
            if (textColor != 0) {
                textView.setTextColor(textColor);
            }

            textView.setEllipsize(TextUtils.TruncateAt.END);

            textView.setSingleLine();

            // Set focusable true and click listener
            textView.setFocusable(true);

            ClickListener clickListener = new ClickListener();
            textView.setOnClickListener(clickListener);
        }
    }

    private void setIndicatorParams(Context context, float density) {
        mTabPaint.setColor(mIndicatorColor);

        // Note: this follows the rules for Resources#getDimensionPixelOffset/Size: sizes round up, offsets round down.
        mIndicatorHeight = (int) (INDICATOR_HEIGHT * density + 0.5f);
        mMinPaddingBottom = (int) (MIN_PADDING_BOTTOM * density + 0.5f);
        mMinTextSpacing = (int) (MIN_TEXT_SPACING * density);
        mTabPadding = (int) (TAB_PADDING * density + 0.5f);
        mMinStripHeight = (int) (MIN_STRIP_HEIGHT * density + 0.5f);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

        // Enforce restrictions
        setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), getPaddingBottom());
        setTextSpacing(getTextSpacing());

        setWillNotDraw(false);
    }


    //--------- INDICATOR START ------

    /**
     * Set the color of the tab indicator bar.
     *
     * @param color Color to set as an 0xRRGGBB value. The high byte (alpha) is ignored.
     */
    @SuppressWarnings("unused")
    public void setTabIndicatorColor(@ColorInt int color) {
        mIndicatorColor = color;
        mTabPaint.setColor(mIndicatorColor);
        invalidate();
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        if (bottom < mMinPaddingBottom) {
            bottom = mMinPaddingBottom;
        }
        super.setPadding(left, top, right, bottom);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();
        if (action != MotionEvent.ACTION_DOWN && mIgnoreTap) {
            return false;
        }

        // Any tap within touch slop to either side of the current item
        // will scroll to prev/next.
        final float x = ev.getX();
        final float y = ev.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mInitialMotionX = x;
                mInitialMotionY = y;
                mIgnoreTap = false;
                break;

            case MotionEvent.ACTION_MOVE:
                if (Math.abs(x - mInitialMotionX) > mTouchSlop
                        || Math.abs(y - mInitialMotionY) > mTouchSlop) {
                    mIgnoreTap = true;
                }
                break;

            case MotionEvent.ACTION_UP:
                if (x < mTextViews[CURR].getLeft() - mTabPadding) {
                    mPager.setCurrentItem(mPager.getCurrentItem() - 1);
                } else if (x > mTextViews[CURR].getRight() + mTabPadding) {
                    mPager.setCurrentItem(mPager.getCurrentItem() + 1);
                }
                break;
        }

        return true;
    }

    public void setIndicatorVisible(boolean indicatorVisible) {
        mIndicatorVisible = indicatorVisible;
        mIndicatorHeight = 0;
        mMinPaddingBottom = 0;
        mIndicatorHeight = 0;
        invalidate();
    }

    @SuppressWarnings({"UnnecessaryLocalVariable", "NumericOverflow"})
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!mIndicatorVisible) {
            return;
        }

        final int height = getHeight();
        final int width = getHeight() / 3;
        final int bottom = height;
        final int top = bottom - mIndicatorHeight;
        final int left = mTextViews[CURR].getLeft() - mTabPadding;
        final int right = mTextViews[CURR].getRight() + mTabPadding;
        final int middle = left + ((right - left) / 2);
        final int tLeft = middle - width / 2;
        final int tRight = middle + width / 2;

        mTabPaint.setColor(mTabAlpha << 24 | (mIndicatorColor & 0xEEEEEE));
        //canvas.drawRect(left, top, right, bottom, mTabPaint);
        mPath.reset();
        mPath.moveTo(tLeft, bottom);
        mPath.lineTo(middle, top);
        mPath.lineTo(tRight, bottom);
        mPath.close();
        canvas.drawPath(mPath, mTabPaint);
    }


    //----------------


    /**
     * Set the required spacing between title segments.
     *
     * @param spacingPixels Spacing between each title displayed in pixels
     */
    private void setTextSpacing(int spacingPixels) {
        if (spacingPixels < mMinTextSpacing) {
            spacingPixels = mMinTextSpacing;
        }
        mScaledTextSpacing = spacingPixels;
        requestLayout();
    }

    /**
     * @return The required spacing between title segments in pixels
     */
    private int getTextSpacing() {
        return mScaledTextSpacing;
    }

    /**
     * Set the alpha value used for non-primary page titles.
     *
     * @param alpha Opacity value in the range 0-1f
     */
    private void setNonPrimaryAlpha(@FloatRange(from = 0.0, to = 1.0) float alpha) {
        mNonPrimaryAlpha = (int) (alpha * 255) & 0xFF;
        final int transparentColor = (mNonPrimaryAlpha << 24) | (mTextColor & 0xFFFFFF);
        for (TextView textView : mTextViews) {
            if (isTextViewCurrent(textView)) {
                continue;
            }
            textView.setTextColor(transparentColor);
        }
    }

    private boolean isTextViewCurrent(TextView textView) {
        return (int) textView.getTag() == CURR;
    }

    /**
     * Set the color value used as the base color for all displayed page titles.
     * Alpha will be ignored for non-primary page titles. See {@link #setNonPrimaryAlpha(float)}.
     *
     * @param color Color hex code in 0xAARRGGBB format
     */
    public void setTextColor(@ColorInt int color) {
        mTextColor = color;
        final int transparentColor = (mNonPrimaryAlpha << 24) | (mTextColor & 0xFFFFFF);
        for (TextView textView : mTextViews) {
            if (isTextViewCurrent(textView)) {
                textView.setTextColor(color);
            } else {
                textView.setTextColor(transparentColor);
            }
        }
    }

    public void setTextColors(@ColorInt int selectedColor, @ColorInt int otherColor) {
        mTextColor = otherColor;
        for (TextView textView : mTextViews) {
            if (isTextViewCurrent(textView)) {
                textView.setTextColor(selectedColor);
            } else {
                textView.setTextColor(otherColor);
            }
        }
    }

    /**
     * Set the default text size to a given unit and value.
     * See {@link TypedValue} for the possible dimension units.
     * <p>
     * <p>Example: to set the text size to 14px, use
     * setTextSize(TypedValue.COMPLEX_UNIT_PX, 14);</p>
     *
     * @param unit The desired dimension unit
     * @param size The desired size in the given units
     */
    public void setTextSize(int unit, float size) {
        for (TextView textView : mTextViews) {
            textView.setTextSize(unit, size);
        }
    }

    public void setSelectedTitleBold(boolean isBold) {
        mTextViews[CURR].setTypeface(null, isBold ? Typeface.BOLD : Typeface.NORMAL);
    }

    public void setAllTitleBold(boolean isBold) {
        for (TextView textView : mTextViews) {
            textView.setTypeface(null, isBold ? Typeface.BOLD : Typeface.NORMAL);
        }
    }

    /**
     * Set the {@link Gravity} used to position text within the title strip.
     * Only the vertical gravity component is used.
     *
     * @param gravity {@link Gravity} constant for positioning title text
     */
    public void setGravity(int gravity) {
        mGravity = gravity;
        requestLayout();
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        final ViewParent parent = getParent();
        if (!(parent instanceof ViewPager)) {
            throw new IllegalStateException(
                    "PagerTitleStrip must be a direct child of a ViewPager.");
        }

        final ViewPager pager = (ViewPager) parent;
        final PagerAdapter adapter = pager.getAdapter();

        pager.setOnPageChangeListener(mPageListener);
        pager.addOnAdapterChangeListener(mPageListener);
        mPager = pager;
        updateAdapter(mWatchingAdapter != null ? mWatchingAdapter.get() : null, adapter);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mPager != null) {
            updateAdapter(mPager.getAdapter(), null);
            mPager.setOnPageChangeListener(null);
            mPager.removeOnAdapterChangeListener(mPageListener);
            mPager = null;
        }
    }

    private void updateText(int currentItem, PagerAdapter adapter) {
        final int itemCount = adapter != null ? adapter.getCount() : 0;
        mUpdatingText = true;

        CharSequence text = null;
        if (currentItem >= 2 && adapter != null) {
            text = adapter.getPageTitle(currentItem - 2);
        }
        mTextViews[0].setText(text);

        if (currentItem >= 1 && adapter != null) {
            text = adapter.getPageTitle(currentItem - 1);
        }
        mTextViews[1].setText(text);

        mTextViews[2].setText(adapter != null && currentItem < itemCount
                ? adapter.getPageTitle(currentItem) : null);

        text = null;
        if (currentItem + 1 < itemCount && adapter != null) {
            text = adapter.getPageTitle(currentItem + 1);
        }
        mTextViews[3].setText(text);

        if (currentItem + 2 < itemCount && adapter != null) {
            text = adapter.getPageTitle(currentItem + 2);
        }
        mTextViews[4].setText(text);

        // Measure everything
        final int width = getWidth() - getPaddingLeft() - getPaddingRight();
        final int maxWidth = Math.max(0, (int) (width * 0.8f));
        final int childWidthSpec = MeasureSpec.makeMeasureSpec(maxWidth, MeasureSpec.AT_MOST);
        final int childHeight = getHeight() - getPaddingTop() - getPaddingBottom();
        final int maxHeight = Math.max(0, childHeight);
        final int childHeightSpec = MeasureSpec.makeMeasureSpec(maxHeight, MeasureSpec.AT_MOST);

        for (TextView textView : mTextViews) {
            textView.measure(childWidthSpec, childHeightSpec);
        }

        mLastKnownCurrentPage = currentItem;

        if (!mUpdatingPositions) {
            updateTextPositions(currentItem, mLastKnownPositionOffset, false);
        }

        mUpdatingText = false;
    }

    @Override
    public void requestLayout() {
        if (!mUpdatingText) {
            super.requestLayout();
        }
    }

    private void updateAdapter(PagerAdapter oldAdapter, PagerAdapter newAdapter) {
        if (oldAdapter != null) {
            oldAdapter.unregisterDataSetObserver(mPageListener);
            mWatchingAdapter = null;
        }
        if (newAdapter != null) {
            newAdapter.registerDataSetObserver(mPageListener);
            mWatchingAdapter = new WeakReference<>(newAdapter);
        }
        if (mPager != null) {
            mLastKnownCurrentPage = -1;
            mLastKnownPositionOffset = -1;
            updateText(mPager.getCurrentItem(), newAdapter);
            requestLayout();
        }
    }

    private void updateTextPositions(int position, float positionOffset, boolean force) {
        if (position != mLastKnownCurrentPage) {
            updateText(position, mPager.getAdapter());
        } else if (!force && positionOffset == mLastKnownPositionOffset) {
            return;
        }

        //MyLog.d("Position: " + position + "   positionOffset: " + positionOffset);

        mUpdatingPositions = true;

        final int prevPrevWidth = mTextViews[PREV_PREV].getMeasuredWidth();
        final int prevWidth = mTextViews[PREV].getMeasuredWidth();
        final int currWidth = mTextViews[CURR].getMeasuredWidth();
        final int nextWidth = mTextViews[NEXT].getMeasuredWidth();
        final int nextNextWidth = mTextViews[NEXT_NEXT].getMeasuredWidth();
        final int halfCurrWidth = currWidth / 2;

        final int stripWidth = getWidth();
        final int stripHeight = getHeight();
        final int paddingLeft = getPaddingLeft();
        final int paddingRight = getPaddingRight();
        final int paddingTop = getPaddingTop();
        final int paddingBottom = getPaddingBottom();
        final int textPaddedLeft = paddingLeft + halfCurrWidth;
        final int textPaddedRight = paddingRight + halfCurrWidth;
        final int contentWidth = stripWidth - textPaddedLeft - textPaddedRight;

        float currOffset = positionOffset + 0.5f;
        if (currOffset > 1.f) {
            currOffset -= 1.f;
        }
        final int currCenter = stripWidth - textPaddedRight - (int) (contentWidth * currOffset);
        final int currLeft = Math.min(
                Math.max(currCenter - currWidth / 2, prevWidth + mScaledTextSpacing), stripWidth - nextWidth - currWidth - mScaledTextSpacing);
        final int currRight = currLeft + currWidth;


        final int prevPrevBaseline = mTextViews[PREV_PREV].getBaseline();
        final int prevBaseline = mTextViews[PREV].getBaseline();
        final int currBaseline = mTextViews[CURR].getBaseline();
        final int nextBaseline = mTextViews[NEXT].getBaseline();
        final int nextNextBaseline = mTextViews[NEXT_NEXT].getBaseline();

        //final int maxBaseline = Math.max(Math.max(prevBaseline, currBaseline), nextBaseline);
        final int maxBaseline = Math.max(currBaseline,
                Math.max(Math.max(prevPrevBaseline, prevBaseline), Math.max(nextBaseline, nextNextBaseline)));

        final int prevPrevTopOffset = maxBaseline - prevPrevBaseline;
        final int prevTopOffset = maxBaseline - prevBaseline;
        final int currTopOffset = maxBaseline - currBaseline;
        final int nextTopOffset = maxBaseline - nextBaseline;
        final int nextNextTopOffset = maxBaseline - nextNextBaseline;

        final int alignedPrevPrevHeight = prevPrevTopOffset + mTextViews[PREV_PREV].getMeasuredHeight();
        final int alignedPrevHeight = prevTopOffset + mTextViews[PREV].getMeasuredHeight();
        final int alignedCurrHeight = currTopOffset + mTextViews[CURR].getMeasuredHeight();
        final int alignedNextHeight = nextTopOffset + mTextViews[NEXT].getMeasuredHeight();
        final int alignedNextNextHeight = nextNextTopOffset + mTextViews[NEXT_NEXT].getMeasuredHeight();
        //final int maxTextHeight = Math.max(Math.max(alignedPrevHeight, alignedCurrHeight), alignedNextHeight);
        final int maxTextHeight = Math.max(alignedCurrHeight,
                Math.max(Math.max(alignedPrevPrevHeight, alignedPrevHeight), Math.max(alignedNextHeight, alignedNextNextHeight)));

        final int vGrav = mGravity & Gravity.VERTICAL_GRAVITY_MASK;

        int prevPrevTop;
        int prevTop;
        int currTop;
        int nextTop;
        int nextNextTop;
        switch (vGrav) {
            default:
            case Gravity.TOP:
                prevPrevTop = paddingTop + prevPrevTopOffset;
                prevTop = paddingTop + prevTopOffset;
                currTop = paddingTop + currTopOffset;
                nextTop = paddingTop + nextTopOffset;
                nextNextTop = paddingTop + nextNextTopOffset;
                break;
            case Gravity.CENTER_VERTICAL:
                final int paddedHeight = stripHeight - paddingTop - paddingBottom;
                final int centeredTop = (paddedHeight - maxTextHeight) / 2;
                prevPrevTop = centeredTop + prevPrevTopOffset;
                prevTop = centeredTop + prevTopOffset;
                currTop = centeredTop + currTopOffset;
                nextTop = centeredTop + nextTopOffset;
                nextNextTop = centeredTop + nextNextTopOffset;
                break;
            case Gravity.BOTTOM:
                final int bottomGravTop = stripHeight - paddingBottom - maxTextHeight;
                prevPrevTop = bottomGravTop + prevPrevTopOffset;
                prevTop = bottomGravTop + prevTopOffset;
                currTop = bottomGravTop + currTopOffset;
                nextTop = bottomGravTop + nextTopOffset;
                nextNextTop = bottomGravTop + nextNextTopOffset;
                break;
        }

        mTextViews[CURR].layout(currLeft, currTop, currRight, currTop + mTextViews[CURR].getMeasuredHeight());

        //final int prevLeft = Math.min(paddingLeft, currLeft - mScaledTextSpacing - prevWidth);
        final int prevLeft = Math.min(paddingLeft + prevPrevWidth + mScaledTextSpacing, currLeft - mScaledTextSpacing - prevWidth);
        mTextViews[PREV].layout(prevLeft, prevTop, prevLeft + prevWidth, prevTop + mTextViews[PREV].getMeasuredHeight());

        final int prevPrevLeft = Math.min(paddingLeft, prevLeft - mScaledTextSpacing - prevPrevWidth);
        mTextViews[PREV_PREV].layout(prevPrevLeft, prevPrevTop, prevPrevLeft + prevPrevWidth,
                prevPrevTop + mTextViews[PREV_PREV].getMeasuredHeight());

        //final int nextLeft = Math.max(stripWidth - paddingRight - nextWidth, currRight + mScaledTextSpacing);
        final int nextLeft = Math.max(stripWidth - paddingRight - nextNextWidth - mScaledTextSpacing - nextWidth, currRight + mScaledTextSpacing);
        mTextViews[NEXT].layout(nextLeft, nextTop, nextLeft + nextWidth, nextTop + mTextViews[NEXT].getMeasuredHeight());

        final int nextNextLeft = Math.max(stripWidth - paddingRight - nextNextWidth, nextLeft + nextWidth + mScaledTextSpacing);
        mTextViews[NEXT_NEXT].layout(nextNextLeft, nextNextTop, nextNextLeft + nextNextWidth,
                nextNextTop + mTextViews[NEXT_NEXT].getMeasuredHeight());

        mLastKnownPositionOffset = positionOffset;
        mUpdatingPositions = false;

        //---- Indicator
        mTabAlpha = (int) (Math.abs(positionOffset - 0.5f) * 2 * 0xFF);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        if (widthMode != MeasureSpec.EXACTLY) {
            throw new IllegalStateException("Must measure with an exact width");
        }

        final int heightPadding = getPaddingTop() + getPaddingBottom();
        final int childHeightSpec = getChildMeasureSpec(heightMeasureSpec, heightPadding, LayoutParams.WRAP_CONTENT);

        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        final int widthPadding = (int) (widthSize * 0.2f);
        final int childWidthSpec = getChildMeasureSpec(widthMeasureSpec, widthPadding, LayoutParams.WRAP_CONTENT);

        for (TextView textView : mTextViews) {
            textView.measure(childWidthSpec, childHeightSpec);
        }

        final int height;
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (heightMode == MeasureSpec.EXACTLY) {
            height = MeasureSpec.getSize(heightMeasureSpec);
        } else {
            final int textHeight = mTextViews[CURR].getMeasuredHeight();
            final int minHeight = getMinHeight();
            height = Math.max(minHeight, textHeight + heightPadding);
        }

        final int childState = mTextViews[CURR].getMeasuredState();
        final int measuredHeight = View.resolveSizeAndState(height, heightMeasureSpec,
                childState << View.MEASURED_HEIGHT_STATE_SHIFT);
        setMeasuredDimension(widthSize, measuredHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (mPager != null) {
            final float offset = mLastKnownPositionOffset >= 0 ? mLastKnownPositionOffset : 0;
            updateTextPositions(mLastKnownCurrentPage, offset, true);
        }
    }

    private int getMinHeight() {
        int minHeight = 0;
        final Drawable bg = getBackground();
        if (bg != null) {
            minHeight = bg.getIntrinsicHeight();
        }

        return Math.max(minHeight, mMinStripHeight);
    }

    private class ClickListener implements OnClickListener {
        @Override
        public void onClick(View view) {
            switch ((int) view.getTag()) {
                case PREV_PREV:
                    mPager.setCurrentItem(mPager.getCurrentItem() - 1);
                    mPager.setCurrentItem(mPager.getCurrentItem() - 1);
                    break;
                case PREV:
                    mPager.setCurrentItem(mPager.getCurrentItem() - 1);
                    break;
                case NEXT:
                    mPager.setCurrentItem(mPager.getCurrentItem() + 1);
                    break;
                case NEXT_NEXT:
                    mPager.setCurrentItem(mPager.getCurrentItem() + 1);
                    mPager.setCurrentItem(mPager.getCurrentItem() + 1);
                    break;

            }
        }
    }

    private class PageListener extends DataSetObserver implements ViewPager.OnPageChangeListener,
            ViewPager.OnAdapterChangeListener {
        private int mScrollState;

        PageListener() {
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (positionOffset > 0.5f) {
                // Consider ourselves to be on the next page when we're 50% of the way there.
                position++;
            }
            updateTextPositions(position, positionOffset, false);
        }

        @Override
        public void onPageSelected(int position) {
            if (mScrollState == ViewPager.SCROLL_STATE_IDLE) {
                // Only update the text here if we're not dragging or settling.
                updateText(mPager.getCurrentItem(), mPager.getAdapter());

                final float offset = mLastKnownPositionOffset >= 0 ? mLastKnownPositionOffset : 0;
                updateTextPositions(mPager.getCurrentItem(), offset, true);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            mScrollState = state;
        }

        @Override
        public void onAdapterChanged(@NonNull ViewPager viewPager, PagerAdapter oldAdapter,
                                     PagerAdapter newAdapter) {
            updateAdapter(oldAdapter, newAdapter);
        }

        @Override
        public void onChanged() {
            updateText(mPager.getCurrentItem(), mPager.getAdapter());

            final float offset = mLastKnownPositionOffset >= 0 ? mLastKnownPositionOffset : 0;
            updateTextPositions(mPager.getCurrentItem(), offset, true);
        }
    }
}
