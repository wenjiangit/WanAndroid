package com.wenjian.wanandroid.ui.search


import android.arch.lifecycle.Observer
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.wenjian.wanandroid.R
import com.wenjian.wanandroid.base.BaseFragment
import com.wenjian.wanandroid.extension.apiModelDelegate
import com.wenjian.wanandroid.ui.adapter.ArticleListAdapter
import kotlinx.android.synthetic.main.fragment_serch.*


/**
 * A simple [Fragment] subclass.
 *
 */
class SearchFragment : BaseFragment() {

    private val mSearchModel: SearchModel by apiModelDelegate(SearchModel::class.java)

    companion object {
        val TAG: String = SearchFragment::class.java.simpleName
    }

    private var isLoadMore: Boolean = false

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

    override fun initViews() {
        super.initViews()

        swipeRefresh.isEnabled = false

        searchRecycler.setHasFixedSize(true)
        searchRecycler.layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false)
        searchRecycler.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        searchRecycler.adapter = mAdapter

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
        if (!input.isBlank()) {
            isLoadMore = false
            mSearchModel.doSearch(input)
        }
    }


}

