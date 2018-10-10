package com.wenjian.wanandroid.model.data

import android.arch.lifecycle.LiveData
import com.wenjian.wanandroid.entity.*
import com.wenjian.wanandroid.model.BaseRepository
import com.wenjian.wanandroid.model.view.ViewCallback
import com.wenjian.wanandroid.net.Resp
import io.reactivex.Observable
import io.reactivex.functions.BiFunction

/**
 * Description: DataRepository
 * Date: 2018/10/9
 *
 * @author jian.wen@ubtrobot.com
 */
class DataRepository private constructor() : BaseRepository() {

    fun loadArticles(pager: Int, callback: ViewCallback,
                     handle: (ListContract<List<Article>>) -> Unit): LiveData<List<Article>> {
        return doListAction(mService.loadArticles(pager), callback, handle)
    }

    fun loadHomeData(callback: ViewCallback): LiveData<Pair<List<Banner>, List<Article>>?> {
        val banners = mService.loadBanners()
        val articles = mService.loadArticles(0)
        return doSimpleAction(Observable.zip(banners, articles, BiFunction { t1, t2 ->
            Resp.Builder<Pair<List<Banner>, List<Article>>>()
                    .code(t1.errorCode)
                    .msg(t1.errorMsg)
                    .data(Pair(t1.data, t2.data.datas))
                    .build()
        }), callback) {}

    }

    fun loadTree(callback: ViewCallback): LiveData<List<TreeEntry>> {
        return doSimpleAction(mService.loadTree(), callback) {}
    }

    fun search(k: String, page: Int, callback: ViewCallback, handle: (ListContract<List<Article>>) -> Unit): LiveData<List<Article>> {
        return doListAction(mService.search(k, page), callback, handle)
    }

    fun loadTreeArticles(pager: Int, cid: Int, callback: ViewCallback, handle: (ListContract<List<Article>>) -> Unit): LiveData<List<Article>> {
        return doListAction(mService.loadTreeArticles(pager, cid), callback, handle)
    }

    fun loadHotWords(callback: ViewCallback): LiveData<List<HotWord>> {
        return doSimpleAction(mService.loadHotWords(), callback) {}
    }

    fun loadProjectTree(callback: ViewCallback): LiveData<List<ProjectTree>> {
        return doSimpleAction(mService.loadProjectTree(), callback) {}
    }

    fun loadProjects(pager: Int, cid: Int,
                     callback: ViewCallback,
                     handle: (ListContract<List<Project>>) -> Unit): LiveData<List<Project>> {
        return doListAction(mService.loadProjects(pager, cid), callback, handle)
    }

    fun login(username: String, password: String,
              callback: ViewCallback,
              handle: (UserInfo) -> Unit): LiveData<UserInfo> {
        return doSimpleAction(mService.login(username, password), callback, handle)
    }

    fun register(username: String, password: String, repassword: String,
                 callback: ViewCallback,
                 handle: (UserInfo) -> Unit):
            LiveData<UserInfo> {
        return doSimpleAction(mService.register(username, password, repassword), callback, handle)
    }

    fun logout(callback: ViewCallback): LiveData<Unit> {
        return doSimpleAction(mService.logout(), callback) {}
    }

    fun loadCollects(pager: Int, callback: ViewCallback, handle: (ListContract<List<Article>>) -> Unit): LiveData<List<Article>> {
        return doListAction(mService.loadCollects(pager), callback, handle)
    }

    fun collect(id: Int, callback: ViewCallback): LiveData<Unit> {
        return doSimpleAction(mService.collect(id), callback) {}
    }

    fun unCollect(id: Int, originId: Int,
                  callback: ViewCallback): LiveData<Unit> {
        return doSimpleAction(mService.unCollect(id, originId), callback) {}
    }

    companion object {
        private var sInstance: DataRepository? = null

        fun getInstance() = sInstance ?: synchronized(DataRepository::class.java) {
            sInstance ?: DataRepository().also {
                sInstance = it
            }
        }
    }

}