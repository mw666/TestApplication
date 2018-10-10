package newmatch.zbmf.com.testapplication.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Scroller;

import newmatch.zbmf.com.testapplication.component.PLog;
import newmatch.zbmf.com.testapplication.utils.UnitUtils;

/**
 * Created by **
 * on 2018/9/27.
 */

public class MyScrollView extends ScrollView {
    private Context mContext;
    private Integer mH;
    private Scroller mScroller;

    public MyScrollView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        if (mScroller == null) {
            mScroller = new Scroller(mContext);
        }
    }

    public void setScrollH(int h) {
        mH = UnitUtils.pxToDp(mContext, h);
    }

    /*一个事件包含一个down和一个up，并包含若干个move事件*/

    //拦截状态
    private Boolean isIntercept = true;//初始状态：拦截
    //获取down时候的Y坐标
    private int downY;
    //每次移动时候的Y坐标
    private int moveY;
    //每次Y方向移动的距离
    private int deltaY;
    //记录上一次的y坐标
    private int lastInterceptY = 0;
    //记录累计的y方向滑动的距离
    private int countY = 0;

    //标记事件的第一次移动距离
    private Boolean isFirst = true;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //记录最初始的Y坐标
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                /*if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();//中止滑动动画
                }*/
                isIntercept = false;
                //获取down时候的Y坐标
                downY = (int) ev.getY();
                PLog.LogD("  onIntercept  down时的Y坐标 :" + downY);
                break;
            case MotionEvent.ACTION_MOVE:
                moveY = (int) ev.getY();
                if (!isFirst) {
                    deltaY = moveY - lastInterceptY;
                    int i = UnitUtils.pxToDp(mContext, deltaY);
                    countY += i;//累计距离
                    //滑动的方向根据当前的Y坐标与上一次的Y坐标的差值的正负来决定，
                    //当向上滑动时,累计的滑动距离大于mH(同城推荐的高度),就将事件处理权交给内层的RecyclerView，否则-->SC滑动
                    //当向下滑动时,累计的滑动距离小于或等于mH（同城推荐的高度）,就将滑动权交给外层的ScrollView,否则-->RV滑动
                    PLog.LogD("---》》》     累计的滑动距离:    " + countY + "====   >>>    i:" + i);
                    if (countY < 0) {
                        //向上滑动
                        PLog.LogD("向上滑动");
                        int abs = Math.abs(countY);
                        if (abs > mH) {
                            isIntercept = false;//不拦截事件
                        } else {
                            isIntercept = true;//拦截事件
                            this.scrollTo(0, 90);
//                            mScroller.startScroll(0, getScrollY(), 0, i, 500);
                            invalidate();
                        }
                    } else {
                        //向下滑动
                        PLog.LogD("向下滑动");
                        int abs = Math.abs(countY);
                        if (abs <= mH) {
                            isIntercept = true;//拦截事件
                            this.scrollTo(0, 90);
//                            mScroller.startScroll(0, getScrollY(), 0, i, 500);
                            invalidate();
                        } else {
                            isIntercept = false;
                        }
                    }
                }
                lastInterceptY = moveY;
                isFirst = false;
                break;
            case MotionEvent.ACTION_UP:
                countY = 0;
//                if (countY == 0) {
                    isIntercept = true;//恢复最初的状态
//                }
                isFirst = true;
                break;
        }
        return isIntercept;//拦截
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return super.onStartNestedScroll(child, target, nestedScrollAxes);
    }

    @Override
    public void stopNestedScroll() {
        super.stopNestedScroll();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        float touchY = ev.getY();
//        PLog.LogD("  onTouchEvent  DOWN: 落下的Y  " + touchY);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:
                float y = ev.getY();

                break;
            case MotionEvent.ACTION_UP:
                //设置滑动
//                scrollTo(0, 30);
//                mScroller.startScroll(0, 0, 0, -30, 500);
//                mScroller.abortAnimation();

                break;
        }
        onInterceptTouchEvent(ev);
        return super.onTouchEvent(ev);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }


}
