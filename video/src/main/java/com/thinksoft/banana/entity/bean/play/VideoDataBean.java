package com.thinksoft.banana.entity.bean.play;

import com.thinksoft.banana.entity.BaseBean;
import com.txf.other_toolslibrary.tools.JsonTools;
import com.txf.other_toolslibrary.tools.StringTools;

import java.io.Serializable;

/**
 * @author txf
 * @create 2019/3/4 0004
 * @
 */
public class VideoDataBean extends BaseBean implements Serializable {
    /**
     * id : 2
     * is_free : 1
     * title : 测试视频
     * comment_num : 4
     * link : http://1258668526.vod2.myqcloud.com/22ad2b93vodcq1258668526/5733dc5f5285890785992337754/f0.mp4
     * image : http://1258668526.vod2.myqcloud.com/22ad2b93vodcq1258668526/5733dc5f5285890785992337754/5285890785992337755.jpg
     * his : 00:00:17
     * free_time : 6
     * unit : s
     * is_used : 1
     * diamond : 100.00
     * used_count : 1
     * video_type:0
     */

    private int id;
    private int is_free;
    private String title;
    private int comment_num;
    private String link;
    private String image;
    private String his;
    private int free_time;
    private String unit;
    private int is_used;
    private String diamond;
    private int used_count;
    private int video_type;

    public void setVideo_type(int video_type) {
        this.video_type = video_type;
    }

    public int getVideo_type() {
        return video_type;
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

    public int getComment_num() {
        return comment_num;
    }

    public void setComment_num(int comment_num) {
        this.comment_num = comment_num;
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

    public int getFree_time() {
        return free_time;
    }

    public void setFree_time(int free_time) {
        this.free_time = free_time;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getIs_used() {
        return is_used;
    }

    public void setIs_used(int is_used) {
        this.is_used = is_used;
    }

    public String getDiamond() {
        return diamond;
    }

    public double getDiamondDouble() {
        if (StringTools.isNull(diamond))
            return 0;
        return Double.valueOf(diamond);
    }

    public void setDiamond(String diamond) {
        this.diamond = diamond;
    }

    public int getUsed_count() {
        return used_count;
    }

    public void setUsed_count(int used_count) {
        this.used_count = used_count;
    }
}
