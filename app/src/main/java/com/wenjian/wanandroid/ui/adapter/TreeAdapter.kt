package com.wenjian.wanandroid.ui.adapter

import co.lujun.androidtagview.TagContainerLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.wenjian.wanandroid.R
import com.wenjian.wanandroid.entity.TreeEntry

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

            item?.let {
                containerLayout.tags = it.children.map { it.name }
                setText(R.id.tv_chapter, it.name)
            }

        }

    }

}