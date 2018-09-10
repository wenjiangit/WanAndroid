package com.wenjian.wanandroid.model

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.wenjian.wanandroid.entity.Resource
import com.wenjian.wanandroid.net.Resp
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Description: ApiSubscriber
 * Date: 2018/9/10
 *
 * @author jian.wen@ubtrobot.com
 */
class ApiSubscriber<T>(private val liveData: MutableLiveData<Resource<T>>,
                       private val list: CompositeDisposable,
                       private val handler: (Any) -> Unit) : Observer<Resp<*>> {
    override fun onNext(t: Resp<*>) {
        if (t.success()) {
            handler(t.data!!)
        } else {
            liveData.value = Resource.fail(t.errorMsg)
        }
    }

    override fun onComplete() {
    }

    override fun onSubscribe(d: Disposable) {
        list.add(d)
        liveData.value = Resource.loading()
    }

    override fun onError(e: Throwable) {
        Log.e("wj", "onError:", e)
        liveData.value = Resource.fail()
    }

}