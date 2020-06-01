package com.thinksoft.banana.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.thinksoft.banana.R;
import com.thinksoft.banana.app.Constant;
import com.thinksoft.banana.entity.item.PersonDataItem;
import com.txf.ui_mvplibrary.interfaces.OnAppListener;
import com.txf.ui_mvplibrary.ui.adapter.BaseCompleteRecyclerAdapter;
import com.txf.ui_mvplibrary.ui.adapter.hoder.BaseViewHoder;
import com.txf.ui_mvplibrary.utils.BundleUtils;

/**
 * @author txf
 * @create 2019/2/19 0019
 * @
 */
public class PersonDataAdapter extends BaseCompleteRecyclerAdapter<PersonDataItem> {
    public PersonDataAdapter(Context context) {
        super(context);
    }

    public PersonDataAdapter(Context context, OnAppListener.OnAdapterListener listener) {
        super(context, listener);
    }

    @Override
    protected void setItemLayout() {
        super.setItemLayout();
        addItemLayout(Constant.TYPE_ITEM_1, R.layout.item_persondata_1);
        addItemLayout(Constant.TYPE_ITEM_2, R.layout.item_persondata_2);
        addItemLayout(Constant.TYPE_ITEM_3, R.layout.item_persondata_3);
    }

    @Override
    public int getItemViewType(int position) {
        return getDatas().get(position).getItemType();
    }

    @Override
    protected void onBindBaseViewHoder(BaseViewHoder holder, int position, final PersonDataItem item) {
        TextView valueTextView;

        switch (getItemViewType(position)) {
            case Constant.TYPE_ITEM_1:

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getListener().onInteractionAdapter(Constant.TYPE_ITEM_1, BundleUtils.putBaseItem(item));
                    }
                });
                break;
            case Constant.TYPE_ITEM_2:
                valueTextView = holder.getViewById(R.id.valueTextView);//昵称

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getListener().onInteractionAdapter(Constant.TYPE_ITEM_2, BundleUtils.putBaseItem(item));
                    }
                });
                break;
            case Constant.TYPE_ITEM_3:
                valueTextView = holder.getViewById(R.id.valueTextView);//性别

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getListener().onInteractionAdapter(Constant.TYPE_ITEM_3, BundleUtils.putBaseItem(item));
                    }
                });
                break;
        }
    }
}
