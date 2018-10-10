package com.wenjian.wanandroid.net

/**
 * Description: Resp
 * Date: 2018/9/5
 *
 * @author jian.wen@ubtrobot.com
 */
open class Resp<T>(val data: T, val errorCode: Int, val errorMsg: String?) {

    fun success() = errorCode == 0

    fun needLogin() = errorCode == -1001

    fun newBuilder(): Builder<T> {
        return Builder<T>().data(data).code(errorCode).msg(errorMsg)
    }

    class Builder<T> {

        private var data: T? = null
        private var code: Int = -1
        private var msg: String? = null

        fun data(data: T) = apply { this.data = data }
        fun code(code: Int) = apply { this.code = code }
        fun msg(msg: String?) = apply { this.msg = msg }
        fun build() = Resp(data, code, msg)
    }

}