package com.thinksoft.banana.entity.bean.type;

import com.thinksoft.banana.entity.BaseBean;
import com.thinksoft.banana.entity.bean.home.VideosBean;

import java.util.List;

/**
 * @author txf
 * @create 2019/3/13 0013
 * @
 */
public class PerformerVideoBean extends BaseBean {
    List<VideosBean> videoList;

    public void setVideoList(List<VideosBean> videoList) {
        this.videoList = videoList;
    }

    public List<VideosBean> getVideoList() {
        return videoList;
    }
}
