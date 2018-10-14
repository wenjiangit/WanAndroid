package com.wenjian.wanandroid.ui.collect

import android.support.v4.view.ViewCompat
import com.wenjian.wanandroid.R
import com.wenjian.wanandroid.base.BaseSkinActivity
import com.wenjian.wanandroid.extension.logI
import com.wenjian.wanandroid.extension.setupActionBar
import com.wenjian.wanandroid.extension.translucentStatusBar
import com.wenjian.wanandroid.helper.QMUIStatusBarHelper
import com.wenjian.wanandroid.helper.ThemeHelper
import kotlinx.android.synthetic.main.activity_collect.*

class CollectActivity : BaseSkinActivity() {

    private var offset: Int = 0

    private var isDark: Boolean = false

    override fun setup() {
        setContentView(R.layout.activity_collect)
        setupActionBar(title = "收藏")
        translucentStatusBar()

        if (ThemeHelper.isDefault()) {
            app_bar_layout.addOnOffsetChangedListener { _, verticalOffset ->

                val height = ViewCompat.getMinimumHeight(collapsing_toolbar)

                if (verticalOffset - offset > 0) {//up
                    if (Math.abs(verticalOffset) < height) {
                        if (isDark) {
                            return@addOnOffsetChangedListener
                        }
                        QMUIStatusBarHelper.setStatusBarDarkMode(this)
                        isDark = true
                    }

                } else {
                    if (Math.abs(verticalOffset) > height) {
                        if (!isDark) {
                            return@addOnOffsetChangedListener
                        }
                        QMUIStatusBarHelper.setStatusBarLightMode(this)
                        isDark = false
                    }
                }

                offset = verticalOffset
                logI(verticalOffset.toString() + "--- $height")

            }
        }


    }

}
