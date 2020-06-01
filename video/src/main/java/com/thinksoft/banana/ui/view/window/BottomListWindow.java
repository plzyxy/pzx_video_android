package com.thinksoft.banana.ui.view.window;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.view.View;

import com.thinksoft.banana.R;
import com.thinksoft.banana.entity.bean.StringBean;
import com.txf.ui_mvplibrary.interfaces.OnAppListener;
import com.txf.ui_mvplibrary.ui.adapter.BaseCompleteRecyclerAdapter;
import com.txf.ui_mvplibrary.ui.adapter.hoder.BaseViewHoder;
import com.txf.ui_mvplibrary.ui.view.window.BaseBottomListWindow;
import com.txf.ui_mvplibrary.utils.BundleUtils;

/**
 * @author txf
 * @create 2019/2/20
 * @底部列表窗口
 */
public class BottomListWindow extends BaseBottomListWindow<StringBean> implements OnAppListener.OnAdapterListener {
    public BottomListWindow(Activity context) {
        super(context);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }
    @Override
    protected BaseCompleteRecyclerAdapter buildAdapter() {
        return new BaseCompleteRecyclerAdapter<StringBean>(getContext(), this) {
            @Override
            protected void setItemLayout() {
                super.setItemLayout();
                addItemLayout(0, R.layout.item_view_open_camera);
            }
            @Override
            protected void onBindBaseViewHoder(BaseViewHoder holder, int position, final StringBean item) {
                holder.setText(R.id.contentTV, item.getText());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getListener().onInteractionAdapter(0, BundleUtils.putSerializable(item));
                    }
                });
            }
        };
    }
    @Override
    public void onInteractionAdapter(int action, Bundle bundle) {
        l.onInteractionWindow(action, tag, bundle);
        dismiss();
    }
}
