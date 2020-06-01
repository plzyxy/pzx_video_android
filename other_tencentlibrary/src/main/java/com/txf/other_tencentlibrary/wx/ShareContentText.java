package com.txf.other_tencentlibrary.wx;

import static com.txf.other_tencentlibrary.ShareHelper.WEIXIN_SHARE_WAY_TEXT;

/**
 * @author txf
 * @create 2019/2/22 0022
 */
public class ShareContentText extends ShareContent {
    private String content;
    /**
     * 构造分享文字类
     */
    public ShareContentText(String content) {
        this.content = content;
    }
    @Override
    public String getContent() {

        return content;
    }
    @Override
    public String getTitle() {
        return null;
    }
    @Override
    public String getURL() {
        return null;
    }
    @Override
    public int getPicResource() {
        return -1;
    }

    @Override
    public String getImageURL() {
        return null;
    }

    @Override
    public int getShareWay() {
        return WEIXIN_SHARE_WAY_TEXT;
    }
}
