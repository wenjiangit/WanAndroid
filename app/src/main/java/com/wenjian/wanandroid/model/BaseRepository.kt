package com.wenjian.wanandroid.model

import com.wenjian.wanandroid.net.ApiService
import com.wenjian.wanandroid.net.RetrofitManager

/**
 * Description ${name}
 *
 * Date 2018/10/5
 * @author wenjianes@163.com
 */
open class BaseRepository {

    open val mService: ApiService by lazy { RetrofitManager.service }

}