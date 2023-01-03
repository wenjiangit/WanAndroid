package com.wenjian.wanandroid.ui.knowledge

import com.wenjian.wanandroid.entity.Article
import com.wenjian.wanandroid.model.DataViewModel
import com.wenjian.wanandroid.model.ViewState
import com.wenjian.wanandroid.model.getOrNull
import com.wenjian.wanandroid.model.onSuccess
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

    private val mPageLive: MutableStateFlow<Int> = MutableStateFlow(0)

    fun loadData(): Flow<List<Article>> {
        return mPageLive.flatMapConcat { repository.loadTreeArticles(it, cid) }
            .withCommonHandler()
            .onSuccess {
                isOver = it.over
                pageCount = it.pageCount
            }.mapNotNull { it.getOrNull()?.datas }
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
        mPageLive.update { ++pageCount }
    }

}