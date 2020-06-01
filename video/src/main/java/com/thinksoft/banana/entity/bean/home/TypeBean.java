package com.thinksoft.banana.entity.bean.home;

import com.thinksoft.banana.entity.BaseBean;

import java.util.ArrayList;

/**
 * @author txf
 * @create 2019/2/28 0028
 * @
 */
public class TypeBean extends BaseBean {
    private ArrayList<CatesBean> cates;

    public ArrayList<CatesBean> getCates() {
        return cates;
    }
    public void setCates(ArrayList<CatesBean> cates) {
        this.cates = cates;
    }
}
