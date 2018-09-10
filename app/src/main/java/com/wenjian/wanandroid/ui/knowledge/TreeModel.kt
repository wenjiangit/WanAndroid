package com.wenjian.wanandroid.ui.knowledge

import android.arch.lifecycle.MutableLiveData
import com.wenjian.wanandroid.base.BaseViewModel
import com.wenjian.wanandroid.entity.Article
import com.wenjian.wanandroid.entity.ArticlesResp
import com.wenjian.wanandroid.entity.Resource
import com.wenjian.wanandroid.entity.TreeEntry
import com.wenjian.wanandroid.extension.io2Main
import com.wenjian.wanandroid.model.ApiSubscriber
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

    fun getTree() {
        service.loadTree()
                .io2Main()
                .subscribe(ApiSubscriber(tree, disposables) {
                    @Suppress("UNCHECKED_CAST")
                    val data: List<TreeEntry> = it as List<TreeEntry>
                    tree.value = Resource.success(data)
                })
    }


    fun laodArticles(cid: Int, page: Int = 0) {
        service.loadTreeArticles(page, cid)
                .io2Main()
                .subscribe(ApiSubscriber(articles, disposables) {
                    @Suppress("UNCHECKED_CAST")
                    val data: ArticlesResp = it as ArticlesResp
                    if (data.over) {
                        articles.value = Resource.success(emptyList())
                    } else {
                        articles.value = Resource.success(data.datas)
                    }
                })
    }


}