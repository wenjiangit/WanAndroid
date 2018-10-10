package com.wenjian.wanandroid.ui.collect

import com.wenjian.wanandroid.R
import com.wenjian.wanandroid.base.BaseSkinActivity
import com.wenjian.wanandroid.extension.setupActionBar
import com.wenjian.wanandroid.extension.translucentStatusBar
import com.wenjian.wanandroid.utils.Tools

class CollectActivity : BaseSkinActivity() {
    override fun setup() {
        setContentView(R.layout.activity_collect)
        setupActionBar(title = "收藏")
        translucentStatusBar()
    }

}
