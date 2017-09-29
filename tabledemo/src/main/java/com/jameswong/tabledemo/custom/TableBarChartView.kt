package com.jameswong.tabledemo.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

/**
 * ****************************************************
 * author: jameswong
 * created on: 17/09/19 下午3:34
 * e-mail: PassionateWsj@outlook.com
 * name:
 * desc:
 * ****************************************************
 */
class TableBarChartView : View {

    private var min: Float = 0.toFloat()
    private var max: Float = 0.toFloat()
    private var current: Float = 0.toFloat()
    private var mWidth: Float = 0.toFloat()
    private var color: String = "#6699ff"

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    override fun onDraw(canvas: Canvas) {
        if (min == max && 0f == max)
            return
        // 创建画笔
        val p = Paint()
        p.color = Color.parseColor(color)
        when {
            min > 0 || min == 0f -> {
                mWidth = measuredWidth / max
                canvas.drawRect(0f, 0f, mWidth * current, measuredHeight.toFloat(), p)
            }
            max < 0 || max == 0f -> {
                mWidth = measuredWidth / min
                canvas.drawRect(measuredWidth.toFloat() - mWidth * current, 0f, measuredWidth.toFloat(), measuredHeight.toFloat(), p)
            }
            else -> {
                val dividerLineWidth = 3f
                mWidth = (measuredWidth - dividerLineWidth) / (max - min)
                val p1 = Paint()
                p1.color = Color.parseColor("#ffffff")
                canvas.drawRect(mWidth * -min, 0f, mWidth * -min + dividerLineWidth, measuredHeight.toFloat(), p1)
                if (current < 0) {
                    p.color = Color.parseColor("#91c941")
                    canvas.drawRect(mWidth * (-min + current), 0f, mWidth * (-min), measuredHeight.toFloat(), p)
                } else if (current > 0) {
                    canvas.drawRect(mWidth * -min + dividerLineWidth, 0f, mWidth * (-min + current) + dividerLineWidth, measuredHeight.toFloat(), p)
                }
            }
        }

    }

    fun setData(min: Float, max: Float, current: Float, color: String) {
        this.min = min
        this.max = max
        this.current = current
        this.color = color
        this.invalidate()
    }

    fun setData(min: Float, max: Float, current: Float) {
        setData(min, max, current, "#6699ff")
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return super.onTouchEvent(event)
    }
}
