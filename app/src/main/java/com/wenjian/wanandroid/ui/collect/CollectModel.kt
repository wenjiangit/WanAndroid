package com.wenjian.wanandroid.ui.collect

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import com.wenjian.wanandroid.entity.Article
import com.wenjian.wanandroid.model.DataViewModel
import com.wenjian.wanandroid.model.SingleLiveEvent
import com.wenjian.wanandroid.model.ViewState
import com.wenjian.wanandroid.model.view.ViewCallbackImpl

/**
 * Description: CollectModel
 * Date: 2018/9/21
 *
 * @author jian.wen@ubtrobot.com
 */
class CollectModel : DataViewModel() {

    private val mPageLive: SingleLiveEvent<Int> = SingleLiveEvent()
    private var curPage: Int = 0
    private var isOver: Boolean = false

    fun loadCollects(): LiveData<List<Article>> = Transformations.switchMap(mPageLive) { page ->
        getRepository().loadCollects(page, ViewCallbackImpl(viewState)) {
            curPage = it.curPage
            isOver = it.curPage >= it.pageCount - 1
        }
    }

    fun refresh() {
        mPageLive.value = 0
    }


    fun loadMore() {
        if (isOver) {
            viewState.value = ViewState.empty()
            return
        }
        mPageLive.value = ++curPage
    }

    fun collect(id: Int) = getRepository().collect(id, ViewCallbackImpl(viewState))

    fun uncollect(id: Int, originId: Int = -1) = getRepository().unCollect(id, originId, ViewCallbackImpl(viewState))

}