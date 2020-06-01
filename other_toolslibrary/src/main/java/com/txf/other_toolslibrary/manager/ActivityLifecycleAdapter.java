package com.txf.other_toolslibrary.manager;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * @author txf
 * @create 2019/2/13 0013
 * @
 */
public class ActivityLifecycleAdapter implements Application.ActivityLifecycleCallbacks {
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        //一个App所有的任何一个Actvity进入生命周期的onCreate后会回调这个方法。
        AppManager.getInstance().addActivity(activity);
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        //一个App所有的任何一个Actvity进入生命周期的onDestroyed后会回调这个方法。
        AppManager.getInstance().delActivity(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        //一个App所有的任何一个Actvity进入生命周期的onStart后会回调这个方法。
    }

    @Override
    public void onActivityResumed(Activity activity) {
        //一个App所有的任何一个Actvity进入生命周期的onResumed后会回调这个方法。
    }

    @Override
    public void onActivityPaused(Activity activity) {
        //一个App所有的任何一个Actvity进入生命周期的onPaused后会回调这个方法。
    }
    @Override
    public void onActivityStopped(Activity activity) {
        //一个App所有的任何一个Actvity进入生命周期的onStopped后会回调这个方法。
    }
    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }
}
