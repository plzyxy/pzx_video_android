package com.txf.other_toolslibrary.utils;

import android.content.Context;
import android.widget.Toast;

import com.txf.other_toolslibrary.tools.StringTools;

/**
 * 通用吐司，不会重复显示。
 *
 * @note Created by Chaosxing on 2016/10/27.
 */
public class ToastUtils {
    private static Toast toast = null;

    public static void init(Context context) {
        toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
    }

    /**
     * 普通文本消息提示
     *
     * @param text
     */
    public static void show(CharSequence text) {
        if (StringTools.isNull(text.toString()))
            return;
        if (toast == null)
            return;

        toast.setText(text);
        toast.setDuration(Toast.LENGTH_SHORT);
        // 显示消息
        toast.show();
    }
    public static void showRecordStop(String s) {
        show(s);
    }
}
