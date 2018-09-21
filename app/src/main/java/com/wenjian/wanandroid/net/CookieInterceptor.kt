package com.wenjian.wanandroid.net

import com.wenjian.wanandroid.helper.UserHelper
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Description: 添加身份验证
 * Date: 2018/9/21
 *
 * @author jian.wen@ubtrobot.com
 */
class CookieInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (UserHelper.isLogin()) {
            val userInfo = UserHelper.getUserInfo()
            request = request.newBuilder()
                    .addHeader("Cookie", "loginUserName=${userInfo?.username}")
                    .addHeader("Cookie", "loginUserPassword=${userInfo?.password}")
                    .build()
        }
        return chain.proceed(request)
    }
}