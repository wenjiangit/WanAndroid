package com.wenjian.wanandroid.ui.home

import android.arch.lifecycle.Observer
import android.content.Context
import android.os.Handler
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.wenjian.wanandroid.R
import com.wenjian.wanandroid.base.BaseListFragment
import com.wenjian.wanandroid.base.BaseRecyclerAdapter
import com.wenjian.wanandroid.entity.Article
import com.wenjian.wanandroid.extension.addCustomDecoration
import com.wenjian.wanandroid.extension.loadUrl
import com.wenjian.wanandroid.extension.logI
import com.wenjian.wanandroid.ui.adapter.ArticleListAdapter
import com.wenjian.wanandroid.utils.Tools
import com.youth.banner.loader.ImageLoaderInterface

/**
 * Description: HomeFragment
 * Date: 2018/9/6
 *
 * @author jian.wen@ubtrobot.com
 */
class HomeFragment : BaseListFragment<Article, HomeModel>(HomeModel::class.java) {

    override fun createAdapter(): BaseRecyclerAdapter<Article> = ArticleListAdapter()

    lateinit var mBannerPager: ViewPager

    private val mHandler = Handler()

    private var mCurrentIndex: Int = 0

    private val mLoopRunnable = object : Runnable {
        override fun run() {
            mBannerPager.currentItem = ++mCurrentIndex
            mHandler.postDelayed(this, INTERVAL_TIME)
        }
    }

    override fun initViews() {
        super.initViews()
        mRecycler.addCustomDecoration(drawable = R.drawable.divider_tree)
        val inflateView = LayoutInflater.from(context).inflate(R.layout.lay_home_banner, mRecycler, false)
        mBannerPager = inflateView.findViewById(R.id.banner_pager)
        mBannerPager.pageMargin = Tools.dpToPx(context!!, 4)
        mBannerPager.offscreenPageLimit = 1

        mAdapter.addHeaderView(inflateView)
    }


    override fun onStart() {
        super.onStart()
        mBannerPager.currentItem = INITIAIL_INDEX
        mBannerPager.pageMargin = Tools.dpToPx(context!!, 4)
        mHandler.postDelayed(mLoopRunnable, INTERVAL_TIME)
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
                mBannerPager.adapter = MyPagerAdapter(context!!, bannerData.map { it.imagePath })
                mAdapter.setNewData(second)
            }
        })
    }

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }

        private const val INITIAIL_INDEX = 1000000
        private const val INTERVAL_TIME = 3000L
    }

    override fun onStop() {
        super.onStop()
        mHandler.removeCallbacks(mLoopRunnable)
    }

    class MyPagerAdapter(context: Context, private val urls: List<String>) : PagerAdapter() {

        private val size: Int = urls.size

        private val mViewList: SparseArray<ImageView> = SparseArray()

        override fun getCount(): Int = Int.MAX_VALUE

        override fun isViewFromObject(view: View, obj: Any): Boolean {
            return view == obj
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            logI("instantiateItem == $position")
            val newPos = position % size
            val imageView = mViewList[newPos] ?: ImageView(container.context).apply {
                scaleType = ImageView.ScaleType.CENTER_CROP
                loadUrl(urls[newPos])
            }
            container.addView(imageView)
            return imageView
        }

        override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
            container.removeView(obj as View)
            logI("destroyItem == $position")
            mViewList.put(position % size, obj as ImageView)
        }
    }


}