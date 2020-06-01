package com.thinksoft.banana.entity.bean.music;

import com.thinksoft.banana.entity.BaseBean;

/**
 * @author txf
 * @create 2019/3/22 0022
 * @
 */
public class MusicDataBean extends BaseBean {

    /**
     * id : 3
     * description : null
     * title : null
     * image : http://59.188.25.220
     * filepath : null
     * time : 2019-03-21 16:22:13
     * love : 0
     * comment_num : 0
     * islove : 0
     */

    private int id;
    private String description;
    private String title;
    private String image;
    private String filepath;
    private String time;
    private int love;
    private int comment_num;
    private int islove;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
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
}
