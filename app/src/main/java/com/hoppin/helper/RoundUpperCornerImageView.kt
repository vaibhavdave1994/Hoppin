package com.hoppin.helper

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet

/**
 * Created by Ravi Birla on 15,July,2019
 */
class RoundUpperCornerImageView : android.support.v7.widget.AppCompatImageView {

    private val radius = 50.0f
    private var path: Path? = null
    private var rect: RectF? = null
    internal var topLeft = false
    internal var topRight = false
    internal var bottomRight = true
    internal var bottomLeft = true
    internal val radii = FloatArray(8)

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    private fun init() {
        path = Path()

        if (topLeft) {
            radii[0] = radius
            radii[1] = radius
        }

        if (topRight) {
            radii[2] = radius
            radii[3] = radius
        }

        if (bottomRight) {
            radii[4] = radius
            radii[5] = radius
        }

        if (bottomLeft) {
            radii[6] = radius
            radii[7] = radius
        }

    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        rect = RectF(0f, 0f, this.width.toFloat(), this.height.toFloat())
        path!!.addRoundRect(RectF(0f, 0f, width.toFloat(), height.toFloat()),
                radii, Path.Direction.CW)
        canvas.clipPath(path!!)
        super.onDraw(canvas)
    }
}