package com.thinksoft.banana.entity.bean.play;

import com.thinksoft.banana.entity.BaseBean;

import java.io.Serializable;

/**
 * @author txf
 * @create 2019/3/4 0004
 * @
 */
public class CommentDataBean extends BaseBean implements Serializable {
    /**
     * id : 3
     * love : 0
     * content : 233
     * nickname : 您还没有设置昵称
     * image : http://47.106.154.254/uploads/picture/20190222/198c75a5de7942a696e478659141466e.jpg
     * is_love:0
     */

    private int id;
    private int love;
    private String content;
    private String nickname;
    private String image;
    private int is_love;


    public void setIs_love(int is_love) {
        this.is_love = is_love;
    }

    public int getIs_love() {
        return is_love;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLove() {
        return love;
    }

    public void setLove(int love) {
        this.love = love;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
}
