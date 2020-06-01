package com.thinksoft.banana.entity.bean.home;

import com.thinksoft.banana.entity.BaseBean;

import java.io.Serializable;
import java.util.List;

/**
 * @author txf
 * @create 2019/3/20 0020
 * @
 */
public class CatevideosBean extends BaseBean implements Serializable {

    /**
     * id : 11
     * name : 少女
     * videos : []
     */

    private int id;
    private String name;
    private List<VideosBean> videos;

    public CatevideosBean(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<VideosBean> getVideos() {
        return videos;
    }

    public void setVideos(List<VideosBean> videos) {
        this.videos = videos;
    }
}
