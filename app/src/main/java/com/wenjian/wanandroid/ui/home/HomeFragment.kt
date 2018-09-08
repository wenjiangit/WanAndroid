package com.wenjian.wanandroid.ui.home

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bigkoo.convenientbanner.ConvenientBanner
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator
import com.bigkoo.convenientbanner.holder.Holder
import com.wenjian.wanandroid.R
import com.wenjian.wanandroid.base.BaseFragment
import com.wenjian.wanandroid.di.Injector
import com.wenjian.wanandroid.entity.Banner
import com.wenjian.wanandroid.extension.loadUrl
import com.wenjian.wanandroid.ui.adapter.ArticleListAdapter
import com.wenjian.wanandroid.ui.web.WebActivity
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * Description: HomeFragment
 * Date: 2018/9/6
 *
 * @author jian.wen@ubtrobot.com
 */
class HomeFragment : BaseFragment() {

    private val mHomeModel: HomeModel by lazy {
        ViewModelProviders.of(this, Injector.provideHomeModelFactory()).get(HomeModel::class.java)
    }

    private val mArticleAdapter: ArticleListAdapter by lazy {
        ArticleListAdapter()
    }

    private lateinit var mBanner: ConvenientBanner<Banner>

    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun initViews() {
        articleRecycler.adapter = mArticleAdapter
        articleRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        articleRecycler.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        mBanner = LayoutInflater.from(context).inflate(R.layout.lay_banner, articleRecycler, false) as ConvenientBanner<Banner>

        mArticleAdapter.addHeaderView(mBanner)

        mArticleAdapter.openLoadAnimation()
        mArticleAdapter.setEnableLoadMore(true)
        mArticleAdapter.setOnLoadMoreListener({
            mHomeModel.loadMoreArticles()
        }, articleRecycler)

        swipeRefresh.setColorSchemeResources(R.color.colorPrimary)
        swipeRefresh.setOnRefreshListener {
            refresh()
        }
    }

    override fun subscribeUi() {
        //首次加载数据
        mHomeModel.homeData.observe(this, Observer {
            showContentWithStatus(it) {
                val bannerData = it.first
                mBanner.setPages(HolderCreator(), bannerData)
                        .setOnItemClickListener {
                            WebActivity.start(context, bannerData[it].url)
                        }
                mArticleAdapter.setNewData(it.second)
            }
        })

        //加载更多
        mHomeModel.articles.observe(this, Observer {
            showContentWithStatus(it) {
                if (it.isEmpty()) {
                    mArticleAdapter.loadMoreEnd()
                } else {
                    mArticleAdapter.addData(it)
                    mArticleAdapter.loadMoreComplete()
                }
            }
        })
    }

    override fun showLoading() {
        swipeRefresh.isRefreshing = true
    }

    override fun hideLoading() {
        swipeRefresh.isRefreshing = false
    }

    override fun onStart() {
        super.onStart()
        mBanner.startTurning()
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