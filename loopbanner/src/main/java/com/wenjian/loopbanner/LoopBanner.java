package com.wenjian.loopbanner;

import android.annotation.TargetApi;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;

/**
 * Description: LoopBanner
 * Date: 2018/12/4
 *
 * @author jian.wen@ubtrobot.com
 */
public class LoopBanner extends FrameLayout implements LifecycleObserver {

    private static final int GRAVITY_BOTTOM_LEFT = 1;
    private static final int GRAVITY_BOTTOM_RIGHT = 2;
    private static final int GRAVITY_BOTTOM_CENTER = 3;
    private static final int DEFAULT_GRAVITY = GRAVITY_BOTTOM_CENTER;
    private static final long DEFAULT_INTERVAL_TIME = 3000L;
    private static final int DEFAULT_PAGE_LIMIT = 2;
    private static final int DEFAULT_INDICATOR_SIZE = 8;
    private static final int DEFAULT_INDICATOR_MARGIN = 5;
    private static final int DEFAULT_INDICATOR_MARGIN_TO_PARENT = 16;
    private static final long TOUCH_DELAY = 4000;
    private static final String TAG = "LoopBanner";

    /**
     * page切换周期
     */
    private long mInterval;

    /**
     * page对于父布局的上边距
     */
    private int mTopMargin;

    /**
     * page对于父布局的下边距
     */
    private int mBottomMargin;

    /**
     * page相对于父布局的左右边距
     */
    private int mLrMargin;
    private ViewPager mViewPager;
    /**
     * 离屏缓存page个数,和ViewPager的参数保持一致
     */
    private int mOffscreenPageLimit;
    /**
     * 每个page之间的间距
     */
    private int mPageMargin;
    private int mCurrentIndex;
    private Handler mHandler = new Handler();
    /**
     * 循环滚动
     */
    private final Runnable mLoopRunnable = new Runnable() {
        @Override
        public void run() {
            mViewPager.setCurrentItem(++mCurrentIndex);
            mHandler.postDelayed(this, mInterval);
        }
    };
    /**
     * 是否循环播放
     */
    private boolean mCanLoop;
    private FrameLayout.LayoutParams mParams;
    /**
     * 是否处于循环播放中
     */
    private boolean inLoop = false;
    /**
     * 指示器容器
     */
    private LinearLayout mIndicatorContainer;
    /**
     * 单个指示器的大小
     */
    private int mIndicatorSize;
    /**
     * 指示器间距
     */
    private int mIndicatorMargin;
    /**
     * 数据观察者
     */
    private final DataSetObserver mDataSetObserver = new DataSetObserver() {
        @Override
        public void onChanged() {
            Tools.logI(TAG, "onChanged");
            LoopAdapter adapter = getAdapter();
            if (adapter == null) {
                return;
            }
            final int dataSize = adapter.getDataSize();
            createIndicatorIfNeed(dataSize);
            if (dataSize > 0) {
                startInternal(true);
            }
        }

        @Override
        public void onInvalidated() {
            Tools.logI(TAG, "onInvalidated");
        }
    };
    /**
     * 指示器容器与page边距
     */
    private int mIndicatorMarginToParent;
    /**
     * 指示器的位置
     */
    private int mIndicatorGravity;
    /**
     * 是否显示指示器
     */
    private boolean mShowIndicator;


    public LoopBanner(@NonNull Context context) {
        this(context, null);
    }

