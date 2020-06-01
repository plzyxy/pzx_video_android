package com.thinksoft.banana.entity.bean.video;

import com.thinksoft.banana.entity.BaseBean;

import java.io.Serializable;

/**
 * @author txf
 * @create 2019/3/27 0027
 * @
 */
public class VideoGroupDataBean extends BaseBean implements Serializable {

    /**
     * id : 28
     * is_free : 0
     * title : 测试水水水水
     * comment_num : 0
     * link : http://1258668526.vod2.myqcloud.com/22ad2b93vodcq1258668526/356de9115285890786156117148/e6Q2EITmLhQA.mp4
     * image : http://1258668526.vod2.myqcloud.com/22ad2b93vodcq1258668526/356de9115285890786156117148/5285890786156117149.jpg
     * his : 00:00:01
     * free_time : 0
     * unit : s
     * is_used : 0
     * diamond : 0.00
     * used_count : 0
     * is_usecomm : 0
     * banners : []
     * advert_id : 3
     * video_type : 1
     * screen_type : 2
     * buyStatus : 0
     * collcetion : 0
     * love
     * islove
     */
    private int love;
    private int islove;
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
    private int is_usecomm;
    private String banners;
    private int advert_id;
    private int video_type;
    private int screen_type;
    private int buyStatus;
    private int collcetion;

    public void setIslove(int islove) {
        this.islove = islove;
    }

    public int getIslove() {
        return islove;
    }

    public void setLove(int love) {
        this.love = love;
    }

    public int getLove() {
        return love;
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

    public void setDiamond(String diamond) {
        this.diamond = diamond;
    }

    public int getUsed_count() {
        return used_count;
    }

    public void setUsed_count(int used_count) {
        this.used_count = used_count;
    }

    public int getIs_usecomm() {
        return is_usecomm;
    }

    public void setIs_usecomm(int is_usecomm) {
        this.is_usecomm = is_usecomm;
    }

    public String getBanners() {
        return banners;
    }

    public void setBanners(String banners) {
        this.banners = banners;
    }

    public int getAdvert_id() {
        return advert_id;
    }

    public void setAdvert_id(int advert_id) {
        this.advert_id = advert_id;
    }

    public int getVideo_type() {
        return video_type;
    }

    public void setVideo_type(int video_type) {
        this.video_type = video_type;
    }

    public int getScreen_type() {
        return screen_type;
    }

    public void setScreen_type(int screen_type) {
        this.screen_type = screen_type;
    }

    public int getBuyStatus() {
        return buyStatus;
    }

    public void setBuyStatus(int buyStatus) {
        this.buyStatus = buyStatus;
    }

    public int getCollcetion() {
        return collcetion;
    }

    public void setCollcetion(int collcetion) {
        this.collcetion = collcetion;
    }
}
