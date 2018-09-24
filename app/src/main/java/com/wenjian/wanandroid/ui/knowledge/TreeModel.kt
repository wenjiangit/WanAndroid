package com.wenjian.wanandroid.ui.knowledge

import android.arch.lifecycle.MutableLiveData
import com.wenjian.wanandroid.base.BaseViewModel
import com.wenjian.wanandroid.entity.Article
import com.wenjian.wanandroid.entity.ListContract
import com.wenjian.wanandroid.entity.Resource
import com.wenjian.wanandroid.entity.TreeEntry
import com.wenjian.wanandroid.extension.io2Main
import com.wenjian.wanandroid.model.ApiObserver
import com.wenjian.wanandroid.model.PagingObserver
import com.wenjian.wanandroid.net.ApiService

/**
 * Description TreeModel
 *
 * Date 2018/9/8
 * @author wenjianes@163.com
 */
class TreeModel(private val service: ApiService) : BaseViewModel() {

    val tree: MutableLiveData<Resource<List<TreeEntry>>> = MutableLiveData()

    val articles: MutableLiveData<Resource<List<Article>>> = MutableLiveData()

    private var isOver: Boolean = false
    private var pageCount: Int = 0
    private var cid: Int = -1

    fun loadTree() {
        service.loadTree()
                .io2Main()
                .subscribe(ApiObserver(tree, disposables))
    }


    fun loadArticles(cid: Int, page: Int = 0) {
        if (this.cid == -1) {
            this.cid = cid
        }
        service.loadTreeArticles(page, cid)
                .io2Main()
                .subscribe(PagingObserver(articles, disposables) {
                    isOver = it.over
                    pageCount = it.curPage
                })
    }

    fun loadMore() {
        if (isOver) {
            articles.value = Resource.success(emptyList())
            return
        }
        loadArticles(cid, ++pageCount)
    }


}