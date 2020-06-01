package com.thinksoft.banana.entity.item;

import com.txf.ui_mvplibrary.bean.BaseItem;

/**
 * @author txf
 * @create 2019/3/11 0011
 * @
 */
public class TypeHomeListItem<Data> extends BaseItem {
    public TypeHomeListItem(Data o, int type, int action) {
        super(o, type, action);
    }

    public TypeHomeListItem(Data o, int type) {
        super(o, type);
    }
}
