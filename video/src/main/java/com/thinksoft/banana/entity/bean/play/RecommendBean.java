package com.thinksoft.banana.entity.bean.play;

import com.thinksoft.banana.entity.BaseBean;
import com.thinksoft.banana.entity.bean.home.VideosBean;

import java.util.List;

/**
 * @author txf
 * @create 2019/3/11 0011
 * @
 */
public class RecommendBean extends BaseBean {
    List<VideosBean> videos;
    public List<VideosBean> getVideos() {
        return videos;
    }

    public void setVideos(List<VideosBean> videos) {
        this.videos = videos;
    }
}
