package com.wenjian.wanandroid.ui.search


import android.arch.lifecycle.Observer
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.wenjian.wanandroid.R
import com.wenjian.wanandroid.base.BaseFragment
import com.wenjian.wanandroid.extension.addCustomDecoration
import com.wenjian.wanandroid.extension.apiModelDelegate
import com.wenjian.wanandroid.ui.adapter.ArticleListAdapter


/**
 * A simple [Fragment] subclass.
 *
 */
class SearchFragment : BaseFragment() {

    private val mSearchModel: SearchModel by apiModelDelegate(SearchModel::class.java)

    private var isLoadMore: Boolean = false

    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var searchRecycler: RecyclerView

    private val mAdapter: ArticleListAdapter by lazy {
        ArticleListAdapter()
    }

    override fun getLayoutId(): Int = R.layout.fragment_serch


    override fun showLoading() {
        swipeRefresh.isRefreshing = true
    }

    override fun hideLoading() {
        swipeRefresh.isRefreshing = false
    }

    override fun findViews(mRoot: View) {
        swipeRefresh = mRoot.findViewById(R.id.swipeRefresh)
        searchRecycler = mRoot.findViewById(R.id.searchRecycler)
    }

    override fun initViews() {
        super.initViews()
        swipeRefresh.isEnabled = false

        searchRecycler.setHasFixedSize(true)
        searchRecycler.layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false)
        searchRecycler.addCustomDecoration()
        searchRecycler.adapter = mAdapter

        mAdapter.isUseEmpty(true)
        mAdapter.setEnableLoadMore(true)
        mAdapter.setOnLoadMoreListener({
            isLoadMore = true
            mSearchModel.loadMode()
        }, searchRecycler)

    }


    override fun subscribeUi() {
        super.subscribeUi()
        mSearchModel.articles.observe(this, Observer {
            showContentWithStatus(it) {
                if (isLoadMore) {
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

    fun search(input: String) {
        mAdapter.setNewData(null)
        if (!input.isBlank()) {
            isLoadMore = false
            mSearchModel.doSearch(input)
        }
    }


}

