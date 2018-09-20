package com.wenjian.wanandroid.entity

/**
 * Description: UserInfo
 * Date: 2018/9/20
 *
 * @author jian.wen@ubtrobot.com
 */

data class UserInfo(
    val collectIds: List<Int>,
    val email: String,
    val icon: String,
    val id: Int,
    val password: String,
    val token: String,
    val type: Int,
    val username: String
)