package com.wenjian.wanandroid.entity

/**
 * Description: Project
 * Date: 2018/9/14
 *
 * @author jian.wen@ubtrobot.com
 */


/**
 *     {
apkLink: "",
author: "WANZIzZ",
chapterId: 294,
chapterName: "完整项目",
collect: false,
courseId: 13,
desc: "一个简单的Kotlin版本Todo客户端，数据来源wanandroid（第一次分享没有写READMEo(╥﹏╥)o）",
envelopePic: "http://www.wanandroid.com/blogimgs/97286574-9476-46b0-82d9-b1aa3a82ac4a.png",
fresh: false,
id: 3273,
link: "http://www.wanandroid.com/blog/show/2295",
niceDate: "2018-08-16",
origin: "",
projectLink: "https://github.com/WANZIzZ/Todo",
publishTime: 1534412239000,
superChapterId: 294,
superChapterName: "开源项目主Tab",
tags: [
{
name: "项目",
url: "/project/list/1?cid=294"
}
],
title: "Todo客户端-Kotlin",
type: 0,
userId: -1,
visible: 1,
zan: 0
}
]
 *
 */


data class Project(
        val apkLink: String,
        val author: String,
        val chapterId: Int,
        val chapterName: String,
        val collect: Boolean,
        val courseId: Int,
        val desc: String,
        val envelopePic: String,
        val fresh: Boolean,
        val id: Int,
        val link: String,
        val niceDate: String,
        val origin: String,
        val projectLink: String,
        val publishTime: Long,
        val superChapterId: Int,
        val superChapterName: String,
        val tags: List<Tag>,
        val title: String,
        val type: Int,
        val userId: Int,
        val visible: Int,
        val zan: Int
)

data class Tag(
        val name: String,
        val url: String
)