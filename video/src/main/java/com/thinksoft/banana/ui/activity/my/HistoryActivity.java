package com.thinksoft.banana.ui.activity.my;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.gson.JsonElement;
import com.thinksoft.banana.R;
import com.thinksoft.banana.app.Constant;
import com.thinksoft.banana.app.manage.UserInfoManage;
import com.thinksoft.banana.entity.bean.HistoryBean;
import com.thinksoft.banana.entity.bean.HistoryDataBean;
import com.thinksoft.banana.entity.bean.home.VideosBean;
import com.thinksoft.banana.entity.item.HistoryItem;
import com.thinksoft.banana.entity.item.HomeItem;
import com.thinksoft.banana.mvp.contract.CommonContract;
import com.thinksoft.banana.mvp.model.MyModel;
import com.thinksoft.banana.mvp.presenter.CommonPresenter;
import com.thinksoft.banana.net.api.ApiRequestTask;
import com.thinksoft.banana.ui.activity.player.PlayerActivity;
import com.thinksoft.banana.ui.activity.player.PlayerVerticalActivity;
import com.thinksoft.banana.ui.activity.start.LoginActivity;
import com.thinksoft.banana.ui.adapter.HistoryAdapter;
import com.txf.other_toolslibrary.manager.AppManager;
import com.txf.other_toolslibrary.tools.JsonTools;
import com.txf.other_toolslibrary.utils.DateUtils;
import com.txf.ui_mvplibrary.interfaces.ITitleBar;
import com.txf.ui_mvplibrary.interfaces.OnAppListener;
import com.txf.ui_mvplibrary.ui.activity.BaseMvpListActivity;
import com.txf.ui_mvplibrary.ui.adapter.BaseRecyclerAdapter;
import com.txf.ui_mvplibrary.ui.view.deft.DefaultTitleBar;
import com.txf.ui_mvplibrary.utils.BundleUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author txf
 * @create 2019/2/20 0020
 * @观看历史
 */
public class HistoryActivity
        extends BaseMvpListActivity<HistoryItem, CommonContract.View, CommonContract.Presenter>
        implements CommonContract.View, OnAppListener.OnAdapterListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContract(this, new CommonPresenter(this, new MyModel()));
        mITitleBar.setTitleText(getString(R.string.观看历史));
    }

    @Override
    protected BaseRecyclerAdapter<HistoryItem> buildAdapter() {
        return new HistoryAdapter(this, this);
    }

    @Override
    protected void request(int pageIndex, int pageSize) {
        HashMap<String, Object> maps = new HashMap<>();
        maps.put("token", UserInfoManage.getInstance().getUserInfo().getToken());
        maps.put("page", pageIndex);
        getPresenter().getData(ApiRequestTask.my.TAG_HISTORY_LIST, maps);
    }

    private List<HistoryItem> newItem(HistoryBean bean) {
        List<HistoryItem> items = new ArrayList<>();
        if (bean == null || bean.getWatchList() == null || bean.getWatchList().size() == 0) {
            return items;
        }
        for (int i = 0; i < bean.getWatchList().size(); i++) {
            HistoryDataBean dataBean = bean.getWatchList().get(i);
            if (i == 0) {
                items.add(new HistoryItem(dataBean.getRecord_time(), Constant.TYPE_ITEM_1));
            }
            if (i != 0) {
                HistoryDataBean lastDtaBean = bean.getWatchList().get((i - 1));
                if (!DateUtils.isSameDate(dataBean.getRecord_time(), lastDtaBean.getRecord_time())) {
                    items.add(new HistoryItem(dataBean.getRecord_time(), Constant.TYPE_ITEM_1));
                }
            }
            items.add(new HistoryItem(dataBean, Constant.TYPE_ITEM_2));
        }
        return items;
    }


    @Override
    public void onInteractionAdapter(int action, Bundle bundle) {
        HistoryItem item;
        switch (action) {
            case Constant.TYPE_ITEM_2:
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
    public void httpOnSuccess(int sign, JsonElement data, String message) {
        switch (sign) {
            case ApiRequestTask.my.TAG_HISTORY_LIST:
                HistoryBean bean = JsonTools.fromJson(data, HistoryBean.class);
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
