package com.wenjian.wanandroid.base

import androidx.lifecycle.AndroidViewModel
import com.wenjian.wanandroid.WanAndroidApp
import com.wenjian.wanandroid.model.SingleLiveEvent
import com.wenjian.wanandroid.model.ViewState
import com.wenjian.wanandroid.model.data.DataRepository
import com.wenjian.wanandroid.model.view.ViewCallback

/**
 * Description: BaseViewModel
 * Date: 2018/9/6
 *
 * @author jian.wen@ubtrobot.com
 */
open class BaseViewModel : AndroidViewModel(WanAndroidApp.instance), ViewCallback {


    val viewState: SingleLiveEvent<ViewState> = SingleLiveEvent()

    private var repository: DataRepository? = null

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
    }

    open fun getApp() = getApplication<WanAndroidApp>()
}