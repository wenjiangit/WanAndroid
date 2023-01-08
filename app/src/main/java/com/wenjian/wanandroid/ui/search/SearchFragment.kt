package com.wenjian.wanandroid.ui.search


import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.wenjian.wanandroid.base.BaseListFragment
import com.wenjian.wanandroid.base.BaseRecyclerAdapter
import com.wenjian.wanandroid.entity.Article
import com.wenjian.wanandroid.extension.addCustomDecoration
import com.wenjian.wanandroid.ui.adapter.ArticleListAdapter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


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
        mViewModel.articles
            .filterNotNull()
            .onEach { showContent(it) }
            .flowWithLifecycle(lifecycle)
            .launchIn(lifecycleScope)
    }

    fun search(input: String) {
        mAdapter.setNewData(null)
        if (input.isNotBlank()) {
            isLoadMore = false
            mViewModel.doSearch(input)
        }
    }

    override fun onLoadMore() {
        super.onLoadMore()
        mViewModel.loadMore()
    }


}