    public LoopBanner(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoopBanner(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LoopBanner(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LoopBanner, defStyleAttr, defStyleRes);
        try {
            mCanLoop = a.getBoolean(R.styleable.LoopBanner_lb_canLoop, true);
            mShowIndicator = a.getBoolean(R.styleable.LoopBanner_lb_showIndicator, true);
            mInterval = a.getInteger(R.styleable.LoopBanner_lb_interval, (int) DEFAULT_INTERVAL_TIME);
            mOffscreenPageLimit = a.getInteger(R.styleable.LoopBanner_lb_offsetPageLimit, DEFAULT_PAGE_LIMIT);
            mPageMargin = (int) a.getDimension(R.styleable.LoopBanner_lb_pageMargin, 0);
            final int margin = (int) a.getDimension(R.styleable.LoopBanner_lb_margin, 0);
            mLrMargin = (int) a.getDimension(R.styleable.LoopBanner_lb_lrMargin, margin);
            mTopMargin = (int) a.getDimension(R.styleable.LoopBanner_lb_topMargin, margin);
            mBottomMargin = (int) a.getDimension(R.styleable.LoopBanner_lb_bottomMargin, margin);
            //for indicator
            mIndicatorGravity = a.getInt(R.styleable.LoopBanner_lb_indicatorGravity, DEFAULT_GRAVITY);
            mIndicatorSize = (int) a.getDimension(R.styleable.LoopBanner_lb_indicatorSize, Tools.dp2px(context, DEFAULT_INDICATOR_SIZE));
            mIndicatorMargin = (int) a.getDimension(R.styleable.LoopBanner_lb_indicatorMargin, Tools.dp2px(context, DEFAULT_INDICATOR_MARGIN));
            mIndicatorMarginToParent = a.getInt(R.styleable.LoopBanner_lb_indicatorMarginToParent, Tools.dp2px(context, DEFAULT_INDICATOR_MARGIN_TO_PARENT));
            Drawable selectDrawable = a.getDrawable(R.styleable.LoopBanner_lb_indicatorSelect);
            Drawable unSelectDrawable = a.getDrawable(R.styleable.LoopBanner_lb_indicatorUnSelect);

            if (selectDrawable != null && unSelectDrawable != null) {
                StateListDrawable listDrawable = new StateListDrawable();
                listDrawable.addState(new int[]{android.R.attr.state_selected}, selectDrawable);
                listDrawable.addState(new int[]{}, unSelectDrawable);
            }

        } finally {
            a.recycle();
        }
        init();
    }

    private void init() {
        //对超出父布局的子View不进行剪切,禁用硬件加速
        setClipChildren(false);
        setLayerType(LAYER_TYPE_SOFTWARE, null);

//        injectLifeCycle();
        mViewPager = new ViewPager(getContext());
        setupViewPager(mViewPager);
        mParams = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mParams.setMargins(mLrMargin, mTopMargin, mLrMargin, mBottomMargin);
        this.addView(mViewPager, mParams);

        if (mShowIndicator) {
            initIndicatorContainer();
        }
    }

    private void initIndicatorContainer() {
        mIndicatorContainer = new LinearLayout(getContext());
        mIndicatorContainer.setOrientation(LinearLayout.HORIZONTAL);
        LayoutParams layoutParams = createLayoutParams();
        this.addView(mIndicatorContainer, layoutParams);
    }

    public LayoutParams createLayoutParams() {
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layoutParams.bottomMargin = mIndicatorMarginToParent + mBottomMargin;
        switch (mIndicatorGravity) {
            case GRAVITY_BOTTOM_LEFT:
                layoutParams.gravity = Gravity.BOTTOM | Gravity.START;
                layoutParams.leftMargin = mIndicatorMarginToParent + mLrMargin;
                break;
            case GRAVITY_BOTTOM_RIGHT:
                layoutParams.gravity = Gravity.BOTTOM | Gravity.END;
                layoutParams.rightMargin = mIndicatorMarginToParent + mLrMargin;
                break;
            case GRAVITY_BOTTOM_CENTER:
                layoutParams.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
                break;
            default:
        }
        return layoutParams;
    }

    private void injectLifeCycle() {
        Context context = getContext();
        if (context instanceof LifecycleOwner) {
            ((LifecycleOwner) context).getLifecycle().addObserver(this);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onActivityStop() {
        Tools.logI(TAG, "onActivityStop");
        stopInternal();
    }

    private void setupViewPager(ViewPager viewPager) {
        viewPager.setPageMargin(mPageMargin);
        viewPager.setOffscreenPageLimit(mOffscreenPageLimit);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentIndex = position;
                updateIndicators(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_IDLE:
                        startInternal(false);
                        break;
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        stopInternal();
                        break;
                    default:
                }
            }
        });
    }

