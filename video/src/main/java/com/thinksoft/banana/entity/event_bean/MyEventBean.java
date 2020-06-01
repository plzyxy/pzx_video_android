package com.thinksoft.banana.entity.event_bean;

import com.thinksoft.banana.entity.bean.UserInfoBean;

/**
 * @author txf
 * @create 2019/3/1 0001
 * @
 */
public class MyEventBean extends BaseEventBean {
    UserInfoBean mUserInfoBean;

    public void setUserInfoBean(UserInfoBean mUserInfoBean) {
        this.mUserInfoBean = mUserInfoBean;
    }

    public UserInfoBean getUserInfoBean() {
        return mUserInfoBean;
    }
}
