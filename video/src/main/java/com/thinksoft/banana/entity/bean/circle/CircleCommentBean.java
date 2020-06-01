package com.thinksoft.banana.entity.bean.circle;

import com.thinksoft.banana.entity.BaseBean;

import java.io.Serializable;

/**
 * @author txf
 * @create 2019/3/23 0023
 * @
 */
public class CircleCommentBean extends BaseBean implements Serializable {

    /**
     * id : 3
     * user_id : 3
     * content : 233
     * date : 02月27日
     * nickname : 您还没有设置昵称
     * image : http://local.clickplay.com//uploads/picture/20190222/198c75a5de7942a696e478659141466e.jpg
     * islove : 0
     */

    private int id;
    private int user_id;
    private String content;
    private String date;
    private String nickname;
    private String image;
    private int islove;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
}
