package com.wenjian.wanandroid.ui.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.wenjian.wanandroid.R
import com.wenjian.wanandroid.entity.Article
import com.wenjian.wanandroid.extension.loadUrl
import com.wenjian.wanandroid.ui.web.WebActivity

/**
 * Description: ArticleListAdapter
 * Date: 2018/9/7
 *
 * @author jian.wen@ubtrobot.com
 */
class ArticleListAdapter : BaseQuickAdapter<Article, BaseViewHolder>(R.layout.rv_item_article_list) {
    override fun convert(helper: BaseViewHolder?, item: Article?) {
        helper?.apply {
            item?.let {
                setText(R.id.tv_category, it.chapterName)
                setText(R.id.tv_title, it.title)
                setText(R.id.tv_name, it.author)
                setText(R.id.tv_date, it.niceDate)
                getView<ImageView>(R.id.iv_image).loadUrl(it.envelopePic)
            }

            itemView.setOnClickListener {
                WebActivity.start(it.context,item?.link)
            }
        }
    }

}