package com.txf.ui_mvplibrary.ui.view;

import android.content.Context;
import android.util.AttributeSet;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.txf.ui_mvplibrary.app.UiConfig;

/**
 * @author txf
 * @create 2019/1/29 0029
 * @
 */
public class RefreshLayout extends SmartRefreshLayout {
    public RefreshLayout(Context context) {
        super(context);
        init();
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    ClassicsHeader classicsHeader;
    ClassicsFooter classicsFooter;

    public ClassicsFooter getClassicsFooter() {
        return classicsFooter;
    }

    public ClassicsHeader getClassicsHeader() {
        return classicsHeader;
    }

    private void init() {
        setDragRate(1f);//显示下拉高度/手指真实下拉高度=阻尼效果
        setReboundDuration(200);//回弹动画时长（毫秒）

        classicsHeader = new ClassicsHeader(getContext());
        classicsHeader.setAccentColor(UiConfig.getInstance().getRefreshColor() == 0 ? 0xffd76dbb : UiConfig.getInstance().getRefreshColor());//设置颜色
        setRefreshHeader(classicsHeader);//设置 Header

        setHeaderHeight(60);//Header标准高度（显示下拉高度>=标准高度 触发刷新）
        setHeaderMaxDragRate(1.5f);//最大显示下拉高度/Header标准高度
        setHeaderTriggerRate(1f);//触发刷新距离 与 HeaderHeight 的比率1.0.4

        classicsFooter = new ClassicsFooter(getContext());
        classicsFooter.setAccentColor(UiConfig.getInstance().getRefreshColor() == 0 ? 0xffd76dbb : UiConfig.getInstance().getRefreshColor());//设置颜色
        setRefreshFooter(classicsFooter);//设置 Footer
        setFooterHeight(60);//Footer标准高度（显示上拉高度>=标准高度 触发加载）
        setFooterMaxDragRate(1.5f);//最大显示下拉高度/Footer标准高度
        setFooterTriggerRate(1f);//触发加载距离 与 FooterHeight 的比率1.0.4
    }
}
