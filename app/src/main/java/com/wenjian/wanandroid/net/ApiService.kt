package com.wenjian.wanandroid.net

import com.wenjian.wanandroid.entity.*
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
    fun loadArticles(@Path("pager") pager: Int): Observable<ListResp<Article>>

    @GET("/tree/json")
    fun loadTree(): Observable<Resp<List<TreeEntry>>>

    @FormUrlEncoded
    @POST("/article/query/{page}/json")
    fun search(@Field("k") k: String, @Path("page") page: Int): Observable<ListResp<Article>>

    @GET("/article/list/{pager}/json")
    fun loadTreeArticles(@Path("pager") pager: Int, @Query("cid") cid: Int): Observable<ListResp<Article>>

    @GET("/hotkey/json")
    fun loadHotWords(): Observable<Resp<List<HotWord>>>

    @GET("/project/tree/json")
    fun loadProjectTree(): Observable<Resp<List<ProjectTree>>>

    @GET("/project/list/{page}/json")
    fun loadProjects(@Path("page") pager: Int, @Query("cid") cid: Int): Observable<ListResp<Project>>

    @FormUrlEncoded
    @POST("/user/login")
    fun login(@Field("username") username: String, @Field("password") password: String): Observable<Resp<UserInfo>>

    @FormUrlEncoded
    @POST("/user/register")
    fun register(@Field("username") username: String,
                 @Field("password") password: String,
                 @Field("repassword") repassword: String)

    @GET("/lg/collect/list/{page}/json")
    fun loadCollects(@Path("page") pager: Int)


}