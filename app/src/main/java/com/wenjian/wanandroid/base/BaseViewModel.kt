package com.wenjian.wanandroid.base

import androidx.lifecycle.AndroidViewModel
import com.wenjian.wanandroid.WanAndroidApp
import com.wenjian.wanandroid.helper.ExceptionHelper
import com.wenjian.wanandroid.model.SingleLiveEvent
import com.wenjian.wanandroid.model.ViewState
import com.wenjian.wanandroid.model.WResult
import com.wenjian.wanandroid.model.data.DataRepository
import com.wenjian.wanandroid.model.onFail
import com.wenjian.wanandroid.model.view.ViewCallback
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

/**
 * Description: BaseViewModel
 * Date: 2018/9/6
 *
 * @author jian.wen@ubtrobot.com
 */
open class BaseViewModel : AndroidViewModel(WanAndroidApp.instance), ViewCallback {


    val viewState: SingleLiveEvent<ViewState> = SingleLiveEvent()

    private var repository: DataRepository? = null

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
        repository?.unSubscribe()
    }

    fun getRepository(): DataRepository {
        if (!needRepository()) {
            throw UnsupportedOperationException("you should set needRepository true")
        }
        return repository ?: DataRepository.getInstance().also {
            repository = it
        }
    }

    open fun needRepository() = false

    override fun showLoading() {
        viewState.postValue(ViewState.loading())
    }

    override fun hideLoading() {
        viewState.postValue(ViewState.hideLoading())
    }

    override fun showEmpty() {
        viewState.postValue(ViewState.empty())
    }

    override fun showError(msg: String?) {
        viewState.postValue(ViewState.error(msg))
    }

    open fun getApp() = getApplication<WanAndroidApp>()
}