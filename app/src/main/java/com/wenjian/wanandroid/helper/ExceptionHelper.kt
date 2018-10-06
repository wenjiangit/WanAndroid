package com.wenjian.wanandroid.helper

import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

/**
 * Description: ExceptionHelper
 * Date: 2018/9/25
 *
 * @author jian.wen@ubtrobot.com
 */
object ExceptionHelper {

    fun getErrorMsg(e: Throwable?): String {
        if (e == null) {
            return "未知错误"
        }
        return when (e) {
            is UnknownHostException, is ConnectException -> "网络连接不可用,请稍后重试"
            is TimeoutException, is SocketTimeoutException -> "连接超时,请稍后重试"
            is JsonParseException, is JsonSyntaxException -> "数据解析错误"
            else -> e.message?:"你家网络不太给力哟～～～"
        }
    }

}
