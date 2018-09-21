package com.wenjian.wanandroid.ui.collect

import android.os.Bundle
import com.wenjian.wanandroid.R
import com.wenjian.wanandroid.base.BaseActivity
import com.wenjian.wanandroid.extension.setupActionBar

class CollectActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collect)
        setupActionBar(title = "收藏")

    }
}
