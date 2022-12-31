package com.wenjian.wanandroid.ui.project

import androidx.lifecycle.Observer
import android.os.Bundle
import com.wenjian.wanandroid.base.BaseListFragment
import com.wenjian.wanandroid.base.BaseRecyclerAdapter
import com.wenjian.wanandroid.entity.Project
import com.wenjian.wanandroid.extension.addCustomDecoration
import com.wenjian.wanandroid.extension.extraDelegate
import com.wenjian.wanandroid.ui.adapter.ProjectListAdapter

/**
 * Description: ProjectListFragment
 * Date: 2018/9/14
 *
 * @author jian.wen@ubtrobot.com
 */
class ProjectListFragment : BaseListFragment<Project, ProjectModel>(ProjectModel::class.java) {

    private val cid: Int? by extraDelegate(ARG_CID)

    companion object {
        private const val ARG_CID = "project_cid"
        fun newInstance(id: Int): ProjectListFragment {
            val bundle = Bundle().apply { putInt(ARG_CID, id) }
            return ProjectListFragment().apply { arguments = bundle }
        }
    }

    override fun createAdapter(): BaseRecyclerAdapter<Project> = ProjectListAdapter()

    override fun subscribeUi() {
        super.subscribeUi()
        mViewModel.loadProjects().observe(this, Observer { data ->
            showContent(data)
        })
    }

    override fun initViews() {
        super.initViews()
        mRecycler.addCustomDecoration()
    }

    override fun onLazyLoad() {
        super.onLazyLoad()
        mViewModel.refresh(cid!!)
    }

    override fun onLoadMore() {
        super.onLoadMore()
        mViewModel.loadMore()
    }

}