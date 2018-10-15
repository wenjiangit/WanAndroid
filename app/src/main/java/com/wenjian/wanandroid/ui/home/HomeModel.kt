package com.wenjian.wanandroid.ui.home

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import com.wenjian.wanandroid.entity.Article
import com.wenjian.wanandroid.model.DataViewModel
import com.wenjian.wanandroid.model.SingleLiveEvent
import com.wenjian.wanandroid.model.ViewState
import com.wenjian.wanandroid.model.view.ViewCallbackImpl

/**
 * Description: HomeModel
 * Date: 2018/9/6
 *
 * @author jian.wen@ubtrobot.com
 */

class HomeModel : DataViewModel() {

    private var curPage: Int = 0

    private var isOver: Boolean = false

    private val mPageLive: SingleLiveEvent<Int> = SingleLiveEvent()

    fun loadHomeData() = getRepository().loadHomeData(ViewCallbackImpl(viewState))

    fun loadArticles(): LiveData<List<Article>> = Transformations.switchMap(mPageLive) { page ->
        getRepository().loadArticles(page, ViewCallbackImpl(viewState)) {
            isOver = it.over
            curPage = it.curPage
        }
    }

    fun loadMore() {
        if (isOver) {
            viewState.value = ViewState.empty()
            return
        }
        mPageLive.value = ++curPage
    }
}