package com.wenjian.wanandroid.base

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.wenjian.wanandroid.R
import com.wenjian.wanandroid.extension.addCustomDecoration

/**
 * Description: BaseListFragment
 * Date: 2018/9/12
 *
 * @author jian.wen@ubtrobot.com
 */
abstract class BaseListFragment<T> : BaseFragment() {

    private lateinit var mRecycler: RecyclerView
    private lateinit var mLayRefresh: SwipeRefreshLayout
    open lateinit var mAdapter: BaseRecyclerAdapter<T>

    open var isLoadMore: Boolean = false

    override fun getLayoutId(): Int = R.layout.fragment_base_list

    override fun findViews(mRoot: View) {
        mRecycler = mRoot.findViewById(R.id.list_recycler)
        mLayRefresh = mRoot.findViewById(R.id.lay_refresh)
    }

    abstract fun createAdapter(): BaseRecyclerAdapter<T>

    override fun initViews() {
        mRecycler.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addCustomDecoration()
            mAdapter = createAdapter()
            mRecycler.adapter = mAdapter
        }

        mAdapter.apply {
            openLoadAnimation()
            setEnableLoadMore(true)
            setOnLoadMoreListener({
                isLoadMore = true
                onLoadMore()
            }, mRecycler)
        }

        mLayRefresh.setOnRefreshListener {
            refresh()
        }
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

    override fun onLazyLoad() {
        isLoadMore = false
    }

    open fun onLoadMore() {

    }

}
