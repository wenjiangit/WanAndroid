package com.wenjian.wanandroid.base

import android.arch.lifecycle.AndroidViewModel
import com.wenjian.wanandroid.WanAndroidApp
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Description: BaseViewModel
 * Date: 2018/9/6
 *
 * @author jian.wen@ubtrobot.com
 */
open class BaseViewModel : AndroidViewModel(WanAndroidApp.instance) {

    /**
     * 不考虑线程安全地延迟初始化
     */
    open val disposables: CompositeDisposable by lazy(LazyThreadSafetyMode.NONE) { CompositeDisposable() }

    open fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

}