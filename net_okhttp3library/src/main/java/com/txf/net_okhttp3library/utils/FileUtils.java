package com.txf.net_okhttp3library.utils;

import android.os.Environment;

import java.io.File;

/**
 * @author txf
 * @create 2019/3/18 0018
 * @
 */
public class FileUtils {

    public static File getApkFile(String appName) {
        String root = Environment.getExternalStorageDirectory().getPath();
        File file = new File(root, appName + ".apk");
        return file;
    }

    public static long getApkFileLength(String appName) {
        String root = Environment.getExternalStorageDirectory().getPath();
        File file = new File(root, appName + ".apk");
        return file.length();
    }
}
