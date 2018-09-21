package com.wenjian.wanandroid.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewCompat
import com.wenjian.wanandroid.R
import com.wenjian.wanandroid.base.BaseActivity
import com.wenjian.wanandroid.extension.loadAvatar
import com.wenjian.wanandroid.extension.setSystemBarColor
import com.wenjian.wanandroid.extension.setupActionBar
import com.wenjian.wanandroid.helper.UserHelper
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : BaseActivity() {


    companion object {
        fun start(context: Context) {
            Intent(context, ProfileActivity::class.java).let {
                context.startActivity(it)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setupActionBar(title = "个人中心")
        setSystemBarColor(android.R.color.transparent)
        initViews()
        initEvents()
    }

    private fun initEvents() {
        app_bar_layout.addOnOffsetChangedListener { _, verticalOffset ->
            val minHeight = ViewCompat.getMinimumHeight(collapsing_toolbar) * 2
            val scale = (minHeight + verticalOffset).toFloat() / minHeight
            cv_avatar.scaleX = if (scale >= 0) scale else 0f
            cv_avatar.scaleY = if (scale >= 0) scale else 0f

        }
    }

    private fun initViews() {
        UserHelper.getUserInfo()?.apply {
            tv_username.text = username
            cv_avatar.loadAvatar(icon)
        }
    }
}
