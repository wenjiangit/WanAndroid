package com.wenjian.wanandroid.net

import com.wenjian.wanandroid.entity.*
import io.reactivex.Maybe
import io.reactivex.Observable
import retrofit2.http.*

/**
 * Description: ApiService
 * Date: 2018/9/5
 *
 * @author jian.wen@ubtrobot.com
 */
interface ApiService {

    /**
     * 首页banner
     */
    @GET("/banner/json")
    fun loadBanners(): Observable<Resp<List<Banner>>>

    /**
     * 首页文章
     */
    @GET("/article/list/{pager}/json")
    fun loadArticles(@Path("pager") pager: Int): Observable<PagingResp<List<Article>>>

    /**
     * 体系
     */
    @GET("/tree/json")
    fun loadTree(): Observable<Resp<List<TreeEntry>>>

    /**
     * 搜索
     */
    @FormUrlEncoded
    @POST("/article/query/{page}/json")
    fun search(@Field("k") k: String, @Path("page") page: Int): Observable<PagingResp<List<Article>>>

    /**
     * 体系文章
     */
    @GET("/article/list/{pager}/json")
    fun loadTreeArticles(@Path("pager") pager: Int, @Query("cid") cid: Int):
            Observable<PagingResp<List<Article>>>

    /**
     * 热词
     */
    @GET("/hotkey/json")
    fun loadHotWords(): Observable<Resp<List<HotWord>>>

    /**
     * 项目分类
     */
    @GET("/project/tree/json")
    fun loadProjectTree(): Observable<Resp<List<ProjectTree>>>

    /**
     * 项目列表
     */
    @GET("/project/list/{page}/json")
    fun loadProjects(@Path("page") pager: Int, @Query("cid") cid: Int):
            Observable<PagingResp<List<Project>>>

    /**
     * 登录
     */
    @FormUrlEncoded
    @POST("/user/login")
    fun login(@Field("username") username: String, @Field("password") password: String): Observable<Resp<UserInfo>>

    /**
     * 注册
     */
    @FormUrlEncoded
    @POST("/user/register")
    fun register(@Field("username") username: String,
                 @Field("password") password: String,
                 @Field("repassword") repassword: String): Observable<Resp<UserInfo>>

    /**
     * 登出
     */
    @GET("/user/logout/json")
    fun logout(): Observable<Resp<Unit>>

    /**
     * 修改密码
     */
    @FormUrlEncoded
    @POST("/user/lg/password")
    fun modifyPassword(@Field("curPassword") curPass: String,
                       @Field("password") password: String,
                       @Field("repassword") repassword: String): Observable<Resp<Unit>>

    /**
     * 我的收藏
     */
    @GET("/lg/collect/list/{page}/json")
    fun loadCollects(@Path("page") pager: Int): Observable<PagingResp<List<Article>>>

    /**
     * 添加收藏
     */
    @POST("/lg/collect/{id}/json")
    fun collect(@Path("id") id: Int): Observable<Resp<Unit>>

    /**
     * 添加收藏
     */
    @POST("/lg/collect/{id}/json")
    fun collectPost(@Path("id") id: Int): Maybe<Resp<Any>>


    /**
     * 取消收藏
     */
    @FormUrlEncoded
    @POST("/lg/uncollect/{id}/json")
    fun unCollect(@Path("id") id: Int, @Field("originId") originId: Int): Observable<Resp<Unit>>


    /**
     * 取消收藏
     */
    @FormUrlEncoded
    @POST("/lg/uncollect/{id}/json")
    fun unCollectPost(@Path("id") id: Int, @Field("originId") originId: Int): Maybe<Resp<Any>>


}