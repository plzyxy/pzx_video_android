package com.thinksoft.banana.entity.bean;

import com.thinksoft.banana.entity.BaseBean;

/**
 * @author txf
 * @create 2019/3/4 0004
 * @
 */
public class IncomeDataBean extends BaseBean {
    /**
     * diamond : 1000.00
     * time : 2019-02-26 14:29:19
     * name : 充值
     */

    private String diamond;
    private String time;
    private String name;

    public String getDiamond() {
        return diamond;
    }

    public void setDiamond(String diamond) {
        this.diamond = diamond;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
