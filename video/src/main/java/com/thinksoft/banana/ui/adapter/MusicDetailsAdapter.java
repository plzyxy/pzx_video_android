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
import com.thinksoft.banana.entity.bean.music.MusicInfoDataBean;
import com.thinksoft.banana.entity.item.CircleItem;
import com.thinksoft.banana.ui.adapter.CircleImgListAdapter;
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
public class MusicDetailsAdapter extends BaseCompleteRecyclerAdapter<CircleItem> {
    public MusicDetailsAdapter(Context context) {
        super(context);
    }

    public MusicDetailsAdapter(Context context, OnAppListener.OnAdapterListener listener) {
        super(context, listener);
    }

    @Override
    protected void setItemLayout() {
        super.setItemLayout();
        addItemLayout(Constant.TYPE_ITEM_1, R.layout.item_music_details_content);
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
        final MusicInfoDataBean bean = (MusicInfoDataBean) item.getData();
        SimpleDraweeView simpleDraweeView = holder.getViewById(R.id.SimpleDraweeView);
        TextView nameTV = holder.getViewById(R.id.nameTV);
        TextView contentTV = holder.getViewById(R.id.contentTV);

        TextView timeTV = holder.getViewById(R.id.timeTV);
        TextView shareButton = holder.getViewById(R.id.shareButton);
        TextView zhangButton = holder.getViewById(R.id.zhangButton);

        simpleDraweeView.setImageURI(bean.getImage());
        nameTV.setText(bean.getTitle());
        contentTV.setText(bean.getDescription());
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
        simpleDraweeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getListener().onInteractionAdapter(Constant.ACTION_CIRCLE_VIDEO, BundleUtils.putSerializable(bean));
            }
        });
    }
}
