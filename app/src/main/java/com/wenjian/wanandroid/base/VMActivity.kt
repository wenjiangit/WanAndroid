package com.wenjian.wanandroid.base

import androidx.lifecycle.Observer
import androidx.annotation.CallSuper
import android.view.View
import com.wenjian.wanandroid.extension.apiModelDelegate
import com.wenjian.wanandroid.model.DataViewModel
import com.wenjian.wanandroid.model.ViewState

/**
 * Description: VMActivity
 * Date: 2018/10/9
 *
 * @author jian.wen@ubtrobot.com
 */
abstract class VMActivity<VM : BaseViewModel>(clz: Class<VM>) : BaseSkinActivity() {

    open val mViewModel: VM by apiModelDelegate(clz)

    @CallSuper
    override fun bindViewModel() {
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

}