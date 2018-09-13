package com.wenjian.wanandroid.extension

import android.annotation.SuppressLint
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
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
                                     title: String? = getString(R.string.app_name),
                                     show: Boolean = true,
                                     listener: () -> Unit = { finish() }) {


    findViewById<Toolbar>(R.id.toolBar)?.let {
        setSupportActionBar(it)
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
            supportActionBar?.title = title
        }

    }
}

@SuppressLint("RestrictedApi")
fun BottomNavigationView.disableShiftMode() {
    val menuView = getChildAt(0) as BottomNavigationMenuView
    try {
        val shiftingMode = menuView::class.java.getDeclaredField("mShiftingMode")
        shiftingMode.isAccessible = true
        shiftingMode.setBoolean(menuView, false)
        shiftingMode.isAccessible = false
        for (i in 0 until menuView.childCount) {
            val item = menuView.getChildAt(i) as BottomNavigationItemView
            item.setShiftingMode(false)
            // set once again checked value, so view will be updated

            val shiftAmount = item::class.java.getDeclaredField("mShiftAmount")
            shiftAmount.isAccessible = true
            shiftAmount.setInt(item, 0)
            shiftAmount.isAccessible = false

            item.setChecked(item.itemData.isChecked)
        }
    } catch (e: NoSuchFieldException) {
        Log.e("wj", "Unable to get shift mode field", e)
    } catch (e: IllegalStateException) {
        Log.e("wj", "Unable to change value of shift mode", e)
    }
}