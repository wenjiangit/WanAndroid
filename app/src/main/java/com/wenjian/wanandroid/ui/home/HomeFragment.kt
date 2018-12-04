package com.wenjian.wanandroid.ui.home

import android.arch.lifecycle.Observer
import android.view.LayoutInflater
import com.wenjian.loopbanner.LoopBanner
import com.wenjian.wanandroid.R
import com.wenjian.wanandroid.base.BaseListFragment
import com.wenjian.wanandroid.base.BaseRecyclerAdapter
import com.wenjian.wanandroid.entity.Article
import com.wenjian.wanandroid.extension.addCustomDecoration
import com.wenjian.wanandroid.ui.adapter.ArticleListAdapter

/**
 * Description: HomeFragment
 * Date: 2018/9/6
 *
 * @author jian.wen@ubtrobot.com
 */
class HomeFragment : BaseListFragment<Article, HomeModel>(HomeModel::class.java) {

    override fun createAdapter(): BaseRecyclerAdapter<Article> = ArticleListAdapter()

    private lateinit var mBannerPager: LoopBanner

    override fun initViews() {
        super.initViews()
        mRecycler.addCustomDecoration(drawable = R.drawable.divider_tree)
        mBannerPager = LayoutInflater.from(context).inflate(R.layout.lay_home_banner, mRecycler, false) as LoopBanner
        mAdapter.addHeaderView(mBannerPager)
    }

    override fun subscribeUi() {
        super.subscribeUi()
        mViewModel.loadArticles().observe(this, Observer { data ->
            data?.let {
                mAdapter.addData(it)
                mAdapter.loadMoreComplete()
            }
        })
    }

    override fun onLoadMore() {
        mViewModel.loadMore()
    }

    override fun onLazyLoad() {
        super.onLazyLoad()
        mViewModel.loadHomeData().observe(this, Observer { data ->
            data?.let {
                val (bannerData, second) = it
                mBannerPager.setImages(bannerData.map { it.imagePath })
                mAdapter.setNewData(second)
            }
        })
    }

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

}