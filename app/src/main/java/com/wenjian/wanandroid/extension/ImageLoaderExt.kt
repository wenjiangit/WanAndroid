package com.wenjian.wanandroid.extension

import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * Description: ImageLoaderExt
 * Date: 2018/9/6
 *
 * @author jian.wen@ubtrobot.com
 */

fun ImageView.loadUrl(url: String?) {
    Glide.with(context)
            .load(url)
            .into(this)
}
