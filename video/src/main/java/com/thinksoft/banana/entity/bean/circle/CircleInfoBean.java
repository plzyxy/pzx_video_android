package com.thinksoft.banana.entity.bean.circle;

import com.thinksoft.banana.entity.BaseBean;

/**
 * @author txf
 * @create 2019/3/23 0023
 * @
 */
public class CircleInfoBean extends BaseBean {
    CircleBean circleInfo;

    public void setCircleInfo(CircleBean circleInfo) {
        this.circleInfo = circleInfo;
    }

    public CircleBean getCircleInfo() {
        return circleInfo;
    }
}
