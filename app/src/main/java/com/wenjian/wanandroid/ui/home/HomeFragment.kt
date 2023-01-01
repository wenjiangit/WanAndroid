package com.wenjian.wanandroid.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
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
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * Description: HomeFragment
 * Date: 2018/9/6
 *
 * @author jian.wen@ubtrobot.com
 */
class HomeFragment : BaseListFragment<Article, HomeModel>(HomeModel::class.java) {

    override fun createAdapter(): BaseRecyclerAdapter<Article> = ArticleListAdapter()

    private lateinit var mBannerPager: LoopBanner

    private val mBannerAdapter: BannerAdapter by lazy { BannerAdapter() }

    override fun initViews() {
        super.initViews()
        mRecycler.addCustomDecoration(drawable = R.drawable.divider_tree)
        mBannerPager = LayoutInflater.from(context)
            .inflate(R.layout.lay_home_banner, mRecycler, false) as LoopBanner
        mBannerPager.adapter = mBannerAdapter
//        mBannerPager.openDebug()
        mBannerPager.bindLifecycle(this)
        mAdapter.addHeaderView(mBannerPager)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel.articles.filter { it.isNotEmpty() }
            .onEach {
                mAdapter.addData(it)
                mAdapter.loadMoreComplete()
            }.flowWithLifecycle(lifecycle)
            .launchIn(lifecycleScope)
    }

    override fun onLoadMore() {
        mViewModel.loadMore()
    }

    override fun onLazyLoad() {
        super.onLazyLoad()
        mViewModel.loadHomeData().onEach { pair ->
            val (bannerData, second) = pair
            println(bannerData.map { it.imagePath })
            mBannerAdapter.setNewData(bannerData)
            mAdapter.setNewData(second)
        }.launchIn(lifecycleScope)
    }

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    class BannerAdapter : LoopAdapter<Banner>(R.layout.lay_banner_item) {
        override fun onBindView(holder: ViewHolder, data: Banner, position: Int) {
            val image = holder.getView<ImageView>(R.id.iv_image)
            image.loadUrl(data.imagePath)
            holder.itemView.setOnClickListener {
                WebActivity.start(holder.itemView.context, WebModel(data.id, data.url, false))
            }
        }
    }
}