    private void updateIndicators(int position) {
        if (mIndicatorContainer == null) {
            return;
        }
        LoopAdapter adapter = getAdapter();
        if (adapter == null || adapter.getDataSize() == 0) {
            return;
        }

        int dataSize = adapter.getDataSize();
        int realPosition = position % dataSize;
        int childCount = mIndicatorContainer.getChildCount();
        if (childCount > 0) {
            for (int i = 0; i < childCount; i++) {
                mIndicatorContainer.getChildAt(i).setSelected(false);
            }
            mIndicatorContainer.getChildAt(realPosition).setSelected(true);
        }
    }

    private void startInternal(boolean force) {
        if (!mCanLoop) {
            return;
        }
        if (force) {
            mHandler.removeCallbacks(mLoopRunnable);
            mLoopRunnable.run();
            inLoop = true;
        } else {
            if (!inLoop) {
                mHandler.removeCallbacks(mLoopRunnable);
                mHandler.postDelayed(mLoopRunnable, TOUCH_DELAY);
                inLoop = true;
            }
        }
    }

    private void stopInternal() {
        mHandler.removeCallbacks(mLoopRunnable);
        inLoop = false;
    }

    /**
     * 设置是否循环播放
     *
     * @param enable loop
     */
    public void setCanLoop(boolean enable) {
        this.mCanLoop = enable;
    }

    /**
     * 开启调试模式,可以输出日志信息
     */
    public void openDebug() {
        Tools.setDebug(true);
    }

    /**
     * 设置上下左右与父布局的间距
     *
     * @param margin 间距
     */
    public void setMargins(int margin) {
        int marginDp = Tools.dp2px(getContext(), margin);
        mParams.setMargins(marginDp, marginDp, marginDp, marginDp);
        mLrMargin = mTopMargin = mBottomMargin = marginDp;
        mViewPager.setLayoutParams(mParams);
        adjustIndicator();
    }

    /**
     * 设置page页与父布局的上边距
     *
     * @param topMargin 上边距
     */
    public void setTopMargin(int topMargin) {
        final int topMarginDp = Tools.dp2px(getContext(), topMargin);
        mParams.topMargin = topMarginDp;
        mTopMargin = topMarginDp;
        mViewPager.setLayoutParams(mParams);
        adjustIndicator();
    }

    /**
     * 设置page页与父布局的下边距
     *
     * @param bottomMargin 下边距
     */
    public void setBottomMargin(int bottomMargin) {
        mBottomMargin = Tools.dp2px(getContext(), bottomMargin);
        mParams.bottomMargin = mBottomMargin;
        mViewPager.setLayoutParams(mParams);
        adjustIndicator();
    }

    /**
     * 设置中心page与父布局的左右边距
     *
     * @param lrMargin 左右边距
     */
    public void setLrMargin(int lrMargin) {
        mLrMargin = Tools.dp2px(getContext(), lrMargin);
        mParams.setMargins(mLrMargin, mTopMargin, mLrMargin, mBottomMargin);
        mViewPager.setLayoutParams(mParams);
        adjustIndicator();
    }

    private void adjustIndicator() {
        if (mIndicatorContainer != null) {
            mIndicatorContainer.setLayoutParams(createLayoutParams());
        }
    }

    public int getPageMargin() {
        return mPageMargin;
    }

    /**
     * 设置page与page之间的间距
     *
     * @param pageMargin page之间的间距
     */
    public void setPageMargin(int pageMargin) {
        mPageMargin = Tools.dp2px(getContext(), pageMargin);
        mViewPager.setPageMargin(mPageMargin);
        adjustIndicator();
    }

