package com.wenjian.wanandroid.extension

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.wenjian.wanandroid.R

/**
 * Description: ImageLoaderExt
 * Date: 2018/9/6
 *
 * @author jian.wen@ubtrobot.com
 */

/**
 * 加载普通图片
 */
fun ImageView.loadUrl(url: String?) {
    Glide.with(context)
            .load(url)
            .into(this)
}

/**
 * 加载头像
 */
fun ImageView.loadAvatar(url: String?) {
    Glide.with(this)
            .load(url)
            .apply(RequestOptions
                    .placeholderOf(R.drawable.empty_avatar_user)
                    .error(R.drawable.default_avatar)
                    .centerCrop())
            .into(this)
}
