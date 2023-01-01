package com.wenjian.wanandroid.net

import com.wenjian.wanandroid.BuildConfig
import com.wenjian.wanandroid.WanAndroidApp
import com.zyj.retrofit.adapter.FlowCallAdapterFactory
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Description: RetrofitManager
 * Date: 2018/9/5
 *
 * @author jian.wen@ubtrobot.com
 */
object RetrofitManager {

    private const val BASE_URL = "https://www.wanandroid.com"
    private const val TIME_OUT = 10L
    private const val MAX_CACHE_SIZE: Long = 50 * 1024 * 1024

    private val retrofit: Retrofit

    private val loggingIntercept: HttpLoggingInterceptor = HttpLoggingInterceptor()
            .apply { level = HttpLoggingInterceptor.Level.BODY }

    private val myLogInterceptor: Interceptor = Interceptor { chain ->
        var response = chain.proceed(chain.request())

        if (response.cacheResponse != null && response.networkResponse == null) {
            //表示该响应来自缓存
            response = response.newBuilder()
                    .message("from-cache")
                    .build()
        }
        response
    }

    init {

        val cache = Cache(File(WanAndroidApp.instance.externalCacheDir, "okhttp_cache"), MAX_CACHE_SIZE)

        val okhttpBuilder = OkHttpClient.Builder()
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(CacheStrategy.REWRITE_RESPONSE_INTERCEPTOR_OFFLINE)
                .addNetworkInterceptor(CacheStrategy.REWRITE_RESPONSE_INTERCEPTOR)
                .cache(cache)
                .cookieJar(CookieJarImpl.create(WanAndroidApp.instance))

        if (BuildConfig.DEBUG) {
            //调试环境添加请求日志
            okhttpBuilder
                    .addInterceptor(loggingIntercept)
                    .addInterceptor(myLogInterceptor)
        }

        retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(FlowCallAdapterFactory.createAsync())
                .baseUrl(BASE_URL)
                .client(okhttpBuilder.build())
                .build()
    }


    val service: ApiService by lazy { retrofit.create(ApiService::class.java) }


}