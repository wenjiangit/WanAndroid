package com.wenjian.wanandroid.entity

import com.wenjian.wanandroid.net.Resp

/**
 * Description: ListResp
 * Date: 2018/9/17
 *
 * @author jian.wen@ubtrobot.com
 */
class ListResp<T>(data: ListContract<T>, errorCode: Int, msg: String) : Resp<ListContract<T>>(data, errorCode, msg) {

}