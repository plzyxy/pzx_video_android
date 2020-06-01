package com.txf.ui_mvplibrary.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author txf
 * @create 2019/4/8 0008
 * @
 */
public class BitMapUtils {

    public static File saveBitmap(String file, Bitmap srcBitmap) {
        // 保存图片
        File screenshotsDirectory = new File(file);
        if (!screenshotsDirectory.exists()) {
            screenshotsDirectory.mkdirs();
        }
        File savePath = new File(
                screenshotsDirectory.getPath()
                        + File.separator
                        + System.currentTimeMillis() + ".jpg");
        try {
            FileOutputStream out = new FileOutputStream(savePath);
            srcBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

            return savePath;
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        }
        return null;
    }

    /**
     * 通知相册更新 指定目录
     */
    public static void notifyImgUpdate(File file, Context context) {
        try {
            //把文件插入到系统图库
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), file.getName(), null);
            //通知相册更新
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(file);
            intent.setData(uri);
            context.sendBroadcast(intent);
        } catch (FileNotFoundException e) {

        }
    }


}
