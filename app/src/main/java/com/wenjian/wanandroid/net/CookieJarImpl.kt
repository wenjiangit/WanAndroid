package com.wenjian.wanandroid.net

import android.content.Context
import android.util.Log
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

/**
 * Description: CookieJarImpl
 * Date: 2018/9/29
 *
 * @author jian.wen@ubtrobot.com
 */
class CookieJarImpl(private val manager: CookieManager) : CookieJar {

    companion object {
        fun create(context: Context) = CookieJarImpl(CookieManager(context))
    }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        Log.i("wj", "receive cookie ${url.host()} >>> $cookies")
        manager.save(url.host(), cookies)
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        return manager.load(url.host())
    }
}