package com.myexcel.view;


import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

/**
 * 名称：
 * 创建人：邹安富
 * 创建时间：2018/3/1
 * 详细说明：
 */
public class MyHorizontalScrollView extends HorizontalScrollView {

    // 自定义的监听器
    private OnHorizontalScrollListener listener;

    public MyHorizontalScrollView(Context context) {
        super(context);
    }

    public MyHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOnHorizontalScrollListener(OnHorizontalScrollListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        // 通知自定义的listener
        if (listener != null) {
            listener.onHorizontalScrolled(this, l, t, oldl, oldt);
        }
    }

    //内部接口，用来监听系统的onScrollChangedListener监听到的数据
    public interface OnHorizontalScrollListener {
        void onHorizontalScrolled(MyHorizontalScrollView view, int l, int t, int oldl, int oldt);
    }

    private Handler mHandler;
    private ScrollViewListener scrollViewListener;

    /**
     * 滚动状态   IDLE 滚动停止  TOUCH_SCROLL 手指拖动滚动         FLING滚动
     */
    public enum ScrollType {
        IDLE, TOUCH_SCROLL, FLING
    }

    /**
     * 记录当前滚动的距离
     */
    private int currentX = -9999999;
    /**
     * 当前滚动状态
     */
    private ScrollType scrollType = ScrollType.IDLE;
    /**
     * 滚动监听间隔
     */
    private int scrollDealy = 50;
    /**
     * 滚动监听runnable
     */
    private Runnable scrollRunnable = new Runnable() {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            if (getScrollX() == currentX) {
                //滚动停止  取消监听线程
                Log.d("", "停止滚动");
                scrollType = ScrollType.IDLE;
                if (scrollViewListener != null) {
                    scrollViewListener.onScrollChanged(scrollType);
                }
                mHandler.removeCallbacks(this);
                return;
            } else {
                //手指离开屏幕    view还在滚动的时候
                Log.d("", "Fling。。。。。");
                scrollType = ScrollType.FLING;
                if (scrollViewListener != null) {
                    scrollViewListener.onScrollChanged(scrollType);
                }
            }
            currentX = getScrollX();
            mHandler.postDelayed(this, scrollDealy);
        }
    };


    public interface ScrollViewListener {
        void onScrollChanged(ScrollType scrollType);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mHandler != null) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    this.scrollType = ScrollType.TOUCH_SCROLL;
                    scrollViewListener.onScrollChanged(scrollType);
                    //手指在上面移动的时候   取消滚动监听线程
                    mHandler.removeCallbacks(scrollRunnable);
                    break;
                case MotionEvent.ACTION_UP:
                    //手指移动的时候
                    mHandler.post(scrollRunnable);
                    break;
            }
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 必须先调用这个方法设置Handler  不然会出错
     */
    public void setHandler(Handler handler) {
        this.mHandler = handler;
    }

    /**
     * 设置滚动监听
     */
    public void setOnScrollStateChangedListener(ScrollViewListener listener) {
        this.scrollViewListener = listener;
    }
}
