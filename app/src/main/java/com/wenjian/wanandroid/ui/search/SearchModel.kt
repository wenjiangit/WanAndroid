package com.wenjian.wanandroid.ui.search

import androidx.lifecycle.viewModelScope
import com.wenjian.wanandroid.entity.Article
import com.wenjian.wanandroid.helper.UserHelper
import com.wenjian.wanandroid.model.DataViewModel
import com.wenjian.wanandroid.model.ViewState
import com.wenjian.wanandroid.model.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn

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

    private val _articles = MutableStateFlow<List<Article>?>(null)
    val articles : StateFlow<List<Article>?> = _articles

    fun loadHotWords() = repository.loadHotWords()
        .withCommonHandler()

    fun loadMore() {
        if (isOver) {
            updateViewState(ViewState.Empty)
            return
        }
        loadData(++curPage)
    }

    fun loadSearchHistory(): Set<String> = UserHelper.loadSearchHistory()

    fun clearHistory() = UserHelper.clearHistory()

    fun saveHistory(history: Set<String>) = UserHelper.saveSearchHistory(history)

    private fun loadData(page: Int) = repository.search(lastQuery!!, page)
        .withCommonHandler()
        .onSuccess {
            curPage = it.curPage
            isOver = it.over
            _articles.value = it.datas
        }.launchIn(viewModelScope)

    fun doSearch(query: String) {
        lastQuery = query
        loadData(0)
    }
}