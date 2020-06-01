package com.thinksoft.banana.entity.bean.home;

import com.thinksoft.banana.entity.BaseBean;

import java.io.Serializable;

/**
 * @author txf
 * @create 2019/2/28 0028
 * @此文件被多个文件引用, 修改字段请先检查
 */
public class VideosBean extends BaseBean implements Serializable {
    /**
     * id : 2
     * is_free : 1
     * title : 测试视频
     * link : http://1258668526.vod2.myqcloud.com/22ad2b93vodcq1258668526/5733dc5f5285890785992337754/f0.mp4
     * image : http://1258668526.vod2.myqcloud.com/22ad2b93vodcq1258668526/5733dc5f5285890785992337754/5285890785992337755.jpg
     * his : 00:00:17
     * screen_type:1
     */

    private int id;
    private int is_free;
    private String title;
    private String link;
    private String image;
    private String his;
    private int screen_type;

    public void setScreen_type(int screen_type) {
        this.screen_type = screen_type;
    }

    public int getScreen_type() {
        return screen_type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIs_free() {
        return is_free;
    }

    public void setIs_free(int is_free) {
        this.is_free = is_free;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getHis() {
        return his;
    }

    public void setHis(String his) {
        this.his = his;
    }
}
