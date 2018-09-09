package com.wenjian.wanandroid.ui.search

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.wenjian.wanandroid.base.BaseViewModel
import com.wenjian.wanandroid.entity.Article
import com.wenjian.wanandroid.entity.Resource
import com.wenjian.wanandroid.extension.io2Main
import com.wenjian.wanandroid.net.ApiService
import com.wenjian.wanandroid.ui.home.HomeModel

/**
 * Description ${name}
 *
 * Date 2018/9/9
 * @author wenjianes@163.com
 */
class SearchModel(private val service: ApiService) : BaseViewModel() {

    val articles: MutableLiveData<Resource<List<Article>>> = MutableLiveData()

    private var lastQuery: String? = null

    private var count: Int = 0


    fun loadMode() {
        lastQuery?.let {
            doSearch(it, ++count, true)
        }
    }

    fun doSearch(query: String, page: Int = 0, loadMore: Boolean = false) {
        lastQuery = query
        service.search(query, page)
                .io2Main()
                .doOnSubscribe {
                    addDisposable(it)
                    if (!loadMore) {
                        articles.value = Resource.loading()
                    }
                }.subscribe({
                    if (it.success()) {
                        val data = it.data
                        count = data.curPage
                        if (data.over && loadMore) {
                            articles.value = Resource.success(emptyList())
                        } else {
                            articles.value = Resource.success(data.datas)
                        }
                    }
                }, {
                    Log.e(HomeModel.TAG, "doSearch error:", it)
                    articles.value = Resource.fail()
                })
    }
}