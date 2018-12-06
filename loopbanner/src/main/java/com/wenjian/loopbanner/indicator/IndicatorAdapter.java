package com.wenjian.loopbanner.indicator;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Description: IndicatorAdapter
 * Date: 2018/12/6
 *
 * @author jian.wen@ubtrobot.com
 */
public interface IndicatorAdapter {

    /**
     * 添加子indicator
     *
     * @param container 父布局
     * @param drawable  配置的Drawable
     * @param size      配置的指示器大小
     * @param margin    配置的指示器margin值
     */
    void addIndicator(LinearLayout container, Drawable drawable, int size, int margin);

    /**
     * 应用选中效果
     *
     * @param prev    前一个
     * @param current 当前
     */
    void applySelectState(View prev, View current);

    /**
     * 应用为选中效果
     *
     * @param indicator
     */
    void applyUnSelectState(View indicator);


    /**
     * 重置为初始效果(在第一个page选中的时候会触发)
     *
     * @param container
     * @return
     */
    boolean reset(LinearLayout container);


}
