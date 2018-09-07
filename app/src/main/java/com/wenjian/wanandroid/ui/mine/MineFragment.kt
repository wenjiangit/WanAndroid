package com.wenjian.wanandroid.ui.mine


import com.wenjian.wanandroid.R
import com.wenjian.wanandroid.base.BaseFragment


/**
 * Description: MineFragment
 * Date: 2018/9/6
 *
 * @author jian.wen@ubtrobot.com
 */
class MineFragment : BaseFragment() {

    override fun getLayoutId(): Int = R.layout.fragment_mine

    companion object {
        fun newInstance() = MineFragment()
    }


}
