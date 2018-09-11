package com.wenjian.wanandroid.net

import com.wenjian.wanandroid.entity.ArticlesResp
import com.wenjian.wanandroid.entity.Banner
import com.wenjian.wanandroid.entity.HotWord
import com.wenjian.wanandroid.entity.TreeEntry
import io.reactivex.Observable
import retrofit2.http.*

/**
 * Description: ApiService
 * Date: 2018/9/5
 *
 * @author jian.wen@ubtrobot.com
 */
interface ApiService {

    @GET("/banner/json")
    fun loadBanners(): Observable<Resp<List<Banner>>>

    @GET("/article/list/{pager}/json")
    fun loadArticles(@Path("pager") pager: Int): Observable<Resp<ArticlesResp>>

    @GET("/tree/json")
    fun loadTree(): Observable<Resp<List<TreeEntry>>>

    @FormUrlEncoded
    @POST("/article/query/{page}/json")
    fun search(@Field("k") k: String, @Path("page") page: Int): Observable<Resp<ArticlesResp>>

    @GET("/article/list/{pager}/json")
    fun loadTreeArticles(@Path("pager") pager: Int, @Query("cid") cid: Int): Observable<Resp<ArticlesResp>>

    @GET("/hotkey/json")
    fun loadHotWords(): Observable<Resp<List<HotWord>>>


}