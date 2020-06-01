package com.thinksoft.banana.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.thinksoft.banana.R;
import com.thinksoft.banana.app.Constant;
import com.thinksoft.banana.entity.bean.HistoryDataBean;
import com.thinksoft.banana.entity.item.HistoryItem;
import com.txf.other_toolslibrary.utils.DateUtils;
import com.txf.ui_mvplibrary.interfaces.OnAppListener;
import com.txf.ui_mvplibrary.ui.adapter.BaseCompleteRecyclerAdapter;
import com.txf.ui_mvplibrary.ui.adapter.hoder.BaseViewHoder;
import com.txf.ui_mvplibrary.utils.BundleUtils;

/**
 * @author txf
 * @create 2019/2/20 0020
 * @
 */
public class HistoryAdapter extends BaseCompleteRecyclerAdapter<HistoryItem> {
    public HistoryAdapter(Context context) {
        super(context);
    }

    public HistoryAdapter(Context context, OnAppListener.OnAdapterListener listener) {
        super(context, listener);
    }

    @Override
    protected void setItemLayout() {
        super.setItemLayout();
        addItemLayout(Constant.TYPE_ITEM_1, R.layout.item_history_date);
        addItemLayout(Constant.TYPE_ITEM_2, R.layout.item_history_content);
    }

    @Override
    public int getItemViewType(int position) {
        return getDatas().get(position).getItemType();
    }

    @Override
    protected void onBindBaseViewHoder(BaseViewHoder holder, int position, final HistoryItem item) {
        switch (getItemViewType(position)) {
            case Constant.TYPE_ITEM_1:
                long date = (long) item.getData();
                holder.setText(R.id.dateTextView, formDate(date));
                break;
            case Constant.TYPE_ITEM_2:
                HistoryDataBean historyDataBean = (HistoryDataBean) item.getData();
                SimpleDraweeView img = holder.getViewById(R.id.SimpleDraweeView);
                TextView tv1 = holder.getViewById(R.id.tv1);
                TextView tv2 = holder.getViewById(R.id.tv2);
                TextView tv3 = holder.getViewById(R.id.tv3);

                img.setImageURI(historyDataBean.getImage());
                tv1.setText(historyDataBean.getTitle());
                String diamond;
                if (historyDataBean.getIs_free() == 1) {
                    tv2.setTextColor(0xffDF6DB7);
                    diamond = historyDataBean.getDiamond();
                    tv2.setText(getString(R.string.收费) + ": ￥" + diamond);
                } else {
                    tv2.setTextColor(0xff666666);
                    diamond = getString(R.string.免费);
                    tv2.setText(getString(R.string.免费) + ": " + diamond);
                }

                tv3.setText(getString(R.string.时长) + ": " + historyDataBean.getHis());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getListener().onInteractionAdapter(Constant.TYPE_ITEM_2, BundleUtils.putBaseItem(item));
                    }
                });
                break;
        }
    }

    private String formDate(long date) {
        StringBuffer buffer = new StringBuffer();
        long dayTime = 24 * 60 * 60 * 1000;//一天的毫秒值
        long currentTime = System.currentTimeMillis();

        if (DateUtils.isSameDate(currentTime, date)) {
            buffer.append(getString(R.string.今天));
        } else if (DateUtils.isSameDate((currentTime - 1l * dayTime), date)) {
            buffer.append(getString(R.string.昨天));
        } else if (DateUtils.isSameDate((currentTime - 2l * dayTime), date)) {
            buffer.append(getString(R.string.三天以内));
        } else if (DateUtils.isSameDate((currentTime - 3l * dayTime), date)) {
            buffer.append(getString(R.string.四天以内));
        } else if (DateUtils.isSameDate((currentTime - 4l * dayTime), date)) {
            buffer.append(getString(R.string.五天以内));
        } else if (DateUtils.isSameDate((currentTime - 5l * dayTime), date)) {
            buffer.append(getString(R.string.六天以内));
        } else if (DateUtils.isSameDate((currentTime - 6l * dayTime), date)) {
            buffer.append(getString(R.string.七天以内));
        } else {
            buffer.append(DateUtils.formatDate(date, DateUtils.PATTERN_DATE_STRING));
        }
        return buffer.toString();
    }
}
