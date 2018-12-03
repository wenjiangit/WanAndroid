package com.wenjian.wanandroid.ui.adapter

import android.text.Html
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseViewHolder
import com.wenjian.wanandroid.R
import com.wenjian.wanandroid.base.BaseRecyclerAdapter
import com.wenjian.wanandroid.entity.Article
import com.wenjian.wanandroid.extension.getColorAccent
import com.wenjian.wanandroid.extension.gone
import com.wenjian.wanandroid.extension.loadUrl
import com.wenjian.wanandroid.extension.visible
import com.wenjian.wanandroid.ui.web.WebActivity
import org.jetbrains.anko.textColor

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
                val buildString = buildString {
                    if (!chapterName.isNullOrBlank()) {
                        append(chapterName?.trim())
                    }
                    if (!superChapterName.isNullOrBlank()) {
                        append("/${superChapterName?.trim()}")
                    }
                }
                getView<TextView>(R.id.tv_category).apply {
                    text = buildString
//                    textColor = mContext.getColorAccent()
                }
                setText(R.id.tv_title, Html.fromHtml(title?.trim()))
                setText(R.id.tv_name, author?.trim())
                setText(R.id.tv_date, niceDate?.trim())
//                val imageView = getView<ImageView>(R.id.iv_image)
//                if (!envelopePic.isNullOrBlank()) {
//                    imageView.loadUrl(envelopePic)
//                    imageView.visible()
//                } else {
//                    imageView.gone()
//                }
                itemView.setOnClickListener {
                    WebActivity.start(it.context, buildWebModel())
                }

                val likeImage = getView<ImageView>(R.id.iv_like)
                if (showLike) {
                    likeImage.isSelected = collect
                } else {
                    likeImage.gone()
                }
            }
        }
    }

}