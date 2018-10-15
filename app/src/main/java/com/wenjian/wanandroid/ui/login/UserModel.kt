package com.wenjian.wanandroid.ui.login

import com.wenjian.wanandroid.entity.UserInfo
import com.wenjian.wanandroid.helper.UserHelper
import com.wenjian.wanandroid.model.DataViewModel
import com.wenjian.wanandroid.model.RxBus
import com.wenjian.wanandroid.model.UserInfoRefreshEvent
import com.wenjian.wanandroid.model.view.ViewCallbackImpl

/**
 * Description: UserModel
 * Date: 2018/9/20
 *
 * @author jian.wen@ubtrobot.com
 */

class UserModel : DataViewModel() {

    fun login(username: String, password: String) = getRepository()
            .login(username, password, ViewCallbackImpl(viewState)) {
                handleUserInfo(it!!)
            }

    fun register(username: String, password: String, repass: String) = getRepository()
            .register(username, password, repass, ViewCallbackImpl(viewState)) {
                handleUserInfo(it!!)
            }

    private fun handleUserInfo(info: UserInfo) {
        UserHelper.saveUserInfo(info)
        RxBus.post(UserInfoRefreshEvent())
    }
}