package com.wenjian.wanandroid.ui.web

import android.content.Context
import android.content.Intent

/**
 * Description ${name}
 *
 * Date 2018/12/1
 * @author wenjianes@163.com
 */

object WebClient{

    var isWebViewProcess: Boolean? = null

    fun isInWebViewProcess(context: Context): Boolean {
        return false
    }


    fun preLoad(context: Context){
        context.startService(Intent(context,WebService::class.java))
    }
}