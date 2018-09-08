package com.wenjian.wanandroid.entity


/**
 * Description ${name}
 *
 *
 * Date 2018/9/8
 *
 * @author wenjianes@163.com
 */


data class TreeEntry(
    val children: List<Children> = emptyList(),
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val visible: Int
)

data class Children(
    val children: List<Any>,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val visible: Int
)