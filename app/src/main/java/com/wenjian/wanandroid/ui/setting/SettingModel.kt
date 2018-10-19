package com.wenjian.wanandroid.ui.setting

import com.wenjian.wanandroid.extension.io2Main
import com.wenjian.wanandroid.helper.UserHelper
import com.wenjian.wanandroid.model.DataViewModel
import com.wenjian.wanandroid.model.RxBus
import com.wenjian.wanandroid.model.SkinChangeEvent
import com.wenjian.wanandroid.model.view.ViewCallbackImpl
import com.wenjian.wanandroid.utils.FileUtil
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

/**
 * Description: SettingModel
 * Date: 2018/10/15
 *
 * @author jian.wen@ubtrobot.com
 */
class SettingModel : DataViewModel() {

    private var disposable: Disposable? = null

    fun logout() = getRepository().logout(ViewCallbackImpl(viewState)) {
        //退出登录后,清空本地用户信息
        UserHelper.logOut()
        RxBus.post(SkinChangeEvent())
    }

    fun isLogin() = UserHelper.isLogin()

    fun clearCache() {
        FileUtil.deleteFile(getApp().externalCacheDir)
    }

    fun getCacheSize() = FileUtil.getFormatSize(getApp().externalCacheDir)

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}