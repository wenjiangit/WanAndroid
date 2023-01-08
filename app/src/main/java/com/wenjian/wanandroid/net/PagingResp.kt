package com.wenjian.wanandroid.net

import com.wenjian.wanandroid.entity.ListContract

/**
 * Description: PagingResp
 * Date: 2018/9/17
 *
 * @author jian.wen@ubtrobot.com
 */
class PagingResp<T>(data: ListContract<T>, errorCode: Int, msg: String) :
    Resp<ListContract<T>>(data, errorCode, msg) {

}