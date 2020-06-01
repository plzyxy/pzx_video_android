package com.thinksoft.banana.entity.bean.type;

import com.thinksoft.banana.entity.BaseBean;

import java.util.List;

/**
 * @author txf
 * @create 2019/3/13 0013
 * @
 */
public class PerformerBean extends BaseBean {
    List<PerformerDataBean> performerList;

    public List<PerformerDataBean> getPerformerList() {
        return performerList;
    }

    public void setPerformerList(List<PerformerDataBean> performerList) {
        this.performerList = performerList;
    }
}
