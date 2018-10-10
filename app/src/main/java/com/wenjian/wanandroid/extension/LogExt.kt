package com.wenjian.wanandroid.extension

import android.util.Log

/**
 * Description: LogExt
 * Date: 2018/10/10
 *
 * @author jian.wen@ubtrobot.com
 */
fun Any.logE(msg: String, e: Throwable? = null) = Log.e(this::class.java.simpleName, msg, e)

fun Any.logI(msg: String) = Log.i(this::class.java.simpleName, msg)

fun Any.logD(msg: String) = Log.d(this::class.java.simpleName, msg)

