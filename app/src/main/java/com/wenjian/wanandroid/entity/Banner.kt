package com.wenjian.wanandroid.entity

/**
 * Description: Banner
 * Date: 2018/9/5
 *
 * @author jian.wen@ubtrobot.com
 */
data class Banner(val desc: String,
                  val id: Int,
                  val imagePath: String,
                  val isVisible: Int,
                  val order: Int,
                  val title: String,
                  val type: Int,
                  val url: String)

/**
 * desc: "一起来做个App吧",
id: 10,
imagePath: "http://www.wanandroid.com/blogimgs/50c115c2-cf6c-4802-aa7b-a4334de444cd.png",
isVisible: 1,
order: 2,
title: "一起来做个App吧",
type: 0,
url: "http://www.wanandroid.com/blog/show/2"
 *
 * */