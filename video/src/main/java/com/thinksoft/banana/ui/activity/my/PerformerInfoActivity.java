package com.thinksoft.banana.ui.activity.my;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.JsonElement;
import com.thinksoft.banana.R;
import com.thinksoft.banana.app.Constant;
import com.thinksoft.banana.app.manage.UserInfoManage;
import com.thinksoft.banana.entity.bean.home.VideosBean;
import com.thinksoft.banana.entity.bean.type.PerformerDataBean;
import com.thinksoft.banana.entity.bean.type.PerformerInfoBean;
import com.thinksoft.banana.entity.bean.type.PerformerVideoBean;
import com.thinksoft.banana.entity.item.HomeItem;
import com.thinksoft.banana.entity.item.PerformerItem;
import com.thinksoft.banana.mvp.contract.CommonContract;
import com.thinksoft.banana.mvp.model.TypeModel;
import com.thinksoft.banana.mvp.presenter.CommonPresenter;
import com.thinksoft.banana.net.api.ApiRequestTask;
import com.thinksoft.banana.ui.activity.player.PlayerActivity;
import com.thinksoft.banana.ui.activity.player.PlayerVerticalActivity;
import com.thinksoft.banana.ui.activity.start.LoginActivity;
import com.thinksoft.banana.ui.adapter.PerformerInfoAdapter;
import com.txf.other_toolslibrary.manager.AppManager;
import com.txf.other_toolslibrary.tools.JsonTools;
import com.txf.ui_mvplibrary.interfaces.ITitleBar;
import com.txf.ui_mvplibrary.interfaces.OnAppListener;
import com.txf.ui_mvplibrary.ui.activity.BaseMvpListActivity;
import com.txf.ui_mvplibrary.ui.adapter.BaseRecyclerAdapter;
import com.txf.ui_mvplibrary.ui.adapter.item_decoration.ItemDecorationCommon;
import com.txf.ui_mvplibrary.ui.view.deft.DefaultTitleBar;
import com.txf.ui_mvplibrary.utils.BundleUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author txf
 * @create 2019/3/12 0012
 * @演员信息
 */
