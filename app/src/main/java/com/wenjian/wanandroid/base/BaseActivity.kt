package com.wenjian.wanandroid.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import androidx.core.widget.toast
import com.wenjian.wanandroid.entity.Resource

/**
 * Description: BaseActivity
 * Date: 2018/9/5
 *
 * @author jian.wen@ubtrobot.com
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

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
                    res.msg?.let { toast(it) }
                }
            }
        }
    }

    open fun showLoading() {

    }

    open fun hideLoading() {


    }

}