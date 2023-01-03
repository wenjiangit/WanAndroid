package com.wenjian.wanandroid.helper

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.wenjian.wanandroid.WanAndroidApp
import com.wenjian.wanandroid.entity.UserInfo
import com.wenjian.wanandroid.extension.io2Main
import com.wenjian.wanandroid.net.RetrofitManager

/**
 * Description: UserHelper
 * Date: 2018/9/20
 *
 * @author jian.wen@ubtrobot.com
 */
object UserHelper {

    private val mPrefs: SharedPreferences = WanAndroidApp.instance.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    private var mUserInfo: UserInfo? = null
    private val mGson: Gson = Gson()

    private const val KEY_USER_INFO = "key_user_info"
    private const val KEY_SEARCH_HISTORY = "key_search_history"

    fun saveUserInfo(userInfo: UserInfo) {
        this.mUserInfo = userInfo
        putAny(KEY_USER_INFO, userInfo)
    }

    fun getUserInfo(): UserInfo? {
        if (mUserInfo == null) {
            mUserInfo = getAny(KEY_USER_INFO, UserInfo::class.java)
        }
        return mUserInfo
    }

    fun isLogin(): Boolean = getUserInfo() != null

    fun logOut() {
        mPrefs.edit().clear().apply()
        mUserInfo = null
    }

    private fun putAny(key: String, any: Any?) {
        mPrefs.edit().putString(key, mGson.toJson(any)).apply()
    }

    /**
     * 保存搜索历史
     */
    fun saveSearchHistory(list: Set<String>) {
        mPrefs.edit().putStringSet(KEY_SEARCH_HISTORY, list).apply()
    }

    /**
     * 加载搜素历史
     */
    fun loadSearchHistory(): Set<String> {
        val strings = mPrefs.getStringSet(KEY_SEARCH_HISTORY, null)
        return strings ?: emptySet()
    }

    /**
     * 清空历史
     */
    fun clearHistory() {
        mPrefs.edit().remove(KEY_SEARCH_HISTORY).apply()
    }

    fun <T> getList(key: String, typeToken: TypeToken<T>): T? {
        val value = mPrefs.getString(key, null)
        return if (value != null) {
            mGson.fromJson(value, typeToken.type)
        } else {
            null
        }
    }

    fun <T> putList(key: String, list: List<T>) {
        putAny(key, list)
    }

    private fun <T> getAny(key: String, clz: Class<T>): T? {
        val value = mPrefs.getString(key, null)
        return if (value != null) {
            mGson.fromJson(value, clz)
        } else {
            null
        }
    }


    fun autoLogin() {
//        if (isLogin()) {
//            val userInfo = getUserInfo()!!
//            RetrofitManager.service.login(userInfo.username, userInfo.password)
//                    .io2Main()
//                    .subscribe {
//                        if (it.success()) {
//                            saveUserInfo(it.data)
//                            Toast.makeText(WanAndroidApp.instance, "自动登录成功", Toast.LENGTH_SHORT).show()
//                        }
//                    }
//        }
    }

}