package com.donkingliang.imageselector.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;

import com.donkingliang.imageselector.OnImgSelectorListener;
import com.seek.biscuit.Biscuit;
import com.seek.biscuit.CompressResult;
import com.seek.biscuit.OnCompressCompletedListener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author txf
 * @create 2018/12/19 0019
 * @图片选择管理
 */
public class ImageSelectorManage {
    Activity activity;
    File outputImagepath;
    private static final int REQUEST_PHOTO = 1001;
    private static final int REQUEST_PHOTO_ALBUM = 1002;
    OnImgSelectorListener listener;
    Biscuit mBiscuit;

    public ImageSelectorManage(Activity activity, OnImgSelectorListener listener) {
        this.activity = activity;
        this.listener = listener;
    }

    /**
     * 打开相机
     */
    public void openPhoto() {
        if (checkPermission()) {
            SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
            String filename = timeStampFormat.format(new Date());
            outputImagepath = new File(activity.getExternalCacheDir(), filename + ".jpg");
            try {
                if (outputImagepath.exists()) {
                    outputImagepath.delete();
                }
                outputImagepath.createNewFile();
            } catch (IOException e) {
                //获取储存路劲失败
                return;
            }
            Uri imageUri;
            if (Build.VERSION.SDK_INT < 24) {
                imageUri = Uri.fromFile(outputImagepath);
            } else {
                imageUri = FileProvider.getUriForFile(activity, activity.getPackageName(), outputImagepath);
            }
            // 启动相机程序
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            activity.startActivityForResult(intent, REQUEST_PHOTO);
        }
    }

    private boolean checkPermission() {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, 1);
            return false;
        } else
            return true;
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != 1 || grantResults == null || grantResults.length == 0)
            return;
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {//授权
            openPhoto();
        }
    }

    /*
     * 判断sdcard是否被挂载
     */
    public static boolean hasSdcard() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    /**
     * 打开相册
     */
    public void openPhotoAlbum() {
        ImageSelectorUtils.openPhoto(activity, REQUEST_PHOTO_ALBUM, true, 1);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_PHOTO_ALBUM://图片选择结果
                if (data == null) {
                    return;
                }
                ArrayList<String> images = data.getStringArrayListExtra(ImageSelectorUtils.SELECT_RESULT);
                compressImag(images.get(0));
                break;
            case REQUEST_PHOTO://拍照结果
                if (resultCode == Activity.RESULT_OK)
                    compressImag(outputImagepath.getAbsolutePath());
                break;
        }
    }

    public void onStop() {
        if (mBiscuit != null) {
            mBiscuit.setOnCompressCompletedListener(null);
            mBiscuit = null;
            Biscuit.clearCache(activity.getApplicationContext());
        }
    }

    public void compressImag(String imgUris) {
        mBiscuit = Biscuit.with(activity.getApplicationContext())
                .path(imgUris) //可以传入一张图片路径，也可以传入一个图片路径列表进行批量压缩
                .loggingEnabled(false)//是否输出log 默认输出
//                        .quality(50)//质量压缩值（0...100）默认已经非常接近微信，所以没特殊需求可以不用自定义
                .originalName(true) //使用原图名字来命名压缩后的图片，默认不使用原图名字,随机图片名字
//                .listener(mCompressListener)//压缩监听,每压缩完成一张即回调一次监听
                .listener(new OnCompressCompletedListener() {
                    @Override
                    public void onCompressCompleted(CompressResult result) {
                        listener.onCompressCompleted(result.mSuccessPaths, result.mExceptionPaths);
                    }
                })//压缩完成监听，只有传入的所有图片都压缩结束才回调
//                .targetDir(FileUtils.getImageDir())//自定义压缩保存路径
//                        .executor(executor) //自定义实现执行，注意：必须在子线程中执行 默认使用HandlerThread执行
//                        .ignoreAlpha(true)//忽略alpha通道，对图片没有透明度要求可以这么做，默认不忽略。
//                        .compressType(Biscuit.SAMPLE)//采用采样率压缩方式，默认是使用缩放压缩方式，也就是和微信效果类似。
                .ignoreLessThan(500)//忽略小于500kb的图片不压缩，返回原图路径
                .build();
        mBiscuit.asyncCompress();//异步压缩
        listener.onCompressStart();
    }
}
