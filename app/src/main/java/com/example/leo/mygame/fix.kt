package com.example.leo.mygame

/**
 * 用于添加一些常用的 kit
 */

/**
 * @return 随机数，位于 this [until] [other] 上
 */
infix fun Int.randomUntil(other: Int) =
        if (other > this) (Math.random() * (other - this)).toInt() + this
        else throw IllegalArgumentException("Range Illegal of ($this until $other)")

/**
 * @return 随机数，位于 this [to] [other] 上
 */
infix fun Int.randomTo(other: Int) = this randomUntil (other + 1)

/**
 * 简单的重复循环
 * */
inline infix operator fun Int.times(blk: () -> Any) = (1..this).forEach { blk() }

lateinit var time_hint: String
lateinit var score_hint: String
/**两种简单的输出格式*/
fun Int.toScore() = "$score_hint$this"

fun Int.toTime() = "$time_hint${this / 3600}:${this / 60 % 60}:${this % 60}"

