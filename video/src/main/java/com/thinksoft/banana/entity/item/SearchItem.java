package com.thinksoft.banana.entity.item;

import com.txf.ui_mvplibrary.bean.BaseItem;

/**
 * @author txf
 * @create 2019/2/18 0018
 * @
 */
public class SearchItem<Data> extends BaseItem<Data> {
    public SearchItem(Data o, int type, int action) {
        super(o, type, action);
    }
    public SearchItem(Data o, int type) {
        super(o, type);
    }
}
