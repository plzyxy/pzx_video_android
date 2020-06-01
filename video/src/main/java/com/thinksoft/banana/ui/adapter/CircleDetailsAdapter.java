package com.thinksoft.banana.ui.adapter;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.thinksoft.banana.R;
import com.thinksoft.banana.app.Constant;
import com.thinksoft.banana.entity.bean.circle.CircleBean;
import com.thinksoft.banana.entity.bean.circle.CircleCommentBean;
import com.thinksoft.banana.entity.bean.circle.HttpImgBean;
import com.thinksoft.banana.entity.item.CircleItem;
import com.txf.ui_mvplibrary.interfaces.OnAppListener;
import com.txf.ui_mvplibrary.ui.adapter.BaseCompleteRecyclerAdapter;
import com.txf.ui_mvplibrary.ui.adapter.hoder.BaseViewHoder;
import com.txf.ui_mvplibrary.ui.adapter.item_decoration.ItemDecorationCommon;
import com.txf.ui_mvplibrary.utils.BundleUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author txf
 * @create 2019/3/22 0022
 * @
 */
public class CircleDetailsAdapter extends BaseCompleteRecyclerAdapter<CircleItem> {
    public CircleDetailsAdapter(Context context) {
        super(context);
    }

    public CircleDetailsAdapter(Context context, OnAppListener.OnAdapterListener listener) {
        super(context, listener);
    }

    @Override
    protected void setItemLayout() {
        super.setItemLayout();
        addItemLayout(Constant.TYPE_ITEM_1, R.layout.item_circle_details_content);
        addItemLayout(Constant.TYPE_ITEM_2, R.layout.item_circle_comment);
    }

    @Override
    public int getItemViewType(int position) {
        return getDatas().get(position).getItemType();
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
    protected void onBindBaseViewHoder(BaseViewHoder holder, int position, CircleItem item) {
        switch (getItemViewType(position)) {
            case Constant.TYPE_ITEM_1:
                binditem1(holder, position, item);
                break;
            case Constant.TYPE_ITEM_2:
                binditem2(holder, position, item);
                break;
        }
    }

    private void binditem2(BaseViewHoder holder, int position, final CircleItem item) {
        final CircleCommentBean bean = (CircleCommentBean) item.getData();
        SimpleDraweeView userImg = holder.getViewById(R.id.SimpleDraweeView);
        TextView nameTV = holder.getViewById(R.id.nameTV);
        TextView timeTV = holder.getViewById(R.id.timeTV);
        TextView contentTV = holder.getViewById(R.id.contentTV);
        TextView zButton = holder.getViewById(R.id.zButton);

        userImg.setImageURI(bean.getImage());
        nameTV.setText(bean.getNickname());
        timeTV.setText(bean.getDate());
        contentTV.setText(bean.getContent());

        if (bean.getIslove() == 1) {
            zButton.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    ContextCompat.getDrawable(getContext(), R.drawable.icon_play_z_true),
                    null,
                    null);
        } else {
            zButton.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    ContextCompat.getDrawable(getContext(), R.drawable.icon_play_z_false),
                    null,
                    null);
        }
        zButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getListener().onInteractionAdapter(Constant.ACTION_CIRCLE_COMMENT_Z, BundleUtils.putSerializable(bean));
            }
        });
    }

    private void binditem1(BaseViewHoder holder, int position, final CircleItem item) {
        CircleBean bean = (CircleBean) item.getData();

        holder.getViewById(R.id.detailsTV).setVisibility(View.GONE);
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
        CircleImgListAdapter adapter;
        imgList.setAdapter(adapter = new CircleImgListAdapter(getContext(), getListener()));
        adapter.setDatas(data);
        adapter.notifyDataSetChanged();

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
}
