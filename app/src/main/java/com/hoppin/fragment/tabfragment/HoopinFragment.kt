package com.hoppin.fragment.tabfragment


import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.hoppin.R
import com.hoppin.activity.ui.tabactivity.TabActivity
import com.hoppin.base.BaseFragment
import com.hoppin.viewpager.HoopViewPager
import kotlinx.android.synthetic.main.fragment_hoopin.*


class HoopinFragment : BaseFragment() {

    private var indicatorWidth: Int = 0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hoopin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as TabActivity).updateStatusBarColor("#ffffff")
        inItView()
        tablayoutview()
    }

    fun inItView() {
        val viewpager = HoopViewPager(childFragmentManager)
        viewPager.setAdapter(viewpager)
        tabLayout.setupWithViewPager(viewPager)
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }

    fun tablayoutview() {
        tabLayout.post {
            indicatorWidth = tabLayout.width / tabLayout.tabCount
            //Assign new width
            val indicatorParams = indicator.getLayoutParams() as FrameLayout.LayoutParams
            indicatorParams.width = indicatorWidth
            indicator.setLayoutParams(indicatorParams)
        }

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrolled(i: Int, positionOffset: Float, positionOffsetPx: Int) {
                val params = indicator.getLayoutParams() as FrameLayout.LayoutParams
                //Multiply positionOffset with indicatorWidth to get translation
                val translationOffset = (positionOffset + i) * indicatorWidth
                params.leftMargin = translationOffset.toInt()
                params.rightMargin = translationOffset.toInt()
                indicator.setLayoutParams(params)
            }
            override fun onPageSelected(i: Int) {}
            override fun onPageScrollStateChanged(i: Int) {}
        })
    }

}
