package com.thinksoft.banana.ui.activity.circle;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.JsonElement;
import com.orhanobut.logger.Logger;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import com.thinksoft.banana.R;
import com.thinksoft.banana.app.Constant;
import com.thinksoft.banana.app.manage.UserInfoManage;
import com.thinksoft.banana.entity.bean.circle.CircleBean;
import com.thinksoft.banana.entity.bean.circle.CircleCommentBean;
import com.thinksoft.banana.entity.bean.circle.CircleCommentListBean;
import com.thinksoft.banana.entity.bean.circle.CircleInfoBean;
import com.thinksoft.banana.entity.bean.circle.HttpImgBean;
import com.thinksoft.banana.entity.bean.circle.VideoBean;
import com.thinksoft.banana.entity.item.CircleItem;
import com.thinksoft.banana.mvp.contract.CommonContract;
import com.thinksoft.banana.mvp.model.CircleModel;
import com.thinksoft.banana.mvp.presenter.CommonPresenter;
import com.thinksoft.banana.net.api.ApiRequestTask;
import com.thinksoft.banana.ui.activity.my.SpreadLinkActivity;
import com.thinksoft.banana.ui.activity.start.LoginActivity;
import com.thinksoft.banana.ui.adapter.CircleDetailsAdapter;
import com.thinksoft.banana.ui.view.navigation.CircleNavigationBar;
import com.thinksoft.banana.ui.view.window.ShareWindow;
import com.txf.other_tencentlibrary.ShareHelper;
import com.txf.other_tencentlibrary.wx.ShareContentQQ;
import com.txf.other_tencentlibrary.wx.ShareContentWebpage;
import com.txf.other_toolslibrary.manager.AppManager;
import com.txf.other_toolslibrary.tools.JsonTools;
import com.txf.other_toolslibrary.utils.ToastUtils;
import com.txf.ui_mvplibrary.interfaces.INavigationBar;
import com.txf.ui_mvplibrary.interfaces.ITitleBar;
import com.txf.ui_mvplibrary.interfaces.OnAppListener;
import com.txf.ui_mvplibrary.ui.activity.BaseMvpActivity;
import com.txf.ui_mvplibrary.ui.activity.BaseMvpListActivity;
import com.txf.ui_mvplibrary.ui.adapter.BaseRecyclerAdapter;
import com.txf.ui_mvplibrary.ui.view.deft.DefaultTitleBar;
import com.txf.ui_mvplibrary.utils.BundleUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author txf
 * @create 2019/3/21 0021
 * @圈子详情页
 */
