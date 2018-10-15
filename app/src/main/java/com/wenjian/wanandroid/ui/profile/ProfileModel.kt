package com.wenjian.wanandroid.ui.profile

import com.wenjian.wanandroid.base.BaseViewModel
import com.wenjian.wanandroid.helper.UserHelper

/**
 * Description: ProfileModel
 * Date: 2018/10/11
 *
 * @author jian.wen@ubtrobot.com
 */
class ProfileModel : BaseViewModel() {
    fun getUserInfo() = UserHelper.getUserInfo()

}