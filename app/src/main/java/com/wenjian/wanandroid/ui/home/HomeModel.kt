package com.wenjian.wanandroid.ui.home

import androidx.lifecycle.viewModelScope
import com.wenjian.wanandroid.entity.Article
import com.wenjian.wanandroid.model.DataViewModel
import com.wenjian.wanandroid.model.ViewState
import com.wenjian.wanandroid.model.getOrNull
import com.wenjian.wanandroid.model.onSuccess
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

    fun loadHomeData() = getRepository().loadHomeData()
        .withLoading()
        .withErrorHandle()
        .mapNotNull { it.getOrNull() }
        .flowOn(Dispatchers.IO)

    private fun loadArticles() {
        getRepository().loadArticles(++curPage)
            .onSuccess {
                isOver = it.over
                curPage = it.curPage
                _articles.value = it.datas
            }.withErrorHandle()
            .launchIn(viewModelScope)
    }

    fun loadMore() {
        if (isOver) {
            viewState.value = ViewState.empty()
            return
        }
        loadArticles()
    }
}