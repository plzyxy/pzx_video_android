package com.thinksoft.banana.entity.item;

import com.txf.ui_mvplibrary.bean.BaseItem;

/**
 * @author txf
 * @create 2019/2/19 0019
 * @
 */
public class PersonDataItem<Data> extends BaseItem<Data> {
    public PersonDataItem(Data o, int type, int action) {
        super(o, type, action);
    }
    public PersonDataItem(Data o, int type) {
        super(o, type);
    }
}
