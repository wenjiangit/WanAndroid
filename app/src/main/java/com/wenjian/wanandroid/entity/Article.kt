package com.wenjian.wanandroid.entity

/**
 * Description: Ac
 * Date: 2018/9/6
 *
 * @author jian.wen@ubtrobot.com
 */
class Article {
    /**
     * apkLink :
     * author : JerryloveEmily
     * chapterId : 153
     * chapterName : Zygote进程启动
     * collect : false
     * courseId : 13
     * desc :
     * envelopePic :
     * fresh : false
     * id : 3319
     * link : https://juejin.im/post/5b8294dff265da432f654244
     * niceDate : 2018-08-26
     * origin :
     * projectLink :
     * publishTime : 1535284864000
     * superChapterId : 173
     * superChapterName : framework
     * tags : []
     * title : Android系统启动系列----init进程
     * type : 0
     * userId : -1
     * visible : 1
     * zan : 0
     */

    var apkLink: String? = null
    var author: String? = null
    var chapterId: Int = 0
    var chapterName: String? = null
    var isCollect: Boolean = false
    var courseId: Int = 0
    var desc: String? = null
    var envelopePic: String? = null
    var isFresh: Boolean = false
    var id: Int = 0
    var link: String? = null
    var niceDate: String? = null
    var origin: String? = null
    var projectLink: String? = null
    var publishTime: Long = 0
    var superChapterId: Int = 0
    var superChapterName: String? = null
    var title: String? = null
    var type: Int = 0
    var userId: Int = 0
    var visible: Int = 0
    var zan: Int = 0
    var tags: List<Tag>? = null



    class Tag(var name: String, var url: String)

    override fun toString(): String {
        return "Article(apkLink=$apkLink, author=$author, chapterId=$chapterId, chapterName=$chapterName, isCollect=$isCollect, courseId=$courseId, desc=$desc, envelopePic=$envelopePic, isFresh=$isFresh, id=$id, link=$link, niceDate=$niceDate, origin=$origin, projectLink=$projectLink, publishTime=$publishTime, superChapterId=$superChapterId, superChapterName=$superChapterName, title=$title, type=$type, userId=$userId, visible=$visible, zan=$zan, tags=$tags)"
    }

}
