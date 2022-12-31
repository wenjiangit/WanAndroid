package com.wenjian.wanandroid.net

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import com.wenjian.wanandroid.extension.genericType
import com.wenjian.wanandroid.ui.web.WebClient
import okhttp3.Cookie
import java.util.concurrent.ConcurrentHashMap

/**
 * Description: CookieManager
 * Date: 2018/9/29
 *
 * @author jian.wen@ubtrobot.com
 */
class CookieManager private constructor(val context: Context) {

    companion object {
        private const val NAME: String = "cookie_prefs"
        private val TAG: String = CookieManager::class.java.simpleName

        @SuppressLint("StaticFieldLeak")
        private var instance: CookieManager? = null

        private const val COOKIE_URI = "content://com.wenjian.wanandroid.provider"

        fun getInstance(context: Context): CookieManager {
            instance ?: synchronized(CookieManager::class.java) {
                CookieManager(context.applicationContext).also {
                    instance = it
                }
            }
            return instance!!
        }
    }

    private val cookieStore: ConcurrentHashMap<String, ConcurrentHashMap<String, Cookie>?> = ConcurrentHashMap()
    private val cookiePrefs: SharedPreferences
    private val gson: Gson = Gson()

    init {
        cookiePrefs = context.getSharedPreferences(NAME, Context.MODE_PRIVATE)
        val cookieMap = cookiePrefs.all
        cookieMap?.let {
            for (entry in it.entries) {
                cookieStore[entry.key] = decode(entry.value)
            }
        }
        Log.i(TAG, "CookieManager initialized")
    }


    private fun decode(any: Any?): ConcurrentHashMap<String, Cookie>? = when (any) {
        null -> null
        is String -> {
            try {
                val map: Map<String, Cookie> = gson.fromJson(any, genericType<Map<String, Cookie>>())
                ConcurrentHashMap(map)
            } catch (e: Exception) {
                Log.e(TAG, "decode error", e)
                null
            }
        }
        else -> null
    }

    fun loadMultiProcess(host: String): List<Cookie> {
        return if (WebClient.isInWebViewProcess(context)) {
            call("load", host)
        } else {
            load(host)
        }
    }

    fun saveMultiProcess(host: String, cookies: List<Cookie>) {
        if (WebClient.isInWebViewProcess(context)) {
            val bundle = Bundle().apply {
                putString("req", gson.toJson(cookies))
            }
            context.contentResolver.call(Uri.parse(COOKIE_URI), "save", host, bundle)
        } else {
            save(host, cookies)
        }
    }

    private fun call(method: String, arg: String): List<Cookie> {
        val bundle = context.contentResolver.call(Uri.parse(COOKIE_URI), method, arg, null)
        val string = bundle?.getString("ret")
        return gson.fromJson(string, genericType<List<Cookie>>())
    }


    fun load(host: String): List<Cookie> {
        val hashMap = cookieStore[host]
        //及时清理过期数据
        val filter = hashMap?.filter { it.value.expiresAt > System.currentTimeMillis() }
        filter?.let {
            val newMap = ConcurrentHashMap(filter)
            cookieStore[host] = newMap
            serialize(host, newMap)
        }
        return filter?.values?.toList() ?: emptyList()
    }

    fun save(host: String, cookies: List<Cookie>) {
        var hashMap = cookieStore[host]
        if (hashMap == null) {
            hashMap = ConcurrentHashMap()
            cookieStore[host] = hashMap
        }
        cookies.forEach {
            //内存中保存所有值
            hashMap[it.name] = it
        }
        serialize(host, hashMap)
    }

    private fun serialize(host: String, map: ConcurrentHashMap<String, Cookie>) {
        //持久化需要持久化的值
        val filterMap = map.filterValues { it.persistent }
        cookiePrefs.edit().putString(host, encode(filterMap)).apply()
    }

    private fun encode(map: Map<String, Cookie>): String {
        return gson.toJson(map)
    }

    fun remove(host: String) {
        cookiePrefs.edit().remove(host).apply()
        cookieStore.remove(host)
    }


    fun clear() {
        cookiePrefs.edit().clear().apply()
        cookieStore.clear()
    }

}