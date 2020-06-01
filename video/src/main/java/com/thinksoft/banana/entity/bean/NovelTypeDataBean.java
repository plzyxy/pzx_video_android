package com.thinksoft.banana.entity.bean;

import com.thinksoft.banana.entity.BaseBean;

import java.io.Serializable;
import java.util.List;

/**
 * @author txf
 * @create 2019/4/9 0009
 * @
 */
public class NovelTypeDataBean extends BaseBean implements Serializable {


    /**
     * id : 13
     * name : 科幻
     * image : http://58.64.180.125//uploads/picture/20190222/198c75a5de7942a696e478659141466e.jpg
     * parent_id : 0
     */

    private int id;
    private String name;
    private String image;
    private int parent_id;
    private List<NovelTypeDataBean> son;

    public void setSon(List<NovelTypeDataBean> son) {
        this.son = son;
    }

    public List<NovelTypeDataBean> getSon() {
        return son;
    }

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

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }
}
