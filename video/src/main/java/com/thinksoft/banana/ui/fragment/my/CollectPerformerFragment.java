package com.thinksoft.banana.ui.fragment.my;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.google.gson.JsonElement;
import com.thinksoft.banana.R;
import com.thinksoft.banana.app.Constant;
import com.thinksoft.banana.app.manage.UserInfoManage;
import com.thinksoft.banana.entity.bean.home.CatesBean;
import com.thinksoft.banana.entity.bean.type.PerformerBean;
import com.thinksoft.banana.entity.bean.type.PerformerDataBean;
import com.thinksoft.banana.entity.item.CollectItem;
import com.thinksoft.banana.mvp.contract.CommonContract;
import com.thinksoft.banana.mvp.model.MyModel;
import com.thinksoft.banana.mvp.presenter.CommonPresenter;
import com.thinksoft.banana.net.api.ApiRequestTask;
import com.thinksoft.banana.ui.activity.my.PerformerInfoActivity;
import com.thinksoft.banana.ui.activity.start.LoginActivity;
import com.thinksoft.banana.ui.adapter.CollectAdapter;
import com.txf.other_toolslibrary.manager.AppManager;
import com.txf.other_toolslibrary.tools.JsonTools;
import com.txf.ui_mvplibrary.interfaces.OnAppListener;
import com.txf.ui_mvplibrary.ui.adapter.BaseRecyclerAdapter;
import com.txf.ui_mvplibrary.ui.adapter.item_decoration.ItemDecorationCommon;
import com.txf.ui_mvplibrary.ui.fragment.BaseMvpListLjzFragment;
import com.txf.ui_mvplibrary.utils.BundleUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author txf
 * @create 2019/3/12 0012
 * @收藏演员
 */
public class CollectPerformerFragment
        extends BaseMvpListLjzFragment<CollectItem, CommonContract.View, CommonContract.Presenter>
        implements CommonContract.View, OnAppListener.OnAdapterListener {
    CatesBean mCatesBean;

    @Override
    protected BaseRecyclerAdapter buildAdapter() {
        return new CollectAdapter(getContext(), this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCatesBean = BundleUtils.getSerializable(getArguments());
        setContract(this, new CommonPresenter(getContext(), this, new MyModel()));
    }

    @Override
    protected void request(final int pageIndex, int pageSize) {
        HashMap<String, Object> maps = new HashMap<>();
        maps.put("token", UserInfoManage.getInstance().getUserInfo().getToken());
        maps.put("type", mCatesBean.getId());
        maps.put("page", pageIndex);
        getPresenter().getData(ApiRequestTask.my.TAG_COLLECT_LIST_3, maps);
    }

    private List<CollectItem> newItem(PerformerBean bean) {
        List<CollectItem> items = new ArrayList<>();
        if (bean == null || bean.getPerformerList() == null || bean.getPerformerList().size() == 0)
            return items;
        for (PerformerDataBean dataBean : bean.getPerformerList())
            items.add(new CollectItem(dataBean, Constant.TYPE_ITEM_1));
        return items;
    }

    @Override
    public void onInteractionAdapter(int action, Bundle bundle) {
        switch (action) {
            case Constant.TYPE_ITEM_1:
                PerformerDataBean dataBean = BundleUtils.getSerializable(bundle);
                startActivityForResult(PerformerInfoActivity.getIntent(getContext(), dataBean.getId()), 1024);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1024:
                pageIndex = 1;
                startRequest(pageIndex, pageSize);
                break;
        }
    }

    @Override
    public void httpOnSuccess(int sign, JsonElement data, String message) {
        switch (sign) {
            case ApiRequestTask.my.TAG_COLLECT_LIST_3:
                PerformerBean bean = JsonTools.fromJson(data, PerformerBean.class);
                refreshData(newItem(bean));
                break;
        }
    }

    @Override
    public void httpOnError(int sign, int error, String message) {

    }

    @Override
    protected RecyclerView.ItemDecoration buildItemDecoration() {
        return new ItemDecorationCommon(
                4,
                new Rect(dip2px(15), dip2px(15), dip2px(15), 0),
                dip2px(8),
                Constant.TYPE_ITEM_1);
    }

    @Override
    protected int buildColumnCount() {
        return 4;
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
