package com.wenjian.wanandroid.ui.setting

import com.wenjian.wanandroid.helper.UserHelper
import com.wenjian.wanandroid.model.DataViewModel
import com.wenjian.wanandroid.model.RxBus
import com.wenjian.wanandroid.model.SkinChangeEvent
import com.wenjian.wanandroid.model.UserInfoRefreshEvent
import com.wenjian.wanandroid.model.view.ViewCallbackImpl

/**
 * Description: SettingModel
 * Date: 2018/10/15
 *
 * @author jian.wen@ubtrobot.com
 */
class SettingModel : DataViewModel() {

    fun logout() = getRepository().logout(ViewCallbackImpl(viewState)) {
        //退出登录后,清空本地用户信息
        UserHelper.logOut()
        RxBus.post(SkinChangeEvent())
    }

    fun isLogin() = UserHelper.isLogin()
}