package com.wenjian.wanandroid.base

import android.arch.lifecycle.AndroidViewModel
import com.wenjian.wanandroid.WanAndroidApp
import com.wenjian.wanandroid.model.BaseRepository
import com.wenjian.wanandroid.model.SingleLiveEvent
import com.wenjian.wanandroid.model.ViewState
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Description: BaseViewModel
 * Date: 2018/9/6
 *
 * @author jian.wen@ubtrobot.com
 */
abstract class BaseViewModel<Repository : BaseRepository>(val repository: Repository) : AndroidViewModel(WanAndroidApp.instance) {

    /**
     * 不考虑线程安全地延迟初始化
     */
    open val disposables: CompositeDisposable by lazy(LazyThreadSafetyMode.NONE) { CompositeDisposable() }

    val viewState: SingleLiveEvent<ViewState> = SingleLiveEvent()

    open fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
        repository.unSubscribe()
    }

}