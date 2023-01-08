package com.wenjian.wanandroid.net

import com.wenjian.wanandroid.entity.*
import kotlinx.coroutines.flow.Flow
import retrofit2.http.*

/**
 * Description: ApiService
 * Date: 2018/9/5
 *
 * @author jian.wen@ubtrobot.comp
 */
interface ApiService {

    /**
     * 首页banner
     */
    @GET("/banner/json")
    fun loadBanners(): Flow<Resp<List<Banner>>>

    /**
     * 首页文章
     */
    @GET("/article/list/{pager}/json")
    fun loadArticles(@Path("pager") pager: Int): Flow<PagingResp<Article>>

    /**
     * 首页文章
     */
    @GET("/article/top/json")
    fun topArticles(): Flow<Resp<List<Article>>>

    /**
     * 问答
     */
    @GET("/wenda/list/{pager}/json")
    fun dailyQuestions(@Path("pager") pager: Int): Flow<PagingResp<Article>>

    /**
     * 体系
     */
    @GET("/tree/json")
    fun loadTree(): Flow<Resp<List<TreeEntry>>>

    /**
     * 搜索
     */
    @FormUrlEncoded
    @POST("/article/query/{page}/json")
    fun search(@Field("k") k: String, @Path("page") page: Int): Flow<PagingResp<Article>>

    /**
     * 体系文章
     */
    @GET("/article/list/{pager}/json")
    fun loadTreeArticles(@Path("pager") pager: Int, @Query("cid") cid: Int):
            Flow<PagingResp<Article>>

    /**
     * 热词
     */
    @GET("/hotkey/json")
    fun loadHotWords(): Flow<Resp<List<HotWord>>>

    /**
     * 项目分类
     */
    @GET("/project/tree/json")
    fun loadProjectTree(): Flow<Resp<List<ProjectTree>>>

    /**
     * 项目列表
     */
    @GET("/project/list/{page}/json")
    fun loadProjects(@Path("page") pager: Int, @Query("cid") cid: Int):
            Flow<PagingResp<Project>>

    /**
     * 登录
     */
    @FormUrlEncoded
    @POST("/user/login")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Flow<Resp<UserInfo>>

    /**
     * 注册
     */
    @FormUrlEncoded
    @POST("/user/register")
    fun register(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("repassword") repassword: String
    ): Flow<Resp<UserInfo>>

    /**
     * 登出
     */
    @GET("/user/logout/json")
    fun logout(): Flow<Resp<Unit>>

    /**
     * 修改密码
     */
    @FormUrlEncoded
    @POST("/user/lg/password")
    fun modifyPassword(
        @Field("curPassword") curPass: String,
        @Field("password") password: String,
        @Field("repassword") repassword: String
    ): Flow<Resp<Unit>>

    /**
     * 我的收藏
     */
    @GET("/lg/collect/list/{page}/json")
    fun loadCollects(@Path("page") pager: Int): Flow<PagingResp<Article>>

    /**
     * 添加收藏
     */
    @POST("/lg/collect/{id}/json")
    fun collect(@Path("id") id: Int): Flow<Resp<Unit>>

    /**
     * 添加收藏
     */
    @POST("/lg/collect/{id}/json")
    fun collectPost(@Path("id") id: Int): Flow<Resp<Any>>


    /**
     * 取消收藏
     */
    @FormUrlEncoded
    @POST("/lg/uncollect/{id}/json")
    fun unCollect(@Path("id") id: Int, @Field("originId") originId: Int): Flow<Resp<Unit>>


    /**
     * 取消收藏
     */
    @FormUrlEncoded
    @POST("/lg/uncollect/{id}/json")
    fun unCollectPost(@Path("id") id: Int, @Field("originId") originId: Int): Flow<Resp<Any>>


}