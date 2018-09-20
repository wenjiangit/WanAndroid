package com.wenjian.wanandroid.ui.login

import android.arch.lifecycle.MutableLiveData
import com.wenjian.wanandroid.base.BaseViewModel
import com.wenjian.wanandroid.entity.Resource
import com.wenjian.wanandroid.entity.UserInfo
import com.wenjian.wanandroid.extension.io2Main
import com.wenjian.wanandroid.helper.UserHelper
import com.wenjian.wanandroid.model.ApiSubscriber
import com.wenjian.wanandroid.net.ApiService

/**
 * Description: UserModel
 * Date: 2018/9/20
 *
 * @author jian.wen@ubtrobot.com
 */

class UserModel(private val service: ApiService) : BaseViewModel() {

    val userInfo: MutableLiveData<Resource<UserInfo>> = MutableLiveData()

    fun login(username: String, password: String) {
        service.login(username, password)
                .io2Main()
                .subscribe(ApiSubscriber(userInfo, disposables) {
                    val data: UserInfo = it as UserInfo
                    userInfo.value = Resource.success(data)
                    UserHelper.saveUserInfo(data)
                })
    }

    fun register() {

    }

}