package com.wenjian.wanandroid.entity

/**
 * Description: Resource
 * Date: 2018/9/6
 *
 * @author jian.wen@ubtrobot.com
 */
class Resource<T> private constructor(val status: STATUS = STATUS.LOADING, val data: T? = null, val msg: String? = null) {

    companion object {
        fun <T> success(t: T): Resource<T> = Resource(STATUS.SUCCESS, t)
        fun <T> fail(msg: String = "网络异常"): Resource<T> = Resource(STATUS.FAIL, msg = msg)
        fun <T> loading(): Resource<T> = Resource()
    }

    enum class STATUS {
        LOADING, FAIL, SUCCESS
    }


}