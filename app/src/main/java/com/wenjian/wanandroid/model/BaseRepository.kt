package com.wenjian.wanandroid.model

import com.wenjian.wanandroid.net.ApiService
import com.wenjian.wanandroid.net.RetrofitManager
import io.reactivex.disposables.CompositeDisposable

/**
 * Description ${name}
 *
 * Date 2018/10/5
 * @author wenjianes@163.com
 */
open class BaseRepository {

    private val mDisposables: CompositeDisposable by lazy { CompositeDisposable() }

    open val mService: ApiService by lazy { RetrofitManager.service }


    open fun unSubscribe() {
        mDisposables.clear()
    }

}