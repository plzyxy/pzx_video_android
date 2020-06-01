package com.thinksoft.banana.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thinksoft.banana.R;
import com.thinksoft.banana.app.Constant;
import com.thinksoft.banana.entity.item.CircleItem;
import com.thinksoft.banana.ui.view.CircleAddImgView;
import com.thinksoft.banana.ui.view.CircleAddVideoView2;
import com.txf.ui_mvplibrary.interfaces.OnAppListener;
import com.txf.ui_mvplibrary.ui.adapter.BaseCompleteRecyclerAdapter;
import com.txf.ui_mvplibrary.ui.adapter.hoder.BaseViewHoder;

/**
 * @author txf
 * @create 2019/3/21 0021
 * @
 */
public class CircleAddAdapter extends BaseCompleteRecyclerAdapter<CircleItem> {
    public CircleAddAdapter(Context context) {
        super(context);
    }

    public CircleAddAdapter(Context context, OnAppListener.OnAdapterListener listener) {
        super(context, listener);
    }

    public int getDataState() {
        if (getItemViewType(0) == Constant.TYPE_ITEM_1) {
            return 0;

        } else if (getItemViewType(0) == Constant.TYPE_ITEM_2) {
            return 2;

        } else if (getItemViewType(0) == Constant.TYPE_ITEM_3) {
            return 1;
        } else {
            return 1;
        }
    }

    @Override
    public BaseViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layouts != null && layouts.containsKey(viewType))
            return new BaseViewHoder(LayoutInflater.from(getContext()).inflate(layouts.get(viewType), parent, false));
        else
            return new BaseViewHoder(LayoutInflater.from(getContext()).inflate(layouts.get(Constant.TYPE_ITEM_3), parent, false));
    }

    @Override
    protected void setItemLayout() {
        super.setItemLayout();
        addItemLayout(Constant.TYPE_ITEM_1, R.layout.item_circle_add_null);
        addItemLayout(Constant.TYPE_ITEM_2, R.layout.item_circle_add_video);
        addItemLayout(Constant.TYPE_ITEM_3, R.layout.item_circle_add_img);
    }

    @Override
    public int getItemViewType(int position) {
        return getDatas().get(position).getItemType();
    }

    @Override
    protected void onBindBaseViewHoder(BaseViewHoder holder, int position, CircleItem item) {
        switch (getItemViewType(position)) {
            case Constant.TYPE_ITEM_1:
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getListener().onInteractionAdapter(Constant.TYPE_ITEM_1, null);
                    }
                });
                break;
            case Constant.TYPE_ITEM_2:
                bindItem2(holder, position, item);
                break;
            case Constant.TYPE_ITEM_3:
                bindItem3(holder, position, item);
                break;
            default:
                bindItem3(holder, position, item);
        }
    }

    private void bindItem3(BaseViewHoder holder, final int position, CircleItem item) {
        final CircleAddImgView imgView = holder.getViewById(R.id.CircleAddImgView);
        if (item.getType() == 0)
            imgView.setData(item);
        holder.getViewById(R.id.deleteButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgView.delete();
                datas.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    private void bindItem2(BaseViewHoder holder, final int position, CircleItem item) {
        final CircleAddVideoView2 videoView = holder.getViewById(R.id.CircleAddVideoView);
        if (item.getType() == 0)
            videoView.setData(item);
        holder.getViewById(R.id.deleteButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.delete();
                datas.remove(position);
                datas.add(new CircleItem(null, Constant.TYPE_ITEM_1));
                notifyDataSetChanged();
            }
        });
    }


}
