package com.txf.net_okhttp3library;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Authenticator;
import okhttp3.Dispatcher;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @author txf
 * @create 2019/1/31 0031
 * @请求连接
 */
public class HttpClient {
    //读取 超时长，单位：秒
    public static final int READ_TIME_OUT = 30;
    //连接 超时长，单位：秒
    public static final int CONNECT_TIME_OUT = 30;
    private static okhttp3.OkHttpClient mOkHttpClient;
    private static HttpClient mHttpClient;

    public static synchronized HttpClient getInstance() {
        if (mHttpClient == null) {
            mHttpClient = new HttpClient();
        }
        return mHttpClient;
    }


    private HttpClient() {
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLogger());
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        mOkHttpClient = new okhttp3.OkHttpClient.Builder()
                .connectTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                //token验证
                .authenticator(new Authenticator() {
                    @Override
                    public Request authenticate(Route route, Response response) throws IOException {
                        return response.request();
                    }
                })
                .addInterceptor(logInterceptor)
                .build();
    }


    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    Headers mHeaders;

    public OkHttpClient addHeaders(Headers headers) {
        this.mHeaders = headers;
        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request build = chain.request().newBuilder()
                        .headers(mHeaders)
                        .build();
                return chain.proceed(build);
            }
        };
        return mOkHttpClient.newBuilder().addInterceptor(headerInterceptor).build();
    }

    //规定每段显示的长度
    private static int LOG_MAXLENGTH = 2000;

    private class HttpLogger implements HttpLoggingInterceptor.Logger {
        @Override
        public void log(String message) {
            int strLength = message.length();
            int start = 0;
            int end = LOG_MAXLENGTH;
            for (int i = 0; i < 100; i++) {
                //剩下的文本还是大于规定长度则继续重复截取并输出
                if (strLength > end) {
                    Log.i("okHttp3" + i, unicodeToUTF_8(message.substring(start, end)));
                    start = end;
                    end = end + LOG_MAXLENGTH;
                } else {
                    Log.i("okHttp3", unicodeToUTF_8(message.substring(start, strLength)));
                    break;
                }
            }
        }
    }

    public String unicodeToUTF_8(String src) {
        if (null == src) {
            return null;
        }
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < src.length(); ) {
            char c = src.charAt(i);
            if (i + 6 < src.length() && c == '\\' && src.charAt(i + 1) == 'u') {
                String hex = src.substring(i + 2, i + 6);
                try {
                    out.append((char) Integer.parseInt(hex, 16));
                } catch (NumberFormatException nfe) {
                    nfe.fillInStackTrace();
                }
                i = i + 6;
            } else {
                out.append(src.charAt(i));
                ++i;
            }
        }
        return out.toString();
    }
}
