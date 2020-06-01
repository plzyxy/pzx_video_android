package com.thinksoft.banana.entity.bean.type;

import com.thinksoft.banana.entity.BaseBean;

import java.io.Serializable;

/**
 * @author txf
 * @create 2019/3/13 0013
 * @
 */
public class PerformerInfoDataBean extends BaseBean implements Serializable {
    /**
     * id : 12
     * name : 古天乐
     * letter : G
     * image : http://47.106.154.254//uploads/picture/20190312/22b44b15c4ee3a99231206e91e6acb12.jpg
     * description : 刘德华刘德华刘德华刘德华刘德华刘德华刘德华刘德华刘德华刘德华
     * is_collection : 0
     */

    private int id;
    private String name;
    private String letter;
    private String image;
    private String description;
    private int is_collection;

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

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIs_collection() {
        return is_collection;
    }

    public void setIs_collection(int is_collection) {
        this.is_collection = is_collection;
    }
}
