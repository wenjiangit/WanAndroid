package com.wenjian.wanandroid.ui.login

import com.wenjian.wanandroid.entity.UserInfo
import com.wenjian.wanandroid.helper.UserHelper
import com.wenjian.wanandroid.model.DataViewModel
import com.wenjian.wanandroid.model.RxBus
import com.wenjian.wanandroid.model.UserInfoRefreshEvent
import com.wenjian.wanandroid.model.onSuccess

/**
 * Description: UserModel
 * Date: 2018/9/20
 *
 * @author jian.wen@ubtrobot.com
 */

class UserModel : DataViewModel() {

    fun login(username: String, password: String) = repository
        .login(username, password)
        .withCommonHandler()
        .onSuccess {
            handleUserInfo(it)
        }

    fun register(username: String, password: String, repass: String) = repository
        .register(username, password, repass)
        .withCommonHandler()
        .onSuccess {
            handleUserInfo(it)
        }

    private fun handleUserInfo(info: UserInfo) {
        UserHelper.saveUserInfo(info)
        RxBus.post(UserInfoRefreshEvent())
    }
}