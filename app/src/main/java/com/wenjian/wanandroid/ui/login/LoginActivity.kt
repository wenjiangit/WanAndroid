package com.wenjian.wanandroid.ui.login

import android.arch.lifecycle.Observer
import android.view.View
import com.wenjian.wanandroid.MainActivity
import com.wenjian.wanandroid.R
import com.wenjian.wanandroid.base.BaseSkinActivity
import com.wenjian.wanandroid.extension.*
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast

/**
 * Description: LoginActivity
 * Date: 2018/9/20
 *
 * @author jian.wen@ubtrobot.com
 */
class LoginActivity : BaseSkinActivity(), View.OnClickListener {
    private var isLogin: Boolean = true

    private val mUserModel: UserModel by apiModelDelegate(UserModel::class.java)

    override fun setup() {
        setContentView(R.layout.activity_login)
        setupActionBar(title = "登录")
        initEvents()
        subscribeUi()
    }

    private fun subscribeUi() {
        mUserModel.userInfo.observe(this, Observer { res ->
            showContentWithStatus(res) { _ ->
                toast("${if (isLogin) "登录" else "注册"}成功")
                launch(MainActivity::class.java)
            }
        })
    }

    private fun initEvents() {
        fab_convert.setOnClickListener(this)
        bt_login.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fab_convert -> onConvert()
            R.id.bt_login -> {
                if (isLogin) {
                    onLogin()
                } else {
                    onRegister()
                }
            }
        }
    }

    private fun onConvert() {
        if (isLogin) {
            collapsing_toolbar.title = "注册"
            lay_confirm.visible()
        } else {
            collapsing_toolbar.title = "登录"
            lay_confirm.gone()
        }
        isLogin = !isLogin
    }

    override fun showLoading() {
        super.showLoading()
        bt_login.gone()
        progress_bar.visible()
    }

    override fun hideLoading() {
        super.hideLoading()
        progress_bar.gone()
        bt_login.visible()
    }

    private fun onLogin() {
        val user = edit_username.text.trim().toString()
        val pass = edit_password.text.trim().toString()
        when {
            user.isBlank() -> edit_username.error = "请输入用户名"
            pass.isBlank() -> edit_password.error = "请输入密码"
            user.length < 6 -> edit_username.error = "用户名不合格"
            pass.length < 6 -> edit_password.error = "密码不合格"
            else -> mUserModel.login(user, pass)
        }
    }

    private fun onRegister() {
        val user = edit_username.text.trim().toString()
        val pass = edit_password.text.trim().toString()
        val repass = edit_repassword.text.trim().toString()
        when {
            user.isBlank() -> edit_username.error = "请输入用户名"
            pass.isBlank() -> edit_password.error = "请输入密码"
            repass.isBlank() -> edit_repassword.error = "请输入确认密码"
            user.length < 6 -> edit_username.error = "用户名不合格"
            pass.length < 6 -> edit_password.error = "密码不合格"
            pass != repass -> edit_repassword.error = "确认密码不一致"
            else -> mUserModel.register(user, pass, repass)
        }
    }
}
