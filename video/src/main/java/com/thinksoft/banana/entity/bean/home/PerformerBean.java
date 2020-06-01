package com.thinksoft.banana.entity.bean.home;

import com.thinksoft.banana.entity.BaseBean;
import com.thinksoft.banana.entity.bean.type.PerformerDataBean;

import java.util.List;

/**
 * @author txf
 * @create 2019/3/13 0013
 * @
 */
public class PerformerBean extends BaseBean {
    List<PerformerDataBean> performers;

    public List<PerformerDataBean> getPerformers() {
        return performers;
    }

    public void setPerformers(List<PerformerDataBean> performers) {
        this.performers = performers;
    }
}
