package com.thinksoft.banana.entity.bean;

import com.thinksoft.banana.entity.BaseBean;

import java.io.Serializable;

/**
 * Created by mayn on 2018/10/3.
 */

public class StringBean
        extends BaseBean
        implements Serializable {
    int id;
    String text;
    String subText;

    public StringBean(int id, String text) {
        this.id = id;
        this.text = text;
    }

    public StringBean(int id, String text, String subText) {
        this.id = id;
        this.text = text;
        this.subText = subText;
    }

    public String getSubText() {
        return subText;
    }

    public void setSubText(String subText) {
        this.subText = subText;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
