package com.thinksoft.banana.entity.bean.circle;

import com.thinksoft.banana.entity.BaseBean;

import java.util.List;

/**
 * @author txf
 * @create 2019/3/22 0022
 * @
 */
public class CircleListBean extends BaseBean {
    List<CircleBean> circleList;

    public void setCircleList(List<CircleBean> circleList) {
        this.circleList = circleList;
    }

    public List<CircleBean> getCircleList() {
        return circleList;
    }
}
