package com.wenjian.wanandroid.ui.home

import android.arch.lifecycle.Observer
import android.content.Context
import android.view.LayoutInflater
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.wenjian.wanandroid.R
import com.wenjian.wanandroid.base.BaseListFragment
import com.wenjian.wanandroid.base.BaseRecyclerAdapter
import com.wenjian.wanandroid.entity.Article
import com.wenjian.wanandroid.entity.WebModel
import com.wenjian.wanandroid.extension.addCustomDecoration
import com.wenjian.wanandroid.ui.adapter.ArticleListAdapter
import com.wenjian.wanandroid.ui.web.WebActivity
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import com.youth.banner.loader.ImageLoaderInterface

/**
 * Description: HomeFragment
 * Date: 2018/9/6
 *
 * @author jian.wen@ubtrobot.com
 */
class HomeFragment : BaseListFragment<Article, HomeModel>(HomeModel::class.java) {

    override fun createAdapter(): BaseRecyclerAdapter<Article> = ArticleListAdapter()

    private lateinit var mBanner: com.youth.banner.Banner

    override fun initViews() {
        super.initViews()
        mRecycler.addCustomDecoration(drawable = R.drawable.divider_tree)
        mBanner = LayoutInflater.from(context).inflate(R.layout.lay_banner, mRecycler, false) as Banner
        mAdapter.addHeaderView(mBanner)
    }


    override fun onStart() {
        super.onStart()
        mBanner.startAutoPlay()
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
                mBanner.apply {
                    setImages(bannerData.map { it.imagePath })
                    setBannerTitles(bannerData.map { it.title })
                    setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
                    setDelayTime(2000)
                    setImageLoader(GlideImageLoader())
                    setBannerAnimation(Transformer.Accordion)
                    setOnBannerListener { position ->
                        val banner = bannerData[position]
                        WebActivity.start(context, WebModel(banner.id, banner.url, false))
                    }
                    setIndicatorGravity(BannerConfig.RIGHT)
                    start()
                }
                mAdapter.setNewData(second)
            }
        })
    }

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun onStop() {
        super.onStop()
        mBanner.stopAutoPlay()
    }


    class GlideImageLoader : ImageLoaderInterface<ImageView> {
        override fun createImageView(context: Context?): ImageView {
            return ImageView(context)
        }

        override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
            Glide.with(context!!).load(path).into(imageView!!)
        }
    }

}