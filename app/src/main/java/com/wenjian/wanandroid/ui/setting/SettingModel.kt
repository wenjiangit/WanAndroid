package com.wenjian.wanandroid.ui.setting

import com.wenjian.wanandroid.helper.UserHelper
import com.wenjian.wanandroid.model.*
import com.wenjian.wanandroid.net.onSuccess
import com.wenjian.wanandroid.utils.FileUtil

/**
 * Description: SettingModel
 * Date: 2018/10/15
 *
 * @author jian.wen@ubtrobot.com
 */
class SettingModel : DataViewModel() {

    fun logout() = repository.logout()
        .withCommonHandler()
        .onSuccess {
            //退出登录后,清空本地用户信息
            UserHelper.logOut()
            FlowEventBus.post(Event.SkinChange)
        }

    fun isLogin() = UserHelper.isLogin()

    fun clearCache() {
        FileUtil.deleteFile(getApp().externalCacheDir!!)
    }

    fun getCacheSize() = FileUtil.getFormatSize(getApp().externalCacheDir!!)

    fun modifyPass(curPass: String, newPass: String, rePass: String) =
        repository
            .modifyPassword(curPass, newPass, rePass)
            .withCommonHandler()

}