package com.txf.ui_mvplibrary.app;

/**
更多资源请关注：三岁半资源网:sansuib.com
 * @author txf
 * @create 2019/2/23 0023
 * UI配置文件
 */
public class UiConfig {
    public static UiConfig mUiConfig;
    int mRefreshColor;

    public static synchronized UiConfig getInstance() {
        if (mUiConfig == null) {
            mUiConfig = new UiConfig();
        }
        return mUiConfig;
    }

    private UiConfig() {
    }

    public int getRefreshColor() {
        return mRefreshColor;
    }

    public void setRefreshColor(int refreshColor) {
        this.mRefreshColor = refreshColor;
    }

}
