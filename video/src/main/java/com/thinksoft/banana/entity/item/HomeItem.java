package com.thinksoft.banana.entity.item;

import com.txf.ui_mvplibrary.bean.BaseItem;

/**
 * @author txf
 * @create 2019/2/16
 */
public class HomeItem<Data> extends BaseItem<Data> {

    public HomeItem(Data o, int type, int action) {
        super(o, type, action);
    }
    public HomeItem(Data o, int type) {
        super(o, type);
    }
}
