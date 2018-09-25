package com.wenjian.wanandroid.ui.home

import android.arch.lifecycle.Observer
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bigkoo.convenientbanner.ConvenientBanner
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator
import com.bigkoo.convenientbanner.holder.Holder
import com.wenjian.wanandroid.R
import com.wenjian.wanandroid.base.BaseListFragment
import com.wenjian.wanandroid.base.BaseRecyclerAdapter
import com.wenjian.wanandroid.entity.Article
import com.wenjian.wanandroid.entity.Banner
import com.wenjian.wanandroid.entity.WebModel
import com.wenjian.wanandroid.extension.addCustomDecoration
import com.wenjian.wanandroid.extension.apiModelDelegate
import com.wenjian.wanandroid.extension.loadUrl
import com.wenjian.wanandroid.ui.adapter.ArticleListAdapter
import com.wenjian.wanandroid.ui.web.WebActivity

/**
 * Description: HomeFragment
 * Date: 2018/9/6
 *
 * @author jian.wen@ubtrobot.com
 */
class HomeFragment : BaseListFragment<Article>() {

    override fun createAdapter(): BaseRecyclerAdapter<Article> = ArticleListAdapter()

    private val mHomeModel: HomeModel by apiModelDelegate(HomeModel::class.java)

    private lateinit var mBanner: ConvenientBanner<Banner>

    override fun initViews() {
        super.initViews()
        mRecycler.addCustomDecoration(drawable = R.drawable.divider_tree)
        mBanner = LayoutInflater.from(context).inflate(R.layout.lay_banner, mRecycler, false) as ConvenientBanner<Banner>
        mAdapter.addHeaderView(mBanner)
    }

    override fun subscribeUi() {
        //首次加载数据
        mHomeModel.homeData.observe(this, Observer { it ->
            showContentWithStatus(it) { data ->
                val bannerData = data.first
                mBanner.setPages(HolderCreator(), bannerData)
                        .setOnItemClickListener {
                            val banner = bannerData[it]
                            WebActivity.start(context, WebModel(banner.id, banner.url, false))
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
        mBanner.startTurning()
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
        mBanner.stopTurning()
    }

    class HolderCreator : CBViewHolderCreator {
        override fun createHolder(itemView: View): Holder<Banner> {
            return object : Holder<Banner>(itemView) {

                lateinit var imageView: ImageView
                lateinit var tvDesc: TextView

                override fun updateUI(data: Banner) {
                    imageView.loadUrl(data.imagePath)
                    tvDesc.text = data.title
                }

                override fun initView(itemView: View) {
                    imageView = itemView.findViewById(R.id.imageView)
                    tvDesc = itemView.findViewById(R.id.tv_desc)
                }
            }
        }

        override fun getLayoutId(): Int = R.layout.banner_item

    }

}