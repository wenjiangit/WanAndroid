package com.wenjian.wanandroid.utils

import android.content.Context
import android.net.ConnectivityManager


/**
 * Description: NetUtil
 * Date: 2018/9/10
 *
 * @author jian.wen@ubtrobot.com
 */
object NetUtil {

    fun isNetworkAvailable(context: Context): Boolean {
        val manager = context.getSystemService(
                        Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = manager.activeNetworkInfo
        return !(networkInfo == null || !networkInfo.isAvailable)
    }

}