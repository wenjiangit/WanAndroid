package com.wenjian.wanandroid.ui.setting

import android.os.Bundle
import android.support.v7.app.AlertDialog
import com.wenjian.wanandroid.MainActivity
import com.wenjian.wanandroid.R
import com.wenjian.wanandroid.base.BaseActivity
import com.wenjian.wanandroid.extension.launch
import com.wenjian.wanandroid.extension.setupActionBar
import com.wenjian.wanandroid.helper.UserHelper
import com.wenjian.wanandroid.model.RxBus
import com.wenjian.wanandroid.model.UserInfoRefreshEvent
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        setupActionBar(title = "设置")
        tv_logout.setOnClickListener {
            showDialog {
                UserHelper.logOut()
                RxBus.post(UserInfoRefreshEvent())
                launch(MainActivity::class.java)
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
}
