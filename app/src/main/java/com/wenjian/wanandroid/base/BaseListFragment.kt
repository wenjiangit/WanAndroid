package com.wenjian.wanandroid.base

import android.support.annotation.CallSuper
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.wenjian.wanandroid.R
import com.wenjian.wanandroid.widget.MyLoadMoreView

/**
 * Description: BaseListFragment
 * Date: 2018/9/12
 *
 * @author jian.wen@ubtrobot.com
 */
abstract class BaseListFragment<T> : BaseFragment() {

    open lateinit var mRecycler: RecyclerView
    open lateinit var mLayRefresh: SwipeRefreshLayout
    open lateinit var mAdapter: BaseRecyclerAdapter<T>

    open var isLoadMore: Boolean = false

    override fun getLayoutId(): Int = R.layout.fragment_base_list

    override fun findViews(mRoot: View) {
        mRecycler = mRoot.findViewById(R.id.list_recycler)
        mLayRefresh = mRoot.findViewById(R.id.lay_refresh)
    }

    abstract fun createAdapter(): BaseRecyclerAdapter<T>

    @CallSuper
    override fun initViews() {
        mRecycler.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            mAdapter = createAdapter()
            mRecycler.adapter = mAdapter
        }

        mAdapter.apply {
            openLoadAnimation()
            isUseEmpty(true)
            if (loadMoreEnable()) {
                setLoadMoreView(MyLoadMoreView())
                setEnableLoadMore(true)
                setOnLoadMoreListener({
                    isLoadMore = true
                    onLoadMore()
                }, mRecycler)
            }
        }

        mLayRefresh.setOnRefreshListener {
            refresh()
        }
    }

    open fun loadMoreEnable(): Boolean {
        return true
    }

    override fun showLoading() {
        if (!isLoadMore) {
            mLayRefresh.isRefreshing = true
        }
    }

    override fun hideLoading() {
        if (mLayRefresh.isRefreshing) {
            mLayRefresh.isRefreshing = false
        }
    }

    @CallSuper
    override fun onLazyLoad() {
        isLoadMore = false
    }


    open fun onLoadMore() {

    }

}
