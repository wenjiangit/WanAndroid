package com.wenjian.wanandroid.ui.mine


import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.wenjian.wanandroid.R
import com.wenjian.wanandroid.base.BaseFragment
import com.wenjian.wanandroid.extension.*
import com.wenjian.wanandroid.helper.UserHelper
import com.wenjian.wanandroid.model.Event
import com.wenjian.wanandroid.model.FlowEventBus
import com.wenjian.wanandroid.ui.collect.CollectActivity
import com.wenjian.wanandroid.ui.login.LoginActivity
import com.wenjian.wanandroid.ui.profile.ProfileActivity
import com.wenjian.wanandroid.ui.setting.SettingActivity
import com.wenjian.wanandroid.ui.theme.ThemeActivity
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
    private lateinit var mTvUserId: TextView
    private lateinit var mTvLoginRegister: TextView

    override fun findViews(mRoot: View) {
        mTvUser = mRoot.findViewById(R.id.tv_username)
        mIvAvatar = mRoot.findViewById(R.id.iv_avatar)
        mTvUserId = mRoot.findViewById(R.id.tv_userid)
        mTvLoginRegister = mRoot.findViewById(R.id.tv_login_register)

        mRoot.findViewById<ConstraintLayout>(R.id.lay_person)
                .setOnClickListener {
                    if (UserHelper.isLogin()) {
                        launch(ProfileActivity::class.java)
                    } else {
                        launch(LoginActivity::class.java)
                    }
                }

        mRoot.findViewById<ConstraintLayout>(R.id.lay_toolbox)
                .setOnClickListener {
                    context?.toastInfo("敬请期待")
                }

        mRoot.findViewById<ConstraintLayout>(R.id.lay_collect)
                .setOnClickListener {
                    launch(CollectActivity::class.java)
                }

        mRoot.findViewById<ConstraintLayout>(R.id.lay_settings)
                .setOnClickListener {
                    launch(SettingActivity::class.java)
                }

        mRoot.findViewById<ConstraintLayout>(R.id.lay_theme)
                .setOnClickListener {
                    launch(ThemeActivity::class.java)
                }
    }

    override fun initViews() {
        super.initViews()
        updateUserInfo()
        FlowEventBus.observe<Event.UserInfoRefresh>(viewLifecycleOwner) {
            updateUserInfo()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateUserInfo() {
        if (UserHelper.isLogin()) {
            mTvLoginRegister.gone()
            mTvUserId.visible()
            mTvUser.visible()
            UserHelper.getUserInfo()?.apply {
                mTvUser.text = username
                mIvAvatar.loadAvatar(icon)
                mTvUserId.text = "id : $id"
            }
        } else {
            mTvLoginRegister.visible()
            mTvUserId.gone()
            mTvUser.gone()
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_mine

    companion object {
        fun newInstance() = MineFragment()
    }

}
