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
import com.thinksoft.banana.entity.bean.circle.UploadImgBean;
import com.thinksoft.banana.entity.item.CircleItem;
import com.thinksoft.banana.net.listener.HttpJsonListener;
import com.txf.net_okhttp3library.HttpQueue;
import com.txf.net_okhttp3library.HttpRequest;
import com.txf.other_tencentl_uploadlibrary.TXUGCPublish;
import com.txf.other_toolslibrary.tools.JsonTools;
import com.txf.other_toolslibrary.tools.StringTools;
import com.txf.other_toolslibrary.utils.ToastUtils;
import com.txf.ui_mvplibrary.ui.view.BaseViewGroup;

import java.io.File;
import java.util.HashMap;

import okhttp3.Request;

/**
 * @author txf
 * @create 2019/3/22 0022
 * 圈子发布页  图片上传到服务器
 */
public class CircleAddImgView extends BaseViewGroup {
    SimpleDraweeView mSimpleDraweeView;
    ProgressBar mProgressBar;
    TextView progressTV;
    View errorButton;

    TXUGCPublish mVideoPublish;
    CircleItem mCircleItem;

    boolean isDelete;

    public CircleAddImgView(Context context) {
        super(context);
    }

    public CircleAddImgView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleAddImgView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onCreate(Context context) {
        inflate(context, R.layout.view_circle_add_img, this);
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
        mCircleItem.setType(1);
        mSimpleDraweeView.setImageURI(Uri.parse("file://" + mCircleItem.getData()));
        startUpload((String) mCircleItem.getData());
    }

    private void startUpload(String path) {
        isDelete = false;
        setStartUploadUI();
        //调用上传图片接口
        if (StringTools.isNull(path)) {
            setErrorUploadUI();
            return;
        }
        File file = new File(path);
        //上传图片
        HashMap<String, Object> maps = new HashMap<>();
        maps.put("token", UserInfoManage.getInstance().getUserInfo().getToken());
        maps.put("file", file);
        String url = Constant.ROOT_URI + "index/Circle/uploadImage";
        Request request = HttpRequest.createFileRequest(url, maps);
        HttpQueue.newHttpQueue().addRequest(1026, request, new HttpJsonListener() {
            public void onSuccess(int sign, JsonElement data, String message) {
                if (isDelete)
                    return;
                setCompleteUploadUI();
                UploadImgBean imgBean = JsonTools.fromJson(data, UploadImgBean.class);
                mCircleItem.setUploadImgBean(imgBean);
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

    public void delete() {
        isDelete = true;
        HttpQueue.newHttpQueue().cancelRequest(1026);
    }

    private void setStartUploadUI() {
        errorButton.setOnClickListener(null);
        errorButton.setVisibility(VISIBLE);
        mProgressBar.setVisibility(GONE);
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

    private void uploadError(String mesg) {
        ToastUtils.show(mesg);
        setErrorUploadUI();
    }
}
