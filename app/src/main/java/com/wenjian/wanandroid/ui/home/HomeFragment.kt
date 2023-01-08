package com.wenjian.wanandroid.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.wenjian.wanandroid.R
import com.wenjian.wanandroid.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * Description: HomeFragment
 * Date: 2018/9/6
 *
 * @author jian.wen@ubtrobot.com
 */
class HomeFragment : BaseFragment() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun findViews(mRoot: View) {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vp_home.adapter = HomeTabAdapter(
            childFragmentManager, listOf(
                TabItem("热门博文", RecommendFragment.newInstance()),
                TabItem("每日一问", DailyQuestionFragment())
            )
        )
        tabs_home.setupWithViewPager(vp_home)
    }

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    data class TabItem(val title: String, val fragment: Fragment)

    class HomeTabAdapter(fm: FragmentManager, val list: List<TabItem>) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment = list[position].fragment

        override fun getCount(): Int = list.size

        override fun getPageTitle(position: Int): CharSequence {
            return list[position].title
        }
    }


}