    public int getOffscreenPageLimit() {
        return mOffscreenPageLimit;
    }

    /**
     * 设置离屏缓存个数,默认为2,即内存中同时存在5个页面
     *
     * @param limit 离屏缓存个数
     */
    public void setOffscreenPageLimit(int limit) {
        mOffscreenPageLimit = limit;
        mViewPager.setOffscreenPageLimit(limit);
    }

    public long getInterval() {
        return mInterval;
    }

    /**
     * 设置轮播间隔时间
     *
     * @param interval 间隔时间
     */
    public void setInterval(long interval) {
        mInterval = interval;
    }

    /**
     * 设置page切换动画
     *
     * @param pageTransformer 切换动画
     */
    public void setPageTransformer(ViewPager.PageTransformer pageTransformer) {
        mViewPager.setPageTransformer(false, pageTransformer);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        Tools.logI(TAG, "onWindowFocusChanged," + hasWindowFocus);
        if (hasWindowFocus) {
            startInternal(true);
        } else {
            stopInternal();
        }
    }

    /**
     * 强制停止轮播
     */
    public void forceStop() {
        stopInternal();
        mCanLoop = false;
    }

    private void createIndicatorIfNeed(int dataSize) {
        if (mIndicatorContainer == null || dataSize <= 0) {
            return;
        }

        mIndicatorContainer.removeAllViews();

        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.indicator_color_selector);
        if (drawable == null) {
            return;
        }
        final int minimumWidth = drawable.getMinimumWidth();
        final int minimumHeight = drawable.getMinimumHeight();
        LinearLayout.LayoutParams params;
        if (minimumWidth == 0 || minimumHeight == 0) {
            params = new LinearLayout.LayoutParams(mIndicatorSize, mIndicatorSize);
        } else {
            params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        }
        params.leftMargin = mIndicatorMargin;
        for (int i = 0; i < dataSize; i++) {
            ImageView indicator = new ImageView(getContext());
            Drawable localDrawable = ContextCompat.getDrawable(getContext(), R.drawable.indicator_color_selector);
            indicator.setImageDrawable(localDrawable);
            mIndicatorContainer.addView(indicator, params);
        }
        mIndicatorContainer.getChildAt(0).setSelected(true);
    }

    public LoopAdapter getAdapter() {
        PagerAdapter adapter = mViewPager.getAdapter();
        return adapter == null ? null : (LoopAdapter) adapter;
    }

    /**
     * 设置Adapter加载数据
     *
     * @param adapter LoopAdapter
     */
    public void setAdapter(LoopAdapter<?> adapter) {
        mCurrentIndex = 0;
        adapter.setCanLoop(mCanLoop);
        adapter.registerDataSetObserver(mDataSetObserver);
        mViewPager.setAdapter(adapter);
        createIndicatorIfNeed(adapter.getDataSize());
    }

    /**
     * 直接设置图片地址和点击事件
     *
     * @param urls 图片地址
     */
    public void setImages(List<String> urls, OnItemClickListener listener) {
        SimpleImageAdapter imageAdapter = new SimpleImageAdapter(urls);
        imageAdapter.setOnItemClickListener(listener);
        this.setAdapter(imageAdapter);
    }

    public void setImages(List<String> urls) {
        this.setImages(urls, null);
    }

    /**
     * 是否使用指示器
     *
     * @param enable
     */
    public void enableIndicator(boolean enable) {
        this.mShowIndicator = enable;
        if (enable && mIndicatorContainer == null) {
            initIndicatorContainer();
        }
    }

    public interface OnItemClickListener {
        /**
         * Item点击事件处理
         *
         * @param itemView 被点击的view
         * @param position 位置
         */
        void onItemClick(View itemView, int position);
    }


}
