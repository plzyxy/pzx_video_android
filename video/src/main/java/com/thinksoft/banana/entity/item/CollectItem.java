package com.thinksoft.banana.entity.item;

import com.txf.ui_mvplibrary.bean.BaseItem;

/**
 * @author txf
 * @create 2019/3/12 0012
 * @
 */
public class CollectItem<Data> extends BaseItem<Data> {
    public CollectItem(Data data, int type, int action) {
        super(data, type, action);
    }

    public CollectItem(Data data, int type) {
        super(data, type);
    }
}
