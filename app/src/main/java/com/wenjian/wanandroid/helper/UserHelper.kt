package com.wenjian.wanandroid.helper

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.wenjian.wanandroid.WanAndroidApp
import com.wenjian.wanandroid.entity.UserInfo

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


    private fun <T> getAny(key: String, clz: Class<T>): T? {
        val value = mPrefs.getString(key, null)
        return if (value != null) {
            mGson.fromJson(value, clz)
        } else {
            null
        }
    }

}