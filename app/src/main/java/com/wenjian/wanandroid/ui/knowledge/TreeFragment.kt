package com.wenjian.wanandroid.ui.knowledge

import android.arch.lifecycle.Observer
import android.support.v7.widget.LinearLayoutManager
import com.wenjian.wanandroid.R
import com.wenjian.wanandroid.base.BaseFragment
import com.wenjian.wanandroid.di.Injector
import com.wenjian.wanandroid.extension.apiModelDelegate
import com.wenjian.wanandroid.extension.setupToolBar
import com.wenjian.wanandroid.extension.viewModelDelegate
import com.wenjian.wanandroid.ui.adapter.TreeAdapter
import kotlinx.android.synthetic.main.fragment_knowledge.*

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

    private val mTreeAdapter: TreeAdapter by lazy {
        TreeAdapter()
    }

    override fun getLayoutId(): Int = R.layout.fragment_knowledge

    override fun initViews() {

        setupToolBar(R.string.title_knowledge)

        treeRecycler.setHasFixedSize(true)
        treeRecycler.layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false)

        treeRecycler.adapter = mTreeAdapter
        /* val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL).apply {
             setDrawable(ContextCompat.getDrawable(context!!, R.drawable.divider_tree)!!)
         }
         treeRecycler.addItemDecoration(decoration)*/

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
        mTreeModel.getTree()
    }

}