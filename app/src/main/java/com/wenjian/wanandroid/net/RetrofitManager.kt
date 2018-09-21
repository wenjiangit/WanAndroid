package com.wenjian.wanandroid.net

import android.util.Log
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Description: RetrofitManager
 * Date: 2018/9/5
 *
 * @author jian.wen@ubtrobot.com
 */
object RetrofitManager {

    private const val BASE_URL = "http://www.wanandroid.com"

    private const val TIME_OUT = 10L

    private val okHttpClient: OkHttpClient

    private val retrofit: Retrofit

    private val loggingIntercept: HttpLoggingInterceptor = HttpLoggingInterceptor()
            .apply { level = HttpLoggingInterceptor.Level.BODY }

    init {
        okHttpClient = OkHttpClient.Builder()
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(loggingIntercept)
                .addInterceptor(CookieInterceptor())
                .cookieJar(object : CookieJar {
                    private val cookieStore: HashMap<String, MutableList<Cookie>> = HashMap()

                    override fun saveFromResponse(url: HttpUrl, cookies: MutableList<Cookie>) {
                        Log.i("wj", "receive cookie ${url.host()} >>> $cookies")
                        cookieStore[url.host()] = cookies
                    }

                    override fun loadForRequest(url: HttpUrl): MutableList<Cookie> {
                        val list = cookieStore[url.host()]
                        return list ?: arrayListOf()
                    }
                })
                .build()


        retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .build()
    }


    val service: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }


}