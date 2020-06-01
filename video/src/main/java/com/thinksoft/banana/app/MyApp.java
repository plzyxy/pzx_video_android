package com.thinksoft.banana.app;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.tencent.bugly.crashreport.CrashReport;
import com.thinksoft.banana.app.manage.ActivityLifecycleAdapter;
import com.txf.other_tencentlibrary.ShareHelper;
import com.txf.other_toolslibrary.tools.PreferenceTools;
import com.txf.other_toolslibrary.utils.ToastUtils;
import com.umeng.commonsdk.UMConfigure;


/**
 * @author txf
 * @create 2019/1/30 0030
 * @
 */
public class MyApp extends Application {
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        this.context = this;
        initActvityManage();
        initLocal();
        initThird();
    }

    private void initActvityManage() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleAdapter());
    }

    private void initLocal() {
        ToastUtils.init(this);
        PreferenceTools.getInstance().init(this);

        ShareHelper.getInstance().init(this);
        ShareHelper.getInstance().initTencent("222222");
        ShareHelper.getInstance().initWXAPI("wxd930ea5d5a258f4f");
    }

    private void initThird() {
        Fresco.initialize(this);
        Logger.addLogAdapter(new AndroidLogAdapter(getFormatStrategy()));
        CrashReport.initCrashReport(getApplicationContext(), "86a2f416b8", false);

        UMConfigure.init(
                this,
                "5c90a0fb0cafb2458000167f",//app_key
                "guangfang",//渠道名称
                UMConfigure.DEVICE_TYPE_PHONE,//推送类型
                null // Push推送业务的secret
        );
    }

    public static Context getContext() {
        return context;
    }

    private FormatStrategy getFormatStrategy() {
        return PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)
                .methodCount(0)
                .methodOffset(7)
                .build();
    }
}
