package com.wenjian.wanandroid.ui.project


import androidx.lifecycle.Observer
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import android.view.View
import android.widget.ProgressBar
import com.wenjian.wanandroid.R
import com.wenjian.wanandroid.base.VMFragment
import com.wenjian.wanandroid.entity.ProjectTree
import com.wenjian.wanandroid.extension.gone
import com.wenjian.wanandroid.extension.visible


/**
 * A simple [Fragment] subclass.
 *
 */
class ProjectFragment : VMFragment<ProjectModel>(ProjectModel::class.java) {

    private var mTabLayout: TabLayout? = null
    private lateinit var mViewPager: androidx.viewpager.widget.ViewPager
    private lateinit var mPbLoading: ProgressBar
    private var mAppBar: AppBarLayout? = null

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

    override fun onLazyLoad() {
        super.onLazyLoad()
        mViewModel.loadProjectTree()
                .observe(this, Observer {data->
                    data?.let {
                        mViewPager.adapter = ProjectPagerAdapter(childFragmentManager, it)
                        mTabLayout?.setupWithViewPager(mViewPager)
                        mTabLayout?.visible()
                    }
                })
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

    class ProjectPagerAdapter(fm: androidx.fragment.app.FragmentManager, val data: List<ProjectTree>) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): androidx.fragment.app.Fragment = ProjectListFragment.newInstance(data[position].id)

        override fun getCount(): Int = data.size

        override fun getPageTitle(position: Int): CharSequence? {
            return data[position].name
        }

    }

}
