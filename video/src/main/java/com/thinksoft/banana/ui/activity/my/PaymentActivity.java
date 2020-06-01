package com.thinksoft.banana.ui.activity.my;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.JsonElement;
import com.thinksoft.banana.R;
import com.thinksoft.banana.app.Constant;
import com.thinksoft.banana.app.manage.UserInfoManage;
import com.thinksoft.banana.entity.bean.RechargeBean;
import com.thinksoft.banana.entity.bean.RechargeListBean;
import com.thinksoft.banana.entity.item.PaymentItem;
import com.thinksoft.banana.mvp.contract.CommonContract;
import com.thinksoft.banana.mvp.model.MyModel;
import com.thinksoft.banana.mvp.presenter.CommonPresenter;
import com.thinksoft.banana.net.api.ApiRequestTask;
import com.thinksoft.banana.ui.activity.start.LoginActivity;
import com.thinksoft.banana.ui.adapter.PaymentAdapter;
import com.txf.other_toolslibrary.manager.AppManager;
import com.txf.other_toolslibrary.tools.JsonTools;
import com.txf.ui_mvplibrary.interfaces.ITitleBar;
import com.txf.ui_mvplibrary.interfaces.OnAppListener;
import com.txf.ui_mvplibrary.ui.activity.BaseMvpListActivity;
import com.txf.ui_mvplibrary.ui.adapter.BaseRecyclerAdapter;
import com.txf.ui_mvplibrary.ui.adapter.item_decoration.ItemDecorationCommon;
import com.txf.ui_mvplibrary.ui.view.deft.DefaultTitleBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author txf
 * @create 2019/2/20 0020
 * @充值
 */
public class PaymentActivity
        extends BaseMvpListActivity<PaymentItem, CommonContract.View, CommonContract.Presenter>
        implements CommonContract.View, OnAppListener.OnAdapterListener {
    PaymentAdapter mPaymentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContract(this, new CommonPresenter(this, new MyModel()));
        mITitleBar.setTitleText(getString(R.string.充值));
    }

    @Override
    protected BaseRecyclerAdapter buildAdapter() {
        return mPaymentAdapter = new PaymentAdapter(this, this);
    }

    @Override
    protected boolean nextPage(List<PaymentItem> datas) {
        return false;
    }

    @Override
    protected void request(int pageIndex, int pageSize) {
        HashMap<String, Object> maps = new HashMap<>();
        maps.put("token", UserInfoManage.getInstance().getUserInfo().getToken());
        getPresenter().getData(ApiRequestTask.my.TAG_RECHARGE_LIST, maps);
    }

    private List<PaymentItem> newItem(RechargeBean bean) {
        List<PaymentItem> items = new ArrayList<>();
        if (bean == null || bean.getRechargeList() == null || bean.getRechargeList().size() == 0) {
            return items;
        }
        items.add(new PaymentItem(getString(R.string.充值金额), Constant.TYPE_ITEM_1));
        for (int i = 0; i < bean.getRechargeList().size(); i++) {
            RechargeListBean rechargeListBean = bean.getRechargeList().get(i);
            if (i == 0)
                items.add(new PaymentItem(true, rechargeListBean, Constant.TYPE_ITEM_2));
            else
                items.add(new PaymentItem(rechargeListBean, Constant.TYPE_ITEM_2));
        }
        items.add(new PaymentItem(getString(R.string.支付方式), Constant.TYPE_ITEM_1));
        items.add(new PaymentItem(true, getString(R.string.微信支付), Constant.TYPE_ITEM_3));
        items.add(new PaymentItem(getString(R.string.支付宝支付), Constant.TYPE_ITEM_3));

        items.add(new PaymentItem(null, Constant.TYPE_ITEM_4));
        return items;
    }

    @Override
    public void onInteractionAdapter(int action, Bundle bundle) {
        switch (action) {
            case Constant.TYPE_ITEM_4:
                PaymentItem payItem = mPaymentAdapter.getPayType();
                PaymentItem payMentItem = mPaymentAdapter.getPayMentItem();
                //确认充值
                String typeString = (String) payItem.getData();
                RechargeListBean rechargeListBean = (RechargeListBean) payMentItem.getData();

                HashMap<String, Object> maps = new HashMap<>();
                maps.put("token", UserInfoManage.getInstance().getUserInfo().getToken());
                maps.put("id", rechargeListBean.getId());
                if (typeString.equals(getString(R.string.支付宝支付))) {
                    maps.put("type", 2);
                } else if (typeString.equals(getString(R.string.微信支付))) {
                    maps.put("type", 1);
                }
                getPresenter().getData(ApiRequestTask.my.TAG_CREATE_ORDER, maps);
                break;
        }
    }

    @Override
    public void httpOnSuccess(int sign, JsonElement data, String message) {
        switch (sign) {
            case ApiRequestTask.my.TAG_RECHARGE_LIST:
                RechargeBean bean = JsonTools.fromJson(data, RechargeBean.class);
                refreshData(newItem(bean));
                break;
            case ApiRequestTask.my.TAG_CREATE_ORDER:
                //充值成功
                setResult(Activity.RESULT_OK);
                finish();
                break;
        }
    }

    @Override
    public void httpOnError(int sign, int error, String message) {

    }

    @Override
    protected ITitleBar buildTitleBar() {
        return new DefaultTitleBar(this);
    }

    @Override
    protected int buildColumnCount() {
        return 3;
    }

    @Override
    protected GridLayoutManager.SpanSizeLookup buildSpanSizeLookup() {
        return new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (mAdapter.getItemViewType(position)) {
                    case Constant.TYPE_ITEM_2:
                        return 1;
                    case Constant.TYPE_ITEM_1:
                    case Constant.TYPE_ITEM_3:
                    case Constant.TYPE_ITEM_4:
                        return 3;
                    default:
                        return 3;
                }
            }
        };
    }

    @Override
    protected RecyclerView.ItemDecoration buildItemDecoration() {
        return new ItemDecorationCommon(
                3,
                dip2px(15),
                0,
                dip2px(15),
                Constant.TYPE_ITEM_2
        );
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
