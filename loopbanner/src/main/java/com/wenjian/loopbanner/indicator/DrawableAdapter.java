package com.wenjian.loopbanner.indicator;

import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Description: DrawableAdapter
 * Date: 2018/12/6
 *
 * @author jian.wen@ubtrobot.com
 */
public final class DrawableAdapter implements IndicatorAdapter {

    private LinearLayout.LayoutParams mLayoutParams;

    @Override
    public void addIndicator(LinearLayout container, Drawable drawable, int size, int margin) {
        LinearLayout.LayoutParams layoutParams = generateLayoutParams(drawable, size, margin);
        ImageView image = new ImageView(container.getContext());
        ViewCompat.setBackground(image, drawable);
        container.addView(image, layoutParams);
    }

    private LinearLayout.LayoutParams generateLayoutParams(Drawable drawable, int size, int margin) {
        if (mLayoutParams == null) {
            final int minimumWidth = drawable.getMinimumWidth();
            final int minimumHeight = drawable.getMinimumHeight();
            LinearLayout.LayoutParams layoutParams;
            if (minimumWidth == 0 || minimumHeight == 0) {
                layoutParams = new LinearLayout.LayoutParams(size, size);
            } else {
                layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
            }
            layoutParams.leftMargin = margin;
            mLayoutParams = layoutParams;
        }
        return mLayoutParams;
    }

    @Override
    public void applySelectState(View prev,View current) {
        current.setSelected(true);
        current.requestLayout();
    }

    @Override
    public void applyUnSelectState(View indicator) {
        indicator.setSelected(false);
        indicator.requestLayout();
    }

    @Override
    public boolean reset(LinearLayout container) {
        return false;
    }

}
