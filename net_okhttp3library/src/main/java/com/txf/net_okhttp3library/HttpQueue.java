package com.txf.net_okhttp3library;

import com.txf.net_okhttp3library.body.DownloadBody;
import com.txf.net_okhttp3library.listener.DownloadCallback;
import com.txf.net_okhttp3library.listener.DownloadListener;
import com.txf.net_okhttp3library.utils.FileUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author txf
 * @create 2019/1/31 0031
 * @请求队列
 */
public class HttpQueue {
    private static HttpQueue mHttpQueue;
    private OkHttpClient mDownloadClient;

    public synchronized static HttpQueue newHttpQueue() {
        if (mHttpQueue == null) {
            mHttpQueue = new HttpQueue();
        }
        return mHttpQueue;
    }

    /**
     * 取消指定标识的网络请求
     *
     * @param tag 网络请求标识
     */
    public void cancelRequest(int tag) {
        for (Call call : HttpClient.getInstance().getOkHttpClient().dispatcher().queuedCalls()) {
            if (call.request().tag().equals(tag))
                call.cancel();
        }
    }

    /**
     * 取消指定标识的网络请求
     *
     * @param tag 网络请求标识
     */
    public void cancelDownloadRequest(int tag) {
        if (mDownloadClient != null)
            for (Call call : mDownloadClient.dispatcher().queuedCalls()) {
                if (call.request().tag().equals(tag))
                    call.cancel();
            }
    }

    /**
     * 取消全部网络请求
     */
    public void cancelRequestAll() {
        for (Call call : HttpClient.getInstance().getOkHttpClient().dispatcher().queuedCalls()) {
            call.cancel();
        }
    }

    /**
     * 添加请求到队列(发送请求)
     *
     * @param tag 网络请求标识
     */
    public void addRequest(int tag, okhttp3.Request request, final Callback l) {
        Request.Builder builder = request.newBuilder();
        builder.tag(tag);
        Call call = HttpClient.getInstance().getOkHttpClient().newCall(builder.build());
        call.enqueue(l);
    }

    /**
     * 添加请求到队列(发送请求)
     *
     * @param tag 网络请求标识
     */
    public void addDownloadRequest(int tag, okhttp3.Request request, String fileName, long startPoint, final DownloadListener l) {
        Request.Builder builder = request.newBuilder();
        builder.tag(tag);

        Call call = getHttpClient(startPoint, l).newCall(builder.build());

        call.enqueue(new DownloadCallback(fileName, startPoint, l));
    }

    public OkHttpClient getHttpClient(final long startPoint, final DownloadListener l) {
        if (mDownloadClient == null) {
            mDownloadClient = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(5000, TimeUnit.SECONDS)
                    .build();
        }
        // 重写ResponseBody监听请求
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response originalResponse = chain.proceed(chain.request());
                return originalResponse.newBuilder()
                        .body(new DownloadBody(originalResponse, startPoint, l))
                        .build();
            }
        };
        return mDownloadClient.newBuilder().addNetworkInterceptor(interceptor).build();
    }

}
