package com.example.leo.mygame

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.GridLayout
import kotlin.math.abs


class CardGroup @JvmOverloads constructor(mContext: Context, attr: AttributeSet? = null, defStyleAttr: Int = 0) :
        GridLayout(mContext, attr, defStyleAttr) {

    /**保存卡片的二维数组*/
    private lateinit var cardItems: Array<Array<Card>>
    /**手势回调*/
    private val myGestureListener = object : GestureDetector.SimpleOnGestureListener() {

        /**最小移动阈值*/
        private val minDistance = 15

        override fun onFling(p0: MotionEvent, p1: MotionEvent, p2: Float, p3: Float): Boolean {
            if (!onStart) return true
            val dx = p1.x - p0.x
            val dy = p1.y - p0.y
            if (abs(dx) > abs(dy) && abs(dx) > minDistance) {
                if (dx > 0) doAction(Action.RIGHT)
                else doAction(Action.LEFT)
            } else if (abs(dy) > abs(dx) && abs(dy) > minDistance) {
                if (dy > 0) doAction(Action.DOWN)
                else doAction(Action.UP)
            }
            refreshDrawableState()
            return true
        }
    }
    /**手势探测器*/
    private val gesture = GestureDetector(context, myGestureListener)

    init {
        isClickable = true
        setOnTouchListener { _, e -> gesture.onTouchEvent(e) }
    }

    lateinit var gameControlModule: GameControlListener

    private var onStart = false

    private var score = 0

    /**滑动的方向*/
    enum class Action { LEFT, RIGHT, UP, DOWN }

    /** 对于子[Card]的添加*/
    @SuppressLint("DrawAllocation")
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (isLaidOut) return

        cardItems = Array(4) { i ->
            Array(4) { j ->
                getChildAt(i * 4 + j).apply {
                    setOnTouchListener { _, event -> gesture.onTouchEvent(event) }
                } as Card
            }
        }
    }


    fun startGame() {
        cardItems.forEach { r -> r.forEach { it.number = 0 } }
        score = 0
        4 * { genNext() }
        onStart = true
    }

    /**
     * 按照滑动方向移动并合并数字
     * 注意：阻塞
     * @param action 滑动方向(上下左右)
     * */
    fun doAction(action: Action) {
        val posCol = 0 until rowCount
        val negCol = rowCount - 1 downTo 0
        fun mergeEachColumn(rowOrd: IntProgression) {
            posCol.forEach { column ->
                var lastNul = -1
                rowOrd.forEach { row ->
                    var cur = row
                    if (cardItems[row][column].number == 0) {
                        if (lastNul == -1) lastNul = row
                    } else if (lastNul != -1) {
                        //交换
                        cardItems[lastNul][column].number = cardItems[row][column].number
                        cardItems[row][column].number = 0
                        cur = lastNul
                        lastNul += rowOrd.step

                    }
                    //合并
                    if (cur in rowOrd && cur - rowOrd.step in rowOrd &&
                            cardItems[cur][column].number != 0 &&
                            cardItems[cur][column].number == cardItems[cur - rowOrd.step][column].number) {
                        score += 1 shl cardItems[cur][column].number
                        cardItems[cur - rowOrd.step][column].number++
                        cardItems[cur][column].number = 0
                        lastNul = cur
                    }
                }
            }
        }

        fun mergeEachRow(columnOrd: IntProgression) {
            posCol.forEach { row ->
                var lastNul = -1
                columnOrd.forEach { column ->
                    var cur = column
                    if (cardItems[row][column].number == 0) {
                        if (lastNul == -1) lastNul = column
                    } else if (lastNul != -1) {
                        //交换
                        cardItems[row][lastNul].number = cardItems[row][column].number
                        cardItems[row][column].number = 0
                        cur = lastNul
                        lastNul += columnOrd.step

                    }
                    //合并
                    if (cur in columnOrd && cur - columnOrd.step in columnOrd &&
                            cardItems[row][cur].number != 0 &&
                            cardItems[row][cur].number == cardItems[row][cur - columnOrd.step].number) {
                        score += 1 shl cardItems[row][cur].number
                        cardItems[row][cur - columnOrd.step].number++
                        cardItems[row][cur].number = 0
                        lastNul = cur
                    }
                }
            }
        }
        when (action) {
            Action.UP -> mergeEachColumn(posCol)
            Action.DOWN -> mergeEachColumn(negCol)
            Action.LEFT -> mergeEachRow(posCol)
            Action.RIGHT -> mergeEachRow(negCol)
        }
        if (!genNext()) {
            gameControlModule.onGameOver(score)
            onStart = false
        }
    }

    /**
     *生成下一个数字
     * @return true:生成成功
     * @return false:盘面已满，生成失败
     */
    private fun genNext(): Boolean {
        val genList = cardItems.map { it.filter { it.number == 0 } }.reduce { acc, list -> acc + list }
        if (genList.isEmpty()) return false
        val toAdd = 1 randomTo 2
        genList[0 randomUntil genList.size].number = toAdd
        gameControlModule.onScoreChangeListener(score)
        return true
    }

    interface GameControlListener {
        /**游戏结束时调用 */
        fun onGameOver(score: Int)

        /**分数改变时的回调*/
        fun onScoreChangeListener(score: Int)
    }
}
