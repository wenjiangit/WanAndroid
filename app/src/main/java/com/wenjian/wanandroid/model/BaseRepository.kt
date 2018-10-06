package com.wenjian.wanandroid.model

import io.reactivex.disposables.CompositeDisposable

/**
 * Description ${name}
 *
 * Date 2018/10/5
 * @author wenjianes@163.com
 */
open class BaseRepository {

    private val mDisposables: CompositeDisposable by lazy { CompositeDisposable() }


    fun unSubscribe(){
        mDisposables.clear()
    }

}