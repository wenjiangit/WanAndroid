package com.wenjian.wanandroid.ui.adapter

import android.text.Html
import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseViewHolder
import com.wenjian.wanandroid.R
import com.wenjian.wanandroid.base.BaseRecyclerAdapter
import com.wenjian.wanandroid.entity.Article
import com.wenjian.wanandroid.extension.loadUrl
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
                val buildString = buildString {
                    if (!chapterName.isNullOrBlank()) {
                        append(chapterName?.trim())
                    }
                    if (!superChapterName.isNullOrBlank()) {
                        append("/${superChapterName?.trim()}")
                    }
                }
                setText(R.id.tv_category, buildString)
                setText(R.id.tv_title, Html.fromHtml(title?.trim()))
                setText(R.id.tv_name, author?.trim())
                setText(R.id.tv_date, niceDate?.trim())
                val imageView = getView<ImageView>(R.id.iv_image)
                if (!envelopePic.isNullOrBlank()) {
                    imageView.loadUrl(envelopePic)
                    imageView.visibility = View.VISIBLE
                } else {
                    imageView.visibility = View.GONE
                }
                itemView.setOnClickListener {
                    WebActivity.start(it.context, buildWebModel())
                }
            }
        }
    }

}