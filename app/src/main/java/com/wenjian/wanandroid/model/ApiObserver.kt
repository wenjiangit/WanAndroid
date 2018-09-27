package com.wenjian.wanandroid.model

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.wenjian.wanandroid.entity.Resource
import com.wenjian.wanandroid.helper.ExceptionHelper
import com.wenjian.wanandroid.helper.UserHelper
import com.wenjian.wanandroid.net.Resp
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Description: ApiObserver
 * Date: 2018/9/10
 *
 * @author jian.wen@ubtrobot.com
 */
class ApiObserver<T>(private val liveData: MutableLiveData<Resource<T>>,
                     private val list: CompositeDisposable,
                     private val handler: (T) -> Unit = {}) : Observer<Resp<T>> {
    override fun onNext(t: Resp<T>) {
        if (t.success()) {
            liveData.value = Resource.success(t.data)
            handler(t.data)
        } else {
            liveData.value = Resource.error(t.errorMsg)
            if (t.needLogin()) {
                UserHelper.autoLogin()
            }
        }
    }

    override fun onComplete() {
    }

    override fun onSubscribe(d: Disposable) {
        list.add(d)
        liveData.value = Resource.loading()
    }

    override fun onError(e: Throwable) {
        Log.e("wj", "onError: ${e.message}", e)
        liveData.value = Resource.error(ExceptionHelper.getErrorMsg(e))
    }

}