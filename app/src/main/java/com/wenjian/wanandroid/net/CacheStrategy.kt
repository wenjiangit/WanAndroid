package com.wenjian.wanandroid.net

import com.wenjian.wanandroid.WanAndroidApp
import com.wenjian.wanandroid.utils.NetUtil
import okhttp3.Interceptor

/**
 * Description 自定义缓存策略
 *
 * Date 2018/10/6
 * @author wenjianes@163.com
 */
object CacheStrategy {

    private const val TIMEOUT_DISCONNECT: Int = 60 * 60 * 24 * 7
    //默认有网的缓存时长为1分钟
    private const val MAX_AGE: Int = 60

    private const val CACHE_CONTROL = "Cache-Control"

    const val CUSTOM_HEADER = "cache"


    val REWRITE_RESPONSE_INTERCEPTOR: Interceptor = Interceptor { chain ->

        var originResponse = chain.proceed(chain.request())
        val cacheControl = originResponse.header(CACHE_CONTROL)
        if (cacheControl == null) {
            //读取自定义缓存时间
            val cache = chain.request().header(CUSTOM_HEADER)
            originResponse = originResponse.newBuilder()
                    .header(CACHE_CONTROL, "public, max-age=${if (cache.isNullOrBlank()) MAX_AGE.toString() else cache}")
                    .build()
        }
        originResponse
    }

    val REWRITE_RESPONSE_INTERCEPTOR_OFFLINE = Interceptor { chain ->

        var request = chain.request()

        if (!NetUtil.isNetworkAvailable(WanAndroidApp.instance)) {
            request = request.newBuilder()
                    .header(CACHE_CONTROL, "public, only-if-cached, max-stale=$TIMEOUT_DISCONNECT")
                    .build()
        }


        chain.proceed(request)

    }

}