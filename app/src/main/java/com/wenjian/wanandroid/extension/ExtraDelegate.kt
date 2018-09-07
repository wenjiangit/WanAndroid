package com.wenjian.wanandroid.extension

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import kotlin.reflect.KProperty

/**
 * Description: ExtraDelegate
 * Date: 2018/9/7
 *
 * @author jian.wen@ubtrobot.com
 */
class ExtraDelegate<T>(private val key: String, private val default: T) {

    private var extra: T? = null

    operator fun getValue(thisRef: AppCompatActivity, property: KProperty<*>): T {
        extra = thisRef.intent.extras.get(key) as T?
        return extra ?: default
    }

    operator fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        extra = thisRef.arguments?.get(key) as T?
        return extra ?: default
    }

}

fun <T> extraDelegate(key: String, default: T? = null) = ExtraDelegate(key, default)
