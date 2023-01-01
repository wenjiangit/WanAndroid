package com.wenjian.wanandroid.model

import com.wenjian.wanandroid.entity.Article
import com.wenjian.wanandroid.helper.ExceptionHelper
import com.wenjian.wanandroid.net.Resp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

/**
 * Description Result
 *
 * Date 2023/1/1
 * @author wenjian@dayuwuxian.com
 */
class WResult<T>(val value: Any?) {

    val isSuccess: Boolean get() = value !is Failure
    val isFailure: Boolean get() = value is Failure

    data class Failure(
        val e: Exception?,
        val code: Int = 0,
        val msg: String? = null
    ) {

        val errorMsg: String
            get() = msg ?: ExceptionHelper.parseExceptionMsg(e)
    }

    companion object {
        fun <T> success(data: T): WResult<T> {
            return WResult(data)
        }

        fun <T> failure(e: Exception? = null, msg: String? = null, code: Int = 0): WResult<T> {
            return WResult(Failure(e, code, msg))
        }
    }

    val failure: Failure?
        get() = if (value is Failure) value else null

}


@Suppress("UNCHECKED_CAST")
fun <T> WResult<T>.getOrNull(): T? {
    return if (value is WResult.Failure) null
    else value as? T
}

fun <T> Flow<WResult<T>>.onSuccess(action: (T) -> Unit): Flow<WResult<T>> {
    return this.onEach {
        if (it.isSuccess) {
            it.getOrNull()?.run(action)
        }
    }
}

fun <T> Flow<WResult<T>>.onFail(action: (WResult.Failure) -> Unit): Flow<WResult<T>> {
    return this.onEach {
        if (it.isFailure) {
            action(it.failure!!)
        }
    }
}

fun <T> Flow<Resp<T>>.asWResultFlow(mapper: ((Resp<T>) -> T)? = null): Flow<WResult<T>> {
    return this.catch { e -> Result.failure<List<Article>>(e) }
        .map {
            if (it.success()) {
                WResult.success(mapper?.invoke(it) ?: it.data)
            } else {
                WResult.failure(code = it.errorCode, msg = it.errorMsg)
            }
        }
}
