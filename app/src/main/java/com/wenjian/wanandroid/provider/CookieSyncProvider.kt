package com.wenjian.wanandroid.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import com.google.gson.Gson
import com.wenjian.wanandroid.extension.genericType
import com.wenjian.wanandroid.extension.logI
import com.wenjian.wanandroid.net.CookieManager
import okhttp3.Cookie
import java.lang.UnsupportedOperationException

/**
 * Description ${name}
 *
 * Date 2018/12/1
 * @author wenjianes@163.com
 */
class CookieSyncProvider : ContentProvider() {
    override fun insert(uri: Uri, values: ContentValues?): Uri {
        throw UnsupportedOperationException()
    }

    override fun query(uri: Uri, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor {
        throw UnsupportedOperationException()
    }

    override fun onCreate(): Boolean {

        return true
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        throw UnsupportedOperationException()
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        throw UnsupportedOperationException()
    }

    override fun getType(uri: Uri): String {
        throw UnsupportedOperationException()
    }

    override fun call(method: String, arg: String?, extras: Bundle?): Bundle? {
        logI("method: $method,arg: $arg,extras: $extras")
        if (method.isNullOrBlank() || arg.isNullOrBlank()) {
            throw IllegalAccessException("method or arg is null or blank")
        }
        val ret = Bundle()
        return when (method) {
            "save" -> {
                extras?.getString("req")?.let {
                    val cookies: List<Cookie> = Gson().fromJson(it, genericType<List<Cookie>>())
                    CookieManager.getInstance(context!!).save(arg, cookies)
                }
                ret
            }
            "load" -> {
                val list = CookieManager.getInstance(context!!).load(arg)
                ret.putString("ret", Gson().toJson(list))
                ret
            }
            else -> super.call(method, arg, extras)
        }


    }
}
