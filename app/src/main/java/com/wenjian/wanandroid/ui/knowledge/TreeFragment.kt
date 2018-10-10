package com.wenjian.wanandroid.ui.knowledge

import android.arch.lifecycle.Observer
import android.support.v7.widget.LinearLayoutManager
import com.wenjian.wanandroid.R
import com.wenjian.wanandroid.base.BaseListFragment
import com.wenjian.wanandroid.base.BaseRecyclerAdapter
import com.wenjian.wanandroid.entity.TreeEntry
import com.wenjian.wanandroid.extension.setupToolBar
import com.wenjian.wanandroid.ui.adapter.TreeAdapter

/**
 * Description: TreeFragment
 * Date: 2018/9/6
 *
 * @author jian.wen@ubtrobot.com
 */
class TreeFragment : BaseListFragment<TreeEntry, TreeModel>(TreeModel::class.java) {
    override fun createAdapter(): BaseRecyclerAdapter<TreeEntry> = TreeAdapter()

    companion object {
        fun newInstance() = TreeFragment()
    }

    override fun initViews() {
        super.initViews()
        setupToolBar(R.string.title_knowledge)
        mRecycler.setHasFixedSize(true)
        mRecycler.layoutManager = LinearLayoutManager(context)
        mRecycler.adapter = mAdapter
    }

    override fun onLazyLoad() {
        super.onLazyLoad()
        mViewModel.loadTree()
                .observe(this, Observer { data ->
                    data?.let {
                        mAdapter.setNewData(it)
                    }
                })
    }

    override fun loadMoreEnable(): Boolean {
        return false
    }

}