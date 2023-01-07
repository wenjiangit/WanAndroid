package com.wenjian.wanandroid.net

import com.wenjian.wanandroid.helper.ExceptionHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

/**
 * Description: Resp
 * Date: 2018/9/5
 *
 * @author jian.wen@ubtrobot.com
 */
open class Resp<T>(
    val data: T? = null,
    val errorCode: Int = 0,
    val errorMsg: String? = null,
    val exception: Throwable? = null
) {


    companion object {
        fun <T> buildFailure(exception: Throwable): Resp<T> {
            return Resp(exception = exception)
        }
    }

    val isSuccess: Boolean get() = errorCode == 0 && exception == null

    fun needLogin() = errorCode == -1001

    fun newBuilder(): Builder<T> {
        return Builder<T>().data(data).code(errorCode).msg(errorMsg)
    }

    val failure get() = Failure(exception, errorCode, errorMsg)


    data class Failure(
        val e: Throwable?,
        val code: Int = 0,
        val msg: String? = null
    ) {

        val errorMsg: String
            get() = msg ?: ExceptionHelper.parseExceptionMsg(e)
    }


    class Builder<T> {
        private var data: T? = null
        private var code: Int = -1
        private var msg: String? = null
        fun data(data: T?) = apply { this.data = data }
        fun code(code: Int) = apply { this.code = code }
        fun msg(msg: String?) = apply { this.msg = msg }
        fun build() = Resp(data, code, msg)
    }

}

@Suppress("UNCHECKED_CAST")
fun <T> Resp<T>.getOrNull(): T? {
    return if (isSuccess) data
    else null
}

fun <T> Resp<T>.getOrThrow(): T {
    return when {
        exception != null -> throw exception
        errorCode != 0 -> throw ApiException(errorCode, errorMsg ?: "unknown")
        else -> data!!
    }
}

fun <T> Flow<Resp<T>>.onSuccess(action: (T) -> Unit): Flow<Resp<T>> {
    return this.onEach {
        if (it.isSuccess) {
            it.getOrNull()?.run(action)
        }
    }
}

fun <T> Flow<Resp<T>>.onFail(action: (Resp.Failure) -> Unit): Flow<Resp<T>> {
    return this.onEach {
        if (it.isSuccess.not()) {
            action(it.failure)
        }
    }
}