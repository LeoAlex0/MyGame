package com.example.leo.mygame.dummy

import java.util.*

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    val ITEMS: MutableList<DummyItem> = ArrayList()

    /**
     * A dummy item representing a piece of content.
     */
    data class DummyItem(val id: String, val content: String) {

        override fun toString(): String {
            return content
        }
    }
}
