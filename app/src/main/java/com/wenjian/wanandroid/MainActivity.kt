package com.wenjian.wanandroid

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.Menu
import android.view.MenuItem
import com.wenjian.wanandroid.base.BaseActivity
import com.wenjian.wanandroid.extension.disableShiftMode
import com.wenjian.wanandroid.extension.launch
import com.wenjian.wanandroid.extension.setupActionBar
import com.wenjian.wanandroid.ui.home.HomeFragment
import com.wenjian.wanandroid.ui.knowledge.TreeFragment
import com.wenjian.wanandroid.ui.mine.MineFragment
import com.wenjian.wanandroid.ui.project.ProjectFragment
import com.wenjian.wanandroid.ui.search.SearchActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class MainActivity : BaseActivity() {

    private var lastBack: Long = 0

    private var curFragment: Fragment? = null

    private lateinit var mFragments: List<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupActionBar(show = false)

        initFragments()
        navigation.setOnNavigationItemSelectedListener {
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

        navigation.disableShiftMode()
    }

    private fun initFragments() {
        mFragments = listOf(
                HomeFragment.newInstance(),
                TreeFragment.newInstance(),
                ProjectFragment.newInstance(),
                MineFragment.newInstance())

        supportFragmentManager.beginTransaction().apply {
            for (i in 0 until mFragments.size) {
                val fragment = mFragments[i]
                add(R.id.container, fragment)
                if (i != 0) {
                    hide(fragment)
                } else {
                    show(fragment)
                    curFragment = fragment
                }
            }
        }.commit()
    }

    private fun doTabSelect(position: Int) {
        val fragment = mFragments[position]
        if (fragment == curFragment) {
            return
        }
        supportFragmentManager.beginTransaction().apply {
            hide(curFragment)
            show(fragment)
        }.commit()
        curFragment = fragment
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


    class MainPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        private val fragments: List<Fragment>

        init {
            fragments = listOf(
                    HomeFragment.newInstance(),
                    TreeFragment.newInstance(),
                    ProjectFragment.newInstance(),
                    MineFragment.newInstance())
        }

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getCount(): Int = fragments.size

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
}
