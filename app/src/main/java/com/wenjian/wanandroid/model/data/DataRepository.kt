package com.wenjian.wanandroid.model.data

import androidx.lifecycle.LiveData
import com.wenjian.wanandroid.entity.*
import com.wenjian.wanandroid.model.BaseRepository
import com.wenjian.wanandroid.model.WResult
import com.wenjian.wanandroid.model.asWResultFlow
import com.wenjian.wanandroid.model.view.ViewCallback
import com.wenjian.wanandroid.net.Resp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

/**
 * Description: DataRepository
 * Date: 2018/10/9
 *
 * @author jian.wen@ubtrobot.com
 */
class DataRepository : BaseRepository() {

    fun loadArticles(pager: Int): Flow<WResult<ListContract<List<Article>>>> {
        return mService.loadArticles(pager).asWResultFlow()
    }

    fun loadHomeData(): Flow<WResult<Pair<List<Banner>, List<Article>>?>> {
        val banners = mService.loadBanners()
        val articles = mService.loadArticles(0)
        return banners.combine(articles) { a, b ->
            Resp.Builder<Pair<List<Banner>, List<Article>>>()
                .code(a.errorCode)
                .msg(a.errorMsg)
                .data(Pair(a.data, b.data.datas))
                .build()
        }.asWResultFlow()
    }

    fun loadTree(callback: ViewCallback): LiveData<List<TreeEntry>> {
        return doSimpleAction(mService.loadTree(), callback) {}
    }

    fun search(
        k: String,
        page: Int,
        callback: ViewCallback,
        handle: (ListContract<List<Article>>) -> Unit
    ): LiveData<List<Article>> {
        return doListAction(mService.search(k, page), callback, handle)
    }

    fun loadTreeArticles(
        pager: Int,
        cid: Int,
        callback: ViewCallback,
        handle: (ListContract<List<Article>>) -> Unit
    ): LiveData<List<Article>> {
        return doListAction(mService.loadTreeArticles(pager, cid), callback, handle)
    }

    fun loadHotWords(callback: ViewCallback): LiveData<List<HotWord>> {
        return doSimpleAction(mService.loadHotWords(), callback) {}
    }

    fun loadProjectTree(callback: ViewCallback): LiveData<List<ProjectTree>> {
        return doSimpleAction(mService.loadProjectTree(), callback) {}
    }

    fun loadProjects(
        pager: Int, cid: Int,
        callback: ViewCallback,
        handle: (ListContract<List<Project>>) -> Unit
    ): LiveData<List<Project>> {
        return doListAction(mService.loadProjects(pager, cid), callback, handle)
    }

    fun login(
        username: String, password: String,
        callback: ViewCallback,
        handle: (UserInfo?) -> Unit
    ): LiveData<UserInfo> {
        return doSimpleAction(mService.login(username, password), callback, handle)
    }

    fun register(
        username: String, password: String, repassword: String,
        callback: ViewCallback,
        handle: (UserInfo?) -> Unit
    ):
            LiveData<UserInfo> {
        return doSimpleAction(mService.register(username, password, repassword), callback, handle)
    }

    fun logout(callback: ViewCallback, handle: (Unit?) -> Unit): LiveData<Unit> {
        return doSimpleAction(mService.logout(), callback, handle)
    }

    fun loadCollects(
        pager: Int,
        callback: ViewCallback,
        handle: (ListContract<List<Article>>) -> Unit
    ): LiveData<List<Article>> {
        return doListAction(mService.loadCollects(pager), callback, handle)
    }

    fun collect(id: Int, callback: ViewCallback): LiveData<Unit> {
        return doSimpleAction(mService.collect(id), callback) {}
    }

    fun unCollect(
        id: Int, originId: Int,
        callback: ViewCallback
    ): LiveData<Unit> {
        return doSimpleAction(mService.unCollect(id, originId), callback) {}
    }

    fun modifyPassword(
        curPass: String, password: String, repassword: String,
        callback: ViewCallback, handle: (Unit?) -> Unit
    ): LiveData<Unit> {
        return doSimpleAction(
            mService.modifyPassword(curPass, password, repassword),
            callback,
            handle
        )
    }
}