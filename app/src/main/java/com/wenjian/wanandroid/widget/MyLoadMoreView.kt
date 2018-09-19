package com.wenjian.wanandroid.widget

import com.chad.library.adapter.base.loadmore.LoadMoreView
import com.wenjian.wanandroid.R

/**
 * Description: MyLoadMoreView
 * Date: 2018/9/19
 *
 * @author jian.wen@ubtrobot.com
 */
class MyLoadMoreView : LoadMoreView() {
    override fun getLayoutId(): Int = R.layout.lay_load_more
    override fun getLoadingViewId(): Int = R.id.lay_loading
    override fun getLoadEndViewId(): Int = R.id.lay_load_end
    override fun getLoadFailViewId(): Int = R.id.lay_load_error
}