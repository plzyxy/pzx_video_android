package com.thinksoft.banana.entity.bean.img;

import com.thinksoft.banana.entity.BaseBean;
import com.thinksoft.banana.entity.bean.circle.HttpImgBean;

import java.util.List;

/**
 * @author txf
 * @create 2019/4/4 0004
 * @
 */
public class ImagInfoDataBean extends BaseBean {

    /**
     * id : 3
     * images : [{"max":"http://59.188.25.220/uploads/picture/20190321/16fba88c670b5b3d189c2952df1a72a9.jpg","min":"http://59.188.25.220/uploads/picture/20190321/min_16fba88c670b5b3d189c2952df1a72a9.jpg"},{"max":"http://59.188.25.220/uploads/picture/20190321/56d651fb3b7612e767b84321bc1d72a6.jpg","min":"http://59.188.25.220/uploads/picture/20190321/min_56d651fb3b7612e767b84321bc1d72a6.jpg"}]
     * description : null
     * title : null
     * time : 2019-03-21 16:22:13
     * sort : null
     * love : 0
     * comment_num : 0
     * islove : 0
     */

    private int id;
    private String description;
    private String title;
    private String time;
    private String sort;
    private int love;
    private int comment_num;
    private int islove;
    private List<HttpImgBean> images;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public int getLove() {
        return love;
    }

    public void setLove(int love) {
        this.love = love;
    }

    public int getComment_num() {
        return comment_num;
    }

    public void setComment_num(int comment_num) {
        this.comment_num = comment_num;
    }

    public int getIslove() {
        return islove;
    }

    public void setIslove(int islove) {
        this.islove = islove;
    }

    public List<HttpImgBean> getImages() {
        return images;
    }

    public void setImages(List<HttpImgBean> images) {
        this.images = images;
    }

}
