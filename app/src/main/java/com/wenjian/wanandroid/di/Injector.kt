package com.wenjian.wanandroid.di

import com.wenjian.wanandroid.net.RetrofitManager
import com.wenjian.wanandroid.ui.home.HomeModelFactory
import com.wenjian.wanandroid.ui.knowledge.TreeModelFactory

/**
 * Description: Injector
 * Date: 2018/9/6
 *
 * @author jian.wen@ubtrobot.com
 */
object Injector {

    fun provideHomeModelFactory() = HomeModelFactory(RetrofitManager.service)
    fun provideTreeModelFactory() = TreeModelFactory(RetrofitManager.service)

}