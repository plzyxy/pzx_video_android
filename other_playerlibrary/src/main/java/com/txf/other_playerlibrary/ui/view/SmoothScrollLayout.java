package com.txf.other_playerlibrary.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
import android.widget.Scroller;


/**
 * @author txf
 * @create 2019/2/14 0014
 * 1.手势控制 子视图 左右滑动
 * 2.左右超过%50 子视图全部移出屏幕外 否则回弹至初始位置
 * 3.可设置 //不响应触摸范围(此范围内 触摸事件将向下传递)
 */
public class SmoothScrollLayout extends RelativeLayout {
    //不响应触摸范围
    private int mLeft;
    private int mTop;
    private int mRight;
    private int mBottom;
    /////////////////////////////////////////
//    //右滑视图X轴的最大坐标(暂时获取屏幕的宽度)
//    private int mMaxRightX;
//    //左滑视图X轴的最大坐标(暂时获取屏幕的宽度)
//    private int mMaxLeftX;

    //用于不断更新视图x轴坐标(手指移动会不断重置该值)
    private int mLastX;

    private Scroller mScroller;
    //手势探测
    private GestureDetector mDetector;
    //轻扫最小水平距离
    private int minDistance = 20;
    //轻扫右
    private boolean isFlingRight;
    //轻扫左
    private boolean isFlingLeft;

    private int mScrollX;

    ////////////////////////////
    public SmoothScrollLayout(Context context) {
        super(context);
        init(context);
    }

    public SmoothScrollLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SmoothScrollLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mScroller = new Scroller(context);
        mDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                float _velocityX = Math.abs(velocityX);
                if (e1.getX() - e2.getX() > minDistance && _velocityX > 2000) {
                    isFlingLeft = true;
                    isFlingRight = false;
                } else if (e2.getX() - e1.getX() > minDistance && _velocityX > 2000) {
                    isFlingLeft = false;
                    isFlingRight = true;
                }
                return true;
            }
        });
    }

    @Override
    protected void onDetachedFromWindow() {
        if (!mScroller.isFinished())
            mScroller.abortAnimation();
        super.onDetachedFromWindow();
    }

    /**
     * 设置不响应触摸范围(此范围内 触摸事件将向下传递)
     *
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public void setNotTouch(int left, int top, int right, int bottom) {
        mLeft = left;
        mTop = top;
        mRight = right;
        mBottom = bottom;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        //横竖切换 滚动至初始状态
        int startX = getScrollX();
        mScroller.startScroll(startX, 0, -startX, 0, 500);
        if (startX > 0) {
            mScroller.startScroll(startX, 0, -startX, 0, 500);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        if (!isNotTouch(x, y))
            mDetector.onTouchEvent(event);
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                if (isNotTouch(x, y))
                    return false;
                if (!mScroller.isFinished()) // 如果上次的调用没有执行完就取消。
                    mScroller.abortAnimation();
                mLastX = x;
                return true;
            }
            case MotionEvent.ACTION_MOVE: {
                // 计算手指此时的坐标和上次的坐标滑动的距离。
                int dx = x - mLastX;
                mLastX = x;
                mScrollX = getScrollX();
                if (mScrollX >= 0 && dx <= 0) {
                    return true;
                }
                scrollBy(-dx, 0);
                return true;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL: {
                int width = getMeasuredWidth();
                int startX = getScrollX();
                //右滑
                if (startX < 0 && startX < -width * 0.5 && !isFlingLeft || isFlingRight) {
                    isFlingRight = false;
                    mScroller.startScroll(startX, 0, -width - startX, 0, 500);

                    //回弹
                } else {
                    isFlingLeft = false;
                    mScroller.startScroll(startX, 0, -startX, 0, 500);
                }
                invalidate();
                return true;
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 判断触摸不响应范围
     */
    public boolean isNotTouch(float _x, float _y) {
        if (_x >= mLeft && _x <= mRight && _y >= mTop && _y <= mBottom) {
            //点击不响应触摸范围不做处理
            return true;
        }
        return false;
    }

    /**
     * 这个方法在调用了invalidate()后被回调。
     */
    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) { // 计算新位置，并判断上一个滚动是否完成。
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            mScrollX = getScrollX();
            invalidate();// 再次调用computeScroll。
        }
    }
}
