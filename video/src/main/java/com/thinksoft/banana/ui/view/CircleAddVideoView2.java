package com.thinksoft.banana.ui.view;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.JsonElement;
import com.thinksoft.banana.R;
import com.thinksoft.banana.app.Constant;
import com.thinksoft.banana.app.manage.UserInfoManage;
import com.thinksoft.banana.entity.bean.circle.UploadVideoBean;
import com.thinksoft.banana.entity.item.CircleItem;
import com.thinksoft.banana.net.listener.HttpJsonListener;
import com.txf.net_okhttp3library.HttpQueue;
import com.txf.net_okhttp3library.HttpRequest;
import com.txf.other_toolslibrary.tools.JsonTools;
import com.txf.other_toolslibrary.utils.ToastUtils;
import com.txf.ui_mvplibrary.ui.view.BaseViewGroup;

import java.io.File;
import java.util.HashMap;

import okhttp3.Request;

/**
 * @author txf
 * @create 2019/3/22 0022
 * 圈子发布页  视频上传到服务器
 */
public class CircleAddVideoView2 extends BaseViewGroup {
    SimpleDraweeView mSimpleDraweeView;
    ProgressBar mProgressBar;
    TextView progressTV;
    View errorButton;

    CircleItem mCircleItem;

    boolean isDelete;

    public CircleAddVideoView2(Context context) {
        super(context);
    }

    public CircleAddVideoView2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleAddVideoView2(Context context, AttributeSet attrs, int defStyleAttr) {
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

        progressTV.setVisibility(GONE);
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
        File file = new File(path);
        //上传图片
        HashMap<String, Object> maps = new HashMap<>();
        maps.put("token", UserInfoManage.getInstance().getUserInfo().getToken());
        maps.put("file", file);
        String url = Constant.ROOT_URI + "index/circle/upload_video";
        Request request = HttpRequest.createFileRequest(url, maps);
        HttpQueue.newHttpQueue().addRequest(1027, request, new HttpJsonListener() {
            public void onSuccess(int sign, JsonElement data, String message) {
                if (isDelete)
                    return;
                setCompleteUploadUI();
                UploadVideoBean imgBean = JsonTools.fromJson(data, UploadVideoBean.class);
                mCircleItem.setVideo_id(imgBean.getId());
            }

            @Override
            public void onError(int sign, int error, String message) {
                if (isDelete)
                    return;
                if (error == -1) {
                    //登录失效
                    logInvalid();
                } else {
                    ToastUtils.show(message);
                    setErrorUploadUI();
                }
            }
        });
    }

    private void logInvalid() {

    }

    private void setStartUploadUI() {
        errorButton.setOnClickListener(null);
        errorButton.setVisibility(VISIBLE);
        progressTV.setVisibility(VISIBLE);
        progressTV.setText("上传中...");
    }

    private void setCompleteUploadUI() {
        progressTV.setText("");
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
        progressTV.setVisibility(VISIBLE);
        progressTV.setText("上传失败: 点击重试");
    }

    public void delete() {
        isDelete = true;
    }

    private void uploadError(String mesg) {
        if (isDelete)
            return;
        ToastUtils.show(mesg);
        setErrorUploadUI();
    }
}
