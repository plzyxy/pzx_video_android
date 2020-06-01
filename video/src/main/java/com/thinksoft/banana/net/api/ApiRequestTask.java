package com.thinksoft.banana.net.api;

import android.net.Uri;

import com.thinksoft.banana.app.Constant;
import com.thinksoft.banana.app.manage.UserInfoManage;
import com.txf.net_okhttp3library.HttpQueue;
import com.txf.net_okhttp3library.HttpRequest;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.Request;

/**
 * @author txf
 * @create 2018/12/12 0012
 * @app请求任务 聚合类
 */

public class ApiRequestTask {
    /**
     * 添加通用请求头
     */
    private static Request addCoomonHeader(Request request) {
        Headers headers = new Headers
                .Builder()
//                .add("Authorization", "Bearer " + "eyJhbGciOiJIUzUxMiIsImlhdCI6MTU0ODkyMzQ5MiwiZXhwIjozMDk3ODU0MTg0LjY4NDg5MTd9.eyJpYXQiOjE1NDg5MjM0OTIuNjg0ODkxNywidWlkIjoiNWM0NDVmNzUwMWNhNDMwYmVlYjQ0OWYzIiwidXNlcm5hbWUiOiJ0b21hdG8iLCJ0eXBlIjowLCJyIjpmYWxzZX0.U4xpdQ1fXwmDLSgiXl_GxGmdPaHZid6iynMoTY6XNlsuznSYzftFTdtnUITaQKDBvA7dXp3IGTXDGLgFnYQSKA")
                .build();
        return request.newBuilder()
                .headers(headers)
                .build();
    }

    public static StringBuffer url;

    public static class start {
        /**
         * 发送登录 验证码
         */
        public final static int TAG_SEND_CODE_REGISTER = 0;
        /**
         * 注册
         */
        public final static int TAG_REGISTER = 1;
        /**
         * 登录
         */
        public final static int TAG_LOGIN = 1024;
        /**
         * 发送设置新密码 验证码
         */
        public final static int TAG_SEND_CODE_NEW_PASSWORD = 3;

        /**
         * 设置新密码
         */
        public final static int TAG_NEW_PASSWORD = 4;

        /**
         * 获取用户协议富文本
         */
        public final static int TAG_USER_AGREEMENT = 5;

        /**
         * 获取服务器版本信息
         */
        public static final int TAG_GET_VERSION = 6;

        /**
         * 获取公告
         */
        public static final int TAG_GET_NOTICE = 7;

        /**
         * 获取广告
         */
        public static final int TAG_GET_AD = 8;

        public static void request(int tag, int sign, HashMap<String, Object> maps, Callback l) {
            HttpQueue.newHttpQueue().cancelRequest(sign);
            url = new StringBuffer(Constant.ROOT_URI);
            switch (tag) {
                case TAG_SEND_CODE_REGISTER:
                    url.append("index/User/sendCodeRegister");
                    break;
                case TAG_REGISTER:
                    url.append("index/User/doRegister");
                    break;
                case TAG_LOGIN:
                    url.append("index/User/doLogin");
                    break;
                case TAG_SEND_CODE_NEW_PASSWORD:
                    url.append("index/User/sendCodeRepass");
                    break;
                case TAG_NEW_PASSWORD:
                    url.append("index/User/setNewPassword");
                    break;
                case TAG_USER_AGREEMENT:
                    url.append("index/Get/userAgreement");
                    break;
                case TAG_GET_VERSION:
                    url.append("index/Get/version");
                    break;
                case TAG_GET_NOTICE:
                    url.append("index/Get/notice");
                    break;
                case TAG_GET_AD:
                    url.append("index/Get/appStartAdvert");
                    break;
            }
            startRequest(sign, maps, l, url.toString());
        }
    }


