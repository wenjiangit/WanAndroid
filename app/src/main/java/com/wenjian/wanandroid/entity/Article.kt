package com.wenjian.wanandroid.entity

/**
 * Description: Ac
 * Date: 2018/9/6
 *
 * @author jian.wen@ubtrobot.com
 */
data class Article(
    val apkLink: String?,
    val author: String?,
    val chapterId: Int,
    val chapterName: String?,
    val collect: Boolean,
    val courseId: Int,
    val desc: String,
    val envelopePic: String?,
    val fresh: Boolean,
    val id: Int,
    val link: String,
    val niceDate: String?,
    val origin: String,
    val projectLink: String,
    val publishTime: Long,
    val superChapterId: Int,
    val superChapterName: String?,
    val tags: List<Tag>,
    val title: String?,
    val type: Int,
    val userId: Int,
    val visible: Int,
    val zan: Int
){

    fun buildWebModel() = WebModel(id, link, collect)
    class Tag(var name: String, var url: String)

}
