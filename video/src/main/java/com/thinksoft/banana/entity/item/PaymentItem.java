package com.thinksoft.banana.entity.item;

import com.txf.ui_mvplibrary.bean.BaseItem;

/**
 * @author txf
 * @create 2019/2/20 0020
 * @
 */
public class PaymentItem<Data> extends BaseItem<Data> {
    private boolean isCheck;

    public PaymentItem(Data data, int type, int action) {
        super(data, type, action);
    }

    public PaymentItem(boolean isCheck, Data data, int type) {
        super(data, type);
        this.isCheck = isCheck;
    }

    public PaymentItem(Data data, int type) {
        super(data, type);
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public boolean isCheck() {
        return isCheck;
    }
}