public class PerformerInfoActivity
        extends BaseMvpListActivity<PerformerItem, CommonContract.View, CommonContract.Presenter>
        implements CommonContract.View, OnAppListener.OnAdapterListener {
    PerformerInfoBean mPerformerInfoBean;
    int mPerformerID;


    public static Intent getIntent(Context context, int id) {
        Intent i = new Intent(context, PerformerInfoActivity.class);
        i.putExtra("data", id);
        return i;
    }

    @Override
    protected BaseRecyclerAdapter<PerformerItem> buildAdapter() {
        return new PerformerInfoAdapter(this, this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPerformerID = getIntent().getIntExtra("data", -1);
        setContract(this, new CommonPresenter(this, new TypeModel()));
        mITitleBar.setTitleText(getString(R.string.演员信息));
    }

    @Override
    public void onInteractionAdapter(int action, Bundle ext) {
        switch (action) {
            case Constant.TYPE_ITEM_1:
                //收藏\取消收藏
                PerformerInfoBean mPerformerInfoBean = BundleUtils.getSerializable(ext);
                HashMap<String, Object> maps = new HashMap<>();
                maps.put("token", UserInfoManage.getInstance().getUserInfo().getToken());
                maps.put("id", mPerformerInfoBean.getPerformer().getId());
                getPresenter().getData(ApiRequestTask.type.TAG_PERFORMER_COLLECTION, maps);
                break;
            case Constant.TYPE_ITEM_2:
                PerformerItem item = BundleUtils.getBaseItem(ext);
                VideosBean mVideosBean = (VideosBean) item.getData();
                if (mVideosBean.getScreen_type() == 2) {
                    startActivity(PlayerVerticalActivity.getIntent(getContext(), mVideosBean.getId()));
                } else {
                    startActivity(PlayerActivity.getIntent(getContext(), mVideosBean.getId()));
                }
                break;
        }
    }

    @Override
    protected void request(int pageIndex, int pageSize) {
        if (pageIndex == 1) {
            //获取演员详情
            HashMap<String, Object> maps = new HashMap<>();
            maps.put("token", UserInfoManage.getInstance().getUserInfo().getToken());
            maps.put("id", mPerformerID);
            getPresenter().getData(ApiRequestTask.type.TAG_TYPE_PERFORMER_INFO, maps);
        } else {
//            根据演员获取电影
            requestVideo(pageIndex);
        }
    }

    private void requestVideo(int pageIndex) {
        HashMap<String, Object> maps = new HashMap<>();
        maps.put("id", mPerformerInfoBean.getPerformer().getId());
        maps.put("page", pageIndex);
        getPresenter().getData(ApiRequestTask.type.TAG_TYPE_PERFORMER_VIDEO, maps);
    }

    private List<PerformerItem> newItem(PerformerVideoBean videoBean) {
        List<PerformerItem> items = new ArrayList<>();
        if (pageIndex == 1) {
            if (mPerformerInfoBean == null || mPerformerInfoBean.getPerformer() == null) {
                return items;
            }
            items.add(new PerformerItem(mPerformerInfoBean, Constant.TYPE_ITEM_1));
        }

        if (videoBean == null || videoBean.getVideoList() == null || videoBean.getVideoList().size() == 0) {
            return items;
        }
        for (VideosBean videosBean : videoBean.getVideoList()) {
            items.add(new PerformerItem(videosBean, Constant.TYPE_ITEM_2));
        }
        return items;
    }

    @Override
    protected ITitleBar buildTitleBar() {
        return new DefaultTitleBar(getContext());
    }

    @Override
    protected int buildColumnCount() {
        return 2;
    }

    @Override
    protected GridLayoutManager.SpanSizeLookup buildSpanSizeLookup() {
        return new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (mAdapter.getItemViewType(position)) {
                    case Constant.TYPE_ITEM_2:
                        return 1;
                    default:
                        return 2;
                }
            }
        };
    }

    @Override
    protected RecyclerView.ItemDecoration buildItemDecoration() {
        return new ItemDecorationCommon(
                2,
                new Rect(dip2px(15), dip2px(15), dip2px(15), 0),
                dip2px(8),
                Constant.TYPE_ITEM_2
        );
    }

    @Override
    public void httpOnSuccess(int sign, JsonElement data, String message) {
        switch (sign) {
            case ApiRequestTask.type.TAG_TYPE_PERFORMER_INFO:
                //演员信息
                mPerformerInfoBean = JsonTools.fromJson(data, PerformerInfoBean.class);
                pageIndex = 1;
                requestVideo(pageIndex);
                break;
            case ApiRequestTask.type.TAG_TYPE_PERFORMER_VIDEO:
                //演员视频
                PerformerVideoBean videoBean = JsonTools.fromJson(data, PerformerVideoBean.class);
                refreshData(newItem(videoBean));
                break;
            case ApiRequestTask.type.TAG_PERFORMER_COLLECTION:
                //收藏演员
                pageIndex = 1;
                startRequest(pageIndex, pageSize);
                break;

        }
    }

    @Override
    public void httpOnError(int sign, int error, String message) {

    }

    @Override
    public void logInvalid() {
        super.logInvalid();
        UserInfoManage.getInstance().cleanUserInfo();
        new Builder()
                .setWith(1024)
                .setButton3(getString(R.string.确认))
                .setContent(getString(R.string.登录过期请重新登录))
                .setCancelable(false)
                .setDialogListener(new DialogListener(null, 0) {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == DialogInterface.BUTTON_POSITIVE) {
                            //登录过期
                            startActivity(new Intent(getContext(), LoginActivity.class));
                            AppManager.getInstance().retainAcitivity(LoginActivity.class);
                        }
                    }
                })
                .show();
    }
}
