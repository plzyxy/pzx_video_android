package com.thinksoft.banana.entity.bean;

import com.thinksoft.banana.entity.BaseBean;

import java.util.List;

/**
 * @author txf
 * @create 2019/3/4 0004
 * @
 */
public class IncomeBean extends BaseBean {

    /**
     * diamond : 2000.00
     * incomeList : [{"diamond":"1000.00","time":"2019-02-26 14:29:19","name":"充值"},{"diamond":"1000.00","time":"2019-02-26 14:28:56","name":"充值"},{"diamond":"1000.00","time":"2019-02-26 14:26:57","name":"充值"}]
     */

    private String diamond;
    private List<IncomeDataBean> incomeList;

    public String getDiamond() {
        return diamond;
    }

    public void setDiamond(String diamond) {
        this.diamond = diamond;
    }

    public List<IncomeDataBean> getIncomeList() {
        return incomeList;
    }

    public void setIncomeList(List<IncomeDataBean> incomeList) {
        this.incomeList = incomeList;
    }

}
