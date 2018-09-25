package com.wenjian.wanandroid.ui.project


import android.arch.lifecycle.Observer
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import android.widget.ProgressBar
import com.wenjian.wanandroid.R
import com.wenjian.wanandroid.base.BaseFragment
import com.wenjian.wanandroid.entity.ProjectTree
import com.wenjian.wanandroid.extension.apiModelDelegate
import com.wenjian.wanandroid.extension.gone
import com.wenjian.wanandroid.extension.visible


/**
 * A simple [Fragment] subclass.
 *
 */
class ProjectFragment : BaseFragment() {

    private var mTabLayout: TabLayout? = null
    private lateinit var mViewPager: ViewPager
    private lateinit var mPbLoading: ProgressBar

    private val mProjectModel: ProjectModel by apiModelDelegate(ProjectModel::class.java)

    companion object {
        fun newInstance() = ProjectFragment()
    }

    override fun getLayoutId(): Int = R.layout.fragment_project

    override fun findViews(mRoot: View) {
        mTabLayout = mRoot.findViewById(R.id.tab_layout)
        mViewPager = mRoot.findViewById(R.id.project_pager)
        mPbLoading = mRoot.findViewById(R.id.pbLoading)
    }

    override fun subscribeUi() {
        mProjectModel.projectTrees.observe(this, Observer { it ->
            showContentWithStatus(it) {
                //fix FragmentManager is already executing transactions
                //提供给子fragment只能用getChildFragmentManager
                mViewPager.adapter = ProjectPagerAdapter(childFragmentManager, it)
                mTabLayout?.setupWithViewPager(mViewPager)
                mTabLayout?.visible()
            }
        })
    }

    override fun onLazyLoad() {
        super.onLazyLoad()
        mProjectModel.loadProjectTree()
    }

    override fun showLoading() {
        mPbLoading.visible()
    }

    override fun hideLoading() {
        mPbLoading.gone()
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
