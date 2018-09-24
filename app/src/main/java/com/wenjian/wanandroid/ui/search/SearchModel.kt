package com.wenjian.wanandroid.ui.search

import android.arch.lifecycle.MutableLiveData
import com.wenjian.wanandroid.base.BaseViewModel
import com.wenjian.wanandroid.entity.Article
import com.wenjian.wanandroid.entity.HotWord
import com.wenjian.wanandroid.entity.Resource
import com.wenjian.wanandroid.extension.io2Main
import com.wenjian.wanandroid.model.ApiObserver
import com.wenjian.wanandroid.model.PagingObserver
import com.wenjian.wanandroid.net.ApiService

/**
 * Description ${name}
 *
 * Date 2018/9/9
 * @author wenjianes@163.com
 */
class SearchModel(private val service: ApiService) : BaseViewModel() {

    val articles: MutableLiveData<Resource<List<Article>>> = MutableLiveData()
    val hotWords: MutableLiveData<Resource<List<HotWord>>> = MutableLiveData()

    private var lastQuery: String? = null
    private var count: Int = 0
    private var isOver: Boolean = false

    fun loadHotWords() {
        service.loadHotWords()
                .io2Main()
                .subscribe(ApiObserver(hotWords, disposables))
    }

    fun loadMore() {
        if (isOver) {
            articles.value = Resource.success(emptyList())
            return
        }
        lastQuery?.let {
            doSearch(it, ++count)
        }
    }

    fun doSearch(query: String, page: Int = 0) {
        lastQuery = query
        service.search(query, page)
                .io2Main()
                .subscribe(PagingObserver(articles, disposables) {
                    count = it.curPage
                    isOver = it.over
                })
    }
}