public class CircleDetailsActivity
        extends BaseMvpListActivity<CircleItem, CommonContract.View, CommonContract.Presenter>
        implements CommonContract.View, OnAppListener.OnAdapterListener, OnAppListener.OnWindowListener {
    CircleNavigationBar mNavigationBar;

    int mCircleId;
    CircleInfoBean mCircleInfoBean;

    CircleBean mCircleBean;
    CircleCommentBean mCommentBean;

    public static Intent getIntent(Context context, CircleBean bean) {
        Intent i = new Intent(context, CircleDetailsActivity.class);
        i.putExtra("data", bean.getId());
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContract(this, new CommonPresenter(getContext(), new CircleModel()));
        initData();
    }

    private void initData() {
        mCircleId = getIntent().getIntExtra("data", -1);
        mITitleBar.setTitleText(getText(R.string.详情));
    }

    @Override
    protected BaseRecyclerAdapter<CircleItem> buildAdapter() {
        return new CircleDetailsAdapter(this, this);
    }

    @Override
    protected void request(int pageIndex, int pageSize) {
        if (pageIndex == 1) {
            HashMap<String, Object> maps = new HashMap<>();
            maps.put("id", mCircleId);
            getPresenter().getData(ApiRequestTask.circle.TAG_CIRCLE_INFO, maps);
        } else {
            requestCommentList(pageIndex);
        }
    }

    private void requestCommentList(int pageIndex) {
        HashMap<String, Object> maps = new HashMap<>();
        maps.put("id", mCircleId);
        maps.put("page", pageIndex);
        getPresenter().getData(ApiRequestTask.circle.TAG_CIRCLE_COMMENT_LIST, maps);
    }

    private List<CircleItem> newItem(CircleCommentListBean bean) {
        List<CircleItem> items = new ArrayList<>();
        if (pageIndex == 1) {
            if (mCircleInfoBean != null && mCircleInfoBean.getCircleInfo() != null) {
                items.add(new CircleItem(mCircleInfoBean.getCircleInfo(), Constant.TYPE_ITEM_1));
            }
        }
        if (bean == null || bean.getCommentList() == null || bean.getCommentList().size() <= 0)
            return items;

        for (CircleCommentBean commentBean : bean.getCommentList()) {
            items.add(new CircleItem(commentBean, Constant.TYPE_ITEM_2));
        }
        return items;
    }

    @Override
    public void onInteractionAdapter(int action, Bundle bundle) {
        CircleItem item;
        HashMap<String, Object> maps;
        switch (action) {
            case Constant.ACTION_CIRCLE_DETAILS:
                break;
            case Constant.ACTION_CIRCLE_SHARE:
                item = BundleUtils.getBaseItem(bundle);
                //分享
                startActivity(new Intent(getContext(), SpreadLinkActivity.class));
                break;
            case Constant.ACTION_CIRCLE_Z:
                item = BundleUtils.getBaseItem(bundle);
                mCircleBean = (CircleBean) item.getData();
                //点赞 圈子信息
                maps = new HashMap<>();
                maps.put("id", mCircleBean.getId());
                getPresenter().getData(ApiRequestTask.circle.TAG_CIRCLE_LOVE_CIRCLE, maps);
                break;
            case Constant.ACTION_CIRCLE_IMG:
                item = BundleUtils.getBaseItem(bundle);
                //图片
                HttpImgBean imgBean = (HttpImgBean) bundle.getSerializable("data1");
                ArrayList<CircleItem> datas = (ArrayList<CircleItem>) bundle.getSerializable("data2");
                startActivity(CirleImgActivity.getIntent(getContext(), imgBean, datas));
                break;
            case Constant.ACTION_CIRCLE_VIDEO:
                VideoBean videoBean = BundleUtils.getSerializable(bundle);
                //视频
                startActivity(CircleVideoActivity.getIntent(getContext(), videoBean));
                break;
            case Constant.ACTION_CIRCLE_COMMENT_Z:
                mCommentBean = BundleUtils.getSerializable(bundle);
                //点赞 评论
                maps = new HashMap<>();
                maps.put("id", mCommentBean.getId());
                getPresenter().getData(ApiRequestTask.circle.TAG_CIRCLE_COMMENT_Z, maps);
                break;
        }
    }

    @Override
    public void onInteractionWindow(int action, int tag, Bundle ext) {
        startShare(action);
    }

    @Override
    public void onInteractionView(int action, Bundle bundle) {
        super.onInteractionView(action, bundle);
        HashMap maps;
        switch (action) {
            case Constant.ACTION_SEND_TEXT:
                String commentText = BundleUtils.getString(bundle);
                maps = new HashMap<>();
                maps.put("circle_id", mCircleId);
                maps.put("content", commentText);
                getPresenter().getData(ApiRequestTask.circle.TAG_CIRCLE_SEND_COMMENT, maps);
                break;
        }
    }

    @Override
    public void httpOnSuccess(int sign, JsonElement data, String message) {
        switch (sign) {
            case ApiRequestTask.circle.TAG_CIRCLE_INFO:
                mCircleInfoBean = JsonTools.fromJson(data, CircleInfoBean.class);

                pageIndex = 1;
                requestCommentList(pageIndex);
                break;
            case ApiRequestTask.circle.TAG_CIRCLE_COMMENT_LIST:
                CircleCommentListBean commentListBean = JsonTools.fromJson(data, CircleCommentListBean.class);
                refreshData(newItem(commentListBean));

                break;
            case ApiRequestTask.circle.TAG_CIRCLE_LOVE_CIRCLE:
                //点赞 圈子
                if (mCircleBean.getIslove() == 1) {
                    mCircleBean.setIslove(0);
                    mCircleBean.setLove(mCircleBean.getLove() - 1);
                } else {
                    mCircleBean.setIslove(1);
                    mCircleBean.setLove(mCircleBean.getLove() + 1);
                }
                mAdapter.notifyDataSetChanged();
                break;
            case ApiRequestTask.circle.TAG_CIRCLE_COMMENT_Z:
                //点赞 评论
                if (mCommentBean.getIslove() == 1) {
                    mCommentBean.setIslove(0);
                } else {
                    mCommentBean.setIslove(1);
                }
                mAdapter.notifyDataSetChanged();
                break;
            case ApiRequestTask.circle.TAG_CIRCLE_SEND_COMMENT:
                //发送评论成功
                ToastUtils.show(getString(R.string.发送成功等待管理员审核通过));
                mRefreshLayout.autoRefresh();
                mNavigationBar.sendSuccess();
                break;

        }
    }

    @Override
    public void httpOnError(int sign, int error, String message) {
        switch (sign) {
            case ApiRequestTask.circle.TAG_CIRCLE_SEND_COMMENT:
                //发送评论失败
                mNavigationBar.sendError();
                break;
        }
    }

    @Override
    protected ITitleBar buildTitleBar() {
        return new DefaultTitleBar(this);
    }

    @Override
    protected INavigationBar buildNavigationBar() {
        return mNavigationBar = new CircleNavigationBar(this);
    }

    private void startShare(int action) {
        switch (action) {
            case Constant.ACTION_SHARE_QQ:
                ShareHelper.getInstance().shareQQ(this, new ShareContentQQ(
                        "我在测试",
                        "测试",
                        "http://connect.qq.com/",
                        "http://img3.cache.netease.com/photo/0005/2013-03-07/8PBKS8G400BV0005.jpg"
                ), qqShareListener);
                break;
            case Constant.ACTION_SHARE_QQ_PY:
                ShareHelper.getInstance().shareQzone(this, new ShareContentQQ(
                        "我在测试",
                        "测试",
                        "http://connect.qq.com/",
                        "http://img3.cache.netease.com/photo/0005/2013-03-07/8PBKS8G400BV0005.jpg"
                ), qqShareListener);
                break;
            case Constant.ACTION_SHARE_WX:
                ShareHelper.getInstance().shareWX(
                        new ShareContentWebpage(
                                "我在测试",
                                "测试",
                                "http://connect.qq.com/",
                                R.mipmap.ic_launcher));
                break;
            case Constant.ACTION_SHARE_WX_PX:
                ShareHelper.getInstance().shareWXFrends(new ShareContentWebpage(
                        "我在测试",
                        "测试",
                        "http://connect.qq.com/",
                        R.mipmap.ic_launcher));
                break;
        }
    }

    IUiListener qqShareListener = new IUiListener() {
        @Override
        public void onCancel() {
            Logger.i("分享取消");
        }

        @Override
        public void onComplete(Object response) {
            Logger.i("分享成功");
        }

        @Override
        public void onError(UiError e) {
            Logger.i("分享失败: " + JsonTools.toJSON(e));
        }
    };

}
