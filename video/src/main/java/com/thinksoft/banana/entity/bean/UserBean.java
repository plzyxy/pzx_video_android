package com.thinksoft.banana.entity.bean;


import com.thinksoft.banana.entity.BaseBean;

/**
 * @author txf
 * @create 2018/12/17 0017
 * @
 */
public class UserBean extends BaseBean {
    String token;

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
