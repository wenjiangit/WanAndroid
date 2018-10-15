package com.wenjian.wanandroid.ui.collect

import android.graphics.Color
import android.support.design.widget.AppBarLayout
import com.wenjian.wanandroid.R
import com.wenjian.wanandroid.base.BaseSkinActivity
import com.wenjian.wanandroid.extension.getCompatColor
import com.wenjian.wanandroid.extension.setupActionBar
import com.wenjian.wanandroid.extension.translucentStatusBar
import com.wenjian.wanandroid.helper.StatusBarHelper
import com.wenjian.wanandroid.helper.ThemeHelper
import com.wenjian.wanandroid.widget.appbar.AppBarStateChangeListener
import kotlinx.android.synthetic.main.activity_collect.*

class CollectActivity : BaseSkinActivity() {

    override fun setup() {
        setContentView(R.layout.activity_collect)
        setupActionBar(title = "收藏")
        translucentStatusBar()

        if (ThemeHelper.isDefault()) {
            app_bar_layout.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
                override fun onStateChange(appBarLayout: AppBarLayout, state: State) {
                    when (state) {
                        State.EXPANDED -> {
                            StatusBarHelper.setStatusBarDarkMode(this@CollectActivity)
                            toolBar.setTitleTextColor(getCompatColor(android.R.color.transparent))
                            toolBar.navigationIcon?.setTint(Color.WHITE)
                        }
                        State.COLLAPSED -> {
                            StatusBarHelper.setStatusBarLightMode(this@CollectActivity)
                            toolBar.setTitleTextColor(getCompatColor(R.color.black))
                            toolBar.navigationIcon?.setTint(Color.BLACK)
                        }
                        State.IDLE -> {
                            toolBar.setTitleTextColor(getCompatColor(android.R.color.transparent))
                        }
                    }
                }
            })
        }


    }

}
