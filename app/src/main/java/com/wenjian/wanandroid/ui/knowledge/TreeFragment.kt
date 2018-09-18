package com.wenjian.wanandroid.ui.knowledge

import android.arch.lifecycle.Observer
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.wenjian.wanandroid.R
import com.wenjian.wanandroid.base.BaseFragment
import com.wenjian.wanandroid.extension.apiModelDelegate
import com.wenjian.wanandroid.extension.setupToolBar
import com.wenjian.wanandroid.ui.adapter.TreeAdapter

/**
 * Description: TreeFragment
 * Date: 2018/9/6
 *
 * @author jian.wen@ubtrobot.com
 */
class TreeFragment : BaseFragment() {


    companion object {
        fun newInstance() = TreeFragment()
    }

    private val mTreeModel: TreeModel by apiModelDelegate(TreeModel::class.java)

    private lateinit var mTreeRecycler: RecyclerView

    private val mTreeAdapter: TreeAdapter by lazy {
        TreeAdapter()
    }

    override fun getLayoutId(): Int = R.layout.fragment_knowledge

    override fun findViews(mRoot: View) {
        mTreeRecycler = mRoot.findViewById(R.id.treeRecycler)
    }

    override fun initViews() {

        setupToolBar(R.string.title_knowledge)

        mTreeRecycler.setHasFixedSize(true)
        mTreeRecycler.layoutManager = LinearLayoutManager(context)
        mTreeRecycler.adapter = mTreeAdapter

    }

    override fun subscribeUi() {
        super.subscribeUi()

        mTreeModel.tree.observe(this, Observer {
            showContentWithStatus(it) {
                mTreeAdapter.setNewData(it)
            }
        })


    }

    override fun onLazyLoad() {
        super.onLazyLoad()
        mTreeModel.loadTree()
    }

}