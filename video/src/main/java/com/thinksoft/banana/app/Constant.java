package com.thinksoft.banana.app;

import android.support.v4.app.Fragment;

import com.thinksoft.banana.ui.view.InputView;
import com.txf.ui_mvplibrary.app.UIConstant;

/**
 * @author txf
 * @create 2019/2/16
 */
public class Constant extends UIConstant {
    public static final String KEY_LOG_IN_PARAM = "key_log_in_param";
    /**
     * EventBean Type
     */
    public static final int TYPE_CHANGE_USER_INFO = 0;

    /**
     * 接口地址(凹凸社区)
     */
    public static final String ROOT_URI = "http://45.40.234.171:8086/";
    public static final String secretId = "AKIDJXcdUXdhX5qNp7vPJ2cpupbHoAPeDvph";
    public static final String secretKey = "yxrmeXTL5iDcGnDUbgdQ2WpxwungT4Tv";


    /**
     * 接口地址(测试)
     */
//    public static final String ROOT_URI = "http://47.106.154.254/";
//    public static final String secretId = "AKIDVwELTUS4czCGBvi7GF5vb8Tdd9h9BbcS";
//    public static final String secretKey = "qZVIuMrCMLAbxAHF2d2KFzXwAC5mQp23";
    //'secretId' => 'AKIDVwELTUS4czCGBvi7GF5vb8Tdd9h9BbcS',
    //'secretKey' => 'qZVIuMrCMLAbxAHF2d2KFzXwAC5mQp23',
    /**
     * InputView 相关type
     * {@link InputView#setInputType(int)}
     */
    public static final int TYPE_PHONE = 0;
    public static final int TYPE_PASSWORD = 1;
    public static final int TYPE_CODE = 2;
    public static final int TYPE_INVITE = 3;

    /**
     * OnViewListener 相关Action
     * {@link com.txf.ui_mvplibrary.interfaces.OnAppListener.OnViewListener}
     * {@link com.txf.ui_mvplibrary.interfaces.OnAppListener.OnAdapterListener}
     */
    public final static int TYPE_NULL = 1000;//暂无数据
    public final static int TYPE_ITEM_1 = 1;
    public final static int TYPE_ITEM_2 = 2;
    public final static int TYPE_ITEM_3 = 3;
    public final static int TYPE_ITEM_4 = 4;
    public final static int TYPE_ITEM_5 = 5;
    public final static int TYPE_ITEM_6 = 6;
    public final static int TYPE_ITEM_7 = 7;
    public final static int TYPE_ITEM_8 = 8;
    public final static int TYPE_ITEM_9 = 9;
    public final static int TYPE_ITEM_10 = 10;

    public static final int ACTION_HOME_NAVIGATION_TAB1 = 111;
    public static final int ACTION_HOME_NAVIGATION_TAB2 = 112;
    public static final int ACTION_HOME_NAVIGATION_TAB3 = 113;
    public static final int ACTION_HOME_NAVIGATION_TAB4 = 114;
    public static final int ACTION_HOME_NAVIGATION_TAB5 = 115;

    public static final int ACTION_HOME_INPUT_TEXT = 15;
    public static final int ACTION_PLAYER_LANDSCAPE = 16;
    public static final int ACTION_PLAYER_PORTRAIT = 17;
    public static final int ACTION_SEND_TEXT = 18;
    public static final int ACTION_COLLECTION = 19;
    public final static int ACTION_BANNER = 20;
    public final static int ACTION_SHARE = 21;
    public static final int ACTION_PLAYER_PAYMENT = 22;


    public static final int ACTION_CIRCLE_DETAILS = 23;
    public static final int ACTION_CIRCLE_SHARE = 24;
    public static final int ACTION_CIRCLE_Z = 25;
    public static final int ACTION_CIRCLE_IMG = 26;
    public static final int ACTION_CIRCLE_VIDEO = 27;
    public static final int ACTION_CIRCLE_COMMENT_Z = 28;
    public static final int ACTION_CIRCLE_DELETE = 29;
    public static final int ACTION_FABULOUS = 30;
    public static final int ACTION_IMG = 31;
    public static final int ACTION_NOVEL_DETAILS_VIP = 32;
    /**
     * OnFragmentListener 相关Action
     * {@link com.txf.ui_mvplibrary.interfaces.OnAppListener.OnFragmentListener}
     */
    public static final int ACTION_MY_REFRESH_ING = 1;
    public static final int ACTION_PLAYER_VERTICAL_COLLECTION = 2;
    public static final int ACTION_PLAYER_EXTR_COLLECTION = 3;
    /**
     * OnWindowListener 相关Action
     * {@link com.txf.ui_mvplibrary.interfaces.OnAppListener.OnWindowListener}
     */
    public static final int ACTION_SHARE_QQ = 1;
    public static final int ACTION_SHARE_QQ_PY = 2;
    public static final int ACTION_SHARE_WX = 3;
    public static final int ACTION_SHARE_WX_PX = 4;

    /**
     * Fragment  相关tag
     * {@link com.txf.ui_mvplibrary.ui.activity.BaseActivity#showFragment(int, Fragment, String)}
     */
    public static final String TAG_HOME = "TAG_HOME";
    public static final String TAG_MY = "TAG_MY";
    public static final String TAG_VIDEO = "TAG_VIDEO";
    public static final String TAG_TYPE = "TAG_TYPE";
    public static final String TAG_CIRCLE = "TAG_CIRCLE";

}
