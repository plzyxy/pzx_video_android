package com.txf.net_okhttp3library.listener;

import com.txf.net_okhttp3library.utils.FileUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @author txf
 * @create 2019/3/18 0018
 * @
 */
public class DownloadCallback implements Callback {
    long startsPoint;
    DownloadListener l;
    String fileName;

    public DownloadCallback(String fileName, long startPoint, final DownloadListener l) {
        startsPoint = startPoint;
        this.l = l;
        this.fileName = fileName;
    }

    @Override
    public void onFailure(Call call, IOException e) {
        l.fail(-1, e.getMessage());
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        long length = response.body().contentLength();
        if (length / 1024 == 0) {
            // 说明文件已经下载完，直接跳转安装就好
            l.complete(String.valueOf(FileUtils.getApkFile(fileName).getAbsoluteFile()));
            return;
        }
        l.start(length + startsPoint);
        // 保存文件到本地
        InputStream is = null;
        RandomAccessFile randomAccessFile = null;
        BufferedInputStream bis = null;

        byte[] buff = new byte[2048];
        int len = 0;
        try {
            is = response.body().byteStream();
            bis = new BufferedInputStream(is);

            File file = FileUtils.getApkFile(fileName);
            // 随机访问文件，可以指定断点续传的起始位置
            randomAccessFile = new RandomAccessFile(file, "rw");
            randomAccessFile.seek(startsPoint);
            while ((len = bis.read(buff)) != -1) {
                randomAccessFile.write(buff, 0, len);
            }
            // 下载完成
            l.complete(String.valueOf(file.getAbsoluteFile()));
        } catch (Exception e) {
            e.printStackTrace();
            l.loadfail(e.getMessage());
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (bis != null) {
                    bis.close();
                }
                if (randomAccessFile != null) {
                    randomAccessFile.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}