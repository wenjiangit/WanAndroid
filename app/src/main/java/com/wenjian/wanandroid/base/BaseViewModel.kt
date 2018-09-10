package com.wenjian.wanandroid.base

import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Description: BaseViewModel
 * Date: 2018/9/6
 *
 * @author jian.wen@ubtrobot.com
 */
open class BaseViewModel : ViewModel() {

    open val disposables: CompositeDisposable = CompositeDisposable()


    open fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }


    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}