package com.wenjian.wanandroid.ui.search

import android.arch.lifecycle.MutableLiveData
import com.wenjian.wanandroid.base.BaseViewModel
import com.wenjian.wanandroid.entity.Article
import com.wenjian.wanandroid.entity.ArticlesResp
import com.wenjian.wanandroid.entity.Resource
import com.wenjian.wanandroid.extension.io2Main
import com.wenjian.wanandroid.model.ApiSubscriber
import com.wenjian.wanandroid.net.ApiService

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
                .subscribe(ApiSubscriber(articles, disposables) {
                    val data: ArticlesResp = it as ArticlesResp
                    count = data.curPage
                    if (data.over && loadMore) {
                        articles.value = Resource.success(emptyList())
                    } else {
                        articles.value = Resource.success(data.datas)
                    }
                })
    }
}