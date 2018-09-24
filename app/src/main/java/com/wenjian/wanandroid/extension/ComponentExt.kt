package com.wenjian.wanandroid.extension

import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.wenjian.wanandroid.R
import com.wenjian.wanandroid.utils.Tools
import org.jetbrains.anko.contentView


/**
 * Description ${name}
 *
 * Date 2018/9/8
 * @author wenjianes@163.com
 */


fun Fragment.setupToolBar(@StringRes resId: Int = -1, title: String = "") {
    view?.findViewById<Toolbar>(R.id.toolBar)?.let {
        it.setLogo(R.drawable.ic_menu)
        if (resId != -1) {
            it.title = context?.getString(resId)
        } else {
            it.title = title
        }
    }
}

/**
 * 设置状态栏颜色
 */
fun AppCompatActivity.setSystemBarColor(@ColorRes resId: Int = -1, @ColorInt colorInt: Int = -1) {
    Tools.setSystemBarColor(this, resId, colorInt)
}

fun Fragment.snak(text: CharSequence) {
    Snackbar.make(this.view!!, text, Snackbar.LENGTH_SHORT).show()
}


fun AppCompatActivity.snak(text: CharSequence) {
    Snackbar.make(this.contentView!!, text, Snackbar.LENGTH_SHORT).show()
}

fun AppCompatActivity.setupActionBar(@DrawableRes resId: Int = -1,
                                     title: String? = "",
                                     show: Boolean = true,
                                     listener: () -> Unit = { finish() }) {


    findViewById<Toolbar>(R.id.toolBar)!!.let { it ->
        setSupportActionBar(it)
        supportActionBar?.title = title
        if (!show) {
            return
        }
        it.setNavigationOnClickListener {
            listener()
        }
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeButtonEnabled(true)
            if (resId != -1) {
                it.setHomeAsUpIndicator(resId)
            }
        }
    }
}
