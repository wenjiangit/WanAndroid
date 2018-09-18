package com.wenjian.wanandroid.entity

import java.util.*

/**
 * Description: ListContract
 * Date: 2018/9/6
 *
 * @author jian.wen@ubtrobot.com
 */
class ListContract<T>{

    var curPage: Int = 0
    var datas: List<T> = ArrayList()
    var offset: Int = 0
    var over: Boolean = false
    var pageCount: Int = 0
    var size: Int = 0
    var total: Int = 0
    override fun toString(): String {
        return "ListContract(curPage=$curPage, datas=$datas, offset=$offset, over=$over, pageCount=$pageCount, size=$size, total=$total)"
    }


}