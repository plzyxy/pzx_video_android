package com.txf.other_toolslibrary.manager;

import android.app.Activity;
import android.content.Context;

import java.util.LinkedList;

/**
 * 应用程序Activity管理类：用于Activity,Service管理和应用程序退出
 */
public class AppManager {

    private static LinkedList<Activity> activityStack = null;
    private static AppManager instance;

    private AppManager() {
        activityStack = new LinkedList<>();
    }

    /**
     * 单一实例
     */
    public static AppManager getInstance() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }

    public static boolean contains(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 添加Activity
     */
    public void addActivity(Activity activity) {
        activityStack.add(activity);
    }

    /**
     * 删除Activity
     */
    public void delActivity(Activity activity) {
        if (activityStack.contains(activity))
            activityStack.remove(activity);
    }

    /**
     * 结束指定的Activity
     */
    private void finishActivity(Activity activity) {
        if (activity != null) {
            delActivity(activity);
            activity.finish();
        }
    }
    /**
     * 保留指定 Activity
     * */
    public void retainAcitivity(Class<?> cls) {
        for (int i = 0; i < activityStack.size(); i++) {
            Activity activity = activityStack.get(i);
            if (!activity.getClass().equals(cls)) {
                finishActivity(activity);
                i--;
            }
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
                return;
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        if (activityStack != null) {
            for (int i = 0; i < activityStack.size(); i++) {
                if (null != activityStack.get(i)) {
                    activityStack.get(i).finish();
                }
            }
            activityStack.clear();
        }
    }

    /**
     * 退出应用程序
     */
    public void AppExit(Context context) {
        try {
            finishAllActivity();
            //只退出应用不再结束进程
            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Exception e) {
        }
    }
}