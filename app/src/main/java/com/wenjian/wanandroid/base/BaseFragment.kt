package com.wenjian.wanandroid.base

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wenjian.wanandroid.entity.Resource
import org.jetbrains.anko.support.v4.toast

/**
 * Description: BaseFragment
 * Date: 2018/9/6
 *
 * @author jian.wen@ubtrobot.com
 */
abstract class BaseFragment : Fragment() {

    companion object {
        const val TAG = "wj"
    }

    @LayoutRes
    abstract fun getLayoutId(): Int

    private var viewInitiated: Boolean = false
    private var dataInitiated: Boolean = false
    private var forceUpdate: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    open fun subscribeUi() {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        subscribeUi()
        viewInitiated = true
        tryLoadData()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        Log.i(TAG, "setUserVisibleHint")
        tryLoadData()
    }

    private fun tryLoadData() {
        if (viewInitiated && userVisibleHint && (!dataInitiated || forceUpdate)) {
            onLazyLoad()
            dataInitiated = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i(TAG, "onDestroyView")
        viewInitiated = false
    }

    open fun onLazyLoad() {
        Log.i(TAG, "onLazyLoad")
    }

    open fun showLoading() {

    }

    open fun hideLoading() {

    }

    open fun <T> showContentWithStatus(it: Resource<T>?, refresh: Boolean = true, render: (T) -> Unit) {
        it?.let {
            when (it.status) {
                Resource.STATUS.SUCCESS -> {
                    hideLoading()
                    render(it.data!!)
                }
                Resource.STATUS.LOADING -> {
                    if (refresh) {
                        showLoading()
                    }
                    return
                }
                Resource.STATUS.FAIL -> {
                    hideLoading()
                    it.msg?.let {
                        toast(it)
                    }
                }
            }
        }
    }


    open fun refresh() {
        onLazyLoad()
    }


    open fun initViews() {
    }
}