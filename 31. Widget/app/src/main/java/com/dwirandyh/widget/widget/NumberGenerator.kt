package com.dwirandyh.widget.widget

import java.util.*

object NumberGenerator {

    fun generate(max: Int): Int {
        val random = Random()
        return random.nextInt(max)
    }
}