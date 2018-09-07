package com.wenjian.wanandroid.net

/**
 * Description: Resp
 * Date: 2018/9/5
 *
 * @author jian.wen@ubtrobot.com
 */
data class Resp<T>(val data: T, val errorCode: Int, val errorMsg: String) {

    fun success() = errorCode == 0

}