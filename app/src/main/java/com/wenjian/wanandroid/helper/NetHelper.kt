package com.wenjian.wanandroid.helper

import android.annotation.SuppressLint
import android.content.Context
import com.wenjian.wanandroid.WanAndroidApp
import com.wenjian.wanandroid.extension.toastError
import com.wenjian.wanandroid.net.ApiService
import com.wenjian.wanandroid.net.RetrofitManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

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
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (!it.success()) {
                        mAppContext.toastError(it.errorMsg ?: "添加收藏失败")
                    }
                }, {
                    mAppContext.toastError("添加收藏失败")
                })
    }


    @SuppressLint("CheckResult")
    fun unCollect(id: Int) {
        mService.unCollectPost(id, -1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (!it.success()) {
                        mAppContext.toastError(it.errorMsg ?: "取消收藏失败")
                    }
                }, {
                    mAppContext.toastError("取消收藏失败")
                })
    }
}