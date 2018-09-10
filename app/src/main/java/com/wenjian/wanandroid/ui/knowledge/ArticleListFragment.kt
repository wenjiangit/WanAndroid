package com.wenjian.wanandroid.ui.knowledge


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import com.wenjian.wanandroid.R
import com.wenjian.wanandroid.base.BaseFragment
import com.wenjian.wanandroid.di.Injector
import com.wenjian.wanandroid.extension.apiModelDelegate
import com.wenjian.wanandroid.extension.extraDelegate
import com.wenjian.wanandroid.ui.adapter.ArticleListAdapter
import kotlinx.android.synthetic.main.fragment_article_list.*

/**
 * A simple [Fragment] subclass.
 *
 */
class ArticleListFragment : BaseFragment() {

    private val mAdapter: ArticleListAdapter by lazy {
        ArticleListAdapter()
    }

    private lateinit var mTreeModel: TreeModel

    private val cid: Int? by extraDelegate(ARG_ID)

    companion object {
        private const val ARG_ID = "category_id"

        fun newInstance(id: Int): ArticleListFragment {
            val bundle = Bundle().apply { putInt(ARG_ID, id) }
            return ArticleListFragment().apply { arguments = bundle }
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_article_list

    override fun initViews() {
        super.initViews()
        subRecycler.setHasFixedSize(true)
        subRecycler.layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false)
        subRecycler.adapter = mAdapter
    }

    override fun subscribeUi() {
        super.subscribeUi()
        mTreeModel = ViewModelProviders.of(this, Injector.provideApiModelFactory())
                .get(TreeModel::class.java)

        mTreeModel.articles.observe(this, Observer {
            showContentWithStatus(it) {
                mAdapter.setNewData(it)
            }
        })
    }

    override fun onLazyLoad() {
        super.onLazyLoad()
        mTreeModel.laodArticles(cid!!)
    }


}
