package com.thinksoft.banana.entity.bean;

import com.thinksoft.banana.entity.BaseBean;

import java.util.List;

/**
 * @author txf
 * @create 2019/4/8 0008
 * @
 */
public class SpreadInfoBean extends BaseBean {

    /**
     * nickname : 您还没有设置昵称
     * image : http://59.188.25.220uploads/picture/20190314/a316e5c8e962c7c01f21180a23b6baa1.jpg
     * all_free_count : 0
     * free_count : 0
     * level : L0
     * level_image : http://59.188.25.220/uploads/picture/20190408/6bec3b9225c9659d2742a70b9d083135.png
     * next_level : L1
     * next_level_image : http://59.188.25.220/uploads/picture/20190408/91fb04a13cd06c7463e82c5b284dd59f.png
     * progress : 0
     * spread_note :
     * spreadList : []
     */

    private String nickname;
    private String image;
    private int all_free_count;
    private int free_count;
    private String level;
    private String level_image;
    private String next_level;
    private String next_level_image;
    private int progress;
    private String spread_note;
    private List<SpreadListBean> spreadList;

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

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLevel_image() {
        return level_image;
    }

    public void setLevel_image(String level_image) {
        this.level_image = level_image;
    }

    public String getNext_level() {
        return next_level;
    }

    public void setNext_level(String next_level) {
        this.next_level = next_level;
    }

    public String getNext_level_image() {
        return next_level_image;
    }

    public void setNext_level_image(String next_level_image) {
        this.next_level_image = next_level_image;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getSpread_note() {
        return spread_note;
    }

    public void setSpread_note(String spread_note) {
        this.spread_note = spread_note;
    }

    public List<SpreadListBean> getSpreadList() {
        return spreadList;
    }

    public void setSpreadList(List<SpreadListBean> spreadList) {
        this.spreadList = spreadList;
    }
}
