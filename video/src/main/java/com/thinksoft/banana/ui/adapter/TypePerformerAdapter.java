package com.thinksoft.banana.ui.adapter;

import android.content.Context;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.donkingliang.imageselector.constant.Constants;
import com.facebook.drawee.view.SimpleDraweeView;
import com.thinksoft.banana.R;
import com.thinksoft.banana.app.Constant;
import com.thinksoft.banana.entity.bean.type.PerformerDataBean;
import com.thinksoft.banana.entity.bean.type.ScreenBean;
import com.thinksoft.banana.entity.item.PerformerItem;
import com.txf.ui_mvplibrary.interfaces.OnAppListener;
import com.txf.ui_mvplibrary.ui.adapter.BaseCompleteRecyclerAdapter;
import com.txf.ui_mvplibrary.ui.adapter.hoder.BaseViewHoder;
import com.txf.ui_mvplibrary.utils.BundleUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author txf
 * @create 2019/3/12 0012
 * @
 */
public class TypePerformerAdapter extends BaseCompleteRecyclerAdapter<PerformerItem> {
    public TypePerformerAdapter(Context context) {
        super(context);
    }

    public TypePerformerAdapter(Context context, OnAppListener.OnAdapterListener listener) {
        super(context, listener);
    }

    @Override
    protected void setItemLayout() {
        super.setItemLayout();
        addItemLayout(Constant.TYPE_ITEM_1, R.layout.item_performer_screen);
        addItemLayout(Constant.TYPE_ITEM_2, R.layout.item_performer_screen_a);
        addItemLayout(Constant.TYPE_ITEM_3, R.layout.item_performer_content);
    }

    @Override
    public int getItemViewType(int position) {
        return getDatas().get(position).getItemType();
    }

    private ScreenBean mScreenBean2;
    private TextView mSelTV2;

    private ScreenBean mScreenBean1;
    private TextView mSelTV1;

    public int getScreenNamePos(List<ScreenBean> datas2) {
        for (int i = 0; i < datas2.size(); i++) {
            if (datas2.get(i).isCheck()) {
                return i;
            }
        }
        return 0;
    }

    @Override
    protected void onBindBaseViewHoder(BaseViewHoder holder, int position, final PerformerItem item) {
        switch (getItemViewType(position)) {
            case Constant.TYPE_ITEM_1:
                RecyclerView recyclerView = holder.getViewById(R.id.RecyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                recyclerView.setAdapter(mAdapter1);
                ArrayList<ScreenBean> datas1 = (ArrayList<ScreenBean>) item.getData();
                mAdapter1.setDatas(datas1);
                mAdapter1.notifyDataSetChanged();
                break;
            case Constant.TYPE_ITEM_2:
                final TextView titleTV = holder.getViewById(R.id.titleTV);
                RecyclerView screenList = holder.getViewById(R.id.screenList);
                LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                screenList.setLayoutManager(manager);
                screenList.setAdapter(mAdapter2);
                ArrayList<ScreenBean> datas2 = (ArrayList<ScreenBean>) item.getData();
                mAdapter2.setDatas(datas2);
                mAdapter2.notifyDataSetChanged();
                manager.scrollToPosition(getScreenNamePos(datas2));
                break;
            case Constant.TYPE_ITEM_3:
                final PerformerDataBean dataBean = (PerformerDataBean) item.getData();
                SimpleDraweeView simpleDraweeView = holder.getViewById(R.id.SimpleDraweeView);
                TextView TV1 = holder.getViewById(R.id.TV1);
                simpleDraweeView.setImageURI(dataBean.getImage());
                TV1.setText(dataBean.getName());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getListener().onInteractionAdapter(Constant.TYPE_ITEM_3, BundleUtils.putSerializable(dataBean));
                    }
                });
                break;
        }
    }

    BaseCompleteRecyclerAdapter mAdapter2 = new BaseCompleteRecyclerAdapter<ScreenBean>(getContext()) {
        @Override
        protected void setItemLayout() {
            super.setItemLayout();
            addItemLayout(0, R.layout.item_screen_a_item);
        }

        @Override
        protected void onBindBaseViewHoder(BaseViewHoder holder, int position, final ScreenBean item) {
            final TextView TV1 = holder.getViewById(R.id.TV1);
            TV1.setText(item.getText());
            if (item.isCheck()) {
                mScreenBean2 = item;
                mSelTV2 = TV1;
                TV1.setTextColor(0xffD96DBA);
            } else {
                TV1.setTextColor(0xff333333);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (item.isCheck()) {
                        return;
                    }
                    if (mScreenBean2 != null) {
                        mScreenBean2.setCheck(false);
                        mSelTV2.setTextColor(0xff333333);
                    }
                    mScreenBean2 = item;
                    mSelTV2 = TV1;

                    mScreenBean2.setCheck(true);
                    mSelTV2.setTextColor(0xffD96DBA);
                    TypePerformerAdapter.this.getListener().onInteractionAdapter(Constant.TYPE_ITEM_2, null);
                }
            });
        }
    };

    BaseCompleteRecyclerAdapter mAdapter1 = new BaseCompleteRecyclerAdapter<ScreenBean>(getContext()) {
        @Override
        protected void setItemLayout() {
            super.setItemLayout();
            addItemLayout(0, R.layout.item_screen_item);
        }

        @Override
        protected void onBindBaseViewHoder(BaseViewHoder holder, int position, final ScreenBean item) {
            final TextView TV1 = holder.getViewById(R.id.TV1);
            TV1.setText(item.getText());
            if (item.isCheck()) {
                mScreenBean1 = item;
                mSelTV1 = TV1;
                TV1.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.icon_radius_d76dbb_40));
            } else {
                TV1.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.icon_radius_d9d9d9_40));
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (item.isCheck()) {
                        return;
                    }
                    if (mScreenBean1 != null) {
                        mScreenBean1.setCheck(false);
                        mSelTV1.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.icon_radius_d9d9d9_40));
                    }
                    mScreenBean1 = item;
                    mSelTV1 = TV1;

                    mScreenBean1.setCheck(true);
                    mSelTV1.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.icon_radius_d76dbb_40));

                    TypePerformerAdapter.this.getListener().onInteractionAdapter(Constant.TYPE_ITEM_1, null);
                }
            });
        }
    };
}
