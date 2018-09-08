package com.wenjian.wanandroid.ui.knowledge

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.wenjian.wanandroid.net.ApiService

/**
 * Description ${name}
 *
 * Date 2018/9/8
 * @author wenjianes@163.com
 */

class TreeModelFactory(private val service: ApiService) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TreeModel(service) as T
    }
}
