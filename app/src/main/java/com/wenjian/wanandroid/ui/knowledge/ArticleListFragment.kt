package com.wenjian.wanandroid.ui.knowledge


import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.wenjian.wanandroid.R
import com.wenjian.wanandroid.base.BaseFragment
import com.wenjian.wanandroid.extension.addCustomDecoration
import com.wenjian.wanandroid.extension.apiModelDelegate
import com.wenjian.wanandroid.extension.extraDelegate
import com.wenjian.wanandroid.ui.adapter.ArticleListAdapter

/**
 * Description: ArticleListFragment
 * Date: 2018/9/6
 *
 * @author jian.wen@ubtrobot.com
 */
class ArticleListFragment : BaseFragment() {

    private val mAdapter: ArticleListAdapter by lazy {
        ArticleListAdapter()
    }

    private val mTreeModel: TreeModel by apiModelDelegate(TreeModel::class.java)

    private val cid: Int? by extraDelegate(ARG_ID)

    private var loadMore: Boolean = false
    private lateinit var subRecycler:RecyclerView
    private lateinit var layRefresh:SwipeRefreshLayout

    companion object {
        private const val ARG_ID = "category_id"

        fun newInstance(id: Int): ArticleListFragment {
            val bundle = Bundle().apply { putInt(ARG_ID, id) }
            return ArticleListFragment().apply { arguments = bundle }
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_article_list

    override fun findViews(mRoot: View) {
        subRecycler = mRoot.findViewById(R.id.subRecycler)
        layRefresh = mRoot.findViewById(R.id.layRefresh)
    }

    override fun initViews() {
        super.initViews()
        subRecycler.setHasFixedSize(true)
        subRecycler.layoutManager = LinearLayoutManager(context)
        subRecycler.addCustomDecoration()
        subRecycler.adapter = mAdapter
        mAdapter.apply {
            setEnableLoadMore(true)
            openLoadAnimation()
            setOnLoadMoreListener({
                loadMore = true
                mTreeModel.loadMore()
            }, subRecycler)
        }

        layRefresh.setOnRefreshListener {
            refresh()
        }

    }

    override fun subscribeUi() {
        super.subscribeUi()
        mTreeModel.articles.observe(this, Observer {
            showContentWithStatus(it) {
                if (loadMore) {
                    if (it.isEmpty()) {
                        mAdapter.loadMoreEnd()
                    } else {
                        mAdapter.addData(it)
                        mAdapter.loadMoreComplete()
                    }
                } else {
                    mAdapter.setNewData(it)
                }
            }
        })
    }

    override fun onLazyLoad() {
        super.onLazyLoad()
        loadMore = false
        mTreeModel.loadArticles(cid!!)
    }

    override fun showLoading() {
        layRefresh.isRefreshing = true
    }

    override fun hideLoading() {
        layRefresh.isRefreshing = false
    }

}
