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
class SearchFragment : BaseListFragment<Article>() {
    override fun createAdapter(): BaseRecyclerAdapter<Article> = ArticleListAdapter()

    private val mSearchModel: SearchModel by apiModelDelegate(SearchModel::class.java)

    override fun initViews() {
        super.initViews()
        mLayRefresh.isEnabled = false
        mRecycler.addCustomDecoration()
    }

    override fun subscribeUi() {
        super.subscribeUi()
        mSearchModel.articles.observe(this, Observer { it ->
           showContent(it)
        })
    }

    fun search(input: String) {
        mAdapter.setNewData(null)
        if (!input.isBlank()) {
            isLoadMore = false
            mSearchModel.doSearch(input)
        }
    }

    override fun onLoadMore() {
        super.onLoadMore()
        mSearchModel.loadMore()
    }


}

