package com.wenjian.wanandroid.model.data

import com.wenjian.wanandroid.entity.Article
import com.wenjian.wanandroid.entity.Banner
import com.wenjian.wanandroid.model.BaseRepository
import com.wenjian.wanandroid.net.ApiService
import com.wenjian.wanandroid.net.Resp
import com.wenjian.wanandroid.net.RetrofitManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

/**
 * Description: DataRepository
 * Date: 2018/10/9
 *
 * @author jian.wen@ubtrobot.com
 */
class DataRepository(private val apiService: ApiService = RetrofitManager.service) :
    BaseRepository(),
    ApiService by apiService {

    fun loadHomeData(): Flow<Resp<Pair<List<Banner>, List<Article>>>> {
        val bannerFlow = apiService.loadBanners()
        val articleFlow = apiService.loadArticles(0)
        val topArticleFlow = apiService.topArticles()
        return combine(bannerFlow, articleFlow, topArticleFlow) { banner, home, top ->
            Resp.Builder<Pair<List<Banner>, List<Article>>>()
                .code(top.errorCode)
                .msg(top.errorMsg)
                .data(Pair(banner.data ?: emptyList(), mutableListOf<Article>().apply {
                    top.data?.let { addAll(it) }
                    home.data?.let { addAll(it.datas) }
                }))
                .build()
        }
    }
}