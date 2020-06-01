package com.thinksoft.banana.entity.item;

import com.txf.ui_mvplibrary.bean.BaseItem;

/**
 * @author txf
 * @create 2019/2/20 0020
 * @
 */
public class HistoryItem<Data> extends BaseItem<Data> {
    public HistoryItem(Data o, int type, int action) {
        super(o, type, action);
    }

    public HistoryItem(Data o, int type) {
        super(o, type);
    }
}
