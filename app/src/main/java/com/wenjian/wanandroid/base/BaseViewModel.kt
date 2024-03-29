package com.wenjian.wanandroid.base

import androidx.lifecycle.AndroidViewModel
import com.wenjian.wanandroid.WanAndroidApp
import com.wenjian.wanandroid.helper.ExceptionHelper
import com.wenjian.wanandroid.model.ViewState
import com.wenjian.wanandroid.model.data.DataRepository
import com.wenjian.wanandroid.model.view.ViewCallback
import com.wenjian.wanandroid.net.Resp
import com.wenjian.wanandroid.net.getOrThrow
import com.wenjian.wanandroid.net.onFail
import kotlinx.coroutines.Dispatchers
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

    fun <T> Flow<Resp<T>>.withLoading(): Flow<Resp<T>> {
        return this.onStart {
            showLoading()
        }.onCompletion {
            hideLoading()
        }
    }

    fun <T> Flow<Resp<T>>.withErrorHandler(): Flow<Resp<T>> {
        return this.map { Resp(it.getOrThrow()) }
            .catch { emit(Resp.buildFailure(it)) }
            .onFail {
                ExceptionHelper.handleFailure(it)
                showError(it.errorMsg)
            }
    }

    fun <T> Flow<Resp<T>>.withCommonHandler(): Flow<Resp<T>> {
        return this.withLoading()
            .withErrorHandler()
            .flowOn(Dispatchers.IO)
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