package com.wenjian.wanandroid.ui.web

import android.app.IntentService
import android.content.Context
import android.content.Intent
import com.squareup.leakcanary.internal.LeakCanaryInternals
import com.wenjian.wanandroid.extension.logI

/**
 * Description ${name}
 *
 * Date 2018/12/1
 * @author wenjianes@163.com
 */
class WebService : IntentService("WebService") {


    companion object {

        var isWebViewProcess: Boolean? = null

        fun isInWebViewProcess(context: Context): Boolean {

            val webViewProcess = isWebViewProcess
            if (webViewProcess == null) {
                isWebViewProcess = LeakCanaryInternals.isInServiceProcess(context,WebService::class.java)
            }
            return isWebViewProcess!!
        }


        fun preLoad(context: Context){
            context.startService(Intent(context,WebService::class.java))
        }
    }


    override fun onHandleIntent(intent: Intent?) {
        logI("WebService start")
    }


}