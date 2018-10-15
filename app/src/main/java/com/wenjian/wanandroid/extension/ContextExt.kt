package com.wenjian.wanandroid.extension

import android.content.Context
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat
import android.widget.Toast
import com.wenjian.wanandroid.R
import com.wenjian.wanandroid.helper.ThemeHelper
import es.dmoral.toasty.Toasty

/**
 * Description ${name}
 *
 * Date 2018/10/11
 * @author wenjianes@163.com
 */
fun Context.getCompatColor(@ColorRes resId: Int) = ContextCompat.getColor(this, resId)

fun Context.getColorPrimary() = ThemeHelper.obtainColorAttrValue(this, android.R.attr.colorPrimary, R.color.colorPrimary)

fun Context.getColorAccent() = ThemeHelper.obtainColorAttrValue(this, android.R.attr.colorAccent, R.color.colorAccent)

fun Context.toastInfo(msg: String) {
    Toasty.info(this, msg, Toast.LENGTH_SHORT, true).show()
}

fun Context.toastWarning(msg: String) {
    Toasty.warning(this, msg, Toast.LENGTH_SHORT, true).show()
}

fun Context.toastError(msg: String) {
    Toasty.error(this, msg, Toast.LENGTH_SHORT, false).show()
}

fun Context.toastSuccess(msg: String) {
    Toasty.success(this, msg, Toast.LENGTH_SHORT, true).show()
}


