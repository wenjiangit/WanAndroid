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
import com.wenjian.wanandroid.extension.apiModelDelegate
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
class HomeFragment : BaseListFragment<Article>() {

    override fun createAdapter(): BaseRecyclerAdapter<Article> = ArticleListAdapter()

    private val mHomeModel: HomeModel by apiModelDelegate(HomeModel::class.java)

    private lateinit var mBanner:com.youth.banner.Banner

    override fun initViews() {
        super.initViews()
        mRecycler.addCustomDecoration(drawable = R.drawable.divider_tree)
        mBanner = LayoutInflater.from(context).inflate(R.layout.lay_banner, mRecycler, false) as Banner
        mAdapter.addHeaderView(mBanner)
    }

    override fun subscribeUi() {
        //首次加载数据
        mHomeModel.homeData.observe(this, Observer { it ->
            showContentWithStatus(it) { data ->
                val bannerData = data.first
                mBanner.apply {
                    setImages(bannerData.map { it.imagePath })
                    setBannerTitles(bannerData.map { it.title })
                    setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
                    setDelayTime(2000)
                    setImageLoader(GlideImageLoader())
                    setBannerAnimation(Transformer.Accordion)
                    setOnBannerListener {
                        val banner = bannerData[it]
                        WebActivity.start(context, WebModel(banner.id, banner.url, false))
                    }
                    setIndicatorGravity(BannerConfig.RIGHT)
                    start()
                }
                mAdapter.setNewData(data.second)
            }
        })

        //加载更多
        mHomeModel.articles.observe(this, Observer { it ->
            showContent(it)
        })
    }

    override fun onStart() {
        super.onStart()
        mBanner.startAutoPlay()
    }

    override fun onLoadMore() {
        mHomeModel.loadMoreArticles()
    }


    override fun onLazyLoad() {
        super.onLazyLoad()
        mHomeModel.loadHomeData()
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


    class GlideImageLoader :ImageLoaderInterface<ImageView>{
        override fun createImageView(context: Context?): ImageView {
            return ImageView(context)
        }

        override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
            Glide.with(context!!).load(path).into(imageView!!)
        }
    }

}