package com.thinksoft.banana.entity.bean.circle;

import com.thinksoft.banana.entity.BaseBean;

import java.io.Serializable;

/**
 * @author txf
 * @create 2019/3/22 0022
 * @
 */
public class HttpImgBean extends BaseBean  implements Serializable {
    String max;
    String min;

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }
}
