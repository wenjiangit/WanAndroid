package com.wenjian.wanandroid.ui.collect


import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.wenjian.wanandroid.base.BaseListFragment
import com.wenjian.wanandroid.base.BaseRecyclerAdapter
import com.wenjian.wanandroid.entity.Article
import com.wenjian.wanandroid.extension.addCustomDecoration
import com.wenjian.wanandroid.ui.adapter.ArticleListAdapter
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * A simple [Fragment] subclass.
 *
 */
class CollectFragment : BaseListFragment<Article, CollectModel>(CollectModel::class.java) {

    override fun createAdapter(): BaseRecyclerAdapter<Article> = ArticleListAdapter()

    override fun onLazyLoad() {
        super.onLazyLoad()
        mViewModel.refresh()
    }

    override fun initViews() {
        super.initViews()
        mRecycler.addCustomDecoration()
    }

    override fun subscribeUi() {
        super.subscribeUi()
        mViewModel.articles
            .filter { it.isNotEmpty() }
            .onEach { res ->
            showContent(res)
        }.flowWithLifecycle(lifecycle)
            .launchIn(lifecycleScope)
    }

    override fun onLoadMore() {
        mViewModel.loadMore()
    }

    override fun showContent(res: List<Article>?) {
        res?.let { list ->
            val collectList = list.onEach { it.collect = true }
            if (isLoadMore) {
                mAdapter.addData(collectList)
                mAdapter.loadMoreComplete()
            } else {
                mAdapter.setNewData(collectList)
            }
        }
    }


}
