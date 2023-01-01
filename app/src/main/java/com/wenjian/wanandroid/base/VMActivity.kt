package com.wenjian.wanandroid.base

import androidx.annotation.CallSuper
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.wenjian.wanandroid.extension.apiModelDelegate
import com.wenjian.wanandroid.model.ViewState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

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

}