package com.wenjian.wanandroid.extension

import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import com.chad.library.adapter.base.BaseViewHolder
import com.wenjian.wanandroid.widget.NoLastDecoration

/**
 * Description: RecyclerViewExt
 * Date: 2018/9/11
 *
 * @author jian.wen@ubtrobot.com
 */
/**
 * 添加自定义的装饰器,最后一行没有装饰
 */
fun RecyclerView.addCustomDecoration(direction: Int = DividerItemDecoration.VERTICAL,
                                     @DrawableRes drawable: Int = -1) {
    val noLastDecoration = NoLastDecoration(context, direction).apply {
        if (drawable != -1) {
            setDrawable(ContextCompat.getDrawable(context, drawable)!!)
        }
    }
    addItemDecoration(noLastDecoration)
}



fun BaseViewHolder.setImageUrl(resId: Int, url: String) {
    getView<ImageView>(resId).loadUrl(url)
}