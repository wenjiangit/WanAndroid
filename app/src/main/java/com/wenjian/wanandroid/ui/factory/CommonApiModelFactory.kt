package com.wenjian.wanandroid.ui.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wenjian.wanandroid.net.ApiService

/**
 * Description ${name}
 *
 * Date 2018/9/9
 * @author wenjianes@163.com
 */

class CommonApiModelFactory : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.newInstance()
    }
}