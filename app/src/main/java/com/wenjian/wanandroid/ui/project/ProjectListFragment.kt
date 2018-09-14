package com.wenjian.wanandroid.ui.project

import android.arch.lifecycle.Observer
import android.os.Bundle
import com.wenjian.wanandroid.base.BaseListFragment
import com.wenjian.wanandroid.base.BaseRecyclerAdapter
import com.wenjian.wanandroid.entity.Project
import com.wenjian.wanandroid.extension.apiModelDelegate
import com.wenjian.wanandroid.extension.extraDelegate
import com.wenjian.wanandroid.ui.adapter.ProjectListAdapter

/**
 * Description: ProjectListFragment
 * Date: 2018/9/14
 *
 * @author jian.wen@ubtrobot.com
 */
class ProjectListFragment : BaseListFragment<Project>() {

    private val cid: Int? by extraDelegate(ARG_CID)

    companion object {
        private const val ARG_CID = "project_cid"
        fun newInstance(id: Int): ProjectListFragment {
            val bundle = Bundle().apply { putInt(ARG_CID, id) }
            return ProjectListFragment().apply { arguments = bundle }
        }
    }

    private val mProjectModel: ProjectModel by apiModelDelegate(ProjectModel::class.java)

    override fun createAdapter(): BaseRecyclerAdapter<Project> = ProjectListAdapter()

    override fun subscribeUi() {
        super.subscribeUi()
        mProjectModel.projects.observe(this, Observer {
            showContentWithStatus(it) {
                mAdapter.setNewData(it)
            }
        })
    }

    override fun onLazyLoad() {
        super.onLazyLoad()
        mProjectModel.loadProjects(cid!!)
    }

}