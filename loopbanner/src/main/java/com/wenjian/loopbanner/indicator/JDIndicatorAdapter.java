package com.wenjian.loopbanner.indicator;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wenjian.loopbanner.R;

/**
 * Description: JDIndicatorAdapter
 * Date: 2018/12/6
 *
 * @author jian.wen@ubtrobot.com
 */
public class JDIndicatorAdapter implements IndicatorAdapter {

    private final int drawableId;

    private boolean initialed = false;
    private float mScale;

    public JDIndicatorAdapter(int drawableId) {
        this.drawableId = drawableId;
    }

    public JDIndicatorAdapter() {
        this(R.drawable.indicator_jd);
    }

    @Override
    public void addIndicator(LinearLayout container, Drawable drawable, int size, int margin) {
        drawable = ContextCompat.getDrawable(container.getContext(), drawableId);
        if (drawable == null) {
            throw new IllegalArgumentException("please provide valid drawableId");
        }
        ImageView image = new ImageView(container.getContext());
        ViewCompat.setBackground(image, drawable);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.leftMargin = margin;
        container.addView(image, params);

        computeScale(drawable.getMinimumWidth(), margin);

    }

    @Override
    public void applySelectState(View prev, View current) {
        prev.setPivotX(0);
        prev.setPivotY(prev.getHeight()/2);
        prev.animate().scaleX(mScale).setDuration(200).start();
    }

    @Override
    public void applyUnSelectState(View indicator) {

    }

    @Override
    public boolean reset(LinearLayout container) {
        int childCount = container.getChildCount();
        if (childCount > 0) {
            for (int i = 0; i < childCount; i++) {
                View childAt = container.getChildAt(i);
                childAt.setPivotX(0);
                childAt.setPivotY(childAt.getHeight()/2);
                childAt.animate().scaleX(1).setDuration(100).start();
            }
        }
        return true;
    }

    private void computeScale(int width, int margin) {
        if (!initialed) {
            mScale = width == 0 ? 2 : ((width + margin + width / 2) * 1f) / width;
            initialed = true;
        }
    }

    private Animation getResetAnimation() {
        Animation animation2 = new ScaleAnimation(mScale, 1, 1, 1, 0, 0.5f);
        animation2.setFillAfter(true);
        animation2.setDuration(100);
        return animation2;
    }

    private Animation getSelectAnimation() {
        Animation animation1 = new ScaleAnimation(1, mScale, 1, 1, 0, 0.5f);
        animation1.setFillAfter(true);
        animation1.setDuration(200);
        return animation1;
    }
}
