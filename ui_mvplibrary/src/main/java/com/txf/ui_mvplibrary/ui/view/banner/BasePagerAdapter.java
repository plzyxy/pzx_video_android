package com.txf.ui_mvplibrary.ui.view.banner;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.txf.ui_mvplibrary.interfaces.OnAppListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author txf
 * @Title ViewPager适配器基类
 * @package com.common.library.ui.view.adapter
 * @date 2017/6/6 0006
 */
public abstract class BasePagerAdapter<T> extends PagerAdapter {
    static final int IGNORE_ITEM_VIEW_TYPE = AdapterView.ITEM_VIEW_TYPE_IGNORE;
    private List<T> data = new ArrayList<>();
    private final RecycleBean recycleBean;
    private int viewType = 2;
    private OnAppListener.OnAdapterListener l;
    private boolean loop;

    public int getItemViewType(int position) {
        return AdapterView.ITEM_VIEW_TYPE_IGNORE;
    }

    public BasePagerAdapter() {
        this(new RecycleBean());
    }

    public OnAppListener.OnAdapterListener getListener() {
        return l;
    }

    public List<T> getData() {
        return data;
    }

    public BasePagerAdapter(OnAppListener.OnAdapterListener l) {
        this(new RecycleBean());
        this.l = l;
    }

    public int getViewTypeCount() {
        return 1;
    }

    BasePagerAdapter(RecycleBean recycleBin) {
        this.recycleBean = recycleBin;
        recycleBin.setViewTypeCount(getViewTypeCount());
    }

    public void setData(List<T> data) {
        setData(data, false);
    }

    /**
     * @param data
     * @param loop true: 循环 false 不循环
     */
    public void setData(List<T> data, boolean loop) {
        this.loop = loop;
        this.data = data == null ? new ArrayList<T>() : data;
        notifyDataSetChanged();
    }

    public boolean isLoop() {
        return loop;
    }

    @Override
    public int getCount() {
        if (loop && data.size() > 1) {
            if (data.size() > 0) {
                return Integer.MAX_VALUE;
            } else {
                return 0;
            }
        } else {
            return data.size();
        }
    }

    @Override
    public final Object instantiateItem(ViewGroup container, int position) {
        int viewType = getItemViewType(position);
        View view = null;
        position = position % data.size();
        if (viewType != IGNORE_ITEM_VIEW_TYPE) {
            view = recycleBean.getScrapView(position, viewType);
        }
        view = getItemView(position, view, container);
        container.addView(view);
        return view;
    }

    @Override
    public final void destroyItem(ViewGroup container, int position, Object object) {
        position = position % data.size();
        View view = (View) object;
        container.removeView(view);
        removeItemView(position, view);
        int viewType = getItemViewType(position);
        if (viewType != IGNORE_ITEM_VIEW_TYPE) {
            recycleBean.addScrapView(view, position, viewType);
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * 子类实现
     * viewPager添加视图的时候调用 同ListView的getView方法
     */
    protected abstract View getItemView(int position, View contentView, ViewGroup container);

    /**
     * 子类可重写
     * viewPager移除视图的时候调用
     */
    protected void removeItemView(int position, View view) {

    }

    ////////////////////以下是为了让notifyDataSetChanged();方法生效 ////////////////////
    protected int mChildCount = 0;

    @Override
    public void notifyDataSetChanged() {
        mChildCount = getCount();
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        if (mChildCount > 0) {
            mChildCount--;
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }
}
