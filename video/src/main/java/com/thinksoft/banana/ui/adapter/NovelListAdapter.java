package com.thinksoft.banana.ui.adapter;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.thinksoft.banana.R;
import com.thinksoft.banana.app.Constant;
import com.thinksoft.banana.entity.bean.NovelBean;
import com.thinksoft.banana.entity.bean.home.BannersBean;
import com.thinksoft.banana.entity.bean.type.ScreenBean;
import com.thinksoft.banana.entity.item.CircleItem;
import com.thinksoft.banana.ui.view.banner.BannerWidget;
import com.txf.ui_mvplibrary.interfaces.OnAppListener;
import com.txf.ui_mvplibrary.ui.adapter.BaseCompleteRecyclerAdapter;
import com.txf.ui_mvplibrary.ui.adapter.hoder.BaseViewHoder;
import com.txf.ui_mvplibrary.ui.adapter.item_decoration.ItemDecorationCommon;
import com.txf.ui_mvplibrary.utils.BundleUtils;
import com.txf.ui_mvplibrary.utils.FrescoUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author txf
 * @create 2019/2/18 0018
 * @
 */
public class NovelListAdapter extends BaseCompleteRecyclerAdapter<CircleItem> {
    public NovelListAdapter(Context context) {
        super(context);
    }

    public NovelListAdapter(Context context, OnAppListener.OnAdapterListener listener) {
        super(context, listener);
    }

    ItemDecorationCommon mItemDecorationCommon = new ItemDecorationCommon(
            3,
            new Rect(0, 0, 0, 0),
            dip2px(8),
            Constant.TYPE_ITEM_1);

    ItemDecorationCommon mItemDecorationCommon2 = new ItemDecorationCommon(
            1,
            new Rect(0, 0, 0, 0),
            0,
            Constant.TYPE_ITEM_1);

    @Override
    protected void setItemLayout() {
        super.setItemLayout();
        addItemLayout(Constant.TYPE_ITEM_1, R.layout.item_circle_list_img);
        addItemLayout(Constant.TYPE_ITEM_3, R.layout.item_circle_screen);
        addItemLayout(Constant.TYPE_ITEM_2, R.layout.item_novel_list_content);
        addItemLayout(Constant.TYPE_ITEM_4, R.layout.item_circle_ad_img);
    }

    public int getScreenNamePos(List<ScreenBean> datas2) {
        for (int i = 0; i < datas2.size(); i++) {
            if (datas2.get(i).isCheck()) {
                return i;
            }
        }
        return 0;
    }


    @Override
    public int getItemViewType(int position) {
        return getDatas().get(position).getItemType();
    }

    @Override
    protected void onBindBaseViewHoder(BaseViewHoder holder, int position, final CircleItem item) {
        switch (getItemViewType(position)) {
            case Constant.TYPE_ITEM_1:
                BannerWidget bannerWidget = holder.getViewById(R.id.BannerWidget);
                List<BannersBean> banners = (List<BannersBean>) item.getData();
                bannerWidget.setData(banners);
                break;
            case Constant.TYPE_ITEM_2:
                binditem2(holder, position, item);
                break;
            case Constant.TYPE_ITEM_3:
                binditem3(holder, position, item);
                break;
            case Constant.TYPE_ITEM_4:
                binditem4(holder, position, item);
                break;
        }
    }

    private void binditem4(BaseViewHoder holder, int position, CircleItem item) {
        final BannersBean bannersBean = (BannersBean) item.getData();
        final SimpleDraweeView imgView = holder.getViewById(R.id.imgView);

        FrescoUtils.setGifImgUrl(
                imgView,
                bannersBean.getImage(),
                FrescoUtils.getControllerListener(imgView, getContext().getResources().getDisplayMetrics().widthPixels));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getListener().onInteractionAdapter(Constant.TYPE_ITEM_4, BundleUtils.putSerializable(bannersBean));
            }
        });
    }

    private void binditem3(BaseViewHoder holder, int position, CircleItem item) {
        RecyclerView recyclerView = holder.getViewById(R.id.RecyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mAdapter1);
        ArrayList<ScreenBean> datas1 = (ArrayList<ScreenBean>) item.getData();
        mAdapter1.setDatas(datas1);
        mAdapter1.notifyDataSetChanged();
        manager.scrollToPosition(getScreenNamePos(datas1));
    }

    private void binditem2(BaseViewHoder holder, int position, final CircleItem item) {
        NovelBean bean = (NovelBean) item.getData();
        TextView nameTV = holder.getViewById(R.id.nameTV);
        TextView contentTV = holder.getViewById(R.id.contentTV);
        SimpleDraweeView userImg = holder.getViewById(R.id.userImg);
        TextView shareButton = holder.getViewById(R.id.shareButton);
        TextView zhangButton = holder.getViewById(R.id.zhangButton);

        nameTV.setText(bean.getTitle());
        contentTV.setText(bean.getDescription());
        zhangButton.setText(bean.getLove() + "");

        String cateName = "地区: " + (bean.getCate_name() == null ? "无" : bean.getCate_name());
        String age = "年龄: " + (bean.getAge() == null ? "无" : bean.getAge());
        String service_num = "服务价格: " + (bean.getService_num() == null ? "无" : bean.getService_num());
        holder.setText(R.id.tv1, cateName);
        holder.setText(R.id.tv2, age);
        holder.setText(R.id.tv3, service_num);

        holder.setText(R.id.commentTV, bean.getComment_num() + "");
        if (bean.getImages() != null && bean.getImages().size() > 0) {
            userImg.setImageURI(bean.getImages().get(0).getMin());
        }else {
            userImg.setImageURI("");
        }

        if (bean.getIslove() == 1) {
            zhangButton.setTextColor(0xffd76dbb);
            zhangButton.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(getContext(), R.drawable.icon_circle_z_true), null, null, null);
        } else {
            zhangButton.setTextColor(0xff999999);
            zhangButton.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(getContext(), R.drawable.icon_circle_z_false), null, null, null);
        }

        holder.getViewById(R.id.buttonDetails).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getListener().onInteractionAdapter(Constant.ACTION_CIRCLE_DETAILS, BundleUtils.putBaseItem(item));
            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getListener().onInteractionAdapter(Constant.ACTION_CIRCLE_SHARE, BundleUtils.putBaseItem(item));
            }
        });

        zhangButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getListener().onInteractionAdapter(Constant.ACTION_CIRCLE_Z, BundleUtils.putBaseItem(item));
            }
        });
    }


    private ScreenBean mScreenBean1;
    private TextView mSelTV1;

    public ScreenBean getScreenBean() {
        return mScreenBean1;
    }

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

                    NovelListAdapter.this.getListener().onInteractionAdapter(Constant.TYPE_ITEM_3, null);
                }
            });
        }
    };
}