    public static class home {
        /**
         * 主页列表
         */
        public final static int TAG_HOME_LIST = 0;
        /**
         * 获取类型列表
         */
        public final static int TAG_TYPE_LIST = 1;
        /**
         * 获取指定类型数据
         */
        public final static int TAG_TYPE_DATA = 2;
        /**
         * 搜索
         */
        public final static int TAG_SEARCH_VIDEO = 3;
        /**
         * 获取视频信息
         */
        public static final int TAG_VIDEO_INFO = 4;
        /**
         * 添加到收藏(短视频)
         */
        public static final int TAG_ADD_COLLECTION = 5;
        /**
         * 获取视频评论列表
         */
        public static final int TAG_VIDEO_COMMENT_LIST = 6;
        /**
         * 发布评论
         */
        public static final int TAG_SEND_COMMENT = 7;
        /**
         * 点赞
         */
        public static final int TAG_FABULOUS = 8;
        /**
         * 视频推荐列表
         */
        public static final int TAG_VIDEO_RECOMMEND_LIST = 9;
        /**
         * 添加到收藏(电影)
         */
        public static final int TAG_ADD_COLLECTION_FILM = 10;
        /**
         * 获取用户剩余钻石免费次数
         */
        public static final int TAG_USER_BALANCE = 11;
        /**
         * 购买视频
         */
        public static final int TAG_PAY_WATCH = 12;
        /**
         * 获取指定视频的演员列表
         */
        public static final int PERFORMER_LIST = 13;
        /**
         * 刷一刷 视频列表
         */
        public static final int TAG_VIDEO_GROUP_LIST = 14;
        /**
         * 点赞 刷一刷 视频列表
         */
        public static final int TAG_VIDEO_GROUP_FABULOUS = 15;

        ////////////////////////小说////////////////////////////
        /**
         * 幻灯片
         */
        public static final int TAG_NOVEL_BANNER = 16;
        /**
         * 小说列表
         */
        public static final int TAG_NOVEL_LIST = 17;
        /**
         * 小说广告
         */
        public static final int TAG_NOVEL_ADVERT = 18;
        /**
         * 小说列表点赞
         */
        public static final int TAG_NOVEL_LOVE = 19;
        /**
         * 小说详情
         */
        public static final int TAG_NOVEL_INFO = 20;
        /**
         * 小说评论列表
         */
        public static final int TAG_NOVEL_COMMENT = 21;
        /**
         * 小说评论点赞
         */
        public static final int TAG_NOVEL_COMMENT_LOVE = 22;
        /**
         * 小说发送评论
         */
        public static final int TAG_NOVEL_SEND_COMMENT = 23;
        ////////////////////////图片////////////////////////////
        /**
         * 图片幻灯片
         */
        public static final int TAG_IMGE_BANNER = 24;
        /**
         * 图片列表
         */
        public static final int TAG_IMGE_LIST = 25;
        /**
         * 图片广告
         */
        public static final int TAG_IMGE_ADVERT = 26;
        /**
         * 图片列表点赞
         */
        public static final int TAG_IMGE_LOVE = 27;
        /**
         * 图片详情
         */
        public static final int TAG_IMGE_INFO = 28;
        /**
         * 图片评论列表
         */
        public static final int TAG_IMGE_COMMENT = 29;
        /**
         * 图片评论点赞
         */
        public static final int TAG_IMGE_COMMENT_LOVE = 30;
        /**
         * 图片发送评论
         */
        public static final int TAG_IMGE_SEND_COMMENT = 31;
        ////////////////////////音频////////////////////////////
        /**
         * 音频幻灯片
         */
        public static final int TAG_MUSIC_BANNER = 32;
        /**
         * 音频列表
         */
        public static final int TAG_MUSIC_LIST = 33;
        /**
         * 音频广告
         */
        public static final int TAG_MUSIC_ADVERT = 34;
        /**
         * 音频列表点赞
         */
        public static final int TAG_MUSIC_LOVE = 35;
        /**
         * 音频详情
         */
        public static final int TAG_MUSIC_INFO = 36;
        /**
         * 音频评论列表
         */
        public static final int TAG_MUSIC_COMMENT = 37;
        /**
         * 音频评论点赞
         */
        public static final int TAG_MUSIC_COMMENT_LOVE = 38;
        /**
         * 音频发送评论
         */
        public static final int TAG_MUSIC_SEND_COMMENT = 39;
        /////////////////////////////////////////////////////////

