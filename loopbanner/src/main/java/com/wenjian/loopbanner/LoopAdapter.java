package com.wenjian.loopbanner;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: LoopAdapter
 * Date: 2018/12/4
 *
 * @author jian.wen@ubtrobot.com
 */
public abstract class LoopAdapter<T> extends PagerAdapter {

    private SparseArray<ViewHolder> mHolderList = new SparseArray<>();
    private List<T> mData;
    private int mLayoutId;
    private boolean mCanLoop = true;
    private LoopBanner.OnItemClickListener mClickListener;

    public LoopAdapter(List<T> data, int layoutId) {
        mData = data;
        mLayoutId = layoutId;
    }

    public LoopAdapter(List<T> data) {
        this(data, -1);
    }

    public LoopAdapter(int layoutId) {
        this(new ArrayList<T>(), layoutId);
    }

    public LoopAdapter() {
        this(new ArrayList<T>(), -1);
    }

    @Override
    public final int getCount() {
        final int size = mData.size();
        if (size != 0) {
            return mCanLoop ? Integer.MAX_VALUE : size;
        }
        return 0;
    }

    @NonNull
    @Override
    public final Object instantiateItem(@NonNull ViewGroup container, int position) {
        final int newPosition = computePosition(position);
        ViewHolder holder = mHolderList.get(newPosition);
        if (holder == null) {
            View convertView = onCreateView(container);
            addClickListener(newPosition, convertView);
            holder = new ViewHolder(convertView);
            container.setTag(R.id.key_holder, holder);
            onBindView(holder, mData.get(newPosition));
        }
        container.addView(holder.itemView);
        return holder.itemView;
    }

    @Override
    public final void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
        mHolderList.put(computePosition(position), (ViewHolder) ((View) object).getTag(R.id.key_holder));
    }

    @Override
    public final boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    private void addClickListener(final int newPosition, View convertView) {
        if (mClickListener != null) {
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mClickListener != null) {
                        mClickListener.onItemClick(v, newPosition);
                    }
                }
            });
        }
    }

    /**
     * 获取真实的数据个数
     */
    public int getDataSize() {
        return mData.size();
    }

    private int computePosition(int position) {
        int size = mData.size();
        return size == 0 ? -1 : position % size;
    }

    /**
     * 子类可以复写此方法,添加自己的自定义View
     *
     * @param container ViewGroup
     * @return itemView
     */
    protected View onCreateView(@NonNull ViewGroup container) {
        View view;
        if (mLayoutId != -1) {
            view = LayoutInflater.from(container.getContext()).inflate(mLayoutId, container, false);
        } else {
            ImageView imageView = new ImageView(container.getContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view = imageView;
        }
        return view;
    }

    /**
     * 为item绑定数据
     *
     * @param holder ViewHolder
     * @param data   数据
     */
    public abstract void onBindView(ViewHolder holder, T data);

    /**
     * 绑定数据
     *
     * @param data List<T> data
     */
    public final void setNewData(List<T> data) {
        mData = data != null ? data : new ArrayList<T>();
        mHolderList.clear();
        notifyDataSetChanged();
    }

    void setOnItemClickListener(LoopBanner.OnItemClickListener listener) {
        this.mClickListener = listener;
    }

    void setCanLoop(boolean canLoop) {
        mCanLoop = canLoop;
    }

    public static final class ViewHolder {
        public View itemView;

        private SparseArray<View> mViewList = new SparseArray<>();

        ViewHolder(View itemView) {
            this.itemView = itemView;
        }

        @SuppressWarnings("unchecked")
        public <T> T getView(@IdRes int viewId) {
            View view = mViewList.get(viewId);
            if (view == null) {
                view = itemView.findViewById(viewId);
                mViewList.put(viewId, view);
            }
            return (T) view;
        }

        /**
         * Will set the text of a TextView.
         *
         * @param viewId The view id.
         * @param value  The text to put in the text view.
         * @return The BaseViewHolder for chaining.
         */
        public ViewHolder setText(@IdRes int viewId, CharSequence value) {
            TextView view = getView(viewId);
            view.setText(value);
            return this;
        }

        public ViewHolder setText(@IdRes int viewId, @StringRes int strId) {
            TextView view = getView(viewId);
            view.setText(strId);
            return this;
        }

        /**
         * Will set the image of an ImageView from a resource id.
         *
         * @param viewId     The view id.
         * @param imageResId The image resource id.
         * @return The BaseViewHolder for chaining.
         */
        public ViewHolder setImageResource(@IdRes int viewId, @DrawableRes int imageResId) {
            ImageView view = getView(viewId);
            view.setImageResource(imageResId);
            return this;
        }

        /**
         * Will set background color of a view.
         *
         * @param viewId The view id.
         * @param color  A color, not a resource id.
         * @return The BaseViewHolder for chaining.
         */
        public ViewHolder setBackgroundColor(@IdRes int viewId, @ColorInt int color) {
            View view = getView(viewId);
            view.setBackgroundColor(color);
            return this;
        }

        /**
         * Will set background of a view.
         *
         * @param viewId        The view id.
         * @param backgroundRes A resource to use as a background.
         * @return The BaseViewHolder for chaining.
         */
        public ViewHolder setBackgroundRes(@IdRes int viewId, @DrawableRes int backgroundRes) {
            View view = getView(viewId);
            view.setBackgroundResource(backgroundRes);
            return this;
        }

        /**
         * Will set text color of a TextView.
         *
         * @param viewId    The view id.
         * @param textColor The text color (not a resource id).
         * @return The BaseViewHolder for chaining.
         */
        public ViewHolder setTextColor(@IdRes int viewId, @ColorInt int textColor) {
            TextView view = getView(viewId);
            view.setTextColor(textColor);
            return this;
        }


        /**
         * Will set the image of an ImageView from a drawable.
         *
         * @param viewId   The view id.
         * @param drawable The image drawable.
         * @return The BaseViewHolder for chaining.
         */
        public ViewHolder setImageDrawable(@IdRes int viewId, Drawable drawable) {
            ImageView view = getView(viewId);
            view.setImageDrawable(drawable);
            return this;
        }

        /**
         * Add an action to set the image of an image view. Can be called multiple times.
         */
        public ViewHolder setImageBitmap(@IdRes int viewId, Bitmap bitmap) {
            ImageView view = getView(viewId);
            view.setImageBitmap(bitmap);
            return this;
        }

        /**
         * Set a view visibility to VISIBLE (true) or GONE (false).
         *
         * @param viewId  The view id.
         * @param visible True for VISIBLE, false for GONE.
         * @return The BaseViewHolder for chaining.
         */
        public ViewHolder setGone(@IdRes int viewId, boolean visible) {
            View view = getView(viewId);
            view.setVisibility(visible ? View.VISIBLE : View.GONE);
            return this;
        }

        /**
         * Set a view visibility to VISIBLE (true) or INVISIBLE (false).
         *
         * @param viewId  The view id.
         * @param visible True for VISIBLE, false for INVISIBLE.
         * @return The BaseViewHolder for chaining.
         */
        public ViewHolder setVisible(@IdRes int viewId, boolean visible) {
            View view = getView(viewId);
            view.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
            return this;
        }
    }
}
