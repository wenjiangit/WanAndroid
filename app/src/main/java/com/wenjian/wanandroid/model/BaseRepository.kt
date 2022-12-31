package com.wenjian.wanandroid.model

import androidx.lifecycle.LiveData
import com.wenjian.wanandroid.entity.ListContract
import com.wenjian.wanandroid.extension.io2Main
import com.wenjian.wanandroid.helper.ExceptionHelper
import com.wenjian.wanandroid.model.view.ViewCallback
import com.wenjian.wanandroid.net.ApiService
import com.wenjian.wanandroid.net.PagingResp
import com.wenjian.wanandroid.net.Resp
import com.wenjian.wanandroid.net.RetrofitManager
import io.reactivex.Observable
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

    open fun <T> doSimpleAction(observable: Observable<Resp<T>>, callback: ViewCallback, handle: (T?) -> Unit): LiveData<T> {
        val live: SingleLiveEvent<T> = SingleLiveEvent()
        mDisposables.add(observable.io2Main()
                .doOnSubscribe {
                    callback.showLoading()
                }
                .subscribe({
                    if (it.success()) {
                        live.value = it.data
                        handle(it.data)
                    } else {
                        callback.showError(it.errorMsg)
                    }
                }, {
                    callback.showError(ExceptionHelper.getErrorMsg(it))
                },{
                    callback.hideLoading()
                }))
        return live
    }

    open fun <T> doListAction(observable: Observable<PagingResp<T>>, callback: ViewCallback, handle: (ListContract<T>) -> Unit): LiveData<T> {
        val live: SingleLiveEvent<T> = SingleLiveEvent()
        mDisposables.add(observable.io2Main()
                .doOnSubscribe {
                    callback.showLoading()
                }
                .subscribe({
                    if (it.success()) {
                        live.value = it.data.datas
                        handle(it.data)
                    } else {
                        callback.showError(it.errorMsg)
                    }
                }, {
                    callback.showError(ExceptionHelper.getErrorMsg(it))
                },{
                    callback.hideLoading()
                }))
        return live
    }

    open fun unSubscribe() {
        mDisposables.clear()
    }

}