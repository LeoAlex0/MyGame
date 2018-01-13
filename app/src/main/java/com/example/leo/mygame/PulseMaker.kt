package com.example.leo.mygame

import android.os.Handler
import android.os.Message
import kotlin.concurrent.thread

object PulseMaker {
    private val threadSet = mutableSetOf<Thread>()

    /**
     * 脉冲发生线程，每隔一定的时间就调用一次blk函数
     * @param milliseconds:脉冲间隔的毫秒数
     */
    fun newPulse(milliseconds: Long, handler: Handler) = thread {
        try {
            var times = 1
            while (true) {
                Thread.sleep(milliseconds)
                handler.sendMessage(Message().apply { arg1 = times++ })
            }
        } catch (e: InterruptedException) {
            synchronized(threadSet) {
                threadSet.remove(Thread.currentThread())
            }
        }
    }.apply {
        synchronized(threadSet) {
            threadSet.add(this)
        }
    }

}