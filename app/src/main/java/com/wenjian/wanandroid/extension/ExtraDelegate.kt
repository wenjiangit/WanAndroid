package com.wenjian.wanandroid.extension

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.wenjian.wanandroid.di.Injector
import kotlin.reflect.KProperty

/**
 * Description: ExtraDelegate
 * Date: 2018/9/7
 *
 * @author jian.wen@ubtrobot.com
 */
class ExtraDelegate<T>(private val key: String, private val default: T) {

    private var extra: T? = null

    @Suppress("UNCHECKED_CAST")
    operator fun getValue(thisRef: AppCompatActivity, property: KProperty<*>): T {
        val temp = extra ?: thisRef.intent?.extras?.get(key) as T?
        return temp ?: default
    }

    @Suppress("UNCHECKED_CAST")
    operator fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        val temp = extra ?: thisRef.arguments?.get(key) as T?
        return temp ?: default
    }

}

fun <T> extraDelegate(key: String, default: T? = null) = ExtraDelegate(key, default)


class ViewModelDelegate<T : ViewModel>(private val factory: ViewModelProvider.Factory? = null,
                                       private val clz: Class<T>) {
    operator fun getValue(thisRef: Fragment, property: KProperty<*>): T{
        Log.i("wj", "getValue")
        factory?.run {
            ViewModelProviders.of(thisRef, factory).get(clz)
        }
        return ViewModelProviders.of(thisRef).get(clz)
    }

    operator fun getValue(thisRef: AppCompatActivity, property: KProperty<*>): T {
        factory?.run {
            ViewModelProviders.of(thisRef, factory).get(clz)
        }
        return ViewModelProviders.of(thisRef).get(clz)
    }
}


fun <T : ViewModel> viewModelDelegate(factory: ViewModelProvider.Factory?, clz: Class<T>) = ViewModelDelegate(factory, clz)


fun <T : ViewModel> apiModelDelegate(clz: Class<T>) = viewModelDelegate(Injector.provideApiModelFactory(), clz)
