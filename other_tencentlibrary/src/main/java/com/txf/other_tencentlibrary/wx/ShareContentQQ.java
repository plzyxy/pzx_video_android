package com.txf.other_tencentlibrary.wx;

import static com.txf.other_tencentlibrary.ShareHelper.WEIXIN_SHARE_WAY_WEBPAGE;

/**
 * @author txf
 * @create 2019/2/22 0022
 * @
 */
public class ShareContentQQ extends ShareContent {
    private String title;
    private String content;
    private String url;
    private String imageURL;

    public ShareContentQQ(String title, String content,
                          String url, String imageURL) {
        this.title = title;
        this.content = content;
        this.url = url;
        this.imageURL = imageURL;
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
        return 0;
    }

    @Override
    public String getImageURL() {
        return imageURL;
    }

    @Override
    public int getShareWay() {
        return WEIXIN_SHARE_WAY_WEBPAGE;
    }
}
