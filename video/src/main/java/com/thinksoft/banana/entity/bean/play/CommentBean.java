package com.thinksoft.banana.entity.bean.play;

import com.thinksoft.banana.entity.BaseBean;

import java.util.List;

/**
 * @author txf
 * @create 2019/3/5 0005
 * @
 */
public class CommentBean extends BaseBean {
    private List<CommentDataBean> commentList;

    public List<CommentDataBean> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<CommentDataBean> commentList) {
        this.commentList = commentList;
    }
}
