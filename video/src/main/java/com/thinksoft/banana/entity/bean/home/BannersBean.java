package com.thinksoft.banana.entity.bean.home;

import com.thinksoft.banana.entity.BaseBean;

import java.io.Serializable;

/**
 * @author txf
 * @create 2019/2/28 0028
 * @
 */
public class BannersBean extends BaseBean implements Serializable {
    /**
     * id : 11
     * image : http://47.106.154.254/uploads/picture/20190225/3f427c5240f2eabc4d3df4250d6d86ad.jpg
     * link :
     */

    private int id;
    private String image;
    private String link;
    public BannersBean() {
    }
    public BannersBean(int id, String image, String link) {
        this.id = id;
        this.image = image;
        this.link = link;
    }

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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
