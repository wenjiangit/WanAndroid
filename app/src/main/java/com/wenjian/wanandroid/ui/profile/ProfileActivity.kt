package com.wenjian.wanandroid.ui.profile

import com.wenjian.wanandroid.R
import com.wenjian.wanandroid.base.VMActivity
import com.wenjian.wanandroid.extension.loadAvatar
import com.wenjian.wanandroid.extension.setupActionBar
import com.wenjian.wanandroid.extension.translucentStatusBar
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : VMActivity<ProfileModel>(ProfileModel::class.java) {
    override fun setup() {
        setContentView(R.layout.activity_profile)
        setupActionBar(title = "个人中心")
        translucentStatusBar()
        initViews()
        initEvents()
    }


    private fun initEvents() {
        /*  app_bar_layout.addOnOffsetChangedListener { _, verticalOffset ->
              val minHeight = ViewCompat.getMinimumHeight(collapsing_toolbar)
              val scale = (minHeight + verticalOffset).toFloat() / minHeight
              cv_avatar.scaleX = if (scale >= 0) scale else 0f
              cv_avatar.scaleY = if (scale >= 0) scale else 0f

          }*/


    }

    private fun initViews() {
        mViewModel.getUserInfo()?.apply {
            tv_username.text = username
            cv_avatar.loadAvatar(icon)
        }
    }
}
