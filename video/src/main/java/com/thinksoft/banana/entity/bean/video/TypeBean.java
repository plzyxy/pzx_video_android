package com.thinksoft.banana.entity.bean.video;

import com.thinksoft.banana.entity.BaseBean;
import com.thinksoft.banana.entity.bean.home.CatesBean;

import java.util.ArrayList;

/**
 * @author txf
 * @create 2019/3/11 0011
 * @
 */
public class TypeBean extends BaseBean {
    ArrayList<CatesBean> cates;

    public ArrayList<CatesBean> getCates() {
        return cates;
    }
    public void setCates(ArrayList<CatesBean> cates) {
        this.cates = cates;
    }
}
