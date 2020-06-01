package com.thinksoft.banana.entity.event_bean;

import com.thinksoft.banana.entity.BaseBean;

/**
 * @author txf
 * @create 2019/3/1 0001
 * @
 */
public class BaseEventBean extends BaseBean {
    private int type;

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
