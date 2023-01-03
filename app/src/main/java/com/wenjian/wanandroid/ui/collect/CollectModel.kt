package com.wenjian.wanandroid.ui.collect

import androidx.lifecycle.viewModelScope
import com.wenjian.wanandroid.entity.Article
import com.wenjian.wanandroid.model.DataViewModel
import com.wenjian.wanandroid.model.ViewState
import com.wenjian.wanandroid.model.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn

/**
 * Description: CollectModel
 * Date: 2018/9/21
 *
 * @author jian.wen@ubtrobot.com
 */
class CollectModel : DataViewModel() {

    private var curPage: Int = 0
    private var isOver: Boolean = false

    private val _articles = MutableStateFlow<List<Article>>(emptyList())
    val articles : StateFlow<List<Article>> = _articles

    private fun loadCollects(page: Int) = repository.loadCollects(page)
        .withCommonHandler()
        .onSuccess {
            curPage = it.curPage
            isOver = it.curPage >= it.pageCount - 1
            _articles.value = it.datas
        }.launchIn(viewModelScope)

    fun refresh() {
        loadCollects(0)
    }

    fun loadMore() {
        if (isOver) {
            updateViewState(ViewState.Empty)
            return
        }
        loadCollects(++curPage)
    }

    fun collect(id: Int) = repository.collect(id)
        .withCommonHandler()

    fun uncollect(id: Int, originId: Int = -1) = repository.unCollect(id, originId)
        .withCommonHandler()

}