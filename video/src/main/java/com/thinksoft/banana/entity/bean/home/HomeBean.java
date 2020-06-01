package com.thinksoft.banana.entity.bean.home;

import com.thinksoft.banana.entity.BaseBean;

import java.util.ArrayList;

/**
 * @author txf
 * @create 2019/2/28 0028
 * @
 */
public class HomeBean extends BaseBean {

    private ArrayList<BannersBean> banners;
    private ArrayList<VideosBean> videos;
    private ArrayList<CatesBean> cates;
    private ArrayList<CatevideosBean> catevideos;

    public void setCatevideos(ArrayList<CatevideosBean> catevideos) {
        this.catevideos = catevideos;
    }

    public ArrayList<CatevideosBean> getCatevideos() {
        return catevideos;
    }

    public ArrayList<BannersBean> getBanners() {
        return banners;
    }

    public void setBanners(ArrayList<BannersBean> banners) {
        this.banners = banners;
    }

    public ArrayList<VideosBean> getVideos() {
        return videos;
    }

    public void setVideos(ArrayList<VideosBean> videos) {
        this.videos = videos;
    }

    public ArrayList<CatesBean> getCates() {
        return cates;
    }

    public void setCates(ArrayList<CatesBean> cates) {
        this.cates = cates;
    }
}
