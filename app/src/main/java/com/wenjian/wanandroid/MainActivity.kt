package com.wenjian.wanandroid

import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.FragmentPagerAdapter
import com.google.android.material.appbar.AppBarLayout
import com.wenjian.wanandroid.base.BaseSkinActivity
import com.wenjian.wanandroid.extension.launch
import com.wenjian.wanandroid.extension.setupActionBar
import com.wenjian.wanandroid.extension.toastWarning
import com.wenjian.wanandroid.model.RxBus
import com.wenjian.wanandroid.model.SkinChangeEvent
import com.wenjian.wanandroid.ui.home.HomeFragment
import com.wenjian.wanandroid.ui.knowledge.TreeFragment
import com.wenjian.wanandroid.ui.mine.MineFragment
import com.wenjian.wanandroid.ui.project.ProjectFragment
import com.wenjian.wanandroid.ui.search.SearchActivity
import com.wenjian.wanandroid.widget.appbar.AppBarStateChangeListener
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.common_title_bar.*

class MainActivity : BaseSkinActivity() {

    private var lastBack: Long = 0

    private var disposable: Disposable? = null

    override fun setup() {
        setContentView(R.layout.activity_main)
        setupActionBar(show = false)
        initFragments()

        navigation.apply {
            val stateList = AppCompatResources.getColorStateList(this@MainActivity, R.color.navigation_menu_item_color)
            itemTextColor = stateList
            itemIconTintList = stateList
            setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.home -> {
                        doTabSelect(0, it.title)
                        true
                    }
                    R.id.knowledge -> {
                        doTabSelect(1, it.title)
                        true
                    }
                    R.id.project -> {
                        doTabSelect(2, it.title)
                        true
                    }

                    R.id.mine -> {
                        doTabSelect(3, it.title)
                        true
                    }
                    else -> false
                }
            }
        }

        //导航栏随着appbar做相反运动
        app_bar.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
            override fun onStateChange(appBarLayout: AppBarLayout, state: State) {
                when (state) {
                    State.EXPANDED -> showNavigation()
                    State.COLLAPSED -> hideNavigation()
                    else -> {
                    }
                }
            }
        })

        //注册皮肤变化的监听
        disposable = RxBus.toObservable(SkinChangeEvent::class.java)
                .subscribe {
                    recreate()
                }

    }

    private fun showNavigation() {
        navigation.animate().translationY(0f)
                .setDuration(200L)
                .start()
    }

    private fun hideNavigation() {
        navigation.animate().translationY(navigation.height.toFloat())
                .setDuration(200L)
                .start()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }


    private fun initFragments() {
        val mFragments = listOf(
                HomeFragment.newInstance(),
                TreeFragment.newInstance(),
                ProjectFragment.newInstance(),
                MineFragment.newInstance())
        mainPager.adapter = MainPagerAdapter(supportFragmentManager, mFragments)
    }

    private fun doTabSelect(position: Int, charSequence: CharSequence) {
        mainPager.currentItem = position
        toolBar.title = charSequence
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
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
            toastWarning("再按一次退出" + getString(R.string.app_name))
        } else {
            super.onBackPressed()
        }
        lastBack = current

    }


    class MainPagerAdapter(fm: androidx.fragment.app.FragmentManager, private val fragments: List<androidx.fragment.app.Fragment>) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): androidx.fragment.app.Fragment = fragments[position]
        override fun getCount(): Int = fragments.size
    }


}
