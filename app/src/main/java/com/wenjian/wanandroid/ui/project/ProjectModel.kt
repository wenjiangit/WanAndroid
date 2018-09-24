package com.wenjian.wanandroid.ui.project

import android.arch.lifecycle.MutableLiveData
import com.wenjian.wanandroid.base.BaseViewModel
import com.wenjian.wanandroid.entity.Project
import com.wenjian.wanandroid.entity.ProjectTree
import com.wenjian.wanandroid.entity.Resource
import com.wenjian.wanandroid.extension.io2Main
import com.wenjian.wanandroid.model.ApiObserver
import com.wenjian.wanandroid.model.PagingObserver
import com.wenjian.wanandroid.net.ApiService

/**
 * Description: ProjectModel
 * Date: 2018/9/12
 *
 * @author jian.wen@ubtrobot.com
 */
class ProjectModel(private val service: ApiService) : BaseViewModel() {

    val projectTrees: MutableLiveData<Resource<List<ProjectTree>>> = MutableLiveData()
    val projects: MutableLiveData<Resource<List<Project>>> = MutableLiveData()

    private var isOver: Boolean = false
    private var curPage: Int = 1
    private var cid: Int = -1

    fun loadProjectTree() {
        service.loadProjectTree()
                .io2Main()
                .subscribe(ApiObserver(projectTrees, disposables))
    }


    fun loadProjects(cid: Int, page: Int = 1) {
        if (this.cid == -1) {
            this.cid = cid
        }
        service.loadProjects(page, cid)
                .io2Main()
                .subscribe(PagingObserver(projects, disposables) { data ->
                    curPage = data.curPage
                    isOver = data.over
                })
    }


    fun loadMore() {
        if (isOver) {
            projects.value = Resource.success(emptyList())
            return
        }
        loadProjects(cid, ++curPage)
    }
}