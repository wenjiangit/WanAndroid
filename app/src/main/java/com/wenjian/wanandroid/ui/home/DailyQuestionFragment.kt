package com.wenjian.wanandroid.ui.home

import com.wenjian.wanandroid.base.BaseListFragment
import com.wenjian.wanandroid.base.BaseRecyclerAdapter
import com.wenjian.wanandroid.entity.Article
import com.wenjian.wanandroid.extension.addCustomDecoration
import com.wenjian.wanandroid.extension.safeCollect
import com.wenjian.wanandroid.extension.safeObserve
import com.wenjian.wanandroid.ui.adapter.ArticleListAdapter

/**
 * Description DailyQuestionFragment
 *
 * Date 2023/1/8
 * @author wenjian@dayuwuxian.com
 */
class DailyQuestionFragment :
    BaseListFragment<Article, DailyQuestionViewModel>(DailyQuestionViewModel::class.java) {
    override fun createAdapter(): BaseRecyclerAdapter<Article> {
        return ArticleListAdapter()
    }

    override fun subscribeUi() {
        super.subscribeUi()
        mViewModel.questions.safeObserve(this) {
            showContent(it)
        }
    }

    override fun onLazyLoad() {
        super.onLazyLoad()
        mViewModel.refresh().safeCollect(this) {
            showContent(it)
        }
    }

    override fun initViews() {
        super.initViews()
        mRecycler.addCustomDecoration()
    }

    override fun onLoadMore() {
        mViewModel.loadMore()
    }

}