package com.thinksoft.banana.ui.fragment.my;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.gson.JsonElement;
import com.thinksoft.banana.R;
import com.thinksoft.banana.app.Constant;
import com.thinksoft.banana.app.manage.UserInfoManage;
import com.thinksoft.banana.entity.bean.IncomeBean;
import com.thinksoft.banana.entity.bean.IncomeDataBean;
import com.thinksoft.banana.mvp.contract.CommonContract;
import com.thinksoft.banana.mvp.model.MyModel;
import com.thinksoft.banana.mvp.presenter.CommonPresenter;
import com.thinksoft.banana.net.api.ApiRequestTask;
import com.txf.other_toolslibrary.tools.JsonTools;
import com.txf.ui_mvplibrary.bean.BaseItem;
import com.txf.ui_mvplibrary.ui.adapter.BaseCompleteRecyclerAdapter;
import com.txf.ui_mvplibrary.ui.adapter.BaseRecyclerAdapter;
import com.txf.ui_mvplibrary.ui.adapter.hoder.BaseViewHoder;
import com.txf.ui_mvplibrary.ui.fragment.BaseMvpListLjzFragment;
import com.txf.ui_mvplibrary.utils.BundleUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author txf
 * @create 2019/2/19
 * 我的钱包列表
 */
public class WalletListFragment
        extends BaseMvpListLjzFragment<BaseItem, CommonContract.View, CommonContract.Presenter>
        implements CommonContract.View {
    String type;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = BundleUtils.getString(getArguments());
        setContract(this, new CommonPresenter(getContext(), this, new MyModel()));
    }

    @Override
    protected BaseRecyclerAdapter buildAdapter() {
        return new BaseCompleteRecyclerAdapter<BaseItem>(getContext()) {
            @Override
            protected void setItemLayout() {
                super.setItemLayout();
                addItemLayout(Constant.TYPE_ITEM_1, R.layout.item_walletlist_content);
            }

            @Override
            public int getItemViewType(int position) {
                return getDatas().get(position).getItemType();
            }

            @Override
            protected void onBindBaseViewHoder(BaseViewHoder holder, int position, BaseItem item) {
                IncomeDataBean dataBean = (IncomeDataBean) item.getData();
                holder.setText(R.id.tv1, type.equals(getString(R.string.总收入)) ? dataBean.getName() : "观影");
                holder.setText(R.id.tv2, type.equals(getString(R.string.总收入)) ? dataBean.getTime() : "-400");
                holder.setText(R.id.tv3, type.equals(getString(R.string.总收入)) ? dataBean.getDiamond() : "-400");
            }
        };
    }

    @Override
    protected void request(int pageIndex, int pageSize) {
        HashMap<String, Object> maps = new HashMap<>();
        maps.put("token", UserInfoManage.getInstance().getUserInfo().getToken());
        getPresenter().getData(ApiRequestTask.my.TAG_INCOME_LIST, maps);
    }

    private List<BaseItem> newItem(IncomeBean bean) {
        List<BaseItem> items = new ArrayList<>();
        if (bean == null || bean.getIncomeList() == null || bean.getIncomeList().size() == 0)
            return items;
        for (IncomeDataBean dataBean : bean.getIncomeList())
            items.add(new BaseItem(dataBean, Constant.TYPE_ITEM_1));
        return items;
    }

    @Override
    public void httpOnSuccess(int sign, JsonElement data, String message) {
        switch (sign) {
            case ApiRequestTask.my.TAG_INCOME_LIST:
                IncomeBean bean = JsonTools.fromJson(data, IncomeBean.class);
                refreshData(newItem(bean));
                break;
        }
    }

    @Override
    public void httpOnError(int sign, int error, String message) {

    }
}
