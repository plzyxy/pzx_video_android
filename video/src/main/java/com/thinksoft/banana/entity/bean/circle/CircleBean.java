package com.thinksoft.banana.entity.bean.circle;

import com.thinksoft.banana.entity.BaseBean;

import java.io.Serializable;
import java.util.List;

/**
 * @author txf
 * @create 2019/3/22 0022
 * @
 */
public class CircleBean extends BaseBean implements Serializable {

    /**
     * id : 3
     * video_id : 0
     * type : 1
     * images : [{"max":"http://47.106.154.254//uploads/picture/20190321/16fba88c670b5b3d189c2952df1a72a9.jpg","min":"http://47.106.154.254//uploads/picture/20190321/min_16fba88c670b5b3d189c2952df1a72a9.jpg"},{"max":"http://47.106.154.254//uploads/picture/20190321/56d651fb3b7612e767b84321bc1d72a6.jpg","min":"http://47.106.154.254//uploads/picture/20190321/min_56d651fb3b7612e767b84321bc1d72a6.jpg"}]
     * content : sssdfsdfa
     * time : 2019-03-21 16:22:13
     * love : 0
     * nickname : 小米
     * image : http://47.106.154.254//uploads/picture/20190222/198c75a5de7942a696e478659141466e.jpg
     * islove : 0
     * video : {}
     * sex :1  , 1男，2女
     * comment_num : 0
     */
    private int comment_num;
    private int sex;
    private int id;
    private String video_id;
    private int type;
    private String content;
    private String time;
    private int love;
    private String nickname;
    private String image;
    private int islove;
    private List<HttpImgBean> images;
    private VideoBean video;

    public void setComment_num(int comment_num) {
        this.comment_num = comment_num;
    }

    public int getComment_num() {
        return comment_num;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getSex() {
        return sex;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getLove() {
        return love;
    }

    public void setLove(int love) {
        this.love = love;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public VideoBean getVideo() {
        return video;
    }

    public void setVideo(VideoBean video) {
        this.video = video;
    }

}
