package com.wenjian.wanandroid.net

/**
 * Description ApiException
 *
 * Date 2023/1/7
 * @author wenjian@dayuwuxian.com
 */
class ApiException(
    private val code: Int,
    private val msg: String?
) : Exception(msg) {

    override val message: String
        get() = "接口请求异常：[$code]==> $msg"

    companion object {
        const val API_CODE_LOGIN = -1001
    }
}