package com.thinksoft.banana.entity.bean;

import com.thinksoft.banana.entity.BaseBean;

/**
 * @author txf
 * @create 2019/3/1 0001
 * @
 */
public class RechargeListBean extends BaseBean {
    /**
     * id : 4
     * rmb : 6.00
     * diamond : 60.00
     */

    private int id;
    private String rmb;
    private String diamond;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRmb() {
        return rmb;
    }

    public void setRmb(String rmb) {
        this.rmb = rmb;
    }

    public String getDiamond() {
        return diamond;
    }

    public void setDiamond(String diamond) {
        this.diamond = diamond;
    }
}
