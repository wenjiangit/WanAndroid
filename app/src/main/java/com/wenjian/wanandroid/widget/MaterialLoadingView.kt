package com.wenjian.wanandroid.widget

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import com.wenjian.wanandroid.extension.getColorAccent
import com.wenjian.wanandroid.utils.Tools

/**
 * Description: MaterialLoadingView
 * Date: 2018/9/19
 *
 * @author jian.wen@ubtrobot.com
 */
class MaterialLoadingView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    companion object {
        private const val CIRCLE_BG_LIGHT: Long = 0xFFFAFAFA
    }

    private var mProgress: androidx.swiperefreshlayout.widget.CircularProgressDrawable? = null

    init {
        val mCircleView = CircleImageView(context, CIRCLE_BG_LIGHT.toInt())
        mProgress = androidx.swiperefreshlayout.widget.CircularProgressDrawable(
            getContext()
        ).apply {
            setStyle(androidx.swiperefreshlayout.widget.CircularProgressDrawable.DEFAULT)
            setColorSchemeColors(context.getColorAccent())
        }
        mCircleView.setImageDrawable(mProgress)
        val params = FrameLayout.LayoutParams(Tools.dip2px(context, 45f),
                Tools.dip2px(context, 45f), Gravity.CENTER)
        addView(mCircleView, params)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mProgress = null
    }

    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        if (visibility == View.VISIBLE) {
            mProgress?.start()
        } else {
            mProgress?.stop()
        }

    }
}