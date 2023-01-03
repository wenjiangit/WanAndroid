package com.wenjian.wanandroid.ui.setting

import com.wenjian.wanandroid.helper.UserHelper
import com.wenjian.wanandroid.model.DataViewModel
import com.wenjian.wanandroid.model.RxBus
import com.wenjian.wanandroid.model.SkinChangeEvent
import com.wenjian.wanandroid.model.onSuccess
import com.wenjian.wanandroid.utils.FileUtil
import io.reactivex.disposables.Disposable

/**
 * Description: SettingModel
 * Date: 2018/10/15
 *
 * @author jian.wen@ubtrobot.com
 */
class SettingModel : DataViewModel() {

    private var disposable: Disposable? = null

    fun logout() = repository.logout()
        .withCommonHandler()
        .onSuccess {
            //退出登录后,清空本地用户信息
            UserHelper.logOut()
            RxBus.post(SkinChangeEvent())
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


    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}