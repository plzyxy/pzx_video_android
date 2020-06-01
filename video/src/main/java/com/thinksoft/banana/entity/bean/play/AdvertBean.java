package com.thinksoft.banana.entity.bean.play;

import com.thinksoft.banana.entity.BaseBean;

/**
 * @author txf
 * @create 2019/3/11 0011
 * @
 */
public class AdvertBean extends BaseBean {

    /**
     * type : 1
     * image : http://local.clickplay.com//uploads/picture/20190309/da271f20e368b8d51f8950fd42e266fe.jpg
     * video :
     * link : http://www.baidu.com
     */

    private int type;
    private String image;
    private String video;
    private String link;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
