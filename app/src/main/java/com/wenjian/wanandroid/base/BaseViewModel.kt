package com.wenjian.wanandroid.base

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.wenjian.wanandroid.entity.Resource
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Description: BaseViewModel
 * Date: 2018/9/6
 *
 * @author jian.wen@ubtrobot.com
 */
open class BaseViewModel : ViewModel() {

    open val disposables: CompositeDisposable by lazy { CompositeDisposable() }

    var status: MutableLiveData<Resource<String>> = MutableLiveData()


    open fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    open fun showLoading(){
        status.value = Resource.loading()
    }

    open fun showError(){
        status.value = Resource.fail()
    }


}