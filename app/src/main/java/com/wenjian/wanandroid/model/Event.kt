package com.wenjian.wanandroid.model

/**
 * Description: Event
 * Date: 2018/9/26
 *
 * @author jian.wen@ubtrobot.com
 */

sealed class Event {

    /**
     * 我的页面用户信息刷新事件
     */
    object UserInfoRefresh : Event()

    /**
     * 皮肤改变事件
     */
    object SkinChange : Event()

}

