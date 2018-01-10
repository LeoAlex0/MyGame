package com.example.leo.mygame

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.GridLayout

class CardGroup(mContext: Context, attr: AttributeSet? = null, defStyleAttr: Int = 0) :
        GridLayout(mContext, attr, defStyleAttr) {

    /**保存卡片的二维数组*/
    private val cardItems by lazy { Array(4) { Array(4) { Card(context) } } }

    /**默认4x4*/
    private val childSquare = 4

    /**添加子[Card]时公用的参数*/
    private val layoutParam by lazy { LayoutParams() }

    /**滑动的方向*/
    enum class Action { LEFT, RIGHT, UP, DOWN }

    fun Action.toInt() = when (this) {
        Action.LEFT -> 0
        Action.RIGHT -> 1
        Action.UP -> 2
        Action.DOWN -> 3
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (isLaidOut) return

        (1..childSquare).forEach { i ->
            (1..childSquare).forEach { j ->
                layoutParam.rowSpec = spec(i - 1)
                layoutParam.columnSpec = spec(j - 1)

                if (j != childSquare) layoutParam.rightMargin = 10
                if (i != childSquare) layoutParam.topMargin = 10
                layoutParam.setGravity(Gravity.FILL)
                addView(cardItems[i][j], layoutParams)
            }
        }
    }

    //TODO: this
    fun doAction(action: Action) {
        val directionsRow = arrayOf(-1, 1, 0, 0)
        val directionColumn = arrayOf(0, 0 - 1, 1)

        (0 until childSquare).forEach { i ->

        }
    }
}