package com.wenjian.wanandroid.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Description: BaseFragment
 * Date: 2018/9/6
 *
 * @author jian.wen@ubtrobot.com
 */
abstract class BaseFragment : Fragment(){

    @Suppress("LeakingThis", "PropertyName")
    open val TAG: String = this::class.java.simpleName

    private var mRoot: View? = null

    @LayoutRes
    abstract fun getLayoutId(): Int

    private var viewInitiated: Boolean = false
    private var dataInitiated: Boolean = false
    open var forceUpdate: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 提前订阅，利用 flowWithLifecycle 确定实际订阅时机
        subscribeUi()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.i(TAG, "onCreateView")
        if (mRoot == null) {
            mRoot = inflater.inflate(getLayoutId(), container, false)
            Log.i(TAG, "inflate")
            findViews(mRoot!!)
            initViews()
        } else {
            mRoot?.parent?.let {
                (it as ViewGroup).removeView(mRoot)
            }
        }
        return mRoot
    }

    /**
     * fragment中还是老老实实findViewById吧,可以避免许多坑
     * 当然也可以在onViewCreated中进行view的初始化,但是onViewCreated会被调用多次
     * 尤其是与viewPager共同使用时,会导致许多问题
     */
    abstract fun findViews(mRoot: View)

    open fun subscribeUi() {

    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart: ")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume: ")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause: ")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop: ")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(TAG, "onViewCreated")
        viewInitiated = true
        tryLoadData()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        Log.i(TAG, "setUserVisibleHint==$isVisibleToUser")
        tryLoadData()
    }

    private fun tryLoadData() {
        if (viewInitiated && userVisibleHint && (!dataInitiated || forceUpdate)) {
            onLazyLoad()
            dataInitiated = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i(TAG, "onDestroyView")
        viewInitiated = false
    }

    open fun onLazyLoad() {
        Log.i(TAG, "onLazyLoad")
    }


    open fun refresh() {
        onLazyLoad()
    }


    open fun initViews() {
    }
}