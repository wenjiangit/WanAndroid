package com.wenjian.wanandroid.ui.home

import android.arch.lifecycle.Observer
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bigkoo.convenientbanner.ConvenientBanner
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator
import com.bigkoo.convenientbanner.holder.Holder
import com.wenjian.wanandroid.R
import com.wenjian.wanandroid.base.BaseFragment
import com.wenjian.wanandroid.entity.Banner
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
class HomeFragment : BaseFragment() {

    private val mHomeModel: HomeModel by apiModelDelegate(HomeModel::class.java)

    private val mArticleAdapter: ArticleListAdapter by lazy {
        ArticleListAdapter()
    }
    private lateinit var mArticleRecycler: RecyclerView
    private lateinit var mBanner: ConvenientBanner<Banner>
    private lateinit var mSwipeRefresh: SwipeRefreshLayout

    private var isLoadMore: Boolean = false

    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun findViews(mRoot: View) {
        mArticleRecycler = mRoot.findViewById(R.id.articleRecycler)
        mSwipeRefresh = mRoot.findViewById(R.id.swipeRefresh)
    }

    override fun initViews() {
        mArticleRecycler.adapter = mArticleAdapter
        mArticleRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        mArticleRecycler.addCustomDecoration(drawable = R.drawable.divider_tree)

        mBanner = LayoutInflater.from(context).inflate(R.layout.lay_banner, mArticleRecycler, false) as ConvenientBanner<Banner>

        mArticleAdapter.addHeaderView(mBanner)

        mArticleAdapter.openLoadAnimation()
        mArticleAdapter.setEnableLoadMore(true)
        mArticleAdapter.setOnLoadMoreListener({
            isLoadMore = true
            mHomeModel.loadMoreArticles()
        }, mArticleRecycler)

        mSwipeRefresh.setColorSchemeResources(R.color.colorPrimary)
        mSwipeRefresh.setOnRefreshListener {
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
        if (!isLoadMore) {
            mSwipeRefresh.isRefreshing = true
        }
    }

    override fun hideLoading() {
        mSwipeRefresh.isRefreshing = false
    }

    override fun onStart() {
        super.onStart()
        mBanner.startTurning()
    }


    override fun onLazyLoad() {
        super.onLazyLoad()
        isLoadMore = false
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