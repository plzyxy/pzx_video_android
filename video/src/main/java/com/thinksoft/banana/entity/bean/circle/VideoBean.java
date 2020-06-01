package com.thinksoft.banana.entity.bean.circle;

import com.thinksoft.banana.entity.BaseBean;
import com.thinksoft.banana.entity.bean.music.MusicInfoDataBean;

import java.io.Serializable;

/**
 * @author txf
 * @create 2019/3/22 0022
 * @
 */
public class VideoBean extends BaseBean implements Serializable {

    /**
     * link : http://1258668526.vod2.myqcloud.com/22ad2b93vodcq1258668526/5733dc5f5285890785992337754/f0.mp4
     * image : http://1258668526.vod2.myqcloud.com/22ad2b93vodcq1258668526/5733dc5f5285890785992337754/5285890785992337755.jpg
     */
    private String link;
    private String image;

    //非服务器返回字段
    private int type;//0 原数据 1 填充数据
    MusicInfoDataBean bean;

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setBean(MusicInfoDataBean bean) {
        this.bean = bean;
    }

    public MusicInfoDataBean getBean() {
        return bean;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
