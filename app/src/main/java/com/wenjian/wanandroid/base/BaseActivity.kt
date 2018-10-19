package com.wenjian.wanandroid.base

import android.support.v7.app.AppCompatActivity
import com.wenjian.wanandroid.entity.Resource
import com.wenjian.wanandroid.extension.toastError
import com.wenjian.wanandroid.model.view.ViewCallback
import com.wenjian.wanandroid.widget.AppLoadingDialog

/**
 * Description: BaseActivity
 * Date: 2018/9/5
 *
 * @author jian.wen@ubtrobot.com
 */
abstract class BaseActivity : AppCompatActivity(), ViewCallback {

    private val loading: AppLoadingDialog by lazy { AppLoadingDialog(this) }

    override fun onNavigateUp(): Boolean {
        onBackPressed()
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
        loading.show()
    }

    override fun hideLoading() {
        loading.dismiss()
    }

    override fun showError(msg: String?) {
        msg?.let {
            toastError(msg)
        }
    }

    override fun showEmpty() {
    }

}