package com.txf.ui_mvplibrary.ui.adapter;

import android.content.Context;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;

import com.txf.ui_mvplibrary.interfaces.OnAppListener;

import java.util.ArrayList;
import java.util.List;


/**
 * @author txf
 * @Title 基类recycler.Adapter
 * @date 2017/6/6 0006
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected List<T> datas;
    protected Context context;
    protected OnAppListener.OnAdapterListener listener;

    public BaseRecyclerAdapter(Context context) {
        this.context = context;
    }

    public BaseRecyclerAdapter(Context context, OnAppListener.OnAdapterListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public int dip2px(float dipValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public int getItemTypeCount(int itemType) {
        if (getItemCount() == 0)
            return 0;
        int count = 0;
        for (int i = 0; i < getItemCount(); i++) {
            if (getItemViewType(i) == itemType) {
                count++;
            }
        }
        return count;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }

    public void addDatas(List<T> datas) {
        if (datas == null) {
            return;
        }
        if (this.datas == null) {
            this.datas = new ArrayList<>();
        }
        this.datas.addAll(datas);
    }

    public void appendData(T data) {
        if (this.datas == null) {
            this.datas = new ArrayList<>();
        }
        this.datas.add(data);
        notifyDataSetChanged();
    }

    public T getItem(int position) {
        if (datas != null) {
            return datas.get(position);
        } else {
            return null;
        }
    }


    @Override
    public int getItemCount() {
        return datas != null ? datas.size() : 0;
    }

    public boolean isEmpty() {
        if (datas == null || datas.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public final String getString(@StringRes int resId) {
        return context.getResources().getString(resId);
    }

    public List<T> getDatas() {
        return datas == null ? new ArrayList<T>() : datas;
    }

    public Context getContext() {
        return context;
    }

    public OnAppListener.OnAdapterListener getListener() {
        return listener;
    }

}
