package com.wenjian.wanandroid.widget

import android.content.Context
import android.support.v4.widget.CircularProgressDrawable
import android.util.AttributeSet
import android.view.Gravity
import android.widget.FrameLayout
import com.wenjian.wanandroid.R
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

    private var mProgress: CircularProgressDrawable? = null

    init {
        val mCircleView = CircleImageView(context, CIRCLE_BG_LIGHT.toInt())
        mProgress = CircularProgressDrawable(getContext()).apply {
            setStyle(CircularProgressDrawable.DEFAULT)
            setColorSchemeColors(R.color.colorAccent)
        }
        mCircleView.setImageDrawable(mProgress)
        val params = FrameLayout.LayoutParams(Tools.dip2px(context, 45f),
                Tools.dip2px(context, 45f), Gravity.CENTER)
        addView(mCircleView, params)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        mProgress?.start()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mProgress?.stop()
    }
}