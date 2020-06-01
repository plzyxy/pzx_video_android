package com.thinksoft.banana.ui.view.banner;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.thinksoft.banana.R;
import com.thinksoft.banana.app.Constant;
import com.thinksoft.banana.entity.bean.circle.HttpImgBean;
import com.txf.ui_mvplibrary.ui.view.banner.BaseBannerWidget;
import com.txf.ui_mvplibrary.utils.BundleUtils;
import com.txf.ui_mvplibrary.utils.FrescoUtils;


/**
 * @author txf
 * @create 2019/2/23 0023
 * @
 */
public class ImgsWidget extends BaseBannerWidget<HttpImgBean> {

    public ImgsWidget(Context context) {
        super(context);
    }

    public ImgsWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImgsWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected View buildSelectorDot() {
        return new View(getContext());
    }

    @Override
    protected View getItemView(int position, View contentView, ViewGroup container) {
        View view;
        if (contentView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.item_banner_img, null);
        } else {
            view = contentView;
        }
        final HttpImgBean bean = getDatas().get(position);
        SimpleDraweeView mSimpleDraweeView = view.findViewById(R.id.SimpleDraweeView);
        mSimpleDraweeView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getListener().onInteractionView(Constant.ACTION_IMG, BundleUtils.putSerializable(bean));
            }
        });
        FrescoUtils.setGifImgUrl(mSimpleDraweeView, bean.getMax());
        return view;
    }
}
