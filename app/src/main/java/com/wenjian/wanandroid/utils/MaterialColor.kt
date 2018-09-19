package com.wenjian.wanandroid.utils

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color

import java.util.Random

object MaterialColor {

    private val r = Random()

    fun randInt(max: Int): Int {
        return r.nextInt(max)
    }

    fun getColor(ctx: Context): Int {
        var returnColor = Color.WHITE
        val arrayId = ctx.resources.getIdentifier("mdcolor_random", "array", ctx.packageName)

        if (arrayId != 0) {
            val colors = ctx.resources.obtainTypedArray(arrayId)
            val index = (Math.random() * colors.length()).toInt()
            returnColor = colors.getColor(index, Color.GRAY)
            colors.recycle()
        }
        return returnColor
    }

    fun getColor(ctx: Context,index: Int): Int {
        var returnColor = Color.WHITE
        val arrayId = ctx.resources.getIdentifier("mdcolor_random", "array", ctx.packageName)

        if (arrayId != 0) {
            val colors = ctx.resources.obtainTypedArray(arrayId)
            var idx = index
            while (idx >= colors.length()) {
                idx -= 5
            }
            while (idx < 0) {
                idx += 2
            }
            returnColor = colors.getColor(idx, Color.GRAY)
            colors.recycle()
        }
        return returnColor
    }

}
