package com.txf.ui_mvplibrary.utils;

import android.os.Bundle;

import com.txf.ui_mvplibrary.app.UIConstant;
import com.txf.ui_mvplibrary.bean.BaseItem;

import java.io.Serializable;


/**
 * @author txf
 * @create 2019/2/18 0018
 * @
 */
public class BundleUtils {

    public static Bundle putString(String s) {
        Bundle bundle = new Bundle();
        bundle.putString(UIConstant.BUNDLE_KEY_STRING, s);
        return bundle;
    }

    public static String getString(Bundle bundle) {
        return bundle.getString(UIConstant.BUNDLE_KEY_STRING);
    }

    public static Bundle putBaseItem(BaseItem item) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(UIConstant.BUNDLE_KEY_BASE_ITEM, item);
        return bundle;
    }

    public static <T extends BaseItem> T getBaseItem(Bundle bundle) {
        return (T) bundle.getSerializable(UIConstant.BUNDLE_KEY_BASE_ITEM);
    }

    public static Bundle putSerializable(Serializable serializable) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(UIConstant.BUNDLE_KEY_SERIALIZABLE, serializable);
        return bundle;
    }

    public static <T> T getSerializable(Bundle bundle) {
        return (T) bundle.getSerializable(UIConstant.BUNDLE_KEY_SERIALIZABLE);
    }

    public static Bundle putInt(int s) {
        Bundle bundle = new Bundle();
        bundle.putInt(UIConstant.BUNDLE_KEY_INT, s);
        return bundle;
    }

    public static int getInt(Bundle bundle) {
        return bundle.getInt(UIConstant.BUNDLE_KEY_INT);
    }
}
