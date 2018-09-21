package com.wenjian.wanandroid.ui.mine


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.support.v4.content.LocalBroadcastManager
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.wenjian.wanandroid.R
import com.wenjian.wanandroid.base.BaseFragment
import com.wenjian.wanandroid.consts.IntentActions
import com.wenjian.wanandroid.extension.loadAvatar
import com.wenjian.wanandroid.helper.UserHelper
import com.wenjian.wanandroid.ui.collect.CollectActivity
import com.wenjian.wanandroid.ui.login.LoginActivity
import com.wenjian.wanandroid.ui.profile.ProfileActivity
import de.hdodenhof.circleimageview.CircleImageView


/**
 * Description: MineFragment
 * Date: 2018/9/6
 *
 * @author jian.wen@ubtrobot.com
 */
class MineFragment : BaseFragment() {

    private lateinit var mTvUser: TextView
    private lateinit var mIvAvatar: CircleImageView

    private val mLoginReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.d(TAG, "login success")
            updateUserInfo()
        }
    }

    override fun findViews(mRoot: View) {
        mRoot.findViewById<LinearLayout>(R.id.lay_person)
                .setOnClickListener {
                    if (UserHelper.isLogin()) {
                        ProfileActivity.start(context!!)
                    } else {
                        LoginActivity.start(context!!)
                    }
                }
        mTvUser = mRoot.findViewById(R.id.tv_username)
        mIvAvatar = mRoot.findViewById(R.id.iv_avatar)
        mRoot.findViewById<TextView>(R.id.tv_collect)
                .setOnClickListener {
                    startActivity(Intent(context, CollectActivity::class.java))
                }
    }

    override fun initViews() {
        super.initViews()
        updateUserInfo()
        LocalBroadcastManager.getInstance(context!!)
                .registerReceiver(mLoginReceiver, IntentFilter(IntentActions.ACTION_LOGIN))
    }

    fun updateUserInfo() {
        UserHelper.getUserInfo()?.apply {
            mTvUser.text = username
            mIvAvatar.loadAvatar(icon)
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_mine

    companion object {
        fun newInstance() = MineFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(context!!)
                .unregisterReceiver(mLoginReceiver)
    }


}
