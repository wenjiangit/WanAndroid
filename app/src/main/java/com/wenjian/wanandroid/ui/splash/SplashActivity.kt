package com.wenjian.wanandroid.ui.splash

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.wenjian.wanandroid.MainActivity
import com.wenjian.wanandroid.R
import com.wenjian.wanandroid.extension.launch

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        launch(MainActivity::class.java)
        finish()
    }
}
