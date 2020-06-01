package com.txf.other_toolslibrary.tools;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @title JSON工具，
 * 使用fastjson或者gson解析出錯将会抛出异常，该工具主要用于捕获异常,避免程序崩溃
 * @note Created by Chaosxing on 2016/10/24.
 */
public class JsonTools {

    public static String toJson(Object value) {
        return new Gson().toJson(value);
    }

    public static <T> T fromJson(String json, Class<T> cls) {
        try {
            return new Gson().fromJson(json, cls);
        } catch (Exception e) {
            return null;
        }
    }

    public static <T> T fromJson(JsonElement jsonElement, Class<T> cls) {
        try {
            return new Gson().fromJson(jsonElement, cls);
        } catch (Exception e) {
            return null;
        }
    }

    public static <T> T fromJson(String jsonString, Type typeOfT) {
        try {
            return new Gson().fromJson(jsonString, typeOfT);
        } catch (Exception e) {
            return null;
        }
    }

    public static <T> T fromJson(JsonElement jsonElement, Type typeOfT) {
        try {
            return new Gson().fromJson(jsonElement, typeOfT);
        } catch (Exception e) {
            return null;
        }
    }

    public static <T> T fromJson(String json, TypeToken typeToken) {
        try {
            return new Gson().fromJson(json, typeToken.getType());
        } catch (Exception e) {
            return null;
        }
    }

    public static <T> T fromJson(JsonElement jsonElement, TypeToken typeToken) {
        try {
            return new Gson().fromJson(jsonElement, typeToken.getType());
        } catch (Exception e) {
            return null;
        }
    }

    public final static <T> Type buildType(Class<T> cls) {
        return new TypeToken<List<T>>() {
        }.getType();
    }

    public final static String toJSON(Object o) {
        try {
            return new Gson().toJson(o);
        } catch (Exception e) {
            return "";
        }
    }

}
