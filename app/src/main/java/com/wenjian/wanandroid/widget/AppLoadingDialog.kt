package com.wenjian.wanandroid.widget

import android.app.Dialog
import android.content.Context
import android.view.Window
import com.wenjian.wanandroid.R

/**
 * Description: AppLoadingDialog
 * Date: 2018/10/17
 *
 * @author jian.wen@ubtrobot.com
 */
class AppLoadingDialog(context: Context) : Dialog(context) {

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        setContentView(R.layout.lay_loading)
        setCancelable(false)
    }

}