package com.wenjian.wanandroid.model

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

/**
 * Description: RxBus
 * Date: 2018/9/26
 *
 * @author jian.wen@ubtrobot.com
 */
object RxBus {

    private val mBus: Subject<Any> = PublishSubject.create()

    fun post(any: Any) {
        mBus.onNext(any)
    }

    fun <T> toObservable(clz: Class<T>): Observable<T> {
        return mBus.ofType(clz)
    }
}