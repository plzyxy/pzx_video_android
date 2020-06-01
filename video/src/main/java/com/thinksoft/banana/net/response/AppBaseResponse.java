package com.thinksoft.banana.net.response;


import com.google.gson.JsonElement;

/**
 * @author txf
 * @create 2019/1/31 0031
 * @
 */
public class AppBaseResponse {
    private String msg;
    private int code;
    private JsonElement data;
    private String token;

    public JsonElement getData() {
        return data;
    }

    public void setData(JsonElement data) {
        this.data = data;
    }

    public String getMsg() {
        return msg == null ? "" : msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
}
