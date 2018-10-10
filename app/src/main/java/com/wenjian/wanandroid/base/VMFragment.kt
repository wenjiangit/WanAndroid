package com.wenjian.wanandroid.base

import android.arch.lifecycle.Observer
import android.support.annotation.CallSuper
import com.wenjian.wanandroid.extension.apiModelDelegate
import com.wenjian.wanandroid.model.DataViewModel
import com.wenjian.wanandroid.model.ViewState
import com.wenjian.wanandroid.model.view.ViewCallback
import org.jetbrains.anko.support.v4.toast

/**
 * Description: VM
 * Date: 2018/10/10
 *
 * @author jian.wen@ubtrobot.com
 */
abstract class VMFragment<VM : DataViewModel>(clz: Class<VM>) : BaseFragment(), ViewCallback {

    open val mViewModel: VM by apiModelDelegate(clz)

    @CallSuper
    override fun subscribeUi() {
        mViewModel.viewState.observe(this, Observer { state ->
            state?.let {
                when (it.state) {
                    ViewState.State.LOADING -> showLoading()
                    ViewState.State.ERROR -> showError(it.extra)
                    ViewState.State.EMPTY -> showEmpty()
                    ViewState.State.HIDE_LOADING ->hideLoading()
                }
            }
        })
    }


    override fun showEmpty() {
    }

    override fun showError(msg: String?) {
        msg?.let {
            toast(msg)
        }
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }
}