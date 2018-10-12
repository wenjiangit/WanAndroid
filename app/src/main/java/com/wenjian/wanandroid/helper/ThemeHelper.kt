package com.wenjian.wanandroid.helper

import android.content.Context
import android.content.SharedPreferences
import android.support.annotation.AttrRes
import android.support.annotation.ColorRes
import com.wenjian.wanandroid.R
import com.wenjian.wanandroid.WanAndroidApp
import com.wenjian.wanandroid.entity.Skin
import com.wenjian.wanandroid.extension.getCompatColor

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
    private const val DEFAULT_THEME: Int = R.style.AppSkin_White
    private var current: Int = DEFAULT_THEME
    private var changed: Boolean = false

    fun loadSkin(): List<Skin> {
        return arrayListOf<Skin>().apply {
            add(Skin(R.style.AppSkin_White, "默认白", R.color.text_grey, false))
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

            forEach {
                if (getSkinId() == it.id) {
                    it.select = true
                    current = it.id
                }
            }
        }
    }

    fun setSkin(themeId: Int) {
        if (current != themeId) {
            changed = true
            current = themeId
        }
        SKIN_PREFS.edit().putInt(KEY_THEME_ID, themeId).apply()
    }

    /**
     * 主题是否改变
     */
    fun hasChanged() = changed

    fun getSkinId(): Int {
        return SKIN_PREFS.getInt(KEY_THEME_ID, DEFAULT_THEME)
    }

    /**
     * 获取主题属性颜色
     */
    fun obtainColorAttrValue(context: Context, @AttrRes attrId: Int, @ColorRes default: Int): Int {
        val arrayOf = intArrayOf(attrId)
        val typedArray = context.theme.obtainStyledAttributes(getSkinId(), arrayOf)
        val color = typedArray.getColor(0, context.getCompatColor(default))
        typedArray.recycle()
        return color
    }

    fun isDefault(skin: Skin) = skin.id == DEFAULT_THEME

}