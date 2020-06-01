package com.thinksoft.banana.entity.bean;

import com.thinksoft.banana.entity.BaseBean;
import com.thinksoft.banana.entity.bean.circle.HttpImgBean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author txf
 * @create 2019/4/4 0004
 * @
 */
public class NovelInfoDataBean extends BaseBean implements Serializable {

    /**
     * id : 51
     * content : <p>详细地址：河西区黑牛城道解放南路柳莺里洗浴spa<br/>小姐人数：10-20<br/>小姐年龄：22-28<br/>小姐素质：服务态度好，不机车<br/>小姐外形：性感<br/>服务项目：ML KJ<br/>价格一览：398-1200<br/>营业时间：24小时<br/>环境设备：挺浪漫的，床上还配玫瑰花<br/>安全评估：比较安全<br/>联系方式：按照地址查找<br/>综合评价：嘴唇很软，很会吸<br/>详细介绍：闲来无事，去家门口洗浴，一问服务员楼上有spa，一对眼神，小狼就明白了，上了三楼包间，装修相当不错，包间内有洗浴，点了32号，进门黑丝短裙，先谈价格，398一个钟，kj俩个钟给ml，文笔不行，就这了！</p>
     * description : 洗浴中心
     * title : 体验河西区32号小姐姐
     * time : 2019-04-11 00:54:44
     * love : 0
     * comment_num : 0
     * images : null
     * age : null
     * service_num : null
     * concat : null
     * cate_two : 59
     * islove : 0
     * cate_name : 河西区
     */

    private int id;
    private String content;
    private String description;
    private String title;
    private String time;
    private int love;
    private int comment_num;
    private ArrayList<HttpImgBean> images;
    private String age;
    private String service_num;
    private String concat;
    private int cate_two;
    private int islove;
    private String cate_name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getLove() {
        return love;
    }

    public void setLove(int love) {
        this.love = love;
    }

    public int getComment_num() {
        return comment_num;
    }

    public void setComment_num(int comment_num) {
        this.comment_num = comment_num;
    }

    public ArrayList<HttpImgBean> getImages() {
        return images;
    }

    public void setImages(ArrayList<HttpImgBean> images) {
        this.images = images;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getService_num() {
        return service_num;
    }

    public void setService_num(String service_num) {
        this.service_num = service_num;
    }

    public String getConcat() {
        return concat;
    }

    public void setConcat(String concat) {
        this.concat = concat;
    }

    public int getCate_two() {
        return cate_two;
    }

    public void setCate_two(int cate_two) {
        this.cate_two = cate_two;
    }

    public int getIslove() {
        return islove;
    }

    public void setIslove(int islove) {
        this.islove = islove;
    }

    public String getCate_name() {
        return cate_name;
    }

    public void setCate_name(String cate_name) {
        this.cate_name = cate_name;
    }
}
