package com.wenjian.wanandroid.model


/**
 * Description: ViewState
 * Date: 2018/10/9
 *
 * @author jian.wen@ubtrobot.com
 */
sealed class ViewState {
    object Loading : ViewState()
    object Empty : ViewState()
    class Error(val msg: String?) : ViewState()
    object NoLoading : ViewState()
}