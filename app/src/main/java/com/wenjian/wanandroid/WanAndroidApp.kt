package com.wenjian.wanandroid

import android.app.Application
import android.os.Process
import com.squareup.leakcanary.LeakCanary
import com.tencent.bugly.crashreport.CrashReport
import com.wenjian.wanandroid.extension.logI
import com.wenjian.wanandroid.ui.web.WebClient
import kotlin.properties.Delegates

/**
 * Description: WanAndroidApp
 * Date: 2018/9/10
 *
 * @author jian.wen@ubtrobot.com
 */
class WanAndroidApp : Application() {

    companion object {
        var instance: WanAndroidApp by Delegates.notNull()
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        logI("pid: ${Process.myPid()}")
        //LeakCanary检测进程或WebView进程
        if (LeakCanary.isInAnalyzerProcess(this) || WebClient.isInWebViewProcess(this)) {
            return
        }
        //主进程
        LeakCanary.install(this)
        CrashReport.initCrashReport(this)
        //提前启动web进程,避免WebActivity启动时白屏
        WebClient.preLoad(this)
    }


    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        logI("onTrimMemory ${Process.myPid()}")
    }


}