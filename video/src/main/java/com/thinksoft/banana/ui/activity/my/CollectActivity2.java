package com.thinksoft.banana.ui.activity.my;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.JsonElement;
import com.thinksoft.banana.R;
import com.thinksoft.banana.app.Constant;
import com.thinksoft.banana.app.manage.UserInfoManage;
import com.thinksoft.banana.entity.bean.CollectBean;
import com.thinksoft.banana.entity.bean.home.VideosBean;
import com.thinksoft.banana.entity.item.HomeItem;
import com.thinksoft.banana.mvp.contract.CommonContract;
import com.thinksoft.banana.mvp.model.MyModel;
import com.thinksoft.banana.mvp.presenter.CommonPresenter;
import com.thinksoft.banana.net.api.ApiRequestTask;
import com.thinksoft.banana.ui.activity.player.PlayerActivity;
import com.thinksoft.banana.ui.activity.player.PlayerVerticalActivity;
import com.thinksoft.banana.ui.activity.start.LoginActivity;
import com.txf.other_toolslibrary.manager.AppManager;
import com.txf.other_toolslibrary.tools.JsonTools;
import com.txf.ui_mvplibrary.bean.BaseItem;
import com.txf.ui_mvplibrary.interfaces.ITitleBar;
import com.txf.ui_mvplibrary.interfaces.OnAppListener;
import com.txf.ui_mvplibrary.ui.activity.BaseMvpListActivity;
import com.txf.ui_mvplibrary.ui.adapter.BaseCompleteRecyclerAdapter;
import com.txf.ui_mvplibrary.ui.adapter.BaseRecyclerAdapter;
import com.txf.ui_mvplibrary.ui.adapter.hoder.BaseViewHoder;
import com.txf.ui_mvplibrary.ui.adapter.item_decoration.ItemDecorationCommon;
import com.txf.ui_mvplibrary.ui.view.deft.DefaultTitleBar;
import com.txf.ui_mvplibrary.utils.BundleUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author txf
 * @create 2019/2/19
 * 我的收藏  1.0, 2.0版本 收藏页面
 */
public class CollectActivity2
        extends BaseMvpListActivity<BaseItem, CommonContract.View, CommonContract.Presenter>
        implements CommonContract.View, OnAppListener.OnAdapterListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContract(this, new CommonPresenter(this, new MyModel()));
        mITitleBar.setTitleText(getString(R.string.我的收藏));
    }

    @Override
    protected BaseRecyclerAdapter<BaseItem> buildAdapter() {
        return new BaseCompleteRecyclerAdapter<BaseItem>(this, this) {
            @Override
            protected void setItemLayout() {
                super.setItemLayout();
                addItemLayout(Constant.TYPE_ITEM_1, R.layout.item_collect_content);
            }

            @Override
            public int getItemViewType(int position) {
                return getDatas().get(position).getItemType();
            }

            @Override
            protected void onBindBaseViewHoder(final BaseViewHoder holder, int position, final BaseItem item) {
                VideosBean bean = (VideosBean) item.getData();
                SimpleDraweeView simpleDraweeView = holder.getViewById(R.id.SimpleDraweeView);
                simpleDraweeView.setImageURI(bean.getImage());
                if (bean.getIs_free() != 0) {
                    holder.setText(R.id.tagTextView, getString(R.string.收费));
                } else {
                    holder.setText(R.id.tagTextView, getString(R.string.免费));
                }
                holder.setText(R.id.contentTV1, bean.getTitle());
                holder.setText(R.id.contentTV2, bean.getHis());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getListener().onInteractionAdapter(Constant.TYPE_ITEM_1, BundleUtils.putBaseItem(item));
                    }
                });
            }
        };
    }

    @Override
    protected void request(int pageIndex, int pageSize) {
        HashMap<String, Object> maps = new HashMap<>();
        maps.put("token", UserInfoManage.getInstance().getUserInfo().getToken());
        maps.put("page", pageIndex);
        getPresenter().getData(ApiRequestTask.my.TAG_COLLECT_LIST, maps);
    }

    private List<BaseItem> newItem(CollectBean bean) {
        List<BaseItem> items = new ArrayList<>();
        if (bean == null || bean.getVideoList() == null || bean.getVideoList().size() == 0)
            return items;
        for (VideosBean videosBean : bean.getVideoList()) {
            items.add(new BaseItem(videosBean, Constant.TYPE_ITEM_1));
        }
        return items;
    }

    @Override
    public void onInteractionAdapter(int action, Bundle bundle) {
        BaseItem item;
        switch (action) {
            case Constant.TYPE_ITEM_1:
                item = BundleUtils.getBaseItem(bundle);
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
    protected ITitleBar buildTitleBar() {
        return new DefaultTitleBar(this);
    }

    @Override
    protected int buildColumnCount() {
        return 2;
    }

    @Override
    protected RecyclerView.ItemDecoration buildItemDecoration() {
        return new ItemDecorationCommon(
                2,
                dip2px(6),
                dip2px(14),
                dip2px(15),
                Constant.TYPE_ITEM_1
        );
    }

    @Override
    public void httpOnSuccess(int sign, JsonElement data, String message) {
        switch (sign) {
            case ApiRequestTask.my.TAG_COLLECT_LIST:
                CollectBean bean = JsonTools.fromJson(data, CollectBean.class);
                refreshData(newItem(bean));
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
