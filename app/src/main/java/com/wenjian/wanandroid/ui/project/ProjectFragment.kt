package com.wenjian.wanandroid.ui.project


import android.arch.lifecycle.Observer
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import com.wenjian.wanandroid.R
import com.wenjian.wanandroid.base.BaseFragment
import com.wenjian.wanandroid.entity.ProjectTree
import com.wenjian.wanandroid.extension.apiModelDelegate


/**
 * A simple [Fragment] subclass.
 *
 */
class ProjectFragment : BaseFragment() {

    private lateinit var mTabLayout: TabLayout
    private lateinit var mViewPager: ViewPager

    private val mProjectModel: ProjectModel by apiModelDelegate(ProjectModel::class.java)

    companion object {
        fun newInstance() = ProjectFragment()
    }

    override fun getLayoutId(): Int = R.layout.fragment_project

    override fun findViews(mRoot: View) {
        mTabLayout = mRoot.findViewById(R.id.tab_layout)
        mViewPager = mRoot.findViewById(R.id.project_pager)
    }

    override fun initViews() {

    }

    override fun subscribeUi() {
        mProjectModel.projectTrees.observe(this, Observer { it ->
            showContentWithStatus(it){
                mViewPager.adapter = ProjectPagerAdapter(fragmentManager!!, it)
                mTabLayout.setupWithViewPager(mViewPager)
            }
        })
    }

    override fun onLazyLoad() {
        super.onLazyLoad()
        mProjectModel.loadProjectTree()
    }

    class ProjectPagerAdapter(fm: FragmentManager, val data: List<ProjectTree>) : FragmentStatePagerAdapter(fm) {

        private val fragments: List<Fragment>

        init {
            fragments = data.map { ProjectListFragment.newInstance(it.id) }
        }

        override fun getItem(position: Int): Fragment = fragments[position]

        override fun getCount(): Int = fragments.size

        override fun getPageTitle(position: Int): CharSequence? {
            return data[position].name
        }

    }

}
