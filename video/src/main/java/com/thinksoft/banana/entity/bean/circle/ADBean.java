package com.thinksoft.banana.entity.bean.circle;

import com.thinksoft.banana.entity.BaseBean;
import com.thinksoft.banana.entity.bean.home.BannersBean;

import java.util.ArrayList;

/**
 * @author txf
 * @create 2019/3/11 0011
 * @
 */
public class ADBean extends BaseBean {
    ArrayList<BannersBean> banner;

    public ArrayList<BannersBean> getBanners() {
        return banner;
    }

    public void setBanners(ArrayList<BannersBean> banners) {
        this.banner = banners;
    }
}
