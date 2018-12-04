package com.wenjian.loopbanner;

import android.content.Context;

/**
 * Description: Tools
 * Date: 2018/12/4
 *
 * @author jian.wen@ubtrobot.com
 */
class Tools {

    static int dp2px(Context context, int dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return Math.round(dpValue * scale + 0.5f);
    }
}
