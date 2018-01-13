package com.example.leo.mygame

import android.content.Context
import android.content.Context.MODE_APPEND
import java.io.IOException
import java.io.PrintStream
import java.util.*


/**
 * 用于保存和读取数据
 */
object DataSaver {
    private val filename = "scoreHistory"

    fun saveScore(context: Context, score: Int, time: String) {
        val outStream = PrintStream(context.openFileOutput(filename, MODE_APPEND))
        outStream.println("$score $time")
        outStream.flush()
        outStream.close()
    }

    fun readScore(context: Context): ArrayList<Pair<Int, String>> {
        val ret = arrayListOf<Pair<Int, String>>()
        try {
            val scanner = Scanner(context.openFileInput(filename))
            while (scanner.hasNext()) {
                val score = scanner.nextInt()
                ret.add(Pair(score, scanner.nextLine()))
            }
        } catch (e: IOException) {
        }
        return ret
    }
}