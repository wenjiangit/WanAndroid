package com.wenjian.wanandroid.ui.knowledge

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.wenjian.wanandroid.entity.Article
import com.wenjian.wanandroid.model.DataViewModel
import com.wenjian.wanandroid.model.SingleLiveEvent
import com.wenjian.wanandroid.model.ViewState

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

    fun loadTree() = repository.loadTree(this)

    fun loadData(): LiveData<List<Article>> = Transformations.switchMap(mPageLive) { page ->
        repository.loadTreeArticles(page, cid, this) {
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
            updateViewState(ViewState.Empty)
            return
        }
        mPageLive.value = ++pageCount
    }

}