        /**
         * 滑一滑视频播放后添加观看次数，观看历史
         */
        public static final int TAG_VIDEO_SEE_OPREATION = 40;
        /**
         * 小说分类
         */
        public static final int TAG_NOVEL_TYPE = 41;
        public static void request(int tag, int sign, HashMap<String, Object> maps, Callback l) {
            HttpQueue.newHttpQueue().cancelRequest(sign);
            url = new StringBuffer(Constant.ROOT_URI);

            switch (tag) {
                case TAG_HOME_LIST:
                    url.append("index/Get/indexData");
                    break;
                case TAG_TYPE_LIST:
                    url.append("index/Get/cateList");
                    break;
                case TAG_TYPE_DATA:
                    url.append("index/Get/cateVideo");
                    break;
                case TAG_SEARCH_VIDEO:
                    url.append("index/Get/search");
                    break;
                case TAG_VIDEO_INFO:
                    url.append("index/Get/videoInfo");
                    break;
                case TAG_ADD_COLLECTION:
                    url.append("index/User/doCollection");
                    break;
                case TAG_VIDEO_COMMENT_LIST:
                    url.append("index/Get/commentList");
                    break;
                case TAG_SEND_COMMENT:
                    url.append("index/User/publishComment");
                    break;
                case TAG_FABULOUS:
                    url.append("index/User/loveComment");
                    break;
                case TAG_VIDEO_RECOMMEND_LIST:
                    url.append("index/Get/recommendList");
                    break;
                case TAG_ADD_COLLECTION_FILM:
                    url.append("index/Film/doCollection");
                    break;
                case TAG_USER_BALANCE:
                    url.append("index/User/userBalance");
                    break;
                case TAG_PAY_WATCH:
                    url.append("index/User/payWatch");
                    break;
                case PERFORMER_LIST:
                    url.append("index/Performer/performerByVideo");
                    break;
                case TAG_VIDEO_GROUP_LIST:
                    url.append("index/Get/videoList");
                    break;
                case TAG_VIDEO_GROUP_FABULOUS:
                    url.append("index/User/loveVideo");
                    break;
                case TAG_VIDEO_SEE_OPREATION:
                    url.append("index/Get/videoSeeOpreation");
                    break;
                case TAG_NOVEL_BANNER:
                    url.append("index/Novel/getSlide");
                    break;
                case TAG_NOVEL_LIST:
                    url.append("index/Novel/lists");
                    break;
                case TAG_NOVEL_ADVERT:
                    url.append("index/Novel/getAdvert");
                    break;
                case TAG_NOVEL_LOVE:
                    url.append("index/Novel/loveNovel");
                    break;
                case TAG_NOVEL_INFO:
                    url.append("index/Novel/NovelInfo");
                    break;
                case TAG_NOVEL_COMMENT:
                    url.append("index/Novel/commentList");
                    break;
                case TAG_NOVEL_COMMENT_LOVE:
                    url.append("index/Novel/loveComment");
                    break;
                case TAG_NOVEL_SEND_COMMENT:
                    url.append("index/Novel/commentNovel");
                    break;
                case TAG_NOVEL_TYPE:
                    url.append("index/Novel/catelist");
                    break;

                case TAG_IMGE_BANNER:
                    url.append("index/Picture/getSlide");
                    break;
                case TAG_IMGE_LIST:
                    url.append("index/Picture/lists");
                    break;
                case TAG_IMGE_ADVERT:
                    url.append("index/Picture/getAdvert");
                    break;
                case TAG_IMGE_LOVE:
                    url.append("index/Picture/lovePicture");
                    break;
                case TAG_IMGE_INFO:
                    url.append("index/Picture/PictureInfo");
                    break;
                case TAG_IMGE_COMMENT:
                    url.append("index/Picture/commentList");
                    break;
                case TAG_IMGE_COMMENT_LOVE:
                    url.append("index/Picture/loveComment");
                    break;
                case TAG_IMGE_SEND_COMMENT:
                    url.append("index/Picture/commentPicture");
                    break;

                case TAG_MUSIC_BANNER:
                    url.append("index/Music/getSlide");
                    break;
                case TAG_MUSIC_LIST:
                    url.append("index/Music/lists");
                    break;
                case TAG_MUSIC_ADVERT:
                    url.append("index/Music/getAdvert");
                    break;
                case TAG_MUSIC_LOVE:
                    url.append("index/Music/loveMusic");
                    break;
                case TAG_MUSIC_INFO:
                    url.append("index/Music/musicInfo");
                    break;
                case TAG_MUSIC_COMMENT:
                    url.append("index/Music/commentList");
                    break;
                case TAG_MUSIC_COMMENT_LOVE:
                    url.append("index/Music/loveComment");
                    break;
                case TAG_MUSIC_SEND_COMMENT:
                    url.append("index/Music/commentMusic");
                    break;
            }
            startRequest(sign, maps, l, url.toString());
        }
    }

