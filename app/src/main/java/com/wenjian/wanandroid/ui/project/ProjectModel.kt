package com.wenjian.wanandroid.ui.project

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.wenjian.wanandroid.entity.Project
import com.wenjian.wanandroid.model.DataViewModel
import com.wenjian.wanandroid.model.SingleLiveEvent
import com.wenjian.wanandroid.model.ViewState

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

    private val mPageLive: SingleLiveEvent<Int> = SingleLiveEvent()

    fun loadProjectTree() = repository.loadProjectTree(this)

    fun loadProjects(): LiveData<List<Project>> = Transformations.switchMap(mPageLive) { page ->
        repository.loadProjects(page, cid, this) {
            curPage = it.curPage
            isOver = it.over
        }
    }

    fun refresh(cid: Int) {
        this.cid = cid
        mPageLive.value = 1
    }

    fun loadMore() {
        if (isOver) {
            updateViewState(ViewState.Empty)
            return
        }
        mPageLive.value = ++curPage
    }
}