package com.thinksoft.banana.entity.item;

import com.txf.ui_mvplibrary.bean.BaseItem;

/**
 * @author txf
 * @create 2019/3/12 0012
 * @
 */
public class TypeFilmItem<Data> extends BaseItem<Data> {
    public TypeFilmItem(Data data, int type, int action) {
        super(data, type, action);
    }

    public TypeFilmItem(Data data, int type) {
        super(data, type);
    }
}
