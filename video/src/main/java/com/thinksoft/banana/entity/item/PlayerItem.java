package com.thinksoft.banana.entity.item;

import com.txf.ui_mvplibrary.bean.BaseItem;

/**
 * @author txf
 * @create 2019/2/21 0021
 * @
 */
public class PlayerItem<Data> extends BaseItem<Data> {

    public PlayerItem(Data data, int type, int action) {
        super(data, type, action);
    }

    public PlayerItem(Data data, int type) {
        super(data, type);
    }
}
