package com.thinksoft.banana.ui.adapter;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.thinksoft.banana.R;
import com.thinksoft.banana.app.Constant;
import com.thinksoft.banana.entity.bean.circle.CircleBean;
import com.thinksoft.banana.entity.bean.circle.HttpImgBean;
import com.thinksoft.banana.entity.bean.home.BannersBean;
import com.thinksoft.banana.entity.item.CircleItem;
import com.thinksoft.banana.ui.view.banner.BannerWidget;
import com.txf.ui_mvplibrary.interfaces.OnAppListener;
import com.txf.ui_mvplibrary.ui.adapter.BaseCompleteRecyclerAdapter;
import com.txf.ui_mvplibrary.ui.adapter.hoder.BaseViewHoder;
import com.txf.ui_mvplibrary.ui.adapter.item_decoration.ItemDecorationCommon;
import com.txf.ui_mvplibrary.utils.BundleUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author txf
 * @create 2019/2/18 0018
 * @
 */
public class CircleMyAdapter extends BaseCompleteRecyclerAdapter<CircleItem> {
    public CircleMyAdapter(Context context) {
        super(context);
    }

    public CircleMyAdapter(Context context, OnAppListener.OnAdapterListener listener) {
        super(context, listener);
    }

    @Override
    protected void setItemLayout() {
        super.setItemLayout();
        addItemLayout(Constant.TYPE_ITEM_1, R.layout.item_circle_my_content);
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
    public int getItemViewType(int position) {
        return getDatas().get(position).getItemType();
    }

    @Override
    protected void onBindBaseViewHoder(BaseViewHoder holder, int position, final CircleItem item) {
        switch (getItemViewType(position)) {
            case Constant.TYPE_ITEM_1:
                binditem1(holder, position, item);
                break;
        }
    }

    private void binditem1(BaseViewHoder holder, int position, final CircleItem item) {
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

        holder.getViewById(R.id.deleteButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getListener().onInteractionAdapter(Constant.ACTION_CIRCLE_DELETE, BundleUtils.putBaseItem(item));
            }
        });
    }
}
