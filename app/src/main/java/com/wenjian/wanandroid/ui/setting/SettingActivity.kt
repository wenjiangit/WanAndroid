package com.wenjian.wanandroid.ui.setting

import android.arch.lifecycle.Observer
import android.support.v7.app.AlertDialog
import com.wenjian.wanandroid.MainActivity
import com.wenjian.wanandroid.R
import com.wenjian.wanandroid.base.VMActivity
import com.wenjian.wanandroid.extension.launch
import com.wenjian.wanandroid.extension.setupActionBar
import com.wenjian.wanandroid.extension.toastSuccess
import com.wenjian.wanandroid.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : VMActivity<SettingModel>(SettingModel::class.java) {

    override fun setup() {
        setContentView(R.layout.activity_setting)
        setupActionBar(title = "设置")
        initView()
        initEvent()
    }

    private fun initEvent() {
        tv_logout.setOnClickListener {
            if (mViewModel.isLogin()) {
                showDialog {
                    logout()
                }
            } else {
                launch(LoginActivity::class.java)
            }
        }
    }

    private fun logout() {
        mViewModel.logout().observe(this, Observer {
            toastSuccess("退出登录成功")
            launch(MainActivity::class.java)
        })
    }

    private fun initView() {
        if (mViewModel.isLogin()) {
            tv_logout.text = "退出登录"
        } else {
            tv_logout.text = "立即登录"
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
