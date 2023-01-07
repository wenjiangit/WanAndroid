package com.wenjian.wanandroid.ui.search

import androidx.lifecycle.viewModelScope
import com.wenjian.wanandroid.entity.Article
import com.wenjian.wanandroid.helper.UserHelper
import com.wenjian.wanandroid.model.DataViewModel
import com.wenjian.wanandroid.model.ViewState
import com.wenjian.wanandroid.model.onSuccess
import kotlinx.coroutines.flow.*

/**
 * Description ${name}
 *
 * Date 2018/9/9
 * @author wenjianes@163.com
 */
class SearchModel : DataViewModel() {

    private var curPage: Int = 0
    private var isOver: Boolean = false

    private val _articles = MutableStateFlow<List<Article>?>(null)
    val articles = _articles.asStateFlow()

    private val _searchQuery = MutableStateFlow<SearchQuery?>(null)

    init {
        // 使用 debounce 进行防抖动的案例
        _searchQuery
            .filterNotNull()
            .debounce(300)
            .flatMapMerge {
                repository.search(it.query, it.page)
            }
            .withCommonHandler()
            .onSuccess {
                hideLoading()
                curPage = it.curPage
                isOver = it.over
                _articles.value = it.datas
            }.launchIn(viewModelScope)
    }


    fun loadHotWords() = repository.loadHotWords()
        .withCommonHandler()

    fun loadMore() {
        if (isOver) {
            updateViewState(ViewState.Empty)
            return
        }
        _searchQuery.update { SearchQuery(it?.query!!, ++curPage) }
    }

    fun loadSearchHistory(): Set<String> = UserHelper.loadSearchHistory()

    fun clearHistory() = UserHelper.clearHistory()

    fun saveHistory(history: Set<String>) = UserHelper.saveSearchHistory(history)

    fun doSearch(query: String) {
        _searchQuery.value = SearchQuery(query)
    }

    data class SearchQuery(val query: String, val page: Int = 0)
}