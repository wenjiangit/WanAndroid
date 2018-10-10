package com.wenjian.wanandroid.di

import com.wenjian.wanandroid.ui.factory.CommonApiModelFactory

/**
 * Description: Injector
 * Date: 2018/9/6
 *
 * @author jian.wen@ubtrobot.com
 */
object Injector {

    fun provideApiModelFactory() = CommonApiModelFactory()

}