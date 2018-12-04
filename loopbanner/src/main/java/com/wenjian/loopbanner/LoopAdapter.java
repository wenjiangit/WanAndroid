package com.wenjian.loopbanner;

import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
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
        return mCanLoop ? Integer.MAX_VALUE : mData.size();
    }

    @NonNull
    @Override
    public final Object instantiateItem(@NonNull ViewGroup container, int position) {
        final int newPosition = position % mData.size();
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

    private void addClickListener(final int newPosition, View convertView) {
        if (mClickListener != null) {
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickListener.onItemClick(v,newPosition);
                }
            });
        }
    }

    @Override
    public final void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
        mHolderList.put(position % mData.size(), (ViewHolder) ((View) object).getTag(R.id.key_holder));
    }

    @Override
    public final boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    public View onCreateView(@NonNull ViewGroup container) {
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

    public final void setData(List<T> data) {
        mData = data != null ? data : new ArrayList<T>();
        mHolderList.clear();
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(LoopBanner.OnItemClickListener listener) {
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

        public void setText(@IdRes int viewId, String text) {
            TextView view = getView(viewId);
            view.setText(text);
        }

        public void setImageResource(@IdRes int viewId, @DrawableRes int resId) {
            ImageView view = getView(viewId);
            view.setImageResource(resId);
        }
    }
}
