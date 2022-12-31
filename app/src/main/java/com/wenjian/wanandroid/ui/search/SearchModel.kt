package com.wenjian.wanandroid.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.wenjian.wanandroid.entity.Article
import com.wenjian.wanandroid.helper.UserHelper
import com.wenjian.wanandroid.model.DataViewModel
import com.wenjian.wanandroid.model.SingleLiveEvent
import com.wenjian.wanandroid.model.ViewState
import com.wenjian.wanandroid.model.view.ViewCallbackImpl

/**
 * Description ${name}
 *
 * Date 2018/9/9
 * @author wenjianes@163.com
 */
class SearchModel : DataViewModel() {

    private var lastQuery: String? = null
    private var curPage: Int = 0
    private var isOver: Boolean = false

    private val mPageLive: SingleLiveEvent<Int> = SingleLiveEvent()

    fun loadHotWords() = getRepository().loadHotWords(ViewCallbackImpl(viewState))

    fun loadMore() {
        if (isOver) {
            viewState.value = ViewState.empty()
            return
        }
        mPageLive.value = ++curPage
    }

    fun loadSearchHistory(): Set<String> = UserHelper.loadSearchHistory()

    fun clearHistory() = UserHelper.clearHistory()

    fun saveHistory(history: Set<String>) = UserHelper.saveSearchHistory(history)

    fun loadData(): LiveData<List<Article>> = Transformations.switchMap(mPageLive) { page ->
        getRepository().search(lastQuery!!, page, ViewCallbackImpl(viewState)) {
            curPage = it.curPage
            isOver = it.over
        }
    }

    fun doSearch(query: String) {
        lastQuery = query
        mPageLive.value = 0
    }
}