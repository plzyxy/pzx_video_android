package com.thinksoft.banana.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.thinksoft.banana.R;
import com.thinksoft.banana.app.Constant;
import com.thinksoft.banana.entity.bean.NovelTypeDataBean;
import com.thinksoft.banana.entity.item.HomeItem;
import com.txf.ui_mvplibrary.interfaces.OnAppListener;
import com.txf.ui_mvplibrary.ui.adapter.BaseCompleteRecyclerAdapter;
import com.txf.ui_mvplibrary.ui.adapter.hoder.BaseViewHoder;
import com.txf.ui_mvplibrary.utils.BundleUtils;

/**
 * @author txf
 * @create 2019/4/9 0009
 * @
 */
public class NovelTypeListAdapter extends BaseCompleteRecyclerAdapter<HomeItem> {
    public NovelTypeListAdapter(Context context) {
        super(context);
    }

    public NovelTypeListAdapter(Context context, OnAppListener.OnAdapterListener listener) {
        super(context, listener);
    }

    @Override
    protected void setItemLayout() {
        super.setItemLayout();
        addItemLayout(Constant.TYPE_ITEM_1, R.layout.item_novel_list_type);
    }

    @Override
    public int getItemViewType(int position) {
        return getDatas().get(position).getItemType();
    }

    @Override
    protected void onBindBaseViewHoder(BaseViewHoder holder, int position, HomeItem item) {
        switch (getItemViewType(position)) {
            case Constant.TYPE_ITEM_1:
                onBindItem1(holder, position, item);
                break;
        }
    }

    private void onBindItem1(BaseViewHoder holder, int position, final HomeItem item) {
        SimpleDraweeView simpleDraweeView = holder.getViewById(R.id.SimpleDraweeView);
        if (item.getData() == null) {
            holder.itemView.setOnClickListener(null);
            simpleDraweeView.setVisibility(View.INVISIBLE);
            holder.setText(R.id.valueTextView, "");
            return;
        }
        final NovelTypeDataBean bean = (NovelTypeDataBean) item.getData();
        simpleDraweeView.setVisibility(View.VISIBLE);
        if (bean.getId() == -1) {
            simpleDraweeView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.icon_gengduo));
        } else {
            simpleDraweeView.setImageURI(bean.getImage());
        }
        holder.setText(R.id.valueTextView, bean.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getListener().onInteractionAdapter(Constant.TYPE_ITEM_1, BundleUtils.putSerializable(bean));
            }
        });
    }
}
