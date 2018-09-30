package com.wenjian.wanandroid.ui.profile

import android.support.v4.view.ViewCompat
import com.qmuiteam.qmui.util.QMUIStatusBarHelper
import com.wenjian.wanandroid.R
import com.wenjian.wanandroid.base.BaseSkinActivity
import com.wenjian.wanandroid.extension.loadAvatar
import com.wenjian.wanandroid.extension.setupActionBar
import com.wenjian.wanandroid.helper.UserHelper
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : BaseSkinActivity() {
    override fun setup() {
        setContentView(R.layout.activity_profile)
//        QMUIStatusBarHelper.translucent(this)
        setupActionBar(title = "个人中心")
        initViews()
    }


    private fun initEvents() {
        app_bar_layout.addOnOffsetChangedListener { _, verticalOffset ->
            val minHeight = ViewCompat.getMinimumHeight(collapsing_toolbar)
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
