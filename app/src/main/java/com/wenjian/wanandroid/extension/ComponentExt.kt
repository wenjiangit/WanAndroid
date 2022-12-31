package com.wenjian.wanandroid.extension

import android.content.Intent
import android.os.Build
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.gson.reflect.TypeToken
import com.wenjian.wanandroid.R
import com.wenjian.wanandroid.utils.Tools
import org.jetbrains.anko.contentView


/**
 * Description ${name}
 *
 * Date 2018/9/8
 * @author wenjianes@163.com
 */


fun androidx.fragment.app.Fragment.setupToolBar(@StringRes resId: Int = -1, title: String = "") {
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
fun AppCompatActivity.setSystemBarColor(@ColorInt colorInt: Int = -1) {
    Tools.setSystemBarColor(this, colorInt)
}

/**
 * 设置透明状态栏,并将toolbar扩展到状态栏
 */
fun AppCompatActivity.translucentStatusBar() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        Tools.translucent(this)
        findViewById<Toolbar>(R.id.toolBar)?.let {
            Tools.initFullBar(it, this)
        }
    }
}

fun androidx.fragment.app.Fragment.snak(text: CharSequence) {
    com.google.android.material.snackbar.Snackbar.make(this.view!!, text, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).show()
}


fun AppCompatActivity.snak(text: CharSequence) {
    com.google.android.material.snackbar.Snackbar.make(this.contentView!!, text, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).show()
}

fun AppCompatActivity.setupActionBar(@DrawableRes resId: Int = -1,
                                     title: String? = getString(R.string.app_name),
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

fun AppCompatActivity.launch(clz: Class<*>) {
    startActivity(Intent(this, clz))
}

fun androidx.fragment.app.Fragment.launch(clz: Class<*>) {
    startActivity(Intent(context, clz))
}

inline fun <reified T> genericType() = object : TypeToken<T>() {}.type