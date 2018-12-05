package com.wenjian.wanandroid.ui.home

import android.arch.lifecycle.Observer
import android.view.LayoutInflater
import android.widget.ImageView
import com.wenjian.loopbanner.LoopAdapter
import com.wenjian.loopbanner.LoopBanner
import com.wenjian.wanandroid.R
import com.wenjian.wanandroid.base.BaseListFragment
import com.wenjian.wanandroid.base.BaseRecyclerAdapter
import com.wenjian.wanandroid.entity.Article
import com.wenjian.wanandroid.entity.Banner
import com.wenjian.wanandroid.entity.WebModel
import com.wenjian.wanandroid.extension.addCustomDecoration
import com.wenjian.wanandroid.extension.loadUrl
import com.wenjian.wanandroid.ui.adapter.ArticleListAdapter
import com.wenjian.wanandroid.ui.web.WebActivity

/**
 * Description: HomeFragment
 * Date: 2018/9/6
 *
 * @author jian.wen@ubtrobot.com
 */
class HomeFragment : BaseListFragment<Article, HomeModel>(HomeModel::class.java) {

    override fun createAdapter(): BaseRecyclerAdapter<Article> = ArticleListAdapter()

    private lateinit var mBannerPager: LoopBanner

//    private val mBannerAdapter: BannerAdapter by lazy { BannerAdapter() }

    override fun initViews() {
        super.initViews()
        mRecycler.addCustomDecoration(drawable = R.drawable.divider_tree)
        mBannerPager = LayoutInflater.from(context).inflate(R.layout.lay_home_banner, mRecycler, false) as LoopBanner
        mBannerPager.apply {
            setCanLoop(true)
            setLrMargin(20)
            pageMargin = 6
            interval = 3000
        }
//        mBannerPager.setAdapter(mBannerAdapter)
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
//                mBannerAdapter.setNewData(bannerData)
                mBannerPager.setAdapter(BannerAdapter(bannerData))
                mAdapter.setNewData(second)
            }
        })
    }

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    class BannerAdapter(data:List<Banner>) : LoopAdapter<Banner>(data,R.layout.lay_banner_item) {
        override fun onBindView(holder: ViewHolder, data: Banner) {
            val image = holder.getView<ImageView>(R.id.iv_image)
            image.loadUrl(data.imagePath)
            holder.itemView.setOnClickListener {
                WebActivity.start(holder.itemView.context, WebModel(data.id, data.url, false))
            }
        }
    }
}