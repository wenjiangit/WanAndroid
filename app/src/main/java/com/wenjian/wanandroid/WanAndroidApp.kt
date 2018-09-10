package com.wenjian.wanandroid

import android.app.Application
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
    }
}