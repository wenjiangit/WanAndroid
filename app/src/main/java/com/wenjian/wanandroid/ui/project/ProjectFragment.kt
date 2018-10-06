package com.wenjian.wanandroid.ui.project


import android.arch.lifecycle.Observer
import android.support.design.widget.AppBarLayout
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
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
    private var mAppBar: AppBarLayout? = null

    private val mProjectModel: ProjectModel by apiModelDelegate(ProjectModel::class.java)

    companion object {
        fun newInstance() = ProjectFragment()
    }

    override fun getLayoutId(): Int = R.layout.fragment_project

    override fun findViews(mRoot: View) {
        mTabLayout = mRoot.findViewById(R.id.tab_layout)
        mViewPager = mRoot.findViewById(R.id.project_pager)
        mPbLoading = mRoot.findViewById(R.id.pbLoading)
        mAppBar = activity?.findViewById(R.id.app_bar)
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

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            mAppBar?.elevation = 0f
        } else {
            mAppBar?.elevation = 8f
        }

    }

    class ProjectPagerAdapter(fm: FragmentManager, val data: List<ProjectTree>) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment = ProjectListFragment.newInstance(data[position].id)

        override fun getCount(): Int = data.size

        override fun getPageTitle(position: Int): CharSequence? {
            return data[position].name
        }

    }

}
