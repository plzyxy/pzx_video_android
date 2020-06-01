package com.thinksoft.banana.entity.item;

import com.txf.ui_mvplibrary.bean.BaseItem;

/**
 * @author txf
 * @create 2019/2/18 0018
 * @
 */
public class TypeItem<Data> extends BaseItem<Data> {
    public TypeItem(Data o, int type, int action) {
        super(o, type, action);
    }

    public TypeItem(Data o, int type) {
        super(o, type);
    }
}
