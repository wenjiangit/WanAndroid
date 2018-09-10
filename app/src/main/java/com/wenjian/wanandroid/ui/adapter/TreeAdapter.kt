package com.wenjian.wanandroid.ui.adapter

import co.lujun.androidtagview.ColorFactory
import co.lujun.androidtagview.TagContainerLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.wenjian.wanandroid.R
import com.wenjian.wanandroid.entity.SubTree
import com.wenjian.wanandroid.entity.TreeEntry
import com.wenjian.wanandroid.ui.knowledge.SubTreeActivity

/**
 * Description ${name}
 *
 * Date 2018/9/8
 * @author wenjianes@163.com
 */
class TreeAdapter : BaseQuickAdapter<TreeEntry, BaseViewHolder>(R.layout.rv_item_tree) {
    override fun convert(helper: BaseViewHolder?, item: TreeEntry?) {

        helper?.apply {
            val containerLayout = getView<TagContainerLayout>(R.id.tag_container)
            containerLayout.theme = ColorFactory.RANDOM

            helper.itemView.setOnClickListener {
                item?.let {
                    val list = it.children.map { SubTree(it.id, it.name) }
                    SubTreeActivity.start(helper.itemView.context, it.name, ArrayList(list))
                }
            }

            item?.let {
                containerLayout.tags = it.children.map { it.name }
                setText(R.id.tv_chapter, it.name)
            }

        }

    }

}