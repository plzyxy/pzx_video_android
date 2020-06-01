package com.thinksoft.banana.entity.bean;

import com.thinksoft.banana.entity.BaseBean;
import com.thinksoft.banana.entity.bean.home.VideosBean;

import java.util.List;

/**
 * @author txf
 * @create 2019/3/4 0004
 * @
 */
public class CollectBean extends BaseBean {

    private List<VideosBean> videoList;

    public List<VideosBean> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<VideosBean> videoList) {
        this.videoList = videoList;
    }
}
