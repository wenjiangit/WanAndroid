package com.wenjian.wanandroid.base

import android.os.Bundle
import com.wenjian.wanandroid.R

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
    }

    private fun initTheme() {
        setTheme(R.style.AppSkin_Purple)
    }

    abstract fun setup()
}