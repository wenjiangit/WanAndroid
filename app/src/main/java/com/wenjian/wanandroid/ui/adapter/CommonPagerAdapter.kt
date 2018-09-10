package com.wenjian.wanandroid.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Description: CommonPagerAdapter
 * Date: 2018/9/10
 *
 * @author jian.wen@ubtrobot.com
 */
class CommonPagerAdapter(fm: FragmentManager, private val fragments: List<Fragment>) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment = fragments[position]
    override fun getCount(): Int = fragments.size
}
