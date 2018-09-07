package com.wenjian.wanandroid.ui.knowledge

import com.wenjian.wanandroid.R
import com.wenjian.wanandroid.base.BaseFragment

/**
 * Description: KnowledgeFragment
 * Date: 2018/9/6
 *
 * @author jian.wen@ubtrobot.com
 */
class KnowledgeFragment : BaseFragment() {
    override fun getLayoutId(): Int = R.layout.fragment_knowledge

    companion object {
        fun newInstance() = KnowledgeFragment()
    }

}