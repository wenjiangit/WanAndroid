package com.wenjian.wanandroid.ui.knowledge

import android.arch.lifecycle.Observer
import android.support.v7.widget.LinearLayoutManager
import com.wenjian.wanandroid.R
import com.wenjian.wanandroid.base.BaseListFragment
import com.wenjian.wanandroid.base.BaseRecyclerAdapter
import com.wenjian.wanandroid.entity.TreeEntry
import com.wenjian.wanandroid.extension.apiModelDelegate
import com.wenjian.wanandroid.extension.setupToolBar
import com.wenjian.wanandroid.ui.adapter.TreeAdapter

/**
 * Description: TreeFragment
 * Date: 2018/9/6
 *
 * @author jian.wen@ubtrobot.com
 */
class TreeFragment : BaseListFragment<TreeEntry>() {
    override fun createAdapter(): BaseRecyclerAdapter<TreeEntry> = TreeAdapter()

    companion object {
        fun newInstance() = TreeFragment()
    }

    private val mTreeModel: TreeModel by apiModelDelegate(TreeModel::class.java)

    override fun initViews() {
        super.initViews()
        setupToolBar(R.string.title_knowledge)
        mRecycler.setHasFixedSize(true)
        mRecycler.layoutManager = LinearLayoutManager(context)
        mRecycler.adapter = mAdapter
    }

    override fun subscribeUi() {
        super.subscribeUi()
        mTreeModel.tree.observe(this, Observer { it ->
            showContentWithStatus(it) {
                mAdapter.setNewData(it)
            }
        })
    }

    override fun onLazyLoad() {
        super.onLazyLoad()
        mTreeModel.loadTree()
    }

    override fun loadMoreEnable(): Boolean {
        return false
    }

}