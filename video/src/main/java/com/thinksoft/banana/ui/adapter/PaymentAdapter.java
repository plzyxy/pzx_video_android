package com.thinksoft.banana.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.thinksoft.banana.R;
import com.thinksoft.banana.app.Constant;
import com.thinksoft.banana.entity.bean.RechargeListBean;
import com.thinksoft.banana.entity.item.PaymentItem;
import com.txf.ui_mvplibrary.interfaces.OnAppListener;
import com.txf.ui_mvplibrary.ui.adapter.BaseCompleteRecyclerAdapter;
import com.txf.ui_mvplibrary.ui.adapter.hoder.BaseViewHoder;

/**
 * @author txf
 * @create 2019/2/20 0020
 * @
 */
public class PaymentAdapter extends BaseCompleteRecyclerAdapter<PaymentItem> {
    public PaymentAdapter(Context context) {
        super(context);
    }

    public PaymentAdapter(Context context, OnAppListener.OnAdapterListener listener) {
        super(context, listener);
    }

    @Override
    protected void setItemLayout() {
        super.setItemLayout();
        addItemLayout(Constant.TYPE_ITEM_1, R.layout.item_payment_page_type);//页面类型
        addItemLayout(Constant.TYPE_ITEM_2, R.layout.item_payment_money);//支付金额
        addItemLayout(Constant.TYPE_ITEM_3, R.layout.item_payment_pay_type);//支付类型
        addItemLayout(Constant.TYPE_ITEM_4, R.layout.item_payment_pay_button);//支付确认按钮
    }

    @Override
    public int getItemViewType(int position) {
        return getDatas().get(position).getItemType();
    }


    BundleItem mBundleItem2;

    /**
     * 获取选中的 支付金额
     */
    public PaymentItem getPayMentItem() {
        return mBundleItem2.item;
    }

    BundleItem mBundleItem3;

    /**
     * 获取选中的 支付类型
     */
    public PaymentItem getPayType() {
        return mBundleItem3.item;
    }

    @Override
    protected void onBindBaseViewHoder(BaseViewHoder holder, int position, final PaymentItem item) {
        switch (getItemViewType(position)) {
            case Constant.TYPE_ITEM_1:
                holder.setText(R.id.tv1, (String) item.getData());
                break;
            case Constant.TYPE_ITEM_2:
                bindItem2(holder, position, item);
                break;
            case Constant.TYPE_ITEM_3:
                bindItem3(holder, position, item);
                break;
            case Constant.TYPE_ITEM_4:
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getListener().onInteractionAdapter(Constant.TYPE_ITEM_4, null);
                    }
                });

                break;
        }
    }

    private void bindItem3(BaseViewHoder holder, int position, final PaymentItem item) {

        TextView typeTV = holder.getViewById(R.id.typeTV);
        final View iconView = holder.getViewById(R.id.iconView);
        String typeString = (String) item.getData();
        typeTV.setText(typeString);

        if (typeString.equals(getString(R.string.微信支付))) {
            typeTV.setCompoundDrawablesRelativeWithIntrinsicBounds(ContextCompat.getDrawable(getContext(), R.drawable.icon_pay_wx),
                    null, null, null);
        } else if (typeString.equals(getString(R.string.支付宝支付))) {
            typeTV.setCompoundDrawablesRelativeWithIntrinsicBounds(ContextCompat.getDrawable(getContext(), R.drawable.icon_pay_zfb),
                    null, null, null);
        }


        if (item.isCheck()) {
            mBundleItem3 = new BundleItem();
            mBundleItem3.BundleItem3(iconView, item);
            mBundleItem3.setItem3Check(true);
        } else {
            iconView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.icon_sel_flase));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.isCheck())
                    return;
                mBundleItem3.setItem3Check(false);
                mBundleItem3 = new BundleItem();
                mBundleItem3.BundleItem3(iconView, item);
                mBundleItem3.setItem3Check(true);
            }
        });
    }

    private void bindItem2(BaseViewHoder holder, int position, final PaymentItem item) {
        RechargeListBean bean = (RechargeListBean) item.getData();
        final TextView tv1 = holder.getViewById(R.id.tv1);
        final TextView tv2 = holder.getViewById(R.id.tv2);
        tv1.setText(bean.getRmb() + getString(R.string.元));
        tv2.setText(bean.getDiamond() + getString(R.string.砖));

        if (item.isCheck()) {
            mBundleItem2 = new BundleItem();
            mBundleItem2.BundleItem2(tv1, tv2, holder.itemView, item);
            mBundleItem2.setItem2Check(true);
        } else {
            tv1.setTextColor(0xff12bc1a);
            tv2.setTextColor(0xff12bc1a);
            holder.itemView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.icon_rect_12bc1a_false));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.isCheck())
                    return;
                mBundleItem2.setItem2Check(false);
                mBundleItem2 = new BundleItem();
                mBundleItem2.BundleItem2(tv1, tv2, v, item);
                mBundleItem2.setItem2Check(true);
            }
        });
    }

    class BundleItem {
        public TextView tv1;
        public TextView tv2;
        public View view;
        public PaymentItem item;

        public void BundleItem3(View view, PaymentItem item) {
            this.view = view;
            this.item = item;
        }

        public void BundleItem2(TextView tv1, TextView tv2, View view, PaymentItem item) {
            this.tv1 = tv1;
            this.tv2 = tv2;
            this.view = view;
            this.item = item;
        }

        public void setItem3Check(boolean check) {
            item.setCheck(check);
            if (check) {
                view.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.icon_sel_true));
            } else {
                view.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.icon_sel_flase));
            }
        }

        public void setItem2Check(boolean check) {
            item.setCheck(check);
            if (check) {
                tv1.setTextColor(0xffffffff);
                tv2.setTextColor(0xffffffff);
                view.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.icon_rect_12bc1a_true));
            } else {
                tv1.setTextColor(0xff12bc1a);
                tv2.setTextColor(0xff12bc1a);
                view.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.icon_rect_12bc1a_false));
            }
        }
    }

}
