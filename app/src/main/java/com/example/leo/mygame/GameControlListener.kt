package com.example.leo.mygame

interface GameControlListener {
    /**游戏结束时调用 */
    fun onGameOver(score: Int)

    /**分数改变时的回调*/
    fun onScoreChangeListener(score: Int)
}