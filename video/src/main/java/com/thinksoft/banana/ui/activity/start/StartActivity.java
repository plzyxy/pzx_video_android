package com.thinksoft.banana.ui.activity.start;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.google.gson.JsonElement;
import com.thinksoft.banana.R;
import com.thinksoft.banana.app.Constant;
import com.thinksoft.banana.entity.bean.HttpAdBean;
import com.thinksoft.banana.entity.bean.VersionBean;
import com.thinksoft.banana.entity.bean.VersionDataBean;
import com.thinksoft.banana.mvp.contract.CommonContract;
import com.thinksoft.banana.mvp.model.CommonStartModel;
import com.thinksoft.banana.mvp.presenter.CommonPresenter;
import com.thinksoft.banana.net.api.ApiRequestTask;
import com.thinksoft.banana.ui.activity.MainActivity;
import com.txf.net_okhttp3library.HttpQueue;
import com.txf.net_okhttp3library.HttpRequest;
import com.txf.net_okhttp3library.listener.DownloadListener;
import com.txf.net_okhttp3library.utils.FileUtils;
import com.txf.other_playerlibrary.tools.Logger;
import com.txf.other_toolslibrary.tools.JsonTools;
import com.txf.other_toolslibrary.tools.StringTools;
import com.txf.other_toolslibrary.utils.ToastUtils;
import com.txf.ui_mvplibrary.ui.activity.BaseMvpActivity;
import com.txf.ui_mvplibrary.utils.FrescoUtils;

import java.io.File;

import okhttp3.Request;

/**
 * @author txf
 * @create 2019/2/16
 */
public class StartActivity
        extends BaseMvpActivity<CommonContract.View, CommonContract.Presenter>
        implements CommonContract.View {
    SimpleDraweeView adImgView;

    View versionRoot, adRoot;
    TextView tv1, button1;
    ProgressBar progressBar;
    VersionDataBean mVersion;

    boolean isUpdataVersion;

    boolean isGetVersion;
    boolean isGetAD;
    boolean isOpenAD;
    long millis;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_start;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        hideNavigationBar();
        setContract(this, new CommonPresenter(this, new CommonStartModel()));
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        adRoot = findViewById(R.id.adRoot);
        adImgView = findViewById(R.id.adImgView);
        button1 = findViewById(R.id.button1);
        button1.setVisibility(View.GONE);

        versionRoot = findViewById(R.id.versionRoot);
        tv1 = findViewById(R.id.tv1);
        progressBar = findViewById(R.id.progressBar);
        versionRoot.setVisibility(View.GONE);


    }

    @Override
    protected String[] buildPermissions() {
        return new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
        };
    }

    @Override
    protected void requestPermissionsResult() {
        //检查版本更新
        getPresenter().getData(ApiRequestTask.start.TAG_GET_VERSION, false);
        //获取广告图
        getPresenter().getData(ApiRequestTask.start.TAG_GET_AD, false);
    }

    Handler mHandler;
    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
//            if (UserInfoManage.getInstance().checkLoginState() == UserInfoManage.STATE_LOGGED_IN) {
            startActivity(new Intent(StartActivity.this, MainActivity.class));
