package com.thinksoft.banana.ui.fragment.circle;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.gson.JsonElement;
import com.orhanobut.logger.Logger;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import com.thinksoft.banana.R;
import com.thinksoft.banana.app.Constant;
import com.thinksoft.banana.entity.bean.circle.CircleBean;
import com.thinksoft.banana.entity.bean.circle.CircleListBean;
import com.thinksoft.banana.entity.bean.circle.HttpImgBean;
import com.thinksoft.banana.entity.bean.circle.VideoBean;
import com.thinksoft.banana.entity.bean.home.BannersBean;
import com.thinksoft.banana.entity.bean.home.CatesBean;
import com.thinksoft.banana.entity.item.CircleItem;
import com.thinksoft.banana.mvp.contract.CommonContract;
import com.thinksoft.banana.mvp.model.CircleModel;
import com.thinksoft.banana.mvp.model.TypeModel;
import com.thinksoft.banana.mvp.presenter.CommonPresenter;
import com.thinksoft.banana.net.api.ApiRequestTask;
import com.thinksoft.banana.ui.activity.circle.CircleDetailsActivity;
import com.thinksoft.banana.ui.activity.circle.CircleVideoActivity;
import com.thinksoft.banana.ui.activity.circle.CirleImgActivity;
import com.thinksoft.banana.ui.activity.my.SpreadLinkActivity;
import com.thinksoft.banana.ui.adapter.CircleListAdapter;
import com.thinksoft.banana.ui.adapter.CircleMyAdapter;
import com.thinksoft.banana.ui.view.window.ShareWindow;
import com.txf.other_tencentlibrary.ShareHelper;
import com.txf.other_tencentlibrary.wx.ShareContentQQ;
import com.txf.other_tencentlibrary.wx.ShareContentWebpage;
import com.txf.other_toolslibrary.tools.JsonTools;
import com.txf.other_toolslibrary.utils.ToastUtils;
import com.txf.ui_mvplibrary.interfaces.OnAppListener;
import com.txf.ui_mvplibrary.ui.adapter.BaseRecyclerAdapter;
import com.txf.ui_mvplibrary.ui.fragment.BaseMvpListLjzFragment;
import com.txf.ui_mvplibrary.utils.BundleUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author txf
 * @create 2019/2/18 0018
 * @圈子我的
 */
public class CircleMyFragemt
        extends BaseMvpListLjzFragment<CircleItem, CommonContract.View, CommonContract.Presenter>
        implements CommonContract.View, OnAppListener.OnAdapterListener, OnAppListener.OnWindowListener {

    CatesBean mCatesBean;
    CircleBean mCircleBean;

    @Override
    protected BaseRecyclerAdapter buildAdapter() {
        return new CircleMyAdapter(getContext(), this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContract(this, new CommonPresenter(getContext(), this, new CircleModel()));
        mCatesBean = BundleUtils.getSerializable(getArguments());
    }

    @Override
    protected void request(int pageIndex, int pageSize) {
        HashMap<String, Object> maps = new HashMap<>();
        maps.put("page", pageIndex);
        getPresenter().getData(ApiRequestTask.circle.TAG_CIRCLE_MY_LIST, maps);
    }

    private List<CircleItem> newItem(CircleListBean bean) {
        List<CircleItem> items = new ArrayList<>();
        if (bean != null && bean.getCircleList() != null && bean.getCircleList().size() > 0) {
            for (CircleBean circleBean : bean.getCircleList())
                items.add(new CircleItem(circleBean, Constant.TYPE_ITEM_1));
        }
        return items;
    }

    @Override
    public void onInteractionAdapter(int action, Bundle bundle) {
        CircleItem item;
        switch (action) {
            case Constant.ACTION_CIRCLE_DETAILS:
                item = BundleUtils.getBaseItem(bundle);
                CircleBean bean = (CircleBean) item.getData();
                //详情
                startActivity(CircleDetailsActivity.getIntent(getContext(), bean));
                break;
            case Constant.ACTION_CIRCLE_SHARE:
                item = BundleUtils.getBaseItem(bundle);
                //分享
                startActivity(new Intent(getContext(), SpreadLinkActivity.class));
                break;
            case Constant.ACTION_CIRCLE_Z:
                item = BundleUtils.getBaseItem(bundle);
                mCircleBean = (CircleBean) item.getData();
                //点赞
                HashMap<String, Object> maps = new HashMap<>();
                maps.put("id", mCircleBean.getId());
                getPresenter().getData(ApiRequestTask.circle.TAG_CIRCLE_LOVE_CIRCLE, maps);
                break;
            case Constant.ACTION_CIRCLE_IMG:
                //图片
//                HttpImgBean imgBean = (HttpImgBean) bundle.getSerializable("data1");
//                ArrayList<CircleItem> datas = (ArrayList<CircleItem>) bundle.getSerializable("data2");
//                startActivity(CirleImgActivity.getIntent(getContext(), imgBean, datas));
                break;
            case Constant.ACTION_CIRCLE_VIDEO:
//                VideoBean videoBean = BundleUtils.getSerializable(bundle);
//                //视频
//                startActivity(CircleVideoActivity.getIntent(getContext(), videoBean));
                break;
            case Constant.ACTION_CIRCLE_DELETE:
                //删除圈子信息
                item = BundleUtils.getBaseItem(bundle);
                CircleBean deleteCircleBean = (CircleBean) item.getData();
                new Builder()
                        .setExt(BundleUtils.putSerializable(deleteCircleBean))
                        .setContent(getString(R.string.确认删除此条信息))
                        .setButton2(getString(R.string.取消))
                        .setButton3(getString(R.string.确认))
                        .show();
                break;
        }
    }

    @Override
    protected void onInteractionDialog(DialogInterface dialog, int which, int with, Bundle ext) {
        super.onInteractionDialog(dialog, which, with, ext);
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                CircleBean bean = BundleUtils.getSerializable(ext);
                HashMap<String, Object> maps = new HashMap<>();
                maps.put("id", bean.getId());
                getPresenter().getData(ApiRequestTask.circle.TAG_CIRCLE_MY_DELETE, maps);
                break;
        }
    }

    @Override
    public void onInteractionWindow(int action, int tag, Bundle ext) {
        startShare(action);
    }

    @Override
    public void httpOnSuccess(int sign, JsonElement data, String message) {
        switch (sign) {
            case ApiRequestTask.circle.TAG_CIRCLE_MY_LIST:
                CircleListBean bean = JsonTools.fromJson(data, CircleListBean.class);
                refreshData(newItem(bean));
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
            case ApiRequestTask.circle.TAG_CIRCLE_MY_DELETE:
                pageIndex = 1;
                startRequest(pageIndex, pageSize);
                break;
        }
    }

    @Override
    public void httpOnError(int tag, int error, String message) {


    }


    private void startShare(int action) {
        switch (action) {
            case Constant.ACTION_SHARE_QQ:
                ShareHelper.getInstance().shareQQ(getActivity(), new ShareContentQQ(
                        "我在测试",
                        "测试",
                        "http://connect.qq.com/",
                        "http://img3.cache.netease.com/photo/0005/2013-03-07/8PBKS8G400BV0005.jpg"
                ), qqShareListener);
                break;
            case Constant.ACTION_SHARE_QQ_PY:
                ShareHelper.getInstance().shareQzone(getActivity(), new ShareContentQQ(
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
