package com.wenjian.wanandroid.extension

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
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
    operator fun getValue(thisRef: androidx.fragment.app.Fragment, property: KProperty<*>): T {
        val temp = extra ?: thisRef.arguments?.get(key) as T?
        return temp ?: default
    }

}

fun <T> extraDelegate(key: String, default: T? = null) = ExtraDelegate(key, default)


class ViewModelDelegate<T : ViewModel>(private val factory: ViewModelProvider.Factory? = null,
                                       private val clz: Class<T>) {

    private var model: T? = null

    operator fun getValue(thisRef: androidx.fragment.app.Fragment, property: KProperty<*>): T {
        if (model == null) {
            model = if (factory != null) {
                ViewModelProviders.of(thisRef, factory).get(clz)
            } else {
                ViewModelProviders.of(thisRef).get(clz)
            }
        }
        return model!!
    }

    operator fun getValue(thisRef: AppCompatActivity, property: KProperty<*>): T {
        if (model == null) {
            model = if (factory != null) {
                ViewModelProviders.of(thisRef, factory).get(clz)
            } else {
                ViewModelProviders.of(thisRef).get(clz)
            }
        }
        return model!!
    }
}


fun <T : ViewModel> viewModelDelegate(factory: ViewModelProvider.Factory?, clz: Class<T>) = ViewModelDelegate(factory, clz)


fun <T : ViewModel> apiModelDelegate(clz: Class<T>) = viewModelDelegate(Injector.provideApiModelFactory(), clz)
