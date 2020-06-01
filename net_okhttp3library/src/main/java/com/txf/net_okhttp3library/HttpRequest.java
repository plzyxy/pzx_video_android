package com.txf.net_okhttp3library;


import android.webkit.DownloadListener;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author txf
 * @create 2019/1/31 0031
 * @请求
 */
public class HttpRequest {

    public final static String POST = "POST";
    public final static String GET = "GET";
    public final static String PUT = "PUT";
    public final static String DELETE = "DELETE";

    /**
     * 创建网络请求{json}
     */
    public static okhttp3.Request createJsonRequest(String url, String requestType, String jsonString) {
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        okhttp3.Request request = null;
        Request.Builder builder = new Request.Builder().url(url);
        switch (requestType) {
            case POST:
                request = builder.post(RequestBody.create(mediaType, jsonString)).build();
                break;
            case GET:
                request = builder.get().build();
                break;
            case PUT:
                request = builder.put(RequestBody.create(mediaType, jsonString)).build();
                break;
            case DELETE:
                request = builder.delete(RequestBody.create(mediaType, jsonString)).build();
                break;
        }
        return request;
    }

    public static okhttp3.Request createJsonRequest(String url, String requestType) {
        return createJsonRequest(url, requestType, "");
    }

    /**
     * 创建网络请求{form}
     */
    public static okhttp3.Request createFormRequest(String url, String requestType, HashMap<String, Object> maps) {
        okhttp3.Request request = null;
        RequestBody formBody = getRequestBody(maps);
        Request.Builder builder = new Request.Builder().url(url);
        switch (requestType) {
            case POST:
                request = builder.post(formBody).build();
                break;
            case GET:
                request = builder.get().build();
                break;
            case PUT:
                request = builder.put(formBody).build();
                break;
            case DELETE:
                request = builder.delete(formBody).build();
                break;
        }
        return request;
    }


    public static okhttp3.Request createFormRequest(String url, String requestType) {
        return createFormRequest(url, requestType, new HashMap<String, Object>());
    }

    /**
     * 创建上传文件请求
     */
    public static okhttp3.Request createFileRequest(String url, HashMap<String, Object> maps) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        File file = (File) maps.get("file");
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        builder.addFormDataPart("file", file.getName(), fileBody);

        for (Map.Entry<String, Object> entry : maps.entrySet()) {
            if (!entry.getKey().equals("file")) {
                builder.addFormDataPart(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }

        RequestBody requestBody = builder.build();
        okhttp3.Request request = new okhttp3.Request.Builder().
                url(url).
                post(requestBody).
                build();
        return request;
    }

    /**
     * 创建下载文件请求
     */
    public static okhttp3.Request createDownloadRequest(String url, long startsPoint) {
        return new Request.Builder()
                .url(url)
                .header("RANGE", "bytes=" + startsPoint + "-")//断点续传
                .build();
    }


    public static RequestBody getRequestBody(HashMap<String, Object> maps) {
        StringBuffer stringBuffer = new StringBuffer();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
        for (Map.Entry<String, Object> entry : maps.entrySet()) {
            stringBuffer.append(entry.getKey() + "=" + entry.getValue() + "&");
        }
        RequestBody body = RequestBody.create(mediaType, stringBuffer.toString());
        return body;
    }
}
