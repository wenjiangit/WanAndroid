package com.wenjian.wanandroid.ui.project

import androidx.lifecycle.viewModelScope
import com.wenjian.wanandroid.entity.Project
import com.wenjian.wanandroid.extension.logE
import com.wenjian.wanandroid.model.DataViewModel
import com.wenjian.wanandroid.model.ViewState
import com.wenjian.wanandroid.model.getOrNull
import com.wenjian.wanandroid.model.onSuccess
import kotlinx.coroutines.flow.*

/**
 * Description: ProjectModel
 * Date: 2018/9/12
 *
 * @author jian.wen@ubtrobot.com
 */
class ProjectModel : DataViewModel() {

    private var isOver: Boolean = false
    private var curPage: Int = 1
    private var cid: Int = -1

    private val _projects = MutableStateFlow<List<Project>?>(null)
    val projects : StateFlow<List<Project>?> = _projects

    fun loadProjectTree() = repository.loadProjectTree()
        .withCommonHandler()
        .mapNotNull { it.getOrNull() }

    private fun loadProjects() = repository.loadProjects(++curPage, cid)
        .withCommonHandler()
        .onSuccess {
            logE("onSuccess")
            curPage = it.curPage
            isOver = it.over
            _projects.value = it.datas
        }
        .launchIn(viewModelScope)

    fun refresh(cid: Int) {
        this.cid = cid
        loadProjects()
    }

    fun loadMore() {
        if (isOver) {
            updateViewState(ViewState.Empty)
            return
        }
        loadProjects()
    }
}