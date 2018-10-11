package com.wenjian.wanandroid.extension

import android.content.Context
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat
import com.wenjian.wanandroid.helper.ThemeHelper

/**
 * Description ${name}
 *
 * Date 2018/10/11
 * @author wenjianes@163.com
 */
fun Context.getCompatColor(@ColorRes resId: Int) = ContextCompat.getColor(this, resId)


fun Context.getColorPrimary() = ThemeHelper.getColorPrimary(this)
