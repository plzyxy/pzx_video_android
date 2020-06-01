package com.thinksoft.banana.entity.item;

import com.txf.ui_mvplibrary.bean.BaseItem;

/**
 * @author txf
 * @create 2019/4/2 0002
 * @
 */
public class SpreadItem<Data> extends BaseItem<Data> {
    public SpreadItem(Data data, int type, int action) {
        super(data, type, action);
    }

    public SpreadItem(Data data, int type) {
        super(data, type);
    }
}
