package com.wenjian.wanandroid.ui.home

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.wenjian.wanandroid.base.BaseViewModel
import com.wenjian.wanandroid.entity.Article
import com.wenjian.wanandroid.entity.ListResp
import com.wenjian.wanandroid.entity.Banner
import com.wenjian.wanandroid.entity.Resource
import com.wenjian.wanandroid.extension.io2Main
import com.wenjian.wanandroid.model.ApiSubscriber
import com.wenjian.wanandroid.net.ApiService
import com.wenjian.wanandroid.net.Resp
import io.reactivex.Observable
import io.reactivex.functions.BiFunction

/**
 * Description: HomeModel
 * Date: 2018/9/6
 *
 * @author jian.wen@ubtrobot.com
 */

class HomeModel(private val service: ApiService) : BaseViewModel() {

    companion object {
        val TAG = HomeModel::class.java.simpleName!!
    }

    val articles: MutableLiveData<Resource<List<Article>>> = MutableLiveData()

    val homeData: MutableLiveData<Resource<Pair<List<Banner>, List<Article>>>> = MutableLiveData()

    private var curPage: Int = 0

    private var isOver: Boolean = false

    fun loadHomeData() {
        val loadBanners = service.loadBanners()
        val loadArticles = service.loadArticles(0)
        Observable.zip(loadBanners, loadArticles, BiFunction { t1: Resp<List<Banner>>, t2: Resp<ListResp<Article>> -> Pair(t1, t2) })
                .io2Main()
                .doOnSubscribe {
                    addDisposable(it)
                    homeData.value = Resource.loading()
                }.subscribe({
                    val (first, second) = it
                    if (first.success() && second.success()) {
                        this.curPage = second.data.curPage
                        homeData.value = Resource.success(Pair(first.data, second.data.datas))
                    } else {
                        homeData.value = Resource.fail("${first.errorMsg},${second.errorMsg}")
                    }
                }, {
                    Log.e(TAG, "loadHomeData error:", it)
                    homeData.value = Resource.fail()
                })
    }


    fun loadMoreArticles() {
        if (isOver) {
            articles.value = Resource.success(emptyList())
            return
        }
        service.loadArticles(++curPage)
                .io2Main()
                .subscribe(ApiSubscriber(articles, disposables) {
                    @Suppress("UNCHECKED_CAST")
                    val data: ListResp<Article> = it as ListResp<Article>
                    isOver = data.over
                    curPage = data.curPage
                    articles.value = Resource.success(data.datas)
                })
    }

}