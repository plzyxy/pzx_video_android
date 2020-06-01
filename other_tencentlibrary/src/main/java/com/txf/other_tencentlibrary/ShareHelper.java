package com.txf.other_tencentlibrary;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.txf.other_tencentlibrary.wx.ShareContent;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * @author txf
 * @create 2019/2/22 0022
 * @分享助手
 */
public class ShareHelper {
    private static final int THUMB_SIZE = 150;
    /**
     * 文字
     */
    public static final int WEIXIN_SHARE_WAY_TEXT = 1;
    /**
     * 图片
     */
    public static final int WEIXIN_SHARE_WAY_PIC = 2;
    /**
     * 链接
     */
    public static final int WEIXIN_SHARE_WAY_WEBPAGE = 3;

    private static ShareHelper mShareHelper;
    Context mContext;
    Tencent mTencent;
    private IWXAPI api;

    public static synchronized ShareHelper getInstance() {
        if (mShareHelper == null)
            mShareHelper = new ShareHelper();
        return mShareHelper;
    }

    private ShareHelper() {
    }

    public void init(Context context) {
        mContext = context;
    }

    public void initWXAPI(String app_id) {
        api = WXAPIFactory.createWXAPI(mContext, app_id, false);
    }

    public void shareWX(ShareContent shareContent) {
        switch (shareContent.getShareWay()) {
            case WEIXIN_SHARE_WAY_TEXT:
                shareWXText(SendMessageToWX.Req.WXSceneSession, shareContent);
                break;
            case WEIXIN_SHARE_WAY_PIC:
                shareWXPicture(SendMessageToWX.Req.WXSceneSession, shareContent);
                break;
            case WEIXIN_SHARE_WAY_WEBPAGE:
                shareWXWebPage(SendMessageToWX.Req.WXSceneSession, shareContent);
                break;
        }
    }

    public void shareWXFrends(ShareContent shareContent) {
        switch (shareContent.getShareWay()) {
            case WEIXIN_SHARE_WAY_TEXT:
                shareWXText(SendMessageToWX.Req.WXSceneTimeline, shareContent);
                break;
            case WEIXIN_SHARE_WAY_PIC:
                shareWXPicture(SendMessageToWX.Req.WXSceneTimeline, shareContent);
                break;
            case WEIXIN_SHARE_WAY_WEBPAGE:
                shareWXWebPage(SendMessageToWX.Req.WXSceneTimeline, shareContent);
                break;
        }
    }

    public void initTencent(String app_id) {
        mTencent = Tencent.createInstance(app_id, mContext);
    }

    public Tencent getTencent() {
        return mTencent;
    }

    /**
     * 分享qq
     */
    public void shareQQ(Activity activity, ShareContent shareContent, IUiListener var3) {
        Bundle bundle = new Bundle();
        //这条分享消息被好友点击后的跳转URL。
        bundle.putString(QQShare.SHARE_TO_QQ_TARGET_URL, shareContent.getURL());
        //分享的标题。注：PARAM_TITLE、PARAM_IMAGE_URL、PARAM_SUMMARY不能全为空，最少必须有一个是有值的。
        bundle.putString(QQShare.SHARE_TO_QQ_TITLE, shareContent.getTitle());
        //分享的图片URL
        bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, shareContent.getImageURL());
        //分享的消息摘要，最长50个字
        bundle.putString(QQShare.SHARE_TO_QQ_SUMMARY, shareContent.getContent());
        mTencent.shareToQQ(activity, bundle, var3);
    }

    /**
     * 分享qq空间
     */
    public void shareQzone(Activity activity, ShareContent shareContent, IUiListener var3) {
        Bundle bundle = new Bundle();
        //这条分享消息被好友点击后的跳转URL。
        bundle.putString(QQShare.SHARE_TO_QQ_TARGET_URL, shareContent.getURL());
        //分享的标题。注：PARAM_TITLE、PARAM_IMAGE_URL、PARAM_SUMMARY不能全为空，最少必须有一个是有值的。
        bundle.putString(QQShare.SHARE_TO_QQ_TITLE, shareContent.getTitle());
        //分享的图片URL
        ArrayList<String> imageUrls = new ArrayList<String>();
        imageUrls.add(shareContent.getImageURL());
        bundle.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageUrls);
        //分享的消息摘要，最长50个字
        bundle.putString(QQShare.SHARE_TO_QQ_SUMMARY, shareContent.getContent());
        mTencent.shareToQzone(activity, bundle, var3);
    }


    /*
     * 分享文字
     */
    private void shareWXText(int shareType, ShareContent shareContent) {
        String text = shareContent.getContent();
        //初始化一个WXTextObject对象
        WXTextObject textObj = new WXTextObject();
        textObj.text = text;
        //用WXTextObject对象初始化一个WXMediaMessage对象
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;
        msg.description = text;
        //构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        //transaction字段用于唯一标识一个请求
        req.transaction = buildTransaction("textshare");
        req.message = msg;
        //发送的目标场景， 可以选择发送到会话 WXSceneSession 或者朋友圈 WXSceneTimeline。 默认发送到会话。
        req.scene = shareType;
        api.sendReq(req);
    }

    /*
     * 分享图片
     */
    private void shareWXPicture(int shareType, ShareContent shareContent) {
        Bitmap bmp = BitmapFactory.decodeResource(mContext.getResources(), shareContent.getPicResource());
        WXImageObject imgObj = new WXImageObject(bmp);

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;

        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
        bmp.recycle();
        msg.thumbData = bmpToByteArray(thumbBmp, true);  //设置缩略图
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("imgshareappdata");
        req.message = msg;
        req.scene = shareType;
        api.sendReq(req);
    }

    /*
     * 分享链接
     */
    private void shareWXWebPage(int shareType, ShareContent shareContent) {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = shareContent.getURL();
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = shareContent.getTitle();
        msg.description = shareContent.getContent();
        Bitmap bmp = BitmapFactory.decodeResource(mContext.getResources(), shareContent.getPicResource());
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
        bmp.recycle();
        msg.thumbData = bmpToByteArray(thumbBmp, true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = shareType;
        api.sendReq(req);
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    private byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }
        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
