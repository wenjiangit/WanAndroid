package com.wenjian.wanandroid.ui.knowledge

import androidx.lifecycle.viewModelScope
import com.wenjian.wanandroid.entity.Article
import com.wenjian.wanandroid.model.DataViewModel
import com.wenjian.wanandroid.model.ViewState
import com.wenjian.wanandroid.net.getOrNull
import com.wenjian.wanandroid.net.onSuccess
import kotlinx.coroutines.flow.*

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

    fun loadTree() = repository.loadTree()
        .withCommonHandler()
        .mapNotNull { it.getOrNull() }


    private val _articles = MutableStateFlow<List<Article>?>(null)
    val articles: StateFlow<List<Article>?> = _articles

    private fun loadData(page: Int) {
        repository.loadTreeArticles(page, cid)
            .withCommonHandler()
            .onSuccess {
                isOver = it.over
                pageCount = it.pageCount
                _articles.value = it.datas
            }
            .launchIn(viewModelScope)
    }

    fun refresh(cid: Int) {
        this.cid = cid
        loadData(0)
    }

    fun loadMore() {
        if (isOver) {
            updateViewState(ViewState.Empty)
            return
        }
        loadData(++pageCount)
    }

}