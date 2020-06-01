package com.txf.ui_mvplibrary.ui.view.window;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.txf.ui_mvplibrary.R;
import com.txf.ui_mvplibrary.interfaces.OnAppListener;
import com.txf.ui_mvplibrary.ui.adapter.BaseCompleteRecyclerAdapter;

import java.util.List;

import razerdp.basepopup.BasePopupWindow;

/**
 * @author txf
 * @create 2019/2/19 0020
 */
public abstract class BaseBottomListWindow<T> extends BasePopupWindow {
    protected RecyclerView mRecyclerView;
    protected OnAppListener.OnWindowListener l;
    protected BaseCompleteRecyclerAdapter mAdapter;

    protected int tag;//用于区分不同中心弹窗回调
    protected Object o;

    public void setData(List<T> datas) {
        mAdapter.setDatas(datas);
        mAdapter.notifyDataSetChanged();
    }

    protected int dip2px(float dipValue) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public BaseBottomListWindow(Context context) {
        super(context);
        init();
    }

    private void init() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter = buildAdapter());
    }

    protected abstract BaseCompleteRecyclerAdapter buildAdapter();

    public void setOnBackListener(OnAppListener.OnWindowListener l, int tag) {
        setOnBackListener(l, tag, null);
    }

    public void setOnBackListener(OnAppListener.OnWindowListener l, int tag, Object o) {
        this.tag = tag;
        this.l = l;
        this.o = o;
    }

    // 必须实现，这里返回您的contentView
    // 为了让库更加准确的做出适配，强烈建议使用createPopupById()进行inflate
    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.libs_view_popu_base_bottom_list);
    }
    // 以下为可选代码（非必须实现）
    // 返回作用于PopupWindow的show和dismiss动画，本库提供了默认的几款动画，这里可以自由实现

    Animation alphaAnimation;

    @Override
    protected Animation onCreateShowAnimation() {
        alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(250);
        alphaAnimation.setInterpolator(new AccelerateInterpolator());
        return alphaAnimation;
    }
    @Override
    protected Animation onCreateDismissAnimation() {
        alphaAnimation = new AlphaAnimation(1, 0);
        alphaAnimation.setDuration(250);
        alphaAnimation.setInterpolator(new AccelerateInterpolator());
        return alphaAnimation;
    }
}
