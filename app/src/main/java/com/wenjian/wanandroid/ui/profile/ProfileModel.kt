package com.wenjian.wanandroid.ui.profile

import com.wenjian.wanandroid.helper.UserHelper
import com.wenjian.wanandroid.model.DataViewModel
import com.wenjian.wanandroid.model.RxBus
import com.wenjian.wanandroid.model.UserInfoRefreshEvent
import com.wenjian.wanandroid.model.view.ViewCallbackImpl

/**
 * Description: ProfileModel
 * Date: 2018/10/11
 *
 * @author jian.wen@ubtrobot.com
 */
class ProfileModel : DataViewModel() {

    fun logout() = repository.logout(ViewCallbackImpl(viewState)) {
        //退出登录后,清空本地用户信息
        UserHelper.logOut()
        RxBus.post(UserInfoRefreshEvent())
    }

    fun getUserInfo() = UserHelper.getUserInfo()


}