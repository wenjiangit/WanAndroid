package com.wenjian.wanandroid.ui.profile

import android.arch.lifecycle.Observer
import android.support.v7.app.AlertDialog
import com.wenjian.wanandroid.MainActivity
import com.wenjian.wanandroid.R
import com.wenjian.wanandroid.base.VMActivity
import com.wenjian.wanandroid.extension.launch
import com.wenjian.wanandroid.extension.loadAvatar
import com.wenjian.wanandroid.extension.setupActionBar
import com.wenjian.wanandroid.extension.translucentStatusBar
import kotlinx.android.synthetic.main.activity_profile.*
import org.jetbrains.anko.toast

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

        btn_logout.setOnClickListener { _ ->
            showDialog {
                logout()
            }
        }

    }

    private fun showDialog(handler: () -> Unit) {
        AlertDialog.Builder(this)
                .setMessage("确定要退出登录么?")
                .setNegativeButton("取消") { dialog, _ ->
                    dialog.dismiss()
                }.setPositiveButton("确认") { dialog, _ ->
                    handler()
                    dialog.dismiss()
                }.show()
    }

    private fun logout() {
        mViewModel.logout()
                .observe(this, Observer {
                    toast("退出登录成功")
                    launch(MainActivity::class.java)
                })
    }

    private fun initViews() {
        mViewModel.getUserInfo()?.apply {
            tv_username.text = username
            cv_avatar.loadAvatar(icon)
        }
    }
}
