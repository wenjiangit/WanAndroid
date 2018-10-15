package com.wenjian.wanandroid.base

import android.arch.lifecycle.AndroidViewModel
import com.wenjian.wanandroid.WanAndroidApp
import com.wenjian.wanandroid.model.SingleLiveEvent
import com.wenjian.wanandroid.model.ViewState
import com.wenjian.wanandroid.model.data.DataRepository

/**
 * Description: BaseViewModel
 * Date: 2018/9/6
 *
 * @author jian.wen@ubtrobot.com
 */
open class BaseViewModel : AndroidViewModel(WanAndroidApp.instance) {

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

}