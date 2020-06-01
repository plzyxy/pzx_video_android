package com.thinksoft.banana.entity.bean.home;

import com.thinksoft.banana.entity.BaseBean;

import java.io.Serializable;

/**
 * @author txf
 * @create 2019/2/28 0028
 * @
 */
public class CatesBean extends BaseBean implements Serializable {
    public CatesBean(String name) {
        this.name = name;
    }

    public CatesBean(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public CatesBean() {
    }

    /**
     * id : 14
     * name : 喜剧
     * image : http://47.106.154.254/uploads/picture/20190222/198c75a5de7942a696e478659141466e.jpg
     */

    private int id;
    private String name;
    private String image;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
