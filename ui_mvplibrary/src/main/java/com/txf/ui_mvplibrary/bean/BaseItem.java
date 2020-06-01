package com.txf.ui_mvplibrary.bean;

import java.io.Serializable;

/**
 * @author txf
 * @create 2019/1/29 0029
 * 适用于RecyclerView 多布局数据统一 包装对象
 * {@link #data} 原始数据
 * {@link #itemType} itemType  {@link com.txf.ui_mvplibrary.ui.adapter.BaseCompleteRecyclerAdapter#getItemViewType(int)}
 */

public class BaseItem<Data> implements Serializable {
    private Data data;
    private int itemType;
    private int action;

    public BaseItem(Data data, int type, int action) {
        this.data = data;
        this.itemType = type;
        this.action = action;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public BaseItem(Data data, int type) {
        this(data, type, -1);
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public int getItemType() {
        return itemType;
    }
}
