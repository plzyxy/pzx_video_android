package com.thinksoft.banana.entity.bean;

import com.thinksoft.banana.entity.BaseBean;

/**
 * @author txf
 * @create 2019/3/19 0019
 * @
 */
public class HttpNoticeBean extends BaseBean {
    NoticeBean notice;

    public void setNotice(NoticeBean notice) {
        this.notice = notice;
    }

    public NoticeBean getNotice() {
        return notice;
    }
}
