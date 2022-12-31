package com.wenjian.wanandroid.widget.appbar

import com.google.android.material.appbar.AppBarLayout

/**
 * Description: AppBarStateChangeListener
 * Date: 2018/10/15
 *
 * @author jian.wen@ubtrobot.com
 */
abstract class AppBarStateChangeListener : AppBarLayout.OnOffsetChangedListener {

    private var mCurrentState: State = State.IDLE

    override fun onOffsetChanged(appBarLayout: AppBarLayout, offset: Int) {
        fun notifyChange(state: State) {
            if (mCurrentState != state) {
                onStateChange(appBarLayout, state)
            }
            mCurrentState = state
        }
        when {
            offset == 0 -> notifyChange(State.EXPANDED)
            Math.abs(offset) >= appBarLayout.totalScrollRange -> notifyChange(State.COLLAPSED)
            else -> notifyChange(State.IDLE)
        }
    }


    abstract fun onStateChange(appBarLayout: AppBarLayout, state: State)


    enum class State {
        /**
         *  展开
         */
        EXPANDED,
        /**
         * 折叠
         */
        COLLAPSED,
        /**
         * 其他中间状态
         */
        IDLE
    }
}