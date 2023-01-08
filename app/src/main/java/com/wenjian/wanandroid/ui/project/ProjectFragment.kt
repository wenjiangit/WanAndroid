package com.wenjian.wanandroid.ui.project


import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.lifecycleScope
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import com.wenjian.wanandroid.R
import com.wenjian.wanandroid.base.VMFragment
import com.wenjian.wanandroid.entity.ProjectTree
import com.wenjian.wanandroid.extension.gone
import com.wenjian.wanandroid.extension.visible
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


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
        mAppBar = activity?.findViewById(R.id.app_bar) as AppBarLayout?
    }

    override fun onLazyLoad() {
        super.onLazyLoad()
        mViewModel.loadProjectTree()
            .onEach { data ->
                mViewPager.adapter = ProjectPagerAdapter(childFragmentManager, data)
                mTabLayout?.setupWithViewPager(mViewPager)
                mTabLayout?.visible()
            }.launchIn(lifecycleScope)
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

    class ProjectPagerAdapter(
        fm: androidx.fragment.app.FragmentManager,
        val data: List<ProjectTree>
    ) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment =
            ProjectListFragment.newInstance(data[position].id)

        override fun getCount(): Int = data.size

        override fun getPageTitle(position: Int): CharSequence {
            return data[position].name
        }

    }

}
