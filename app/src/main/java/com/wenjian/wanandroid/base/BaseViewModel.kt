package com.wenjian.wanandroid.base

import androidx.lifecycle.AndroidViewModel
import com.wenjian.wanandroid.WanAndroidApp
import com.wenjian.wanandroid.helper.ExceptionHelper
import com.wenjian.wanandroid.model.ViewState
import com.wenjian.wanandroid.model.WResult
import com.wenjian.wanandroid.model.data.DataRepository
import com.wenjian.wanandroid.model.onFail
import com.wenjian.wanandroid.model.view.ViewCallback
import kotlinx.coroutines.flow.*

/**
 * Description: BaseViewModel
 * Date: 2018/9/6
 *
 * @author jian.wen@ubtrobot.com
 */
open class BaseViewModel : AndroidViewModel(WanAndroidApp.instance), ViewCallback {

    private val _viewState = MutableStateFlow<ViewState>(ViewState.NoLoading)
    val viewState = _viewState.asStateFlow()
    val repository by lazy { DataRepository() }

    fun <T> Flow<WResult<T>>.withLoading(): Flow<WResult<T>> {
        return this.onStart {
            showLoading()
        }.onCompletion {
            hideLoading()
        }
    }

    fun <T> Flow<WResult<T>>.withErrorHandle(): Flow<WResult<T>> {
        return this.onFail {
            ExceptionHelper.handleFailure(it)
            showError(it.errorMsg)
        }
    }


    override fun onCleared() {
        super.onCleared()
        repository.unSubscribe()
    }

    override fun showLoading() {
        _viewState.value = ViewState.Loading
    }

    override fun hideLoading() {
        _viewState.value = ViewState.NoLoading
    }

    override fun showEmpty() {
        _viewState.value = ViewState.Empty
    }

    override fun showError(msg: String?) {
        _viewState.value = ViewState.Error(msg)
    }

    open fun updateViewState(viewState: ViewState) {
        _viewState.value = viewState
    }

    open fun getApp() = getApplication<WanAndroidApp>()
}