package com.wenjian.wanandroid.ui.setting

import android.arch.lifecycle.Observer
import android.support.v7.app.AlertDialog
import com.wenjian.wanandroid.MainActivity
import com.wenjian.wanandroid.R
import com.wenjian.wanandroid.base.VMActivity
import com.wenjian.wanandroid.extension.*
import com.wenjian.wanandroid.ui.login.LoginActivity
import com.wenjian.wanandroid.utils.FileUtil
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
                showDialog("确定退出登录么?") {
                    logout()
                }
            } else {
                launch(LoginActivity::class.java)
            }
        }

        tv_check_update.setOnClickListener {
            toastInfo("已是最新版本了")
        }

        lay_clear_cache.setOnClickListener {
            showDialog("确定清除缓存数据么?"){
                clearCache()
            }
        }
    }

    private fun clearCache() {
        FileUtil.deleteFile(externalCacheDir)
        tv_cache_size.text = FileUtil.getFormatSize(externalCacheDir)
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
            tv_change_pass.visible()
        } else {
            tv_logout.text = "立即登录"
            tv_change_pass.gone()
        }

        tv_cache_size.text = FileUtil.getFormatSize(externalCacheDir)
    }

    private fun showDialog(msg: String, handler: () -> Unit) {
        AlertDialog.Builder(this)
                .setMessage(msg)
                .setNegativeButton("取消") { dialog, _ ->
                    dialog.dismiss()
                }.setPositiveButton("确认") { dialog, _ ->
                    handler()
                    dialog.dismiss()
                }.show()
    }
}
