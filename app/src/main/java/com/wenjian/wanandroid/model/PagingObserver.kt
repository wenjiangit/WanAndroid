package com.wenjian.wanandroid.model

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.wenjian.wanandroid.entity.ListContract
import com.wenjian.wanandroid.entity.Resource
import com.wenjian.wanandroid.net.PagingResp
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

/**
 * Description ${name}
 *
 * Date 2018/9/24
 * @author wenjianes@163.com
 */
class PagingObserver<T>(private val liveData: MutableLiveData<Resource<T>>,
                        private val list: CompositeDisposable,
                        private val handler: (ListContract<T>) -> Unit = {}) : Observer<PagingResp<T>> {
    override fun onComplete() {
        liveData.value = Resource.hideLoding()
    }

    override fun onSubscribe(d: Disposable) {
        list.add(d)
        liveData.value = Resource.loading()
    }

    override fun onNext(t: PagingResp<T>) {
        if (t.success()) {
            liveData.value = Resource.success(t.data.datas)
            handler(t.data)
        } else {
            liveData.value = Resource.fail(t.errorMsg)
        }
    }

    override fun onError(e: Throwable) {
        Log.e("wj", "onError:", e)
        if (e is UnknownHostException || e is ConnectException) {
            liveData.value = Resource.fail("请检查网络")
        } else if (e is TimeoutException || e is SocketTimeoutException) {
            liveData.value = Resource.fail("连接超时")
        } else {
            liveData.value = Resource.fail()
        }
    }

}