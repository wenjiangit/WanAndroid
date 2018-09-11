package com.wenjian.wanandroid.ui.knowledge

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.wenjian.wanandroid.R
import com.wenjian.wanandroid.base.BaseActivity
import com.wenjian.wanandroid.entity.SubTree
import com.wenjian.wanandroid.extension.extraDelegate
import com.wenjian.wanandroid.extension.setupActionBar
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

        subTrees?.let {
            treePager.adapter = SubTreeAdapter(supportFragmentManager,it)
            tabLayout.setupWithViewPager(treePager)
        }
    }


    class SubTreeAdapter(fm: FragmentManager, val list: List<SubTree>) : FragmentPagerAdapter(fm) {

        private val fragments: List<Fragment>

        init {
            fragments = list.map { ArticleListFragment.newInstance(it.id) }
        }

        override fun getItem(position: Int): Fragment = fragments[position]

        override fun getCount(): Int = fragments.size

        override fun getPageTitle(position: Int): CharSequence? {
            return list[position].name
        }
    }

}
