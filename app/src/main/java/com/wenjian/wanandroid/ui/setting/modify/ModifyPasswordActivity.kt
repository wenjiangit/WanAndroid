package com.wenjian.wanandroid.ui.setting.modify

import androidx.lifecycle.lifecycleScope
import com.wenjian.wanandroid.R
import com.wenjian.wanandroid.base.VMActivity
import com.wenjian.wanandroid.extension.launch
import com.wenjian.wanandroid.extension.setupActionBar
import com.wenjian.wanandroid.extension.toastSuccess
import com.wenjian.wanandroid.net.onSuccess
import com.wenjian.wanandroid.ui.login.LoginActivity
import com.wenjian.wanandroid.ui.setting.SettingModel
import kotlinx.android.synthetic.main.activity_modify_password.*
import kotlinx.coroutines.flow.launchIn

class ModifyPasswordActivity : VMActivity<SettingModel>(SettingModel::class.java) {
    override fun setup() {
        setContentView(R.layout.activity_modify_password)
        setupActionBar(title = "修改密码")

        initEvent()

    }

    private fun initEvent() {
        tv_modify.setOnClickListener {
            onModifyPass()
        }
    }

    private fun onModifyPass() {
        val originPass = edit_origin_pass.text.toString().trim()
        val newPass = edit_new_pass.text.toString().trim()
        val confirmPass = edit_confirm_pass.text.toString().trim()

        when {
            originPass.isBlank() -> {
                edit_origin_pass.error = "原密码不能为空"
            }
            newPass.isBlank() -> {
                edit_new_pass.error = "新密码不能为空"
            }
            confirmPass.isBlank() -> {
                edit_confirm_pass.error = "确认密码不能为空"
            }
            newPass != confirmPass -> {
                edit_confirm_pass.error = "确认密码与新密码不一致"
            }
            else -> mViewModel.modifyPass(originPass, newPass, confirmPass).onSuccess {
                toastSuccess("修改密码成功,请重新登录")
                launch(LoginActivity::class.java)
            }.launchIn(lifecycleScope)

        }


    }


}
