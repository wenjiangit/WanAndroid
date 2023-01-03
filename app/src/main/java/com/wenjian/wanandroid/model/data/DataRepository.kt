package com.wenjian.wanandroid.model.data

import com.wenjian.wanandroid.entity.*
import com.wenjian.wanandroid.model.BaseRepository
import com.wenjian.wanandroid.model.WResult
import com.wenjian.wanandroid.model.asWResultFlow
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

    fun loadArticles(pager: Int): Flow<WResult<ListContract<Article>>> {
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

    fun loadTree(): Flow<WResult<List<TreeEntry>>> {
        return mService.loadTree().asWResultFlow()
    }

    fun search(
        k: String,
        page: Int
    ): Flow<WResult<ListContract<Article>>> {
        return mService.search(k, page).asWResultFlow()
    }

    fun loadTreeArticles(
        pager: Int,
        cid: Int
    ): Flow<WResult<ListContract<Article>>> {
        return mService.loadTreeArticles(pager, cid).asWResultFlow()
    }

    fun loadHotWords(): Flow<WResult<List<HotWord>>> {
        return mService.loadHotWords().asWResultFlow()
    }

    fun loadProjectTree(): Flow<WResult<List<ProjectTree>>> {
        return mService.loadProjectTree().asWResultFlow()
    }

    fun loadProjects(
        pager: Int, cid: Int
    ): Flow<WResult<ListContract<Project>>> {
        return mService.loadProjects(pager, cid).asWResultFlow()
    }

    fun login(
        username: String, password: String
    ): Flow<WResult<UserInfo>> {
        return mService.login(username, password).asWResultFlow()
    }

    fun register(
        username: String, password: String, repassword: String
    ): Flow<WResult<UserInfo>> {
        return mService.register(username, password, repassword).asWResultFlow()
    }

    fun logout(): Flow<WResult<Unit>> {
        return mService.logout().asWResultFlow()
    }

    fun loadCollects(
        pager: Int
    ): Flow<WResult<ListContract<Article>>> {
        return mService.loadCollects(pager).asWResultFlow()
    }

    fun collect(id: Int): Flow<WResult<Unit>> {
        return mService.collect(id).asWResultFlow()
    }

    fun unCollect(
        id: Int, originId: Int
    ): Flow<WResult<Unit>> {
        return mService.unCollect(id, originId).asWResultFlow()
    }

    fun modifyPassword(
        curPass: String, password: String, repassword: String
    ): Flow<WResult<Unit>> {
        return mService.modifyPassword(curPass, password, repassword).asWResultFlow()
    }
}