    public static class video {
        /**
         * 电影分类
         */
        public final static int TAG_VIDEO_TYPE_LIST = 0;
        /**
         * banner
         */
        public static final int TAG_VIDEO_BANNER = 1;
        public static final int TAG_VIDEO_TYPE_DATA_LIST = 2;


        public static void request(int tag, int sign, HashMap<String, Object> maps, Callback l) {
            HttpQueue.newHttpQueue().cancelRequest(sign);
            String url;
            switch (tag) {
                case TAG_VIDEO_TYPE_LIST:
                    url = Constant.ROOT_URI + "index/Get/filmCateList";
                    startRequest(sign, maps, l, url);
                    break;
                case TAG_VIDEO_BANNER:
                    url = Constant.ROOT_URI + "index/get/getFilmSlide";
                    startRequest(sign, maps, l, url);
                    break;
                case TAG_VIDEO_TYPE_DATA_LIST:
                    url = Constant.ROOT_URI + "index/Get/filmCateVideo";
                    startRequest(sign, maps, l, url);
                    break;
            }
        }
    }

    public static class type {

        /**
         * 电影分类
         */
        public final static int TAG_VIDEO_TYPE_LIST = 0;
        /**
         * 演员列表(全部)
         */
        public final static int TAG_TYPE_PERFORMER_LIS0T_ALL = 1;
        /**
         * 演员列表(人气最高)
         */
        public final static int TAG_TYPE_PERFORMER_LIST_HIGHEST = 2;
        /**
         * 演员列表(片量最多)
         */
        public final static int TAG_TYPE_PERFORMER_LIST_MANY = 3;
        /**
         * 演员详情
         */
        public final static int TAG_TYPE_PERFORMER_INFO = 4;
        /**
         * 根据演员获取电影
         */
        public static final int TAG_TYPE_PERFORMER_VIDEO = 5;
        /**
         * 收藏演员
         */
        public static final int TAG_PERFORMER_COLLECTION = 6;

        public static void request(int tag, int sign, HashMap<String, Object> maps, Callback l) {
            HttpQueue.newHttpQueue().cancelRequest(sign);
            String url;
            switch (tag) {
                case TAG_VIDEO_TYPE_LIST:
                    url = Constant.ROOT_URI + "index/Get/filmCateList";
                    startRequest(sign, maps, l, url);
                    break;
                case TAG_TYPE_PERFORMER_LIS0T_ALL:
                    url = Constant.ROOT_URI + "index/Performer/performerList";
                    startRequest(sign, maps, l, url);
                    break;
                case TAG_TYPE_PERFORMER_LIST_HIGHEST:
                    url = Constant.ROOT_URI + "index/Performer/performerPopularityList";
                    startRequest(sign, maps, l, url);
                    break;
                case TAG_TYPE_PERFORMER_LIST_MANY:
                    url = Constant.ROOT_URI + "index/Performer/performerListByFilmNum";
                    startRequest(sign, maps, l, url);
                    break;
                case TAG_TYPE_PERFORMER_INFO:
                    url = Constant.ROOT_URI + "index/Performer/performerInfo";
                    startRequest(sign, maps, l, url);
                    break;
                case TAG_TYPE_PERFORMER_VIDEO:
                    url = Constant.ROOT_URI + "index/Performer/videoListByPerformer";
                    startRequest(sign, maps, l, url);
                    break;
                case TAG_PERFORMER_COLLECTION:
                    url = Constant.ROOT_URI + "index/Performer/doCollection";
                    startRequest(sign, maps, l, url);
                    break;
            }
        }
    }

