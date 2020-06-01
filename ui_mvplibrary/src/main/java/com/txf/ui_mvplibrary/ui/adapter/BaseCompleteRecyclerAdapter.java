package com.txf.ui_mvplibrary.ui.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.txf.ui_mvplibrary.interfaces.OnAppListener;
import com.txf.ui_mvplibrary.ui.adapter.hoder.BaseViewHoder;

import java.util.HashMap;
import java.util.Map;


/**
 * @author txf
 * @Title 基类recycler.Adapter
 * 调用方法{@link #addItemLayout(int, int)}传入 itemType 加 layout
 * 多布局注意需要重写方法 {@link #getItemViewType(int)}
 * @date 2017/6/6 0006
 */
public abstract class BaseCompleteRecyclerAdapter<T> extends BaseRecyclerAdapter<T> {
    protected Map<Integer, Integer> layouts;

    public BaseCompleteRecyclerAdapter(Context context) {
        super(context);
        setItemLayout();
    }

    public BaseCompleteRecyclerAdapter(Context context, OnAppListener.OnAdapterListener listener) {
        super(context, listener);
        setItemLayout();
    }

    protected void setItemLayout() {

    }

    /**
     * 添加item布局
     *
     * @param itemViewType item类型 {@link #getItemViewType(int)}
     * @param layoutid     item布局
     */
    public void addItemLayout(int itemViewType, @LayoutRes int layoutid) {
        if (layouts == null)
            layouts = new HashMap<>();
        layouts.put(itemViewType, layoutid);
    }

    @Override
    public BaseViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layouts != null && layouts.containsKey(viewType))
            return new BaseViewHoder(LayoutInflater.from(getContext()).inflate(layouts.get(viewType), parent, false));
        else
            return new BaseViewHoder(new View(getContext()));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BaseViewHoder) {
            onBindBaseViewHoder((BaseViewHoder) holder, position, getDatas().get(position));
        }
    }

    protected abstract void onBindBaseViewHoder(BaseViewHoder holder, int position, T item);
}
