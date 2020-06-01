package com.thinksoft.banana.entity.bean.type;

import com.thinksoft.banana.entity.BaseBean;

import java.io.Serializable;

/**
 * @author txf
 * @create 2019/3/13 0013
 * @
 */
public class PerformerDataBean extends BaseBean implements Serializable {

    /**
     * id : 12
     * image : http://47.106.154.254//uploads/picture/20190313/cde95a2d629402b23b7d1c403926c3f9.jpg
     * name : 萝莉
     * is_collection : 1
     */

    private int id;
    private String image;
    private String name;
    private int is_collection;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIs_collection() {
        return is_collection;
    }

    public void setIs_collection(int is_collection) {
        this.is_collection = is_collection;
    }
}
