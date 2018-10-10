package com.wenjian.wanandroid.model

/**
 * Description: ViewState
 * Date: 2018/10/9
 *
 * @author jian.wen@ubtrobot.com
 */
class ViewState(val state: State, val extra: String? = null) {

    companion object {
        fun loading() = ViewState(State.LOADING)
        fun empty() = ViewState(State.EMPTY)
        fun error(msg: String?) = ViewState(State.ERROR, msg)
        fun hideLoading() = ViewState(State.HIDE_LOADING)
    }

    enum class State {
        LOADING, EMPTY, ERROR, HIDE_LOADING
    }

}