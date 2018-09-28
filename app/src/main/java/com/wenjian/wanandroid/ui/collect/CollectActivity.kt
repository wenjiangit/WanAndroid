package com.wenjian.wanandroid.ui.collect

import com.wenjian.wanandroid.R
import com.wenjian.wanandroid.base.BaseSkinActivity
import com.wenjian.wanandroid.extension.setupActionBar

class CollectActivity : BaseSkinActivity() {
    override fun setup() {
        setContentView(R.layout.activity_collect)
        setupActionBar(title = "收藏")
    }

}
