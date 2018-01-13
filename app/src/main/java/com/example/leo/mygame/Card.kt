package com.example.leo.mygame

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.widget.GridView

class Card @JvmOverloads constructor(mContext: Context, attr: AttributeSet? = null, defStyleAttr: Int = 0) :
        GridView(mContext, attr, defStyleAttr) {

    private val paint = Paint().apply { textSize = 50f }
    private val textRect = Rect()

    /**
     * 数字代表阶数:
     * 0->0,1->2,2->4,3->8,etc.
     */
    var number: Int = 0
        set(value) {
            field = value
            val realNum = 1 shl value
            paint.getTextBounds(realNum.toString(), 0, realNum.toString().length, textRect)
            invalidate()
        }

    companion object {
        /**
         * 每一阶的配色，第0阶背景，懒得扒图先做这么多 TODO:去扒更多的配色
         */
        private val colors by lazy {
            arrayOf(
                    "#CCC0B3", "#EEE4DA", "#EDE0C8", "#F2B179", "#F49563", "#F5794D",
                    "#F55D37", "#EEE863", "#EDB04D", "#ECB04D", "#EB9437", "#EA7821")
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        /**设置背景色,顺便要是某位大佬玩出我设置的背景色就给一个默认色[#EA7821]*/
        paint.color = Color.parseColor(try {
            colors[number]
        } catch (e: IndexOutOfBoundsException) {
            "#EA7821"
        })

        paint.style = Paint.Style.FILL
        canvas.drawRect(0f, 0f, measuredWidth.toFloat(), measuredHeight.toFloat(), paint)

        /**画字了*/
        if (number != 0) {
            paint.color = Color.BLACK
            val x = (measuredWidth - textRect.width()) / 2
            val y = (measuredHeight + textRect.height()) / 2
            canvas.drawText((1 shl number).toString(), x.toFloat(), y.toFloat(), paint)
        }
    }

    override fun toString() = number.toString()
}