package com.wenjian.wanandroid.base

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Description: BaseRecyclerAdapter
 * Date: 2018/9/12
 *
 * @author jian.wen@ubtrobot.com
 */
abstract class BaseRecyclerAdapter<T>(resId: Int) : BaseQuickAdapter<T, BaseViewHolder>(resId) {

    open var showLike: Boolean = true

    fun showLike(like: Boolean) {
        this.showLike = like
    }
}