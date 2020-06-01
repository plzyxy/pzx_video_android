package com.thinksoft.banana.entity.bean.type;

import com.thinksoft.banana.entity.BaseBean;

/**
 * @author txf
 * @create 2019/3/12 0012
 * @
 */
public class ScreenBean extends BaseBean {
    String text;
    int id;
    boolean isCheck;

    public ScreenBean() {
    }

    public ScreenBean(int id, String text) {
        this(id, text, false);
    }

    public ScreenBean(int id, String text, boolean isCheck) {
        this.text = text;
        this.id = id;
        this.isCheck = isCheck;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
