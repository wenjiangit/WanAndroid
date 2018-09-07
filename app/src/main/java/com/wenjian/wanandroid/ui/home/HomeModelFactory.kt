package com.wenjian.wanandroid.ui.home

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.wenjian.wanandroid.net.ApiService

/**
 * Description: HomeModelFactory
 * Date: 2018/9/6
 *
 * @author jian.wen@ubtrobot.com
 */
class HomeModelFactory(private val service: ApiService) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeModel(service) as T
    }
}