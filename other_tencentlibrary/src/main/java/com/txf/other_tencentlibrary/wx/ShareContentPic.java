package com.txf.other_tencentlibrary.wx;

import static com.txf.other_tencentlibrary.ShareHelper.WEIXIN_SHARE_WAY_PIC;

/**
 * @author txf
 * @create 2019/2/22 0022
 * @
 */
public class ShareContentPic extends ShareContent {
    private int picResource;

    public ShareContentPic(int picResource) {
        this.picResource = picResource;
    }

    @Override
    public String getContent() {
        return null;
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
        return picResource;
    }

    @Override
    public String getImageURL() {
        return null;
    }

    @Override
    public int getShareWay() {
        return WEIXIN_SHARE_WAY_PIC;
    }
}
