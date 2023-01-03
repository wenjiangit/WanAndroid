package com.wenjian.wanandroid.helper

import android.annotation.SuppressLint
import android.content.Context
import com.wenjian.wanandroid.WanAndroidApp
import com.wenjian.wanandroid.extension.toastError
import com.wenjian.wanandroid.model.asWResultFlow
import com.wenjian.wanandroid.model.onFail
import com.wenjian.wanandroid.net.ApiService
import com.wenjian.wanandroid.net.RetrofitManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn

/**
 * Description: NetHelper
 * Date: 2018/12/3
 *
 * @author jian.wen@ubtrobot.com
 */
@SuppressLint("StaticFieldLeak")
object NetHelper {

    private val mService: ApiService = RetrofitManager.service
    private val mAppContext: Context = WanAndroidApp.instance


    @SuppressLint("CheckResult")
    fun collect(id: Int) {
        mService.collectPost(id)
            .asWResultFlow()
            .onFail {
                mAppContext.toastError(it.errorMsg)
            }.flowOn(Dispatchers.IO)
            .launchIn(WanAndroidApp.appMainScope)
    }


    @SuppressLint("CheckResult")
    fun unCollect(id: Int) {
        mService.unCollectPost(id, -1)
            .asWResultFlow()
            .onFail {
                mAppContext.toastError(it.errorMsg)
            }.flowOn(Dispatchers.IO)
            .launchIn(WanAndroidApp.appMainScope)
    }
}