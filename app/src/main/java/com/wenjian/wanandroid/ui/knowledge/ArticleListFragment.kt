package com.wenjian.wanandroid.ui.knowledge


import android.arch.lifecycle.Observer
import android.os.Bundle
import com.wenjian.wanandroid.base.BaseListFragment
import com.wenjian.wanandroid.base.BaseRecyclerAdapter
import com.wenjian.wanandroid.entity.Article
import com.wenjian.wanandroid.extension.addCustomDecoration
import com.wenjian.wanandroid.extension.extraDelegate
import com.wenjian.wanandroid.ui.adapter.ArticleListAdapter

/**
 * Description: ArticleListFragment
 * Date: 2018/9/6
 *
 * @author jian.wen@ubtrobot.com
 */
class ArticleListFragment : BaseListFragment<Article, TreeModel>(TreeModel::class.java) {
    override fun createAdapter(): BaseRecyclerAdapter<Article> = ArticleListAdapter()

    private val cid: Int? by extraDelegate(ARG_ID)

    companion object {
        private const val ARG_ID = "category_id"

        fun newInstance(id: Int): ArticleListFragment {
            val bundle = Bundle().apply { putInt(ARG_ID, id) }
            return ArticleListFragment().apply { arguments = bundle }
        }
    }

    override fun initViews() {
        super.initViews()
        mRecycler.addCustomDecoration()
    }

    override fun subscribeUi() {
        super.subscribeUi()
        mViewModel.loadData().observe(this, Observer { data ->
            showContent(data)
        })
    }

    override fun onLazyLoad() {
        super.onLazyLoad()
        mViewModel.refresh(cid!!)
    }

    override fun onLoadMore() {
        mViewModel.loadMore()
    }
}
