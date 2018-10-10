package com.wenjian.wanandroid.ui.knowledge

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import com.wenjian.wanandroid.entity.Article
import com.wenjian.wanandroid.entity.Resource
import com.wenjian.wanandroid.entity.TreeEntry
import com.wenjian.wanandroid.model.DataViewModel
import com.wenjian.wanandroid.model.SingleLiveEvent
import com.wenjian.wanandroid.model.ViewState
import com.wenjian.wanandroid.model.view.ViewCallbackImpl

/**
 * Description TreeModel
 *
 * Date 2018/9/8
 * @author wenjianes@163.com
 */
class TreeModel : DataViewModel() {

    private var isOver: Boolean = false
    private var pageCount: Int = 0
    private var cid: Int = -1

    private val mPageLive: SingleLiveEvent<Int> = SingleLiveEvent()

    fun loadTree() = repository.loadTree(ViewCallbackImpl(viewState))

    fun loadData(): LiveData<List<Article>> = Transformations.switchMap(mPageLive) { page ->
        repository.loadTreeArticles(page, cid, ViewCallbackImpl(viewState)) {
            isOver = it.over
            pageCount = it.curPage
        }
    }

    fun refresh(cid: Int) {
        this.cid = cid
        mPageLive.value = 0
    }

    fun loadMore() {
        if (isOver) {
            viewState.value = ViewState.empty()
            return
        }
        mPageLive.value = ++pageCount
    }

}