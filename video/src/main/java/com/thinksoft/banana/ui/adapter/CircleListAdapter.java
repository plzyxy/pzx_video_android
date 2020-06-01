package com.thinksoft.banana.ui.adapter;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.orhanobut.logger.Logger;
import com.thinksoft.banana.R;
import com.thinksoft.banana.app.Constant;
import com.thinksoft.banana.entity.bean.circle.CircleBean;
import com.thinksoft.banana.entity.bean.circle.HttpImgBean;
import com.thinksoft.banana.entity.bean.circle.RegionsDataBean;
import com.thinksoft.banana.entity.bean.home.BannersBean;
import com.thinksoft.banana.entity.bean.home.VideosBean;
import com.thinksoft.banana.entity.bean.type.ScreenBean;
import com.thinksoft.banana.entity.item.CircleItem;
import com.thinksoft.banana.entity.item.VideoItem;
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
public class CircleListAdapter extends BaseCompleteRecyclerAdapter<CircleItem> {
    public CircleListAdapter(Context context) {
        super(context);
    }

    public CircleListAdapter(Context context, OnAppListener.OnAdapterListener listener) {
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
        addItemLayout(Constant.TYPE_ITEM_2, R.layout.item_circle_list_content);

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
        CircleBean bean = (CircleBean) item.getData();

        SimpleDraweeView userImg = holder.getViewById(R.id.userImg);
        TextView nameTV = holder.getViewById(R.id.nameTV);
        TextView contentTV = holder.getViewById(R.id.contentTV);

        TextView timeTV = holder.getViewById(R.id.timeTV);
        TextView shareButton = holder.getViewById(R.id.shareButton);
        TextView zhangButton = holder.getViewById(R.id.zhangButton);

        userImg.setImageURI(bean.getImage());
        nameTV.setText(bean.getNickname());
        contentTV.setText(bean.getContent());
        timeTV.setText(bean.getTime());
        zhangButton.setText(bean.getLove() + "");
        holder.setText(R.id.commentTV, bean.getComment_num() + "");
        if (bean.getSex() == 1) {
            nameTV.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(getContext(), R.drawable.icon_sex_nan), null);
        } else {
            nameTV.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(getContext(), R.drawable.icon_sex_nv), null);
        }

        if (bean.getIslove() == 1) {
            zhangButton.setTextColor(0xffd76dbb);
            zhangButton.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(getContext(), R.drawable.icon_circle_z_true), null, null, null);
        } else {
            zhangButton.setTextColor(0xff999999);
            zhangButton.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(getContext(), R.drawable.icon_circle_z_false), null, null, null);
        }

        RecyclerView imgList = holder.getViewById(R.id.imgList);
        imgList.removeItemDecoration(mItemDecorationCommon);
        imgList.removeItemDecoration(mItemDecorationCommon2);
        List<CircleItem> data = new ArrayList<>();


        if (bean.getType() == 1) {
            //图片
            if (bean.getImages() != null && bean.getImages().size() > 0) {
                if (bean.getImages().size() > 1) {
                    imgList.setLayoutManager(new GridLayoutManager(getContext(), 3));
                    imgList.addItemDecoration(mItemDecorationCommon);
                } else {
                    imgList.setLayoutManager(new GridLayoutManager(getContext(), 1));
                    imgList.addItemDecoration(mItemDecorationCommon2);
                }
                for (HttpImgBean imgBean : bean.getImages()) {
                    data.add(new CircleItem(imgBean, Constant.TYPE_ITEM_1));
                }
            }

        } else if (bean.getType() == 2) {
            //视频
            if (bean.getVideo() != null) {
                imgList.setLayoutManager(new GridLayoutManager(getContext(), 1));
                imgList.addItemDecoration(mItemDecorationCommon2);
                data.add(new CircleItem(bean.getVideo(), Constant.TYPE_ITEM_2));
            }
        }
        CircleImgListNotClickAdapter adapter;
        imgList.setAdapter(adapter = new CircleImgListNotClickAdapter(getContext(), getListener()));
        adapter.setDatas(data);
        adapter.notifyDataSetChanged();

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

                    CircleListAdapter.this.getListener().onInteractionAdapter(Constant.TYPE_ITEM_3, null);
                }
            });
        }
    };
}
