package com.wenjian.wanandroid.ui.knowledge

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.wenjian.wanandroid.R
import com.wenjian.wanandroid.base.BaseActivity
import com.wenjian.wanandroid.entity.SubTree
import com.wenjian.wanandroid.extension.extraDelegate
import com.wenjian.wanandroid.extension.setSystemBarColor
import com.wenjian.wanandroid.extension.setupActionBar
import com.wenjian.wanandroid.utils.MaterialColor
import kotlinx.android.synthetic.main.activity_sub_tree.*

/**
 * Description: SubTreeActivity
 * Date: 2018/9/6
 *
 * @author jian.wen@ubtrobot.com
 */
class SubTreeActivity : BaseActivity() {

    private val mTitle: String? by extraDelegate(EXTRA_TITLE)

    private val subTrees: ArrayList<SubTree>? by extraDelegate(EXTRA_SUBTREE)

    companion object {
        const val EXTRA_TITLE = "title"
        const val EXTRA_SUBTREE = "subtree_list"

        fun start(ctx: Context, title: String, subtrees: ArrayList<SubTree>) {
            Intent(ctx, SubTreeActivity::class.java).apply {
                putExtra(EXTRA_TITLE, title)
                putParcelableArrayListExtra(EXTRA_SUBTREE, subtrees)
            }.let {
                ctx.startActivity(it)
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_tree)
        setupActionBar(title = mTitle)
        transformColor(0)
        subTrees?.let {
            treePager.adapter = SubTreeAdapter(supportFragmentManager, it)
            tabLayout.setupWithViewPager(treePager)
            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabReselected(tab: TabLayout.Tab) {
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {
                }

                override fun onTabSelected(tab: TabLayout.Tab) {
                    //设置状态栏和appbar的背景颜色
                    transformColor(tab.position)
                }
            })
        }
    }

    fun transformColor(index: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val color = MaterialColor.getColor(this, index)
            layAppBar.setBackgroundColor(color)
            setSystemBarColor(colorInt = color)
        }
    }


    class SubTreeAdapter(fm: androidx.fragment.app.FragmentManager, val list: List<SubTree>) : FragmentPagerAdapter(fm) {

        private val fragments: List<androidx.fragment.app.Fragment>

        init {
            fragments = list.map { ArticleListFragment.newInstance(it.id) }
        }

        override fun getItem(position: Int): androidx.fragment.app.Fragment = fragments[position]

        override fun getCount(): Int = fragments.size

        override fun getPageTitle(position: Int): CharSequence? {
            return list[position].name
        }
    }

}
