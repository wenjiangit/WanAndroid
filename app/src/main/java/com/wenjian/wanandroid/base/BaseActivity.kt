package com.wenjian.wanandroid.base

import android.support.v7.app.AppCompatActivity
import androidx.core.widget.toast
import com.wenjian.wanandroid.entity.Resource
import com.wenjian.wanandroid.model.view.ViewFeature

/**
 * Description: BaseActivity
 * Date: 2018/9/5
 *
 * @author jian.wen@ubtrobot.com
 */
abstract class BaseActivity : AppCompatActivity(), ViewFeature {


    override fun onNavigateUp(): Boolean {
        finish()
        return super.onNavigateUp()
    }

    open fun <T> showContentWithStatus(it: Resource<T>?, render: (T) -> Unit) {
        it?.let { res ->
            when (res.status) {
                Resource.STATUS.SUCCESS -> {
                    hideLoading()
                    render(res.data!!)
                }
                Resource.STATUS.LOADING -> showLoading()
                Resource.STATUS.ERROR -> {
                    hideLoading()
                    showError(it.msg)
                }
            }
        }
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showError(msg: String?) {
        msg?.let {
            toast(msg)
        }
    }

    override fun showEmpty() {
    }

}