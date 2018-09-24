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
        fun <T> fail(msg: String = "小主,你家网络不太给力哦~~"): Resource<T> = Resource(STATUS.ERROR, msg = msg)
        fun <T> loading(): Resource<T> = Resource()
        fun <T> hideLoding(): Resource<T> = Resource(STATUS.HIDE_LOADING)
    }

    enum class STATUS {
        LOADING, ERROR, SUCCESS, HIDE_LOADING
    }


}