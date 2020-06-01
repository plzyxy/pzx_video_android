package com.thinksoft.banana.app.manage;

import com.google.gson.Gson;
import com.thinksoft.banana.app.Constant;
import com.thinksoft.banana.entity.bean.UserBean;
import com.txf.other_toolslibrary.tools.PreferenceTools;

import java.lang.reflect.Field;


/**
 * @author txf
 * @title 用户信息管理对象
 * @package com.bqy.ssqyb.tools
 * @date 2017/10/22/022
 */
public class UserInfoManage {
    public static final int STATE_NOT_LOGGED_IN = 0;//未登录
    public static final int STATE_LOGGED_IN = 1;//已登录
    private UserBean mUserBean;

    private static UserInfoManage mUserInfoManage;

    private UserInfoManage() {
    }

    public static UserInfoManage getInstance() {
        if (mUserInfoManage == null) {
            mUserInfoManage = new UserInfoManage();
        }
        return mUserInfoManage;
    }

    public UserBean getUserInfo() {
        if (mUserBean == null) {
            String data = PreferenceTools.getInstance().readPreferences(Constant.KEY_LOG_IN_PARAM, "");
            if (data.equals("")) {
                return new UserBean();
            } else {
                mUserBean = new Gson().fromJson(data, UserBean.class);
            }
        }
        return mUserBean;
    }

    /**
     * 设置用户数据
     */
    public void setUserInfo(UserBean userBean) {
        if (mUserBean == null) {
            mUserBean = getUserInfo();
        }
        if (mUserBean == null) {
            mUserBean = userBean;
            PreferenceTools.getInstance().writePreferences(Constant.KEY_LOG_IN_PARAM, new Gson().toJson(getUserInfo()));
        } else if (!mUserBean.equals(userBean)) {
            cleanUserInfo();
            mUserBean = userBean;
            PreferenceTools.getInstance().writePreferences(Constant.KEY_LOG_IN_PARAM, new Gson().toJson(getUserInfo()));
        }
    }

    /**
     * 通过传入属性名称  修改属性的值
     *
     * @param AttributeName 要修改的属性名称
     * @param value         修改的值 (注意该属性的原类型)
     */
    public void modifyUserAttribute(String AttributeName, Object value) {
        if (getUserInfo() == null) {
            new Exception("用户信息为空 修改 " + AttributeName + " 失败!").printStackTrace();
            return;
        }
        Class userClass = getUserInfo().getClass();
        try {
            Field f = userClass.getDeclaredField(AttributeName);
            f.setAccessible(true);
            f.set(getUserInfo(), value);
            PreferenceTools.getInstance().writePreferences(Constant.KEY_LOG_IN_PARAM, new Gson().toJson(getUserInfo()));
        } catch (NoSuchFieldException e) {
            new Exception("修改用户信息失败, 没有找到 " + AttributeName + " 属性. ").printStackTrace();
        } catch (IllegalArgumentException e) {
            new Exception("修改用户信息失败, 修改的属性 类型错误. " + e.getMessage()).printStackTrace();
        } catch (IllegalAccessException e) {
            //
        }
    }

    /**
     * 检查登录状态
     *
     * @return {@link UserInfoManage#STATE_LOGGED_IN  已登录}, or {@link UserInfoManage#STATE_NOT_LOGGED_IN  未登录}
     */
    public int checkLoginState() {
        if (!checkUserInfo()) {
            return STATE_NOT_LOGGED_IN;
        } else {
            return STATE_LOGGED_IN;
        }
    }

    /**
     * 是否首次启动app
     */
    public boolean isFirst() {
        boolean isFirst = PreferenceTools.getInstance().readPreferences("isFirst", false);
        if (!isFirst) {
            PreferenceTools.getInstance().writePreferences("isFirst", true);
        }
        return !isFirst;
    }

    private boolean checkUserInfo() {
        boolean isUserInfo = true;
        if (mUserBean == null) {
            String data = PreferenceTools.getInstance().readPreferences(Constant.KEY_LOG_IN_PARAM, "");
            if (data.equals("")) {
                isUserInfo = false;
            } else {
                mUserBean = new Gson().fromJson(data, UserBean.class);
            }
        }
        return isUserInfo;
    }

    /**
     * 清空用户信息
     */
    public void cleanUserInfo() {
        if (checkUserInfo()) {
            mUserBean = null;
            PreferenceTools.getInstance().deletePreferences(Constant.KEY_LOG_IN_PARAM);
        }
    }
}