//            } else {
//                startActivity(new Intent(StartActivity.this, LoginActivity.class));
//            }
            finish();
        }
    };

    private void startActivity(long delayMillis) {
        if (isGetVersion && isGetAD && !isUpdataVersion) {
            mHandler = new Handler();
            mHandler.postDelayed(mRunnable, delayMillis);
        }
    }

    private void startActivity() {
        startActivity(1000);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mHandler != null && mRunnable != null) {
            mHandler.removeCallbacks(mRunnable);
            mHandler = null;
            mRunnable = null;
        }
    }

    private int getAppVersionName() {
        PackageManager packageManager = getPackageManager();
        try {
            return packageManager.getPackageInfo(getContext().getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return 0;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isOpenAD) {
            isOpenAD = false;
            if (millis == 0) {
                startActivity(0);
            } else {
                startCountTimer(millis, 1000);
            }
        }
    }

    HttpAdBean.AdvertBean mAdvertBean;

    private void startShowAD() {
        FrescoUtils.setImgUrl(adImgView, mAdvertBean.getImage(), new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                adImgView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isOpenAD = true;
                        startClient(mAdvertBean.getLink());
                    }
                });
                if (!isUpdataVersion) {
                    millis = 3000;
                    startCountTimer(millis, 1000);
                }
            }

            @Override
            public void onFailure(String id, Throwable throwable) {
                if (!isUpdataVersion) {
                    ToastUtils.show("加载广告图失败");
                    startActivity(1000);
                }
            }
        });
    }

    CountDownTimer mTimer;//计时器

    public void startCountTimer(long millisInFuture, long countDownInterval) {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        mTimer = new CountDownTimer(millisInFuture, countDownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                button1.setVisibility(View.VISIBLE);
                millis = millisUntilFinished;
                button1.setText((millisUntilFinished / 1000 + 1) + "s");
            }

            @Override
            public void onFinish() {
                millis = 0;
                button1.setText("0s");
                if (!isOpenAD)
                    startActivity(0);
            }
        };
        mTimer.start();
    }

    @Override
    protected void onInteractionDialog(DialogInterface dialog, int which, int with, Bundle ext) {
        super.onInteractionDialog(dialog, which, with, ext);
        switch (which) {
            case DialogInterface.BUTTON_NEGATIVE:
                finish();
                break;
            case DialogInterface.BUTTON_POSITIVE:
                switch (with) {
                    case 0:
                        //确认下载新版本
                        String url = Constant.ROOT_URI.substring(0, Constant.ROOT_URI.length() - 1) + mVersion.getLink();
                        long startsPoint = FileUtils.getApkFileLength(mVersion.getVersion_name());
                        versionRoot.setVisibility(View.VISIBLE);
                        HttpQueue.newHttpQueue().cancelDownloadRequest(1024);
                        Request request = HttpRequest.createDownloadRequest(url, startsPoint);
                        HttpQueue.newHttpQueue().addDownloadRequest(
                                1024,
                                request,
                                mVersion.getVersion_name(),
                                startsPoint,
                                new DownloadListenerImp());
                        break;
                }
                break;
        }
    }

    @Override
    public void httpOnSuccess(int sign, JsonElement data, String message) {
        switch (sign) {
            case ApiRequestTask.start.TAG_GET_VERSION:
                isGetVersion = true;
                VersionBean bean = JsonTools.fromJson(data, VersionBean.class);
                if (bean.getVersion() == null || StringTools.isNull(bean.getVersion().getLink())) {
                    startActivity();
                    return;
                }
                mVersion = bean.getVersion();
                try {
                    if (Integer.parseInt(mVersion.getVersion_name()) > getAppVersionName()) {
                        isUpdataVersion = true;
                        //服务器版本如果大于当前版本 强制更新
                        new Builder()
                                .setWith(0)
                                .setContent(getString(R.string.新版本提示语))
                                .setButton2(getString(R.string.取消))
                                .setButton3(getString(R.string.确认))
                                .setCancelable(false)
                                .show();
                    }
                } catch (Exception e) {
                    startActivity();
                }
                break;
            case ApiRequestTask.start.TAG_GET_AD:
                isGetAD = true;

                HttpAdBean httpAdBean = JsonTools.fromJson(data, HttpAdBean.class);
                if (httpAdBean == null || httpAdBean.getAdvert() == null || httpAdBean.getAdvert().getStatus() == 0 || StringTools.isNull(httpAdBean.getAdvert().getImage())) {
                    startActivity();
                    return;
                }
                mAdvertBean = httpAdBean.getAdvert();
                startShowAD();
                break;
        }
    }

    @Override
    public void httpOnError(int sign, int error, String message) {
        switch (sign) {
            case ApiRequestTask.start.TAG_GET_VERSION:
                isGetVersion = true;
                startActivity();
                break;
            case ApiRequestTask.start.TAG_GET_AD:
                isGetAD = true;
                startActivity();
                break;
        }
    }

    Handler mHandlera = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    long max = (long) msg.obj;
                    Logger.show("开始下载: " + max / 1024);
                    progressBar.setMax((int) (max / 1024));
                    break;
                case 1:
                    int progress = (int) msg.obj;
                    progressBar.setProgress((progress));
                    break;
                case 2:
                    tv1.setText("下载完成,准备安装");
                    downloadPath = (String) msg.obj;
                    progressBar.setProgress(progressBar.getMax());
                    install(downloadPath);
                    break;
                case 3:
                    String message = (String) msg.obj;
                    tv1.setText("下载失败: " + message);
                    break;
            }
        }
    };

    class DownloadListenerImp implements DownloadListener {
        @Override
        public void start(long max) {
            Message mesg = new Message();
            mesg.what = 0;
            mesg.obj = max;
            mHandlera.sendMessage(mesg);
        }

        @Override
        public void loading(int progress) {
            Message mesg = new Message();
            mesg.what = 1;
            mesg.obj = progress;
            mHandlera.sendMessage(mesg);
        }

        @Override
        public void complete(String path) {
            Message mesg = new Message();
            mesg.what = 2;
            mesg.obj = path;
            mHandlera.sendMessage(mesg);
        }

        @Override
        public void fail(int code, String message) {
            Message mesg = new Message();
            mesg.what = 3;
            mesg.obj = message;
            mHandlera.sendMessage(mesg);
        }

        @Override
        public void loadfail(String message) {
            Message mesg = new Message();
            mesg.what = 3;
            mesg.obj = message;
            mHandlera.sendMessage(mesg);
        }
    }

    String downloadPath;

    @Override
    protected void onDestroy() {
        if (tv1.getText().equals("下载完成,准备安装")) {
            File file = new File(downloadPath);
            file.delete();
        }
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        super.onDestroy();
    }

    private void install(String filePath) {
        File apkFile = new File(filePath);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Logger.show("版本大于 N ，开始使用 fileProvider 进行安装");
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(
                    this
                    , getPackageName()
                    , apkFile);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            Logger.show("正常进行安装");
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        }
        startActivity(intent);
    }
}
