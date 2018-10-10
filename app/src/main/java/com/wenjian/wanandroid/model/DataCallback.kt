package com.wenjian.wanandroid.model

/**
 * Description: DataCallback
 * Date: 2018/10/9
 *
 * @author jian.wen@ubtrobot.com
 */
interface DataCallback<T> {

    fun onSuccess(data: T)

    fun onFail(msg: String?)


}