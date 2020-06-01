package com.thinksoft.banana.entity.bean;

import com.thinksoft.banana.entity.BaseBean;

/**
 * @author txf
 * @create 2019/4/4 0004
 * @
 */
public class SpreadLinkBean extends BaseBean {

    /**
     * share_title : 香蕉视频，让生活硬起来！
     * share_desc : 你们的小伙伴都在看，你懂的！
     * share_back : http://59.188.25.220/uploads/picture/20190225/3f427c5240f2eabc4d3df4250d6d86ad.jpg
     * share_link :
     * share_qrcode : http://59.188.25.220
     * invite_code : 04442904
     */

    private String share_title;
    private String share_desc;
    private String share_back;
    private String share_link;
    private String share_qrcode;
    private String invite_code;

    public String getShare_title() {
        return share_title;
    }

    public void setShare_title(String share_title) {
        this.share_title = share_title;
    }

    public String getShare_desc() {
        return share_desc;
    }

    public void setShare_desc(String share_desc) {
        this.share_desc = share_desc;
    }

    public String getShare_back() {
        return share_back;
    }

    public void setShare_back(String share_back) {
        this.share_back = share_back;
    }

    public String getShare_link() {
        return share_link;
    }

    public void setShare_link(String share_link) {
        this.share_link = share_link;
    }

    public String getShare_qrcode() {
        return share_qrcode;
    }

    public void setShare_qrcode(String share_qrcode) {
        this.share_qrcode = share_qrcode;
    }

    public String getInvite_code() {
        return invite_code;
    }

    public void setInvite_code(String invite_code) {
        this.invite_code = invite_code;
    }
}
