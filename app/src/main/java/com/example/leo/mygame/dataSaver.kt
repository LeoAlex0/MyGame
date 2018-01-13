package com.example.leo.mygame

import android.content.Context
import android.content.Context.MODE_APPEND
import java.io.PrintStream
import java.util.*


/**
 * 用于保存和读取数据
 */
object dataSaver {
    private val filename = "scoreHistory"

    fun saveScore(context: Context, score: Int, time: String) {
        val out = PrintStream(context.openFileOutput(filename, MODE_APPEND))
        out.println(score)
        out.println(time)
        out.flush()
        out.close()
    }

    fun readScore(context: Context): ArrayList<Pair<Int, String>> {
        val ret = arrayListOf<Pair<Int, String>>()
        val scanner = Scanner(context.openFileInput(filename))
        while (scanner.hasNext()) {
            val score = scanner.nextInt()
            ret.add(Pair(score, scanner.next()))
        }
        return ret
    }
}