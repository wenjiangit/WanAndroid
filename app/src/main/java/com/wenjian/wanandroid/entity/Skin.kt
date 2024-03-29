package com.wenjian.wanandroid.entity

import androidx.annotation.ColorRes
import androidx.annotation.StyleRes

/**
 * Description: Skin
 * Date: 2018/10/11
 *
 * @author jian.wen@ubtrobot.com
 */
class Skin(@StyleRes val id: Int, val name: String, @ColorRes val color: Int, var select: Boolean)