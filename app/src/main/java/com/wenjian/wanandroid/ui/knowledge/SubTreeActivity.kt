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
import com.wenjian.wanandroid.ui.adapter.CommonPagerAdapter
import kotlinx.android.synthetic.main.activity_sub_tree.*

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
            it.forEach {
                tabLayout.addTab(tabLayout.newTab().setText(it.name))
            }
            val fragments = it.map { ArticleListFragment.newInstance(it.id) }
            treePager.adapter = CommonPagerAdapter(supportFragmentManager,fragments)
            treePager.offscreenPageLimit = 3
            tabLayout.setupWithViewPager(treePager)
        }
    }

}
