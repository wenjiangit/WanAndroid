package com.wenjian.wanandroid.entity

/**
 * Description: ProjectTree
 * Date: 2018/9/12
 *
 * @author jian.wen@ubtrobot.com
 */

/**
 * {
children: [ ],
courseId: 13,
id: 294,
name: "完整项目",
order: 145000,
parentChapterId: 293,
visible: 0
}
 */

data class ProjectTree(val children: List<*>,
                       val courseId: Int,
                       val id: Int,
                       val name: String,
                       val order: Int,
                       val parentChapterId: Int,
                       val visible: Int)