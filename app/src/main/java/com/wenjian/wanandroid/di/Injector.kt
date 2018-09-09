package com.wenjian.wanandroid.di

import com.wenjian.wanandroid.net.RetrofitManager
import com.wenjian.wanandroid.ui.factory.CommonApiModelFactory
import com.wenjian.wanandroid.ui.home.HomeModelFactory

/**
 * Description: Injector
 * Date: 2018/9/6
 *
 * @author jian.wen@ubtrobot.com
 */
object Injector {

    fun provideHomeModelFactory() = HomeModelFactory(RetrofitManager.service)
    fun provideApiModelFactory() = CommonApiModelFactory(RetrofitManager.service)

}