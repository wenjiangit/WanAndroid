package com.wenjian.wanandroid.base

import android.os.Bundle
import com.wenjian.wanandroid.helper.ThemeHelper

/**
 * Description ${name}
 *
 * Date 2018/9/28
 * @author wenjianes@163.com
 */
abstract class BaseSkinActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initTheme()
        setup()
        bindViewModel()
    }

    open fun bindViewModel() {

    }

    private fun initTheme() {
        setTheme(ThemeHelper.getSkinId())
    }

    abstract fun setup()
}