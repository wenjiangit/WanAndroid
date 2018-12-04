package com.wenjian.loopbanner;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import java.util.List;

/**
 * Description: LoopBanner
 * Date: 2018/12/4
 *
 * @author jian.wen@ubtrobot.com
 */
public class LoopBanner extends FrameLayout {

    private static final long DEFAULT_INTERVAL_TIME = 3000L;
    private static final int DEFAULT_PAGE_LIMIT = 2;

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
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoopBanner, defStyleAttr, defStyleRes);
        try {
            mCanLoop = typedArray.getBoolean(R.styleable.LoopBanner_lb_canLoop, true);
            mInterval = typedArray.getInteger(R.styleable.LoopBanner_lb_interval, (int) DEFAULT_INTERVAL_TIME);
            mLrMargin = (int) typedArray.getDimension(R.styleable.LoopBanner_lb_lrMargin, 0);
            mOffscreenPageLimit = typedArray.getInteger(R.styleable.LoopBanner_lb_offsetPageLimit, DEFAULT_PAGE_LIMIT);
            mPageMargin = (int) typedArray.getDimension(R.styleable.LoopBanner_lb_pageMargin, 0);
            mTopMargin = (int) typedArray.getDimension(R.styleable.LoopBanner_lb_topMargin, 0);
            mBottomMargin = (int) typedArray.getDimension(R.styleable.LoopBanner_lb_bottomMargin, 0);
        } finally {
            typedArray.recycle();
        }
        init();
    }

    private void init() {
        //对超出父布局的子View不进行剪切,禁用硬件加速
        setClipChildren(false);
        setLayerType(LAYER_TYPE_SOFTWARE, null);

        mViewPager = new ViewPager(getContext());
        setupViewPager(mViewPager);
        mParams = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            mParams.setMarginStart(mLrMargin);
            mParams.setMarginEnd(mLrMargin);
        } else {
            mParams.leftMargin = mLrMargin;
            mParams.rightMargin = mLrMargin;
        }
        mParams.topMargin = mTopMargin;
        mParams.bottomMargin = mBottomMargin;
        addView(mViewPager, mParams);
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
                mHandler.postDelayed(mLoopRunnable, 3000);
                inLoop = true;
            }
        }
    }

    private void stopInternal() {
        mHandler.removeCallbacks(mLoopRunnable);
        inLoop = false;
    }

    public void enableLoop(boolean enable) {
        this.mCanLoop = enable;
    }

    public void setTopMargin(int topMargin) {
        mTopMargin = Tools.dp2px(getContext(), topMargin);
        mParams.topMargin = mTopMargin;
        mViewPager.setLayoutParams(mParams);
    }

    public void setBottomMargin(int bottomMargin) {
        mBottomMargin = Tools.dp2px(getContext(), bottomMargin);
        mParams.bottomMargin = mBottomMargin;
        mViewPager.setLayoutParams(mParams);
    }

    public int getLrMargin() {
        return mLrMargin;
    }

    public void setLrMargin(int lrMargin) {
        mLrMargin = Tools.dp2px(getContext(), lrMargin);
        mParams.leftMargin = mLrMargin;
        mParams.rightMargin = mLrMargin;
        mViewPager.setLayoutParams(mParams);
    }

    public int getPageMargin() {
        return mPageMargin;
    }

    public void setPageMargin(int pageMargin) {
        mPageMargin = Tools.dp2px(getContext(), pageMargin);
        mViewPager.setPageMargin(mPageMargin);
    }

    public int getOffscreenPageLimit() {
        return mOffscreenPageLimit;
    }

    public void setOffscreenPageLimit(int offscreenPageLimit) {
        if (offscreenPageLimit < 1) {
            throw new IllegalArgumentException("offscreenPageLimit should be >= 1");
        }
        mOffscreenPageLimit = offscreenPageLimit;
        mViewPager.setOffscreenPageLimit(offscreenPageLimit);
    }

    public long getInterval() {
        return mInterval;
    }

    public void setInterval(long interval) {
        mInterval = interval;
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus) {
            startInternal(true);
        } else {
            stopInternal();
        }
    }

    /**
     * 设置Adapter
     *
     * @param adapter LoopAdapter
     */
    public void setAdapter(LoopAdapter<?> adapter) {
        adapter.setCanLoop(mCanLoop);
        mViewPager.setAdapter(adapter);
        if (mCanLoop) {
            startInternal(true);
        }
    }

    /**
     * 直接设置图片地址
     *
     * @param urls 图片地址
     */
    public void setImages(List<String> urls) {
        SimpleImageAdapter imageAdapter = new SimpleImageAdapter(urls);
        imageAdapter.setCanLoop(mCanLoop);
        mViewPager.setAdapter(imageAdapter);
        if (mCanLoop) {
            startInternal(true);
        }
    }
}
