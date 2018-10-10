package com.wenjian.wanandroid.ui.search


import android.arch.lifecycle.Observer
import android.support.v4.app.Fragment
import com.wenjian.wanandroid.base.BaseListFragment
import com.wenjian.wanandroid.base.BaseRecyclerAdapter
import com.wenjian.wanandroid.entity.Article
import com.wenjian.wanandroid.extension.addCustomDecoration
import com.wenjian.wanandroid.extension.apiModelDelegate
import com.wenjian.wanandroid.ui.adapter.ArticleListAdapter


/**
 * A simple [Fragment] subclass.
 *
 */
class SearchFragment : BaseListFragment<Article,SearchModel>(SearchModel::class.java) {
    override fun createAdapter(): BaseRecyclerAdapter<Article> = ArticleListAdapter()

    override fun initViews() {
        super.initViews()
        mLayRefresh.isEnabled = false
        mRecycler.addCustomDecoration()
    }

    override fun subscribeUi() {
        super.subscribeUi()
        mViewModel.loadData().observe(this, Observer { it ->
           showContent(it)
        })
    }

    fun search(input: String) {
        mAdapter.setNewData(null)
        if (!input.isBlank()) {
            isLoadMore = false
            mViewModel.doSearch(input)
        }
    }

    override fun onLoadMore() {
        super.onLoadMore()
        mViewModel.loadMore()
    }


}

