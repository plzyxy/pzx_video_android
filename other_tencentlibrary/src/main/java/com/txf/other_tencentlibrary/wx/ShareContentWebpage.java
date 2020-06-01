package com.txf.other_tencentlibrary.wx;

import static com.txf.other_tencentlibrary.ShareHelper.WEIXIN_SHARE_WAY_WEBPAGE;

/**
 * @author txf
 * @create 2019/2/22 0022
 * @
 */
public class ShareContentWebpage extends ShareContent {
    private String title;
    private String content;
    private String url;
    private int picResource;

    public ShareContentWebpage(String title, String content,
                               String url, int picResource) {
        this.title = title;
        this.content = content;
        this.url = url;
        this.picResource = picResource;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getURL() {
        return url;
    }

    @Override
    public int getPicResource() {
        return picResource;
    }

    @Override
    public String getImageURL() {
        return "";
    }

    @Override
    public int getShareWay() {
        return WEIXIN_SHARE_WAY_WEBPAGE;
    }
}