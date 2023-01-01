package com.wenjian.wanandroid.base

import android.os.Bundle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.wenjian.wanandroid.extension.apiModelDelegate
import com.wenjian.wanandroid.extension.toastError
import com.wenjian.wanandroid.model.DataViewModel
import com.wenjian.wanandroid.model.ViewState
import com.wenjian.wanandroid.model.view.ViewCallback
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * Description: VM
 * Date: 2018/10/10
 *
 * @author jian.wen@ubtrobot.com
 */
abstract class VMFragment<VM : DataViewModel>(clz: Class<VM>) : BaseFragment(), ViewCallback {

    open val mViewModel: VM by apiModelDelegate(clz)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel.viewState.onEach { state ->
            when (state) {
                is ViewState.Loading -> showLoading()
                is ViewState.Error -> showError(state.msg)
                is ViewState.Empty -> showEmpty()
                is ViewState.NoLoading -> hideLoading()
            }
        }.flowWithLifecycle(lifecycle)
            .launchIn(lifecycleScope)
    }

    override fun showEmpty() {
    }

    override fun showError(msg: String?) {
        msg?.let {
            context?.toastError(msg)
        }
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }
}