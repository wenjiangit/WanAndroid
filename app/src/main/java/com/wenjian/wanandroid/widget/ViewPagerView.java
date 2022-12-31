package com.wenjian.wanandroid.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Description: 可以设置ViewPager是否支持滑动
 * Date: 2018/7/23
 *
 * @author jian.wen@ubtrobot.com
 */
public class ViewPagerView extends ViewPager {

    private boolean isEnabled;

    public ViewPagerView(Context context) {
        super(context);
    }

    public ViewPagerView(@NonNull Context context, AttributeSet attrs) {
        super(context, attrs);
        int[] attrsArray = {android.R.attr.enabled};
        TypedArray array = context.obtainStyledAttributes(attrs, attrsArray);
        isEnabled = array.getBoolean(0, true);
        array.recycle();
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.isEnabled = enabled;
        requestLayout();
    }

    @Override
    public void setAdapter(@Nullable PagerAdapter adapter) {
        super.setAdapter(adapter);
        if (isInEditMode()) return;
        if (adapter != null) {
            setOffscreenPageLimit(adapter.getCount());
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        try {
            return isEnabled() && super.onInterceptTouchEvent(event);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        try {
            return !isEnabled() || super.onTouchEvent(event);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
