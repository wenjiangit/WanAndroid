package com.wenjian.wanandroid.ui.collect

import android.arch.lifecycle.MutableLiveData
import com.tencent.bugly.crashreport.BuglyLog
import com.wenjian.wanandroid.base.BaseViewModel
import com.wenjian.wanandroid.entity.Article
import com.wenjian.wanandroid.entity.Resource
import com.wenjian.wanandroid.extension.io2Main
import com.wenjian.wanandroid.model.PagingObserver
import com.wenjian.wanandroid.net.ApiService

/**
 * Description: CollectModel
 * Date: 2018/9/21
 *
 * @author jian.wen@ubtrobot.com
 */
class CollectModel(private val service: ApiService) : BaseViewModel() {

    val collects: MutableLiveData<Resource<List<Article>>> = MutableLiveData()
    val collect: MutableLiveData<Boolean> = MutableLiveData()
    val uncollect: MutableLiveData<Boolean> = MutableLiveData()

    private var curPage: Int = 0
    private var isOver: Boolean = false

    fun loadCollects(page: Int = 0) {
        service.loadCollects(page)
                .io2Main()
                .subscribe(PagingObserver(collects, disposables) {
                    curPage = it.curPage
                    isOver = it.over
                })


    }


    fun loadMore() {
        if (isOver) {
            collects.value = Resource.success(emptyList())
            return
        }
        loadCollects(++curPage)
    }

    fun collect(id: Int) {
        service.collect(id)
                .io2Main()
                .doOnSubscribe {
                    addDisposable(it)
                }
                .subscribe({
                    collect.value = it.success()
                }, {
                    BuglyLog.e("wj", "collect error", it)
                })
    }

    fun uncollect(id: Int, originId: Int = -1) {
        service.unCollect(id, originId)
                .io2Main()
                .doOnSubscribe {
                    addDisposable(it)
                }
                .subscribe({
                    uncollect.value = it.success()
                }, {
                    BuglyLog.e("wj", "uncollect error", it)
                })
    }

}