package com.txf.net_okhttp3library.listener;

/**
 * @author txf
 * @create 2019/3/15 0015
 * @
 */
public interface DownloadListener {
    /**
     * 开始下载
     */
    void start(long max);

    /**
     * 正在下载
     */
    void loading(int progress);

    /**
     * 下载完成
     */
    void complete(String path);

    /**
     * 请求失败
     */
    void fail(int code, String message);

    /**
     * 下载过程中失败
     */
    void loadfail(String message);
}
