package com.wenjian.wanandroid

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.Menu
import android.view.MenuItem
import com.wenjian.wanandroid.base.BaseActivity
import com.wenjian.wanandroid.extension.launch
import com.wenjian.wanandroid.extension.setupActionBar
import com.wenjian.wanandroid.ui.home.HomeFragment
import com.wenjian.wanandroid.ui.knowledge.TreeFragment
import com.wenjian.wanandroid.ui.mine.MineFragment
import com.wenjian.wanandroid.ui.project.ProjectFragment
import com.wenjian.wanandroid.ui.search.SearchActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.common_title_bar.*
import org.jetbrains.anko.toast

class MainActivity : BaseActivity() {

    private var lastBack: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupActionBar(show = false)
        initFragments()

        navigation.apply {
            enableShiftingMode(false)
            enableItemShiftingMode(false)
//            enableAnimation(false)
            setSmallTextSize(12f)
            setLargeTextSize(13f)
            setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.home -> {
                        doTabSelect(0)
                        true
                    }
                    R.id.knowledge -> {
                        doTabSelect(1)
                        true
                    }
                    R.id.project -> {
                        doTabSelect(2)
                        true
                    }

                    R.id.mine -> {
                        doTabSelect(3)
                        true
                    }
                    else -> false
                }
            }
        }

        //导航栏随着appbar做相反运动
        app_bar.addOnOffsetChangedListener { _, verticalOffset ->
            navigation.translationY = Math.abs(verticalOffset).toFloat()
        }

    }

    private fun initFragments() {
        val mFragments = listOf(
                HomeFragment.newInstance(),
                TreeFragment.newInstance(),
                ProjectFragment.newInstance(),
                MineFragment.newInstance())
        mainPager.adapter = MainPagerAdapter(supportFragmentManager, mFragments)
    }

    private fun doTabSelect(position: Int) {
        mainPager.currentItem = position
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item?.itemId) {
        R.id.action_search -> {
            launch(SearchActivity::class.java)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.common_search, menu)
        return true
    }

    override fun onBackPressed() {
        val current = System.currentTimeMillis()
        if (current - lastBack > 2000) {
            toast("再按一次退出" + getString(R.string.app_name))
        } else {
            super.onBackPressed()
        }
        lastBack = current

    }


    class MainPagerAdapter(fm: FragmentManager, private val fragments: List<Fragment>) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment = fragments[position]
        override fun getCount(): Int = fragments.size
    }


}
