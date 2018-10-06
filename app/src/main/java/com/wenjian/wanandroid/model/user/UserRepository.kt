package com.wenjian.wanandroid.model.user

import com.wenjian.wanandroid.net.ApiService
import com.wenjian.wanandroid.net.RetrofitManager

/**
 * Description ${name}
 *
 * Date 2018/10/5
 * @author wenjianes@163.com
 */
class UserRepository {

    private val mService: ApiService = RetrofitManager.service

    fun logout(){
        mService.logout().subscribe()
    }


}