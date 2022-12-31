package com.wenjian.wanandroid.model.view

import androidx.lifecycle.MutableLiveData
import com.wenjian.wanandroid.model.ViewState

/**
 * Description: ViewCallbackImpl
 * Date: 2018/10/10
 *
 * @author jian.wen@ubtrobot.com
 */
open class ViewCallbackImpl(private val viewState: MutableLiveData<ViewState>) : ViewCallback {

    override fun showLoading() {
        viewState.value = ViewState.loading()
    }

    override fun hideLoading() {
        viewState.value = ViewState.hideLoading()
    }

    override fun showEmpty() {
        viewState.value = ViewState.empty()
    }

    override fun showError(msg: String?) {
        viewState.value = ViewState.error(msg)
        hideLoading()
    }


}
