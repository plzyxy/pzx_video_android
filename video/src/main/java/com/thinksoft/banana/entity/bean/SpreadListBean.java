package com.thinksoft.banana.entity.bean;

import com.thinksoft.banana.entity.BaseBean;

/**
 * @author txf
 * @create 2019/4/8 0008
 * @
 */
public class SpreadListBean extends BaseBean {
    /**
     * name : 新手
     * description : 开始推广获取免费次数吧
     * level : L0
     * image : http://59.188.25.220/uploads/picture/20190408/6bec3b9225c9659d2742a70b9d083135.png
     */
    private String name;
    private String description;
    private String level;
    private String image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
