package com.wenjian.wanandroid.ui.adapter

import com.chad.library.adapter.base.BaseViewHolder
import com.wenjian.wanandroid.R
import com.wenjian.wanandroid.base.BaseRecyclerAdapter
import com.wenjian.wanandroid.entity.Project
import com.wenjian.wanandroid.extension.setImageUrl
import com.wenjian.wanandroid.ui.web.WebActivity

/**
 * Description: ProjectListAdapter
 * Date: 2018/9/14
 *
 * @author jian.wen@ubtrobot.com
 */
class ProjectListAdapter : BaseRecyclerAdapter<Project>(R.layout.rv_item_project) {

    override fun convert(helper: BaseViewHolder?, item: Project?) {
        helper?.apply {
            with(item!!) {
                setText(R.id.tv_title, title.trim())
                setText(R.id.tv_time, niceDate.trim())
                setText(R.id.tv_desc, desc.trim())
                setText(R.id.tv_chapter, "$chapterName/$superChapterName")
                setImageUrl(R.id.iv_image, envelopePic)

                itemView.setOnClickListener {
                    WebActivity.start(it.context, item.buildWebModel())
                }
            }
        }
    }

}