    public static class my {
        /**
         * 用户信息
         */
        public final static int TAG_USER_INFO = 0;
        /**
         * 退出登录
         */
        public final static int TAG_LOGIN_OUT = 1;
        /**
         * 修改用户信息
         */
        public final static int TAG_MODIFY_USER_INFO = 2;
        /**
         * 上传图片
         */
        public final static int TAG_ADD_IMG = 3;
        /**
         * 充值金额比例列表
         */
        public final static int TAG_RECHARGE_LIST = 4;
        /**
         * 收入列表
         */
        public final static int TAG_INCOME_LIST = 5;
        /**
         * 收藏列表
         */
        public static final int TAG_COLLECT_LIST = 6;
        /**
         * 观看历史列表
         */
        public static final int TAG_HISTORY_LIST = 7;
        /**
         * 创建订单
         */
        public static final int TAG_CREATE_ORDER = 8;
        /**
         * 发送验证码 修改手机号码
         */
        public static final int TAG_SEND_CODE_NEW_MOBILE = 9;
        /**
         * 修改手机号码
         */
        public static final int TAG_MODIFY_MOBILE = 10;
        /**
         * 收藏列表(根据演员获取收藏类型)
         */
        public static final int TAG_COLLECT_LIST_3 = 11;
        /**
         * 我的推广详情
         */
        public static final int TAG_SPREAD_SHARE = 12;

        /**
         * 我的推广信息
         */
        public static final int TAG_SPREAD_INFO = 13;

        public static void request(int tag, int sign, HashMap<String, Object> maps, Callback l) {
            HttpQueue.newHttpQueue().cancelRequest(sign);
            String url;
            switch (tag) {
                case TAG_USER_INFO:
                    url = Constant.ROOT_URI + "index/User/ucenter";
                    startRequest(sign, maps, l, url);
                    break;
                case TAG_LOGIN_OUT:
                    url = Constant.ROOT_URI + "index/User/loginout";
                    startRequest(sign, maps, l, url);
                    break;
                case TAG_MODIFY_USER_INFO:
                    url = Constant.ROOT_URI + "index/User/saveUserData";
                    startRequest(sign, maps, l, url);
                    break;
                case TAG_ADD_IMG:
                    requestAddImg(sign, maps, l);
                    break;
                case TAG_RECHARGE_LIST:
                    url = Constant.ROOT_URI + "index/Get/rechargeList";
                    startRequest(sign, maps, l, url);
                    break;
                case TAG_INCOME_LIST:
                    url = Constant.ROOT_URI + "index/User/income";
                    startRequest(sign, maps, l, url);
                    break;
                case TAG_COLLECT_LIST:
                    url = Constant.ROOT_URI + "index/Get/collectionList";
                    startRequest(sign, maps, l, url);
                    break;
                case TAG_HISTORY_LIST:
                    url = Constant.ROOT_URI + "index/Get/watchList";
                    startRequest(sign, maps, l, url);
                    break;
                case TAG_CREATE_ORDER:
                    url = Constant.ROOT_URI + "index/User/createOrder";
                    startRequest(sign, maps, l, url);
                    break;
                case TAG_SEND_CODE_NEW_MOBILE:
                    url = Constant.ROOT_URI + "index/User/sendCodeSetMobile";
                    startRequest(sign, maps, l, url);
                    break;
                case TAG_MODIFY_MOBILE:
                    url = Constant.ROOT_URI + "index/User/setNewMobile";
                    startRequest(sign, maps, l, url);
                    break;
                case TAG_COLLECT_LIST_3:
                    url = Constant.ROOT_URI + "index/Get/collectionListByType";
                    startRequest(sign, maps, l, url);
                    break;
                case TAG_SPREAD_SHARE:
                    url = Constant.ROOT_URI + "index/User/shareInfo";
                    startRequest(sign, maps, l, url);
                    break;
                case TAG_SPREAD_INFO:
                    url = Constant.ROOT_URI + "index/User/spreadInfo";
                    startRequest(sign, maps, l, url);
                    break;
            }
        }

        private static void requestAddImg(int sign, HashMap<String, Object> maps, Callback l) {
            String url = Constant.ROOT_URI + "index/User/uploadImage";
            Request request = HttpRequest.createFileRequest(url, maps);
            HttpQueue.newHttpQueue().addRequest(sign, addCoomonHeader(request), l);
        }
    }

    public static class circle {
        /**
         * 发布信息到圈子
         */
        public final static int TAG_CIRCLE_SAVECIRCLE = 0;
        /**
         * 圈子banner图
         */
        public final static int TAG_CIRCLE_BANNER = 1;
        /**
         * 圈子列表
         */
        public final static int TAG_CIRCLE_LIST = 2;
        /**
         * 给发布的圈子消息点赞
         */
        public final static int TAG_CIRCLE_LOVE_CIRCLE = 3;

