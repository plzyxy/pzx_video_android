package com.txf.ui_mvplibrary.ui.view.banner;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.txf.ui_mvplibrary.R;
import com.txf.ui_mvplibrary.ui.view.BaseViewGroup;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author txf
 * @Title 轮播图控件基类 (自动切换)
 * 1.一张以上图片才自动切换
 * 2.手势切换时 停止自动切换 手势切换停止时 恢复自动切换
 * 3.适配下拉放大控件
 * @package com.bqy.ssqyb.ui.view
 * @date 2017/10/24/024
 */
public abstract class BaseBannerWidget<T> extends BaseViewGroup implements ViewPager.OnPageChangeListener, DelayedTask.OnDelayedTaskListener {
    private ViewPager mViewPager;
    private BasePagerAdapter mAdapter;
    private LinearLayout mViewPagerSelector;
    private DelayedTask mDelayedTask;
    private List<T> datas;
    private long delay = 3000;//自动切换默认时间
    private int mWidth, mHeight;
    private Scroller mScroller;

    public BaseBannerWidget(Context context) {
        super(context);
    }

    public BaseBannerWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseBannerWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onCreate(Context context) {
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
//        Logger.i("onSizeChanged ");
        //当View大小发生改变时  结束ViewPager的切换动画效果
        if (mScroller != null && !mScroller.isFinished()) {
            mViewPager.scrollTo(mScroller.getFinalX(), mScroller.getFinalY());
            mScroller.abortAnimation();
        }
//        if (w == mWidth && h == mHeight) {
//            //当View大小等于原始大小时 开始自动切换
//            startAutoAwitch();
//        } else {
//            //当View大小不等于原始大小时 停止自动切换
//            stopAutoAwitch();
//        }
    }


    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.libs_view_basebanner_widget, this);
        mViewPager = (ViewPager) findViewById(R.id.view_basebanner_widget_ViewPager);
        mViewPagerSelector = (LinearLayout) findViewById(R.id.view_basebanner_widget_ViewPagerSelector);

        ViewPagerScroller mPagerScroller = new ViewPagerScroller(getContext());
        mPagerScroller.initViewPagerScroll(mViewPager);
        mViewPager.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        stopAutoAwitch();
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        startAutoAwitch();
                        break;
                }
                return false;
            }
        });
        mViewPager.setAdapter(mAdapter = buildAdapter() == null ? new DefPagerAdapter() : buildAdapter());

        //通过反射获取mViewPager的mScroller属性值
        mScroller = getScroller();

    }

    private Scroller getScroller() {
        try {
            Field fs = mViewPager.getClass().getDeclaredField("mScroller");
            fs.setAccessible(true);
            if (fs.get(mViewPager) instanceof Scroller) {
                return (Scroller) fs.get(mViewPager);
            }
        } catch (NoSuchFieldException e) {
            return null;
        } catch (IllegalAccessException e) {
            return null;
        }
        return null;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        //view附加到窗口时  获取View的宽 高
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //view脱离窗口时 结束定时器
        if (mDelayedTask != null) {
            mDelayedTask.stopForwardTimer();
            mDelayedTask = null;
        }
    }

    /**
     * 结束自动切换
     */
    public void stopAutoAwitch() {
        if (mDelayedTask != null)
            mDelayedTask.stopForwardTimer();
    }

    /**
     * 开始自动切换
     */
    public void startAutoAwitch() {
        if (mDelayedTask != null)
            mDelayedTask.startForwardTimer(delay, 0);
    }

    public List<T> getDatas() {
        return datas;
    }

    public void setData(List<T> datas) {
        setData(datas, 3000);
    }

    /**
     * @param datas
     * @param delay 自动切换时间
     */
    public void setData(List<T> datas, long delay) {
        if (datas == null || datas.size() <= 0) {
            this.datas = new ArrayList<>();
            return;
        }
        this.delay = delay;
        this.datas = datas;
        mAdapter.setData(datas, true);
        //设置ViewPager相关
        setViewPager();
        //设置选择器相关
        setSelector();
    }

    private void setViewPager() {
        int defPos = 0;
        if (mAdapter.isLoop() && datas.size() > 1) {
            for (int i = Integer.MAX_VALUE / 2; i < Integer.MAX_VALUE; i++) {
                if (i % datas.size() == 0) {
                    defPos = i;
                    break;
                }
            }
            mDelayedTask = new DelayedTask(this);
        }
        mViewPager.setCurrentItem(defPos);
        startAutoAwitch();
    }

    private void setSelector() {
        //如果选择器为空
        if (buildSelectorDot() == null || !mAdapter.isLoop() || datas.size() <= 1) {
            mViewPager.removeOnPageChangeListener(this);
            return;
        }
        //添加选择器View
        mViewPager.addOnPageChangeListener(this);
        mViewPagerSelector.removeAllViews();
        for (int i = 0; i < datas.size(); i++) {
            mViewPagerSelector.addView(buildSelectorDot(), buildSelectorDotLayoutParams());
        }
        //设置选择器默认选中
        refreshPagerSelectorDot(0);
    }

    public int dip2px(float dipValue) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    private void refreshPagerSelectorDot(int pos) {
        int count = mViewPagerSelector.getChildCount();
        if (count == 0)
            return;
        for (int i = 0; i < count; i++) {
            View view = mViewPagerSelector.getChildAt(i);
            view.setBackgroundResource(buildSelectorDotCheckFalse());
        }
        View view = mViewPagerSelector.getChildAt(pos);
        view.setBackgroundResource(buildSelectorDotCheckTrue());
    }

    /**
     * 子类可重写
     * 未选中圆点
     */
    protected int buildSelectorDotCheckFalse() {
        return R.drawable.libs_dot_check_false;
    }

    /**
     * 子类可重写
     * 选中圆点
     */
    protected int buildSelectorDotCheckTrue() {
        return R.drawable.libs_dot_check_true;
    }

    /**
     * 构建选择器的圆点View
     * 如果不重写 就没有选择的小原点
     * 子类可重写
     */
    protected View buildSelectorDot() {
        return null;
    }

    /**
     * 构建选择器的圆点View的LayoutParams
     * 子类可重写
     */
    protected LinearLayout.LayoutParams buildSelectorDotLayoutParams() {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(dip2px(5), dip2px(5));
        lp.setMargins(0, 0, dip2px(3), 0);
        return lp;
    }

    /**
     * 构建PagerAdapter
     * 子类可重写
     */
    protected BasePagerAdapter buildAdapter() {
        return null;
    }

    /**
     * 构建Adapter的ItemView
     * 如果不重写 {@link BaseBannerWidget#buildAdapter()} 该方法必须重写
     */
    protected View getItemView(int position, View contentView, ViewGroup container) {
        return null;
    }


    /**
     * 如果不重写 {@link BaseBannerWidget#buildAdapter()} 则使用该Adapter
     */
    class DefPagerAdapter extends BasePagerAdapter<T> {
        @Override
        protected View getItemView(int position, View contentView, ViewGroup container) {
            return BaseBannerWidget.this.getItemView(position, contentView, container);
        }
    }

    public BasePagerAdapter getmAdapter() {
        return mAdapter;
    }

    /**
     * 定时器回调
     * OnDelayedTaskListener
     */
    @Override
    public void onHandleMessage(int msg) {
        switch (msg) {
            case 0:
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                startAutoAwitch();
                break;
        }
    }

    /**
     * ViewPager发生改变回调
     * OnPageChangeListener
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        refreshPagerSelectorDot(position % datas.size());
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
