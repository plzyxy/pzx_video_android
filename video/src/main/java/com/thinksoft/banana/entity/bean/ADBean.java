package com.thinksoft.banana.entity.bean;

import com.thinksoft.banana.entity.BaseBean;
import com.thinksoft.banana.entity.bean.home.BannersBean;

import java.util.ArrayList;

/**
 * @author txf
 * @create 2019/3/11 0011
 * @
 */
public class ADBean extends BaseBean {
    ArrayList<BannersBean> banners;

    public ArrayList<BannersBean> getBanners() {
        return banners;
    }

    public void setBanners(ArrayList<BannersBean> banners) {
        this.banners = banners;
    }
}
