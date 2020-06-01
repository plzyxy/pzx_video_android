package com.thinksoft.banana.entity.bean.play;

import com.thinksoft.banana.entity.BaseBean;
import com.thinksoft.banana.entity.bean.home.BannersBean;

import java.io.Serializable;
import java.util.List;

/**
 * @author txf
 * @create 2019/2/26 0026
 * @视频信息+推荐列表
 */
public class PlayerBean extends BaseBean implements Serializable {

    /**
     * video : {"id":2,"is_free":1,"title":"测试视频","comment_num":4,"link":"http://1258668526.vod2.myqcloud.com/22ad2b93vodcq1258668526/5733dc5f5285890785992337754/f0.mp4","image":"http://1258668526.vod2.myqcloud.com/22ad2b93vodcq1258668526/5733dc5f5285890785992337754/5285890785992337755.jpg","his":"00:00:17","free_time":6,"unit":"s","is_used":1,"diamond":"100.00","used_count":1}
     * collcetion : 0
     * buyStatus:0
     * commentList : [{"id":3,"love":0,"content":"233","nickname":"您还没有设置昵称","image":"http://47.106.154.254/uploads/picture/20190222/198c75a5de7942a696e478659141466e.jpg"},{"id":2,"love":1,"content":"bddwea","nickname":"小米","image":"http://47.106.154.254/uploads/picture/20190222/198c75a5de7942a696e478659141466e.jpg"},{"id":1,"love":0,"content":"aaaaaaaaaaaaaaaaaaaaaaa","nickname":"小米","image":"http://47.106.154.254/uploads/picture/20190222/198c75a5de7942a696e478659141466e.jpg"}]
     */
    private int collcetion;
    private List<CommentDataBean> commentList;
    private List<BannersBean> banners;
    private VideoDataBean video;
    private int buyStatus;

    //非服务器返回字段
    public int state; // 0重新绑定数据 1不重新绑定数据

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public void setBuyStatus(int buyStatus) {
        this.buyStatus = buyStatus;
    }

    public int getBuyStatus() {
        return buyStatus;
    }

    public List<BannersBean> getBanners() {
        return banners;
    }

    public void setBanners(List<BannersBean> banners) {
        this.banners = banners;
    }


    public VideoDataBean getVideo() {
        return video;
    }

    public void setVideo(VideoDataBean video) {
        this.video = video;
    }

    public int getCollcetion() {
        return collcetion;
    }

    public void setCollcetion(int collcetion) {
        this.collcetion = collcetion;
    }

    public List<CommentDataBean> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<CommentDataBean> commentList) {
        this.commentList = commentList;
    }


}
