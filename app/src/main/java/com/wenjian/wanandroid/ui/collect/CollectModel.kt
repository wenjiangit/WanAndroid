package com.wenjian.wanandroid.ui.collect

import android.arch.lifecycle.MutableLiveData
import com.wenjian.wanandroid.base.BaseViewModel
import com.wenjian.wanandroid.entity.Article
import com.wenjian.wanandroid.entity.ListContract
import com.wenjian.wanandroid.entity.Resource
import com.wenjian.wanandroid.extension.io2Main
import com.wenjian.wanandroid.model.ApiSubscriber
import com.wenjian.wanandroid.net.ApiService

/**
 * Description: CollectModel
 * Date: 2018/9/21
 *
 * @author jian.wen@ubtrobot.com
 */
class CollectModel(private val service: ApiService) : BaseViewModel() {

    val collects: MutableLiveData<Resource<List<Article>>> = MutableLiveData()

    private var curPage: Int = 0
    private var isOver: Boolean = false

    fun loadCollects(page: Int = 0) {
        service.loadCollects(page)
                .io2Main()
                .subscribe(ApiSubscriber(collects, disposables) {
                    @Suppress("UNCHECKED_CAST")
                    val data: ListContract<Article> = it as ListContract<Article>
                    curPage = data.curPage
                    isOver = data.over
                    collects.value = Resource.success(data.datas)
                })

    }


    fun loadMore() {
        if (isOver) {
            collects.value = Resource.success(emptyList())
            return
        }
        loadCollects(++curPage)
    }

}