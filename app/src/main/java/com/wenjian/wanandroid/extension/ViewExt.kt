package com.wenjian.wanandroid.extension

import android.annotation.SuppressLint
import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import android.util.Log

/**
 * Description: ViewExt
 * Date: 2018/9/18
 *
 * @author jian.wen@ubtrobot.com
 */

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
