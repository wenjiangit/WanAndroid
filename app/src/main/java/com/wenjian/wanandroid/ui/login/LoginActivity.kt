package com.wenjian.wanandroid.ui.login

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.view.View
import com.wenjian.wanandroid.MainActivity
import com.wenjian.wanandroid.R
import com.wenjian.wanandroid.base.BaseActivity
import com.wenjian.wanandroid.consts.IntentActions
import com.wenjian.wanandroid.extension.apiModelDelegate
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast

/**
 * Description: LoginActivity
 * Date: 2018/9/20
 *
 * @author jian.wen@ubtrobot.com
 */
class LoginActivity : BaseActivity(), View.OnClickListener {
    companion object {
        fun start(context: Context) {
            Intent(context, LoginActivity::class.java)
                    .let {
                        context.startActivity(it)
                    }
        }
    }

    private val mUserModel: UserModel by apiModelDelegate(UserModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initEvents()
        subscribeUi()
    }

    private fun subscribeUi() {
        mUserModel.userInfo.observe(this, Observer { res ->
            showContentWithStatus(res) { _ ->
                toast("登录成功")
                LocalBroadcastManager.getInstance(this)
                        .sendBroadcast(Intent(IntentActions.ACTION_LOGIN))
                startActivity(Intent(this, MainActivity::class.java))
            }
        })
    }

    private fun initEvents() {
        sign_up.setOnClickListener(this)
        bt_login.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.sign_up -> onSignUp()
            R.id.bt_login -> onLogin()
        }
    }

    private fun onLogin() {
        val user = edit_username.text.trim().toString()
        val pass = edit_password.text.trim().toString()
        when {
            user.length < 6 -> edit_username.error = "用户名不合格"
            pass.length < 6 -> edit_password.error = "密码不合格"
            else -> mUserModel.login(user, pass)
        }
    }

    private fun onSignUp() {

    }
}