        /**
         * 圈子信息
         */
        public final static int TAG_CIRCLE_INFO = 4;

        /**
         * 圈子评论列表
         */
        public final static int TAG_CIRCLE_COMMENT_LIST = 5;
        /**
         * 评论点赞
         */
        public final static int TAG_CIRCLE_COMMENT_Z = 6;
        /**
         * 发送评论
         */
        public final static int TAG_CIRCLE_SEND_COMMENT = 7;
        /**
         * 圈子我的
         */
        public final static int TAG_CIRCLE_MY_LIST = 8;
        /**
         * 圈子我的()删除
         */
        public final static int TAG_CIRCLE_MY_DELETE = 9;

        /**
         * 地区列表
         */
        public static final int TAG_CIRCLE_REGIONS = 10;

        /**
         * 广告 (圈子列表)
         */
        public static final int TAG_CIRCLE_LIST_AD = 11;


        public static void request(int tag, int sign, HashMap<String, Object> maps, Callback l) {
            HttpQueue.newHttpQueue().cancelRequest(sign);
            String url;
            switch (tag) {
                case TAG_CIRCLE_SAVECIRCLE:
                    url = Constant.ROOT_URI + "index/Circle/saveCircle";
                    startRequest(sign, maps, l, url);
                    break;
                case TAG_CIRCLE_LIST:
                    url = Constant.ROOT_URI + "index/Circle/lists";
                    startRequest(sign, maps, l, url);
                    break;
                case TAG_CIRCLE_BANNER:
                    url = Constant.ROOT_URI + "index/Circle/getSlide";
                    startRequest(sign, maps, l, url);
                    break;
                case TAG_CIRCLE_LOVE_CIRCLE:
                    url = Constant.ROOT_URI + "index/Circle/loveCircle";
                    startRequest(sign, maps, l, url);
                    break;
                case TAG_CIRCLE_INFO:
                    url = Constant.ROOT_URI + "index/Circle/circleInfo";
                    startRequest(sign, maps, l, url);
                    break;
                case TAG_CIRCLE_COMMENT_LIST:
                    url = Constant.ROOT_URI + "index/Circle/commentList";
                    startRequest(sign, maps, l, url);
                    break;
                case TAG_CIRCLE_COMMENT_Z:
                    url = Constant.ROOT_URI + "index/Circle/loveComment";
                    startRequest(sign, maps, l, url);
                    break;
                case TAG_CIRCLE_SEND_COMMENT:
                    url = Constant.ROOT_URI + "index/Circle/commentCircle";
                    startRequest(sign, maps, l, url);
                    break;
                case TAG_CIRCLE_MY_LIST:
                    url = Constant.ROOT_URI + "index/Circle/userLists";
                    startRequest(sign, maps, l, url);
                    break;
                case TAG_CIRCLE_MY_DELETE:
                    url = Constant.ROOT_URI + "index/Circle/deleteCircle";
                    startRequest(sign, maps, l, url);
                    break;
                case TAG_CIRCLE_REGIONS:
                    url = Constant.ROOT_URI + "index/Circle/regions";
                    startRequest(sign, maps, l, url);
                    break;
                case TAG_CIRCLE_LIST_AD:
                    url = Constant.ROOT_URI + "index/Circle/getAdvertByRand";
                    startRequest(sign, maps, l, url);
                    break;
            }
        }
    }

    private static void startRequest(int sign, HashMap<String, Object> maps, Callback l, String url) {
        if (UserInfoManage.getInstance().checkLoginState() == UserInfoManage.STATE_LOGGED_IN) {
            maps.put("token", UserInfoManage.getInstance().getUserInfo().getToken());
        } else {
            maps.put("token", "");
        }
        Request request = HttpRequest.createFormRequest(url, HttpRequest.POST, maps);
        HttpQueue.newHttpQueue().addRequest(sign, addCoomonHeader(request), l);
    }

    private static Uri.Builder formatData(String url, HashMap<String, Object> maps) {
        Uri.Builder builder = Uri.parse(url).buildUpon();
        for (Map.Entry<String, Object> entry : maps.entrySet()) {
            builder.appendQueryParameter(entry.getKey(), String.valueOf(entry.getValue()));
        }
        return builder;
    }

}
