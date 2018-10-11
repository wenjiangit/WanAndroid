package com.wenjian.wanandroid.helper

import android.content.Context
import android.content.SharedPreferences
import com.wenjian.wanandroid.R
import com.wenjian.wanandroid.WanAndroidApp
import com.wenjian.wanandroid.entity.Skin

/**
 * Description: ThemeHelper
 * Date: 2018/10/11
 *
 * @author jian.wen@ubtrobot.com
 */
object ThemeHelper {
    private const val NAME: String = "skin_prefs"
    private const val KEY_THEME_ID = "key_theme_id"
    private val SKIN_PREFS: SharedPreferences = WanAndroidApp.instance.getSharedPreferences(NAME, Context.MODE_PRIVATE)

    fun loadSkin(): List<Skin> {
        val ret = arrayListOf<Skin>().apply {
            add(Skin(R.style.AppSkin_Green, "酷安绿", R.color.skin_green_kuan, false))
            add(Skin(R.style.AppSkin_Red, "姨妈红", R.color.skin_red, false))
            add(Skin(R.style.AppSkin_Pink, "哔哩粉", R.color.skin_pink, false))
            add(Skin(R.style.AppSkin_Blue_Yiti, "颐提蓝", R.color.skin_blue_yiti, false))
            add(Skin(R.style.AppSkin_Green_Shuiya, "水鸭青", R.color.skin_green_shuiya, false))
            add(Skin(R.style.AppSkin_Orange, "伊藤橙", R.color.skin_orange_yiteng, false))
            add(Skin(R.style.AppSkin_Purple, "基佬紫", R.color.skin_purple, false))
            add(Skin(R.style.AppSkin_Blue_Zhihu, "知乎蓝", R.color.skin_blue_zhihu, false))
            add(Skin(R.style.AppSkin_Palm_Gutong, "古铜棕", R.color.skin_gutong, false))
            add(Skin(R.style.AppSkin_Grey, "低调灰", R.color.skin_grey, false))
            add(Skin(R.style.AppSkin_Black, "高端黑", R.color.skin_black, false))
        }
        ret.forEach{
            if (getSkinId() == it.id) {
                it.select = true
            }
        }
        return ret
    }

    fun setSkin(themeId: Int) {
        SKIN_PREFS.edit().putInt(KEY_THEME_ID, themeId).apply()
    }

    fun getSkinId(): Int {
        return SKIN_PREFS.getInt(KEY_THEME_ID, -1)
    }

}