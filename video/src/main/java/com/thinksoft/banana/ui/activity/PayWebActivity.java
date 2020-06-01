package com.thinksoft.banana.ui.activity;

import com.thinksoft.banana.R;
import com.thinksoft.banana.app.Constant;
import com.thinksoft.banana.app.manage.UserInfoManage;
import com.txf.other_toolslibrary.tools.StringTools;
import com.txf.ui_mvplibrary.ui.activity.BaseWebActivity;

/**
 * @author txf
 * @create 2019/4/16 0016
 * @会员支付页面
 */
public class PayWebActivity extends BaseWebActivity {
    @Override
    protected String buildUrlString() {
        StringBuffer h5Url = new StringBuffer();
        h5Url.append(Constant.ROOT_URI).append("index/payway?token=");

        if (!StringTools.isNull(UserInfoManage.getInstance().getUserInfo().getToken())) {
            h5Url.append(UserInfoManage.getInstance().getUserInfo().getToken());
        }
        return h5Url.toString();
    }

    @Override
    protected String buildTitleString() {
        return getString(R.string.支付);
    }
}
