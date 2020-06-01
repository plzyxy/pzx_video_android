package com.txf.other_toolslibrary.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;


import java.lang.ref.WeakReference;

/**
 * @author txf
 * @Title 偏好设置 存储工具
 * @package com.txf.prevention
 * @date 2017/5/2 0002
 */
public class PreferenceTools {
    private static final String TAG = "PreferenceTools";
    private static PreferenceTools tools = null;
    private WeakReference<Context> appContext = null;
    private PreferenceTools() {
    }
    /**
     * 保证在application中执行了init - 否则context无效
     *
     * @return
     */
    public static PreferenceTools getInstance() {
        if (tools == null)
            tools = new PreferenceTools();
        return tools;
    }
    public void init(Context context) {
        appContext = new WeakReference<Context>(context);
    }
    /**
     * 从preferce中读取本地数据
     * @param key
     * @param defValue
     * @return
     */
    public String readPreferences(String key, String defValue) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.appContext.get());
        try {
            return preferences.getString(key, defValue);
        } catch (Exception e) {
            return defValue;
        }
    }
    public long readPreferences(String key, long defValue) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.appContext.get());
        try {
            return preferences.getLong(key, defValue);
        } catch (Exception e) {
            return defValue;
        }
    }

    public boolean readPreferences(String key, boolean defValue) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.appContext.get());
        try {
            return preferences.getBoolean(key, defValue);
        } catch (Exception e) {
            return defValue;
        }
    }

    public int readPreferences(String key, int defValue) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.appContext.get());
        try {
            return preferences.getInt(key, defValue);
        } catch (Exception e) {
            return defValue;
        }
    }

    public boolean deletePreferences(String key) {
        try {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.appContext.get());
            Editor editor = preferences.edit();
            editor.remove(key);
            boolean isOK = editor.commit();
//            Logger.i("delete Preferences key : " + key + ", state :" + isOK);
            return isOK;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 想Preferences插入一条数据
     *
     * @param key
     * @param value
     * @return
     */
    public boolean writePreferences(String key, String value) {
        try {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.appContext.get());
            Editor editor = preferences.edit();
            editor.putString(key, value);
            boolean isOK = editor.commit();
            if (isOK) {
//                Logger.i("setVolumePercent OK percent:" + value);
                return true;
            } else {
//                Logger.i("setVolumePercent error percent:" + value);
                return false;
            }
        } catch (Exception e) {
//            Logger.i("setVolumePercent percent:" + value);
            e.printStackTrace();
            return false;
        }
    }

    public boolean writePreferences(String key, long value) {
        try {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.appContext.get());
            Editor editor = preferences.edit();
            editor.putLong(key, value);
            boolean isOK = editor.commit();
            if (isOK) {
//                Logger.i("setVolumePercent OK percent:" + value);
                return true;
            } else {
//                Logger.i("setVolumePercent error percent:" + value);
                return false;
            }
        } catch (Exception e) {
//            Logger.i("setVolumePercent percent:" + value);
            e.printStackTrace();
            return false;
        }
    }

    public synchronized boolean writePreferences(String key, boolean value) {
        try {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.appContext.get());
            Editor editor = preferences.edit();
            editor.putBoolean(key, value);
            boolean isOK = editor.commit();
            if (isOK) {
//                Logger.i("setVolumePercent OK percent:" + value);
                return true;
            } else {
//                Logger.i("setVolumePercent error percent:" + value);
                return false;
            }
        } catch (Exception e) {
//            Logger.i("setVolumePercent percent:" + value);
            e.printStackTrace();
            return false;
        }
    }

    public boolean writePreferences(String key, int value) {
        try {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.appContext.get());
            Editor editor = preferences.edit();
            editor.putInt(key, value);
            boolean isOK = editor.commit();
            if (isOK) {
//                Logger.i("setVolumePercent OK percent:" + value);
                return true;
            } else {
//                Logger.i("setVolumePercent error percent:" + value);
                return false;
            }
        } catch (Exception e) {
//            Logger.i("setVolumePercent percent:" + value);
            e.printStackTrace();
            return false;
        }
    }
}
