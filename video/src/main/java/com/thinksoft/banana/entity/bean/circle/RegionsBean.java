package com.thinksoft.banana.entity.bean.circle;

import com.thinksoft.banana.entity.BaseBean;

import java.util.List;

/**
 * @author txf
 * @create 2019/3/28 0028
 * @
 */
public class RegionsBean extends BaseBean {
    List<RegionsDataBean> regions;

    public void setRegions(List<RegionsDataBean> regions) {
        this.regions = regions;
    }

    public List<RegionsDataBean> getRegions() {
        return regions;
    }
}
