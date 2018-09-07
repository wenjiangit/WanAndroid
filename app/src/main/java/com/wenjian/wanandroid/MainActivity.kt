package com.wenjian.wanandroid

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import com.wenjian.wanandroid.base.BaseActivity
import com.wenjian.wanandroid.ui.home.HomeFragment
import com.wenjian.wanandroid.ui.knowledge.KnowledgeFragment
import com.wenjian.wanandroid.ui.mine.MineFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private val mAdapter: MainPagerAdapter by lazy {
        MainPagerAdapter(supportFragmentManager)
    }

    companion object {
        val TAG = MainActivity::class.java.simpleName!!

        val fragments: List<Fragment> = listOf(HomeFragment.newInstance(),
                KnowledgeFragment.newInstance(),
                MineFragment.newInstance())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        homePager.adapter = mAdapter

        homePager.offscreenPageLimit = 2

        homePager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> navigation.selectedItemId = R.id.home
                    1 -> navigation.selectedItemId = R.id.knowledge
                    2 -> navigation.selectedItemId = R.id.mine
                }
            }
        })

        navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    homePager.currentItem = 0
                    true
                }
                R.id.knowledge -> {
                    homePager.currentItem = 1
                    true
                }
                R.id.mine -> {
                    homePager.currentItem = 2
                    true
                }
                else -> false
            }
        }

    }


    class MainPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getCount(): Int = fragments.size

    }
}
