package com.wenjian.wanandroid.net

import com.wenjian.wanandroid.WanAndroidApp
import okhttp3.Cache
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

    private const val BASE_URL = "http://www.wanandroid.com"
    private const val TIME_OUT = 10L
    private const val MAX_CACHE_SIZE: Long = 50 * 1024 * 1024

    private val okHttpClient: OkHttpClient
    private val retrofit: Retrofit

    private val loggingIntercept: HttpLoggingInterceptor = HttpLoggingInterceptor()
            .apply { level = HttpLoggingInterceptor.Level.BODY }

    init {

        val cache = Cache(File(WanAndroidApp.instance.externalCacheDir, "okhttp_cache"), MAX_CACHE_SIZE)

        okHttpClient = OkHttpClient.Builder()
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(loggingIntercept)
                .cache(cache)
                .cookieJar(CookieJarImpl.create(WanAndroidApp.instance))
                .build()


        retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .build()
    }


    val service: ApiService by lazy { retrofit.create(ApiService::class.java) }


}