package com.thinksoft.banana.ui.view;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.thinksoft.banana.R;
import com.thinksoft.banana.app.Constant;
import com.thinksoft.banana.app.MyApp;
import com.thinksoft.banana.entity.item.CircleItem;
import com.txf.net_okhttp3library.HttpQueue;
import com.txf.other_tencentl_uploadlibrary.TXUGCPublish;
import com.txf.other_tencentl_uploadlibrary.TXUGCPublishTypeDef;
import com.txf.other_tencentl_uploadlibrary.utils.FileUtils;
import com.txf.other_tencentl_uploadlibrary.utils.Signature;
import com.txf.other_toolslibrary.utils.ToastUtils;
import com.txf.ui_mvplibrary.ui.view.BaseViewGroup;

import java.util.Random;

/**
 * @author txf
 * @create 2019/3/22 0022
 * 圈子发布页  视频上传到腾讯云
 */
public class CircleAddVideoView extends BaseViewGroup implements TXUGCPublishTypeDef.ITXVideoPublishListener {
    SimpleDraweeView mSimpleDraweeView;
    ProgressBar mProgressBar;
    TextView progressTV;
    View errorButton;

    TXUGCPublish mVideoPublish;
    CircleItem mCircleItem;

    boolean isDelete;

    public CircleAddVideoView(Context context) {
        super(context);
    }

    public CircleAddVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleAddVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onCreate(Context context) {
        inflate(context, R.layout.view_circle_add_video, this);
        initView();
    }

    private void initView() {
        mSimpleDraweeView = getViewById(R.id.SimpleDraweeView);
        mProgressBar = getViewById(R.id.progressBar);
        progressTV = getViewById(R.id.progressTV);
        errorButton = getViewById(R.id.button);

        errorButton.setVisibility(VISIBLE);
        mProgressBar.setVisibility(GONE);
        progressTV.setText("");
    }

    public void setData(CircleItem item) {
        mCircleItem = item;
        mCircleItem.setType(2);
        mSimpleDraweeView.setImageURI(Uri.parse("file://" + mCircleItem.getData()));
        startUpload((String) mCircleItem.getData());
    }

    private void startUpload(String path) {
        setStartUploadUI();
        isDelete = false;
        if (mVideoPublish == null) {
            mVideoPublish = new TXUGCPublish(MyApp.getContext(), "independence_android");
            mVideoPublish.setListener(this);
        }
        Signature sign = new Signature();
        sign.setSecretId(Constant.secretId);
        sign.setSecretKey(Constant.secretKey);
        sign.setCurrentTime(System.currentTimeMillis() / 1000);
        sign.setRandom(new Random().nextInt(java.lang.Integer.MAX_VALUE));
        sign.setSignValidDuration(3600 * 24 * 2);

        try {
            TXUGCPublishTypeDef.TXPublishParam param = new TXUGCPublishTypeDef.TXPublishParam();
            param.signature = sign.getUploadSignature();
            param.videoPath = path;
            param.coverPath = FileUtils.getVideoThumbnailPath(getContext(), path);
            param.enableResume = false;
//            Logger.i("上传参数:  " + JsonTools.toJSON(param));
            int publishCode = mVideoPublish.publishVideo(param);
            if (publishCode != 0) {
                uploadError("发布失败,错误码: " + publishCode);
            }
        } catch (Exception e) {
            uploadError("发布失败,初始化签名错误");
            return;
        }
    }

    private void setStartUploadUI() {
        errorButton.setOnClickListener(null);
        errorButton.setVisibility(VISIBLE);
        mProgressBar.setVisibility(VISIBLE);
        mProgressBar.setProgress(0);
        mProgressBar.setMax(100);
        progressTV.setText("");
    }

    private void setCompleteUploadUI() {
        progressTV.setText("");
        mProgressBar.setVisibility(GONE);
        errorButton.setVisibility(GONE);
        errorButton.setOnClickListener(null);
    }

    private void setErrorUploadUI() {
        errorButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startUpload((String) mCircleItem.getData());
            }
        });
        errorButton.setVisibility(VISIBLE);
        mProgressBar.setVisibility(GONE);
        progressTV.setText("上传失败: 点击重试");
    }

    public void delete() {
        isDelete = true;
        mVideoPublish.canclePublish();
    }

    private void uploadError(String mesg) {
        if (isDelete)
            return;
        ToastUtils.show(mesg);
        setErrorUploadUI();
    }

    @Override
    public void onPublishProgress(long uploadBytes, long totalBytes) {
        if (isDelete)
            return;
//        Logger.i("上传中:  " + "uploadBytes : " + uploadBytes + "  totalBytes: " + totalBytes);
        int progress = (int) (uploadBytes / (totalBytes * 1.0) * 100);
        mProgressBar.setProgress(progress);
        progressTV.setText("上传中 " + progress + "%");
    }

    @Override
    public void onPublishComplete(TXUGCPublishTypeDef.TXPublishResult result) {
        if (isDelete)
            return;
        if (result.retCode == 0) {
//            Logger.i("上传完成:  " + JsonTools.toJSON(result));
            setCompleteUploadUI();
            mCircleItem.setVideo_id(result.videoId);
        } else {
            uploadError(result.descMsg);
        }
    }
}
