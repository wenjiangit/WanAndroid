package com.wenjian.wanandroid.model.view

/**
 * Description: ViewFeature
 * Date: 2018/10/9
 *
 * @author jian.wen@ubtrobot.com
 */
interface ViewFeature {

    fun showLoading()

    fun hideLoading()

    fun showEmpty()

    fun showError(msg: String?)

}