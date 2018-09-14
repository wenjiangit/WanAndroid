package com.wenjian.wanandroid.ui.project

import android.arch.lifecycle.MutableLiveData
import com.wenjian.wanandroid.base.BaseViewModel
import com.wenjian.wanandroid.entity.ListResp
import com.wenjian.wanandroid.entity.Project
import com.wenjian.wanandroid.entity.ProjectTree
import com.wenjian.wanandroid.entity.Resource
import com.wenjian.wanandroid.extension.io2Main
import com.wenjian.wanandroid.model.ApiSubscriber
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

    fun loadProjectTree() {
        service.loadProjectTree()
                .io2Main()
                .subscribe(ApiSubscriber(projectTrees, disposables) {
                    @Suppress("UNCHECKED_CAST")
                    val data: List<ProjectTree> = it as List<ProjectTree>
                    projectTrees.value = Resource.success(data)
                })
    }


    fun loadProjects(cid: Int, page: Int = 1) {
        service.loadProjects(page, cid)
                .io2Main()
                .subscribe(ApiSubscriber(projects, disposables) {
                    @Suppress("UNCHECKED_CAST")
                    val data: ListResp<Project> = it as ListResp<Project>
                    projects.value = Resource.success(data.datas)
                })

    }
}