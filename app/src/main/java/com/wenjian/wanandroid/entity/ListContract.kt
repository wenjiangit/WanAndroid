package com.wenjian.wanandroid.entity

/**
 * Description: ListContract
 * Date: 2018/9/6
 *
 * @author jian.wen@ubtrobot.com
 */
class ListContract<T>(val curPage: Int,
                      val datas: List<T>,
                      val offset: Int,
                      val over: Boolean,
                      val pageCount: Int,
                      val size: Int,
                      val total: Int)