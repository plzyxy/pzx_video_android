package com.thinksoft.banana.entity.bean;

import com.thinksoft.banana.entity.BaseBean;

import java.io.Serializable;

/**
 * @author txf
 * @create 2019/3/1 0001
 * @
 */
public class UserInfoBean extends BaseBean implements Serializable {


    /**
     * nickname : 您还没有设置昵称
     * image : http://59.188.25.220uploads/picture/20190314/a316e5c8e962c7c01f21180a23b6baa1.jpg
     * all_free_count : 0
     * free_count : 0
     * level_name : 新手
     * level_image : http://59.188.25.220/uploads/picture/20190404/233f6fc940c955d98529bc3895c7fda5.png
     * diamond : 10000.00
     * sex : 1
     * mobile : 18682739642
     * kefu_qq : Q
     * share_title : 香蕉视频，让生活硬起来！
     * share_desc : 你们的小伙伴都在看，你懂的！
     * share_logo : http://59.188.25.220/uploads/picture/20190225/3f427c5240f2eabc4d3df4250d6d86ad.jpg
     * share_link : http://
     * isVip	int	是否是vip，1是，0不是
     * day	    int/string	会员剩余多少天，如果不是会员则显示您不是会员
     * time     string	会员到期时间，如果不是会员则显示您不是会员
     */

    private String nickname;
    private String image;
    private int all_free_count;
    private int free_count;
    private String level_name;
    private String level_image;
    private String diamond;
    private int sex;
    private String mobile;
    private String kefu_qq;
    private String share_title;
    private String share_desc;
    private String share_logo;
    private String share_link;
    private int isVip;
    private String day;
    private String time;

    public int getIsVip() {
        return isVip;
    }

    public void setIsVip(int isVip) {
        this.isVip = isVip;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public int getAll_free_count() {
        return all_free_count;
    }

    public void setAll_free_count(int all_free_count) {
        this.all_free_count = all_free_count;
    }

    public int getFree_count() {
        return free_count;
    }

    public void setFree_count(int free_count) {
        this.free_count = free_count;
    }

    public String getLevel_name() {
        return level_name;
    }

    public void setLevel_name(String level_name) {
        this.level_name = level_name;
    }

    public String getLevel_image() {
        return level_image;
    }

    public void setLevel_image(String level_image) {
        this.level_image = level_image;
    }

    public String getDiamond() {
        return diamond;
    }

    public void setDiamond(String diamond) {
        this.diamond = diamond;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getKefu_qq() {
        return kefu_qq;
    }

    public void setKefu_qq(String kefu_qq) {
        this.kefu_qq = kefu_qq;
    }

    public String getShare_title() {
        return share_title;
    }

    public void setShare_title(String share_title) {
        this.share_title = share_title;
    }

    public String getShare_desc() {
        return share_desc;
    }

    public void setShare_desc(String share_desc) {
        this.share_desc = share_desc;
    }

    public String getShare_logo() {
        return share_logo;
    }

    public void setShare_logo(String share_logo) {
        this.share_logo = share_logo;
    }

    public String getShare_link() {
        return share_link;
    }

    public void setShare_link(String share_link) {
        this.share_link = share_link;
    }
}
