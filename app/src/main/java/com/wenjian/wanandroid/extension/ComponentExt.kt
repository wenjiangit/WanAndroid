package com.wenjian.wanandroid.extension

import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.wenjian.wanandroid.R

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


fun AppCompatActivity.setupActionBar(@DrawableRes resId: Int = -1,
                                     title: String = getString(R.string.app_name),
                                     listener: () -> Unit = {}) {
    findViewById<Toolbar>(R.id.toolBar)?.let {
        setSupportActionBar(it)

        it.setNavigationOnClickListener {
            listener()
        }

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeButtonEnabled(true)
            if (resId != -1) {
                it.setHomeAsUpIndicator(resId)
            }
            supportActionBar?.title = title
        }


    }
}