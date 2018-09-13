package com.wenjian.wanandroid.ui.project

import com.wenjian.wanandroid.base.BaseViewModel
import com.wenjian.wanandroid.extension.io2Main
import com.wenjian.wanandroid.net.ApiService

/**
 * Description: ProjectModel
 * Date: 2018/9/12
 *
 * @author jian.wen@ubtrobot.com
 */
class ProjectModel(val service: ApiService): BaseViewModel() {




    fun loadProjectTree() {
        service.loadProjectTree()
                .io2Main()
                .subscribe()

    }
}