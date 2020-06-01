package com.thinksoft.banana.entity.bean.circle;

import com.thinksoft.banana.entity.BaseBean;

import java.util.List;

/**
 * @author txf
 * @create 2019/3/23 0023
 * @
 */
public class CircleCommentListBean extends BaseBean {
    List<CircleCommentBean>  commentList;

    public void setCommentList(List<CircleCommentBean> commentList) {
        this.commentList = commentList;
    }

    public List<CircleCommentBean> getCommentList() {
        return commentList;
    }
}
