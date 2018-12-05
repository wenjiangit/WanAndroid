package com.wenjian.loopbanner;

import android.content.Context;
import android.util.Log;

/**
 * Description: Tools
 * Date: 2018/12/4
 *
 * @author jian.wen@ubtrobot.com
 */
class Tools {

    private static boolean debug = true;

    static void setDebug(boolean debug) {
        Tools.debug = debug;
    }

    static int dp2px(Context context, int dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return Math.round(dpValue * scale + 0.5f);
    }

    static void logD(String tag, String msg) {
        if (debug) {
            Log.d(tag, msg);
        }
    }

    static void logI(String tag, String msg) {
        if (debug) {
            Log.i(tag, msg);
        }
    }

    static void logE(String tag, String msg, Throwable throwable) {
        if (debug) {
            Log.e(tag, msg, throwable);
        }
    }


}
