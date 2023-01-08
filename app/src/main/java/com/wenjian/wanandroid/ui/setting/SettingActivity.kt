package com.wenjian.wanandroid.ui.setting

import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.wenjian.wanandroid.R
import com.wenjian.wanandroid.base.VMActivity
import com.wenjian.wanandroid.extension.*
import com.wenjian.wanandroid.net.onSuccess
import com.wenjian.wanandroid.ui.login.LoginActivity
import com.wenjian.wanandroid.ui.setting.modify.ModifyPasswordActivity
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

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
            showLoading()
            flowOf(2000)
                .onStart { showLoading() }
                .onEach {
                    delay(2000)
                    hideLoading()
                    toastInfo("已是最新版本了")
                }.launchIn(lifecycleScope)
        }

        lay_clear_cache.setOnClickListener {
            showDialog("确定清除缓存数据么?") {
                clearCache()
            }
        }

        tv_change_pass.setOnClickListener {
            launch(ModifyPasswordActivity::class.java)
        }


    }

    private fun clearCache() {
        mViewModel.clearCache()
        tv_cache_size.text = mViewModel.getCacheSize()
        toastSuccess("清理成功")
    }

    private fun logout() {
        mViewModel.logout().onSuccess {
            toastSuccess("退出登录成功")
            finish()
        }.launchIn(lifecycleScope)
    }

    private fun initView() {
        if (mViewModel.isLogin()) {
            tv_logout.text = "退出登录"
            tv_change_pass.visible()
        } else {
            tv_logout.text = "立即登录"
            tv_change_pass.gone()
        }

        tv_cache_size.text = mViewModel.getCacheSize()
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
