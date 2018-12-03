package com.wenjian.wanandroid.ui.adapter

import android.text.Html
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseViewHolder
import com.wenjian.wanandroid.R
import com.wenjian.wanandroid.base.BaseRecyclerAdapter
import com.wenjian.wanandroid.entity.Article
import com.wenjian.wanandroid.extension.gone
import com.wenjian.wanandroid.helper.NetHelper
import com.wenjian.wanandroid.ui.web.WebActivity

/**
 * Description: ArticleListAdapter
 * Date: 2018/9/7
 *
 * @author jian.wen@ubtrobot.com
 */
class ArticleListAdapter : BaseRecyclerAdapter<Article>(R.layout.rv_item_article_list) {
    override fun convert(helper: BaseViewHolder?, item: Article?) {
        helper?.apply {

            with(item!!) {

                setText(R.id.tv_title, Html.fromHtml(title?.trim()))
                setText(R.id.tv_name, author?.trim())
                setText(R.id.tv_date, niceDate?.trim())
                setText(R.id.tv_avatar,author?.trim()?.get(0).toString())
                setText(R.id.tv_category,chapterName?.trim())

                if (superChapterName?.trim().isNullOrBlank()) {
                    getView<TextView>(R.id.tv_chapter).gone()
                } else {
                    setText(R.id.tv_chapter,superChapterName?.trim())
                }

                itemView.setOnClickListener {
                    WebActivity.start(it.context, buildWebModel())
                }

                val likeImage = getView<ImageView>(R.id.iv_like)
                if (showLike) {
                    likeImage.isSelected = collect
                } else {
                    likeImage.gone()
                }

                likeImage.setOnClickListener {
                    collect = if (collect) {
                        NetHelper.unCollect(id)
                        false
                    } else {
                        NetHelper.collect(id)
                        true
                    }
                    notifyItemChanged(helper.adapterPosition)
                }
            }
        }
    }
}