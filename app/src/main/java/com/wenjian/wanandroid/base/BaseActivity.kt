package com.wenjian.wanandroid.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * Description: BaseActivity
 * Date: 2018/9/5
 *
 * @author jian.wen@ubtrobot.com
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onNavigateUp(): Boolean {
        finish()
        return super.onNavigateUp()
    }

}