package com.wenjian.wanandroid.ui.knowledge


import android.arch.lifecycle.Observer
import android.os.Bundle
import com.wenjian.wanandroid.base.BaseListFragment
import com.wenjian.wanandroid.base.BaseRecyclerAdapter
import com.wenjian.wanandroid.entity.Article
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
class ArticleListFragment : BaseListFragment<Article>() {
    override fun createAdapter(): BaseRecyclerAdapter<Article> = ArticleListAdapter()

    private val mTreeModel: TreeModel by apiModelDelegate(TreeModel::class.java)

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
        mTreeModel.articles.observe(this, Observer { it ->
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

    override fun onLazyLoad() {
        super.onLazyLoad()
        mTreeModel.loadArticles(cid!!)
    }

    override fun onLoadMore() {
        mTreeModel.loadMore()
    }
}
