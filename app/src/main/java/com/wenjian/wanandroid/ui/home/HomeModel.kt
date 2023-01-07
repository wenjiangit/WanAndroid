package com.wenjian.wanandroid.ui.home

import androidx.lifecycle.viewModelScope
import com.wenjian.wanandroid.entity.Article
import com.wenjian.wanandroid.model.DataViewModel
import com.wenjian.wanandroid.model.ViewState
import com.wenjian.wanandroid.net.getOrNull
import com.wenjian.wanandroid.net.onSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

/**
 * Description: HomeModel
 * Date: 2018/9/6
 *
 * @author jian.wen@ubtrobot.com
 */

class HomeModel : DataViewModel() {

    private var curPage: Int = 0
    private var isOver: Boolean = false
    private val _articles = MutableStateFlow<List<Article>>(emptyList())
    val articles = _articles.asStateFlow()

    fun loadHomeData() = repository.loadHomeData()
        .withLoading()
        .withErrorHandler()
        .mapNotNull { it.getOrNull() }
        .flowOn(Dispatchers.IO)

    private fun loadArticles() {
        repository.loadArticles(++curPage)
            .onSuccess {
                isOver = it.over
                curPage = it.curPage
                _articles.value = it.datas
            }.withErrorHandler()
            .launchIn(viewModelScope)
    }

    fun loadMore() {
        if (isOver) {
            updateViewState(ViewState.Empty)
            return
        }
        loadArticles()
    }
}