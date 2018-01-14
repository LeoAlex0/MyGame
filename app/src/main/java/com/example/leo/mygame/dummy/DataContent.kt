package com.example.leo.mygame.dummy

import java.util.*

object DataContent {

    val items: MutableList<DummyItem> = ArrayList()

    data class DummyItem(val id: String, val content: String) {

        override fun toString(): String {
            return content
        }
    }
}
