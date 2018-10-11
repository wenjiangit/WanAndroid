package com.wenjian.wanandroid.ui.adapter

import android.graphics.drawable.GradientDrawable
import android.support.v4.content.ContextCompat
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseViewHolder
import com.wenjian.wanandroid.R
import com.wenjian.wanandroid.base.BaseRecyclerAdapter
import com.wenjian.wanandroid.entity.Skin
import com.wenjian.wanandroid.extension.gone
import com.wenjian.wanandroid.extension.visible
import com.wenjian.wanandroid.utils.Tools
import org.jetbrains.anko.textColor

/**
 * Description: ThemeAdapter
 * Date: 2018/10/11
 *
 * @author jian.wen@ubtrobot.com
 */
class ThemeAdapter : BaseRecyclerAdapter<Skin>(R.layout.rv_item_theme) {

    override fun convert(helper: BaseViewHolder?, item: Skin) {
        helper?.apply {
            val themeColor = ContextCompat.getColor(mContext, item.color)
            setTextColor(R.id.tv_name, themeColor)
            setText(R.id.tv_name, item.name)

            val imageView = getView<ImageView>(R.id.iv_color)
            imageView.setColorFilter(themeColor)

            val useTv = getView<TextView>(R.id.tv_use)
            val drawable = useTv.background as GradientDrawable
            val checkIv = getView<ImageView>(R.id.iv_check)

            if (item.select) {
                useTv.text = "使用中"
                useTv.textColor = themeColor
                drawable.setStroke(Tools.dip2px(mContext, 1f), themeColor)
                checkIv.visible()
            } else {
                val normal = ContextCompat.getColor(mContext, R.color.text_grey)
                useTv.text = "使用"
                useTv.textColor = normal
                drawable.setStroke(Tools.dip2px(mContext, 1f), normal)
                checkIv.gone()
            }
        }
    }

}