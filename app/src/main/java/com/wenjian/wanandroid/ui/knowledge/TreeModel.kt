package com.wenjian.wanandroid.ui.knowledge

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.wenjian.wanandroid.base.BaseViewModel
import com.wenjian.wanandroid.entity.Resource
import com.wenjian.wanandroid.entity.TreeEntry
import com.wenjian.wanandroid.extension.io2Main
import com.wenjian.wanandroid.net.ApiService

/**
 * Description TreeModel
 *
 * Date 2018/9/8
 * @author wenjianes@163.com
 */
class TreeModel(private val service: ApiService) : BaseViewModel() {


    val tree: MutableLiveData<Resource<List<TreeEntry>>> = MutableLiveData()

    fun getTree() {
        service.loadTree()
                .io2Main()
                .doOnSubscribe {
                    addDisposable(it)
                    tree.value = Resource.loading()
                }
                .subscribe({
                    if (it.success()) {
                        tree.value = Resource.success(it.data)
                    } else {
                        tree.value = Resource.fail(it.errorMsg)
                    }
                }, {
                    Log.e("wj", "getTree error:", it)
                    tree.value = Resource.fail()
                })
    }


}