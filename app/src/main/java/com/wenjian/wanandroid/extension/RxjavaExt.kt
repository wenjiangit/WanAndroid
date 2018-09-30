package com.wenjian.wanandroid.extension

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Description: RxjavaExt
 * Date: 2018/9/6
 *
 * @author jian.wen@ubtrobot.com
 */
/**
 * 线程切换,从io线程切换到主线程,这里不能用apply
 */
fun <T> Observable<T>.io2Main(): Observable<T> = subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())


fun <T> Observable<T>.main(): Observable<T> = subscribeOn(AndroidSchedulers.mainThread())


fun <T> Observable<T>.io(): Observable<T> = subscribeOn(Schedulers.io())
