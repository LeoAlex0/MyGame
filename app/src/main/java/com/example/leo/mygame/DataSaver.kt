package com.example.leo.mygame

import android.content.Context
import android.content.Context.MODE_APPEND
import com.example.leo.mygame.dummy.DataContent
import java.io.IOException
import java.io.PrintStream
import java.util.*


/**
 * 用于保存和读取数据
 */
object DataSaver {
    private const val filename = "scoreHistory"

    fun saveScore(context: Context, score: Int, time: Int) {
        val outStream = PrintStream(context.openFileOutput(filename, MODE_APPEND))
        outStream.println("$score $time")
        outStream.close()
        DataContent.items.add(DataContent.DummyItem(time.toTime(),score.toScore()))
    }

    fun readScore(context: Context): List<Pair<Int, Int>> = try {
        val scanner = Scanner(context.openFileInput(filename))
        scanner.asSequence().map(String::toInt).chunked(2).map { (s, t) -> Pair(s, t) }.toList()
    } catch (e: IOException) {
        emptyList()
    }
}