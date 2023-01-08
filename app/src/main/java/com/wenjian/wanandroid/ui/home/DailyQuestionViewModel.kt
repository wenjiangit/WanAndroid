package com.wenjian.wanandroid.ui.home

import androidx.lifecycle.viewModelScope
import com.wenjian.wanandroid.entity.Article
import com.wenjian.wanandroid.model.DataViewModel
import com.wenjian.wanandroid.model.ViewState
import com.wenjian.wanandroid.net.onSuccess
import kotlinx.coroutines.flow.*

/**
 * Description DailyQuestionViewModel
 *
 * Date 2023/1/8
 * @author wenjian@dayuwuxian.com
 */
class DailyQuestionViewModel : DataViewModel() {

    private val _questions = MutableStateFlow<List<Article>?>(null)
    val questions = _questions.asStateFlow()
    private var isOver = false
    private val _pager = MutableStateFlow(1)

    init {
        _pager.flatMapMerge { repository.dailyQuestions(it) }
            .withCommonHandler()
            .onSuccess {
                isOver = it.over
                _questions.value = it.datas
            }
            .launchIn(viewModelScope)
    }

    fun refresh() = repository.dailyQuestions(1)
        .withCommonHandler()
        .mapNotNull { it.data?.datas }

    fun loadMore() {
        if (isOver) {
            updateViewState(ViewState.Empty)
            return
        }
        _pager.update { it + 1 }
    }

}