package com.wenjian.wanandroid.ui.mine


import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.wenjian.wanandroid.R
import com.wenjian.wanandroid.base.BaseFragment
import com.wenjian.wanandroid.extension.launch
import com.wenjian.wanandroid.extension.loadAvatar
import com.wenjian.wanandroid.helper.UserHelper
import com.wenjian.wanandroid.model.RxBus
import com.wenjian.wanandroid.model.UserInfoRefreshEvent
import com.wenjian.wanandroid.ui.collect.CollectActivity
import com.wenjian.wanandroid.ui.login.LoginActivity
import com.wenjian.wanandroid.ui.profile.ProfileActivity
import com.wenjian.wanandroid.ui.setting.SettingActivity
import com.wenjian.wanandroid.ui.theme.ThemeActivity
import de.hdodenhof.circleimageview.CircleImageView
import io.reactivex.disposables.Disposable


/**
 * Description: MineFragment
 * Date: 2018/9/6
 *
 * @author jian.wen@ubtrobot.com
 */
class MineFragment : BaseFragment() {

    private lateinit var mTvUser: TextView
    private lateinit var mIvAvatar: CircleImageView
    private var disposable: Disposable? = null

    override fun findViews(mRoot: View) {
        mRoot.findViewById<LinearLayout>(R.id.lay_person)
                .setOnClickListener {
                    if (UserHelper.isLogin()) {
                        launch(ProfileActivity::class.java)
                    } else {
                        launch(LoginActivity::class.java)
                    }
                }
        mTvUser = mRoot.findViewById(R.id.tv_username)
        mIvAvatar = mRoot.findViewById(R.id.iv_avatar)
        mRoot.findViewById<TextView>(R.id.tv_collect)
                .setOnClickListener {
                    launch(CollectActivity::class.java)
                }
        mRoot.findViewById<TextView>(R.id.tv_setting)
                .setOnClickListener {
                    launch(SettingActivity::class.java)
                }

        mRoot.findViewById<TextView>(R.id.tv_theme)
                .setOnClickListener {
                    launch(ThemeActivity::class.java)
                }
    }

    override fun initViews() {
        super.initViews()
        updateUserInfo()
        disposable = RxBus.toObservable(UserInfoRefreshEvent::class.java)
                .subscribe {
                    updateUserInfo()
                }
    }

    private fun updateUserInfo() {
        if (UserHelper.isLogin()) {
            UserHelper.getUserInfo()?.apply {
                mTvUser.text = username
                mIvAvatar.loadAvatar(icon)
            }
        } else {
            mTvUser.text = "登录/注册"
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_mine

    companion object {
        fun newInstance() = MineFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }


}
