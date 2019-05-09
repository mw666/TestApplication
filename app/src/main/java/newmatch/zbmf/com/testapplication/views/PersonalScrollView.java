package newmatch.zbmf.com.testapplication.views;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;

import newmatch.zbmf.com.testapplication.component.PLog;
import newmatch.zbmf.com.testapplication.events.RVScrollEvent;

/**
 * Created by **
 * on 2018/11/7.
 * <p>
 * 带有阻尼的ScrollView
 * <p>
 * view在Android视图中的坐标关系:https://www.cnblogs.com/tangs/articles/5864092.html
 */

public class PersonalScrollView extends NestedScrollView {

    private static final String TAG = "BounceScrollView";
    //----头部收缩属性--------
    // 记录首次按下位置
    private float mFirstPosition = 0;
    // 头部图片是否正在放大
    private Boolean mScaling = false;
    private View dropZoomView;//需要被放大的view
    private int dropZoomViewWidth;
    private int dropZoomViewHeight;
    //----头部收缩属性end--------
    //------尾部收缩属性--------
    private View inner;// 子View
    private float y;// 点击时y坐标
    private Rect normal = new Rect();// 矩形(这里只是个形式，只是用于判断是否需要动画.)
    private boolean isCount = false;// 是否开始计算
    //最后的坐标
    private float lastX = 0;
    private float lastY = 0;
    //当前坐标
    private float currentX = 0;
    private float currentY = 0;
    //移动的坐标量
    private float distanceX = 0;
    private float distanceY = 0;
    private boolean upDownSlide = false; //判断上下滑动的flag

    /**************************************/
//    private TabLayout mineTabLayout;
//    private Toolbar mToolbar;
    private RelativeLayout mFl;
    private RelativeLayout mHeadRv;
    private int[] locationToolBar = new int[2];
    private int[] locationFl = new int[2];
    private int mToolBarH;
    //toolBar开始逐渐显示的Y坐标的位置
    private int startShowToolBarY = 0;
    private float mToolbarY;
    private float mTabLayoutY;
    private float scale;
    private int alpha;

    //------尾部收缩属性end--------
    public PersonalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public void setTabLayout(TabLayout tabLayout, Toolbar toolbar, RelativeLayout fl, RelativeLayout headRv) {
//        mineTabLayout = tabLayout;
//        mToolbar = toolbar;
        mFl = fl;
        mHeadRv = headRv;
    }

    private Integer RVState;

    public void setRVState(Integer rvState) {
        this.RVState = rvState;
    }

    private boolean isUp = true;

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (t > oldt) {
            // 上滑
            isUp = true;
        } else {
            //下滑
            isUp = false;
        }
    }

    boolean intercepted = false;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        PLog.LogD("===  onInterceptTouchEvent  执行 ");
        int scrollY = getScrollY();
        PLog.LogD("===    移动的Y距离              :  " + scrollY);
//        if (mToolbar != null && mToolBarH == 0) {
//            mToolBarH = mToolbar.getMeasuredHeight();
//        }
//        if (mineTabLayout != null) {
//            mineTabLayout.getLocationOnScreen(locationFl);
//            mTabLayoutY = locationFl[1];
//        }
//        PLog.LogD("===    mFl  的坐标  :  " + mTabLayoutY + "     toolBar的高度 :" + mToolBarH);
        int i = ev.getAction() & MotionEvent.ACTION_MASK;
//        float rawY = ev.getRawY();
//        float y = ev.getY();
//        PLog.LogD("===     rawY:"+rawY+"--     y :"+y);
        switch (i) {
            case MotionEvent.ACTION_DOWN:
                intercepted = false;
                //初始化mActivePointerId
                super.onInterceptTouchEvent(ev);
                break;
            case MotionEvent.ACTION_MOVE:
                if (isUp) {
                    if (mTabLayoutY > mToolBarH) {
                        intercepted = true;//拦截
                    } else {
//                        mToolbar.setVisibility(View.VISIBLE);
                        intercepted = false;//传递给子控件
                    }
                    if (mToolbarY>=mToolBarH&&mToolBarH<=(mToolBarH+50)){
                        //在这个范围区间，显示mToolBar
                        PLog.LogD("===    mFl  的坐标  :  " + mTabLayoutY);

                    }
                } else {
                    if (mTabLayoutY <= mToolBarH) {
                        //并且recyclerView的内容不处于顶端---》滑动权在子控件，否则拦截
                        //判断RecyclerView是否滑动到了底部或顶部  https://blog.csdn.net/msn465780/article/details/77101966
//                        mToolbar.setVisibility(View.VISIBLE);
                        if (RVState== RVScrollEvent.DOWN_REACH_TOP){
                            //RV内容下滑到顶了
                            intercepted=true;
                        }else if (RVState == RVScrollEvent.DOWN_NO_REACH_TOP){
                            //RV内容还没下滑到顶部，可以继续滑动
                            intercepted=false;
                        }
                    }else {
                        intercepted=true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                intercepted = false;
                break;
        }
        return intercepted;
    }

    /*    //滑动拦截
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //获取toolBar左上角的Y坐标（即：Y方向距离屏幕顶部的距离）,获取滑动的距离来控制tollBar的透明度
        // ，以及滑动事件的拦截，谁来获取焦点
        if (mToolBarH == 0) {
            mToolBarH = mToolbar.getMeasuredHeight();
        }
//        PLog.LogD("===  获取到的ToolBar的高度 ：" + mToolBarH);
        if (mToolbarY == 0 && mToolbar != null) {
            mToolbar.getLocationOnScreen(locationToolBar);
            mToolbarY = locationToolBar[1];
        }
//        PLog.LogD("----  toolBar 相对于屏幕的 Y 坐标:" + mToolbarY);
        if (startShowToolBarY == 0) {
            //toolBar显示与否的临界值
            startShowToolBarY = (int) (mToolbarY - mToolBarH);
        }
        PLog.LogD("----  toolBar显示与否的临界值 Y 坐标:" + startShowToolBarY);
        //获取滑动的距离
        int scrollY = getScrollY();
        PLog.LogD("====    滚动的距离  :" + scrollY);
        if (mineTabLayout != null) {
            mineTabLayout.getLocationOnScreen(locationToolBar);
            mTabLayoutY = locationToolBar[1];
//            PLog.LogD("----  TabLayout 相对于屏幕的 Y 坐标:" + y);
        }
        if (mFl != null) {
            mFl.getLocationOnScreen(locationFl);
            float y = locationFl[1];
//            PLog.LogD("----  mFl 相对于屏幕的 Y 坐标:" + y);
        }
        if (mToolbar != null) {
            mToolbar.getLocationOnScreen(locationToolBar);
            mToolbarY = locationToolBar[1];
        }
        boolean intercepted = false;
        int action = ev.getAction() & MotionEvent.ACTION_MASK;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                intercepted = false;
                //初始化mActivePointerId
                super.onInterceptTouchEvent(ev);
                break;
            case MotionEvent.ACTION_MOVE:
                if (scrollY < startShowToolBarY||mToolbarY<0) {
                    mToolbar.setVisibility(View.INVISIBLE);//隐藏
                    scale=0;
                    intercepted = true;
                } else if (scrollY >= startShowToolBarY && scrollY <= mToolbarY) {
                    mToolbar.setVisibility(View.VISIBLE);//显示
                    //根据滑动的距离改变透明度--->alpha: 0 全透明  1 不透明
                    scale = (float) (scrollY - startShowToolBarY) / mToolBarH;//向上滑逐渐接近于1，向下滑逐渐接近于0
                    if (scale < 1) {
                        intercepted = true;
                    } else if (scale >= 1) {
                        scale = 1;
                        intercepted = false;
                    }
                    mToolbar.setAlpha(scale);
                } else if (scrollY > mToolbarY) {
                    scale = 1;
                    mToolbar.setVisibility(View.VISIBLE);//显示
                    mToolbar.setAlpha(scale);
                    intercepted = false;
                }
//                if (*//*mToolbarY == 0 ||*//*mTabLayoutY == mToolBarH) {
//                    scale = 1;
//                    mToolbar.setAlpha(scale);
//                    intercepted=false;
//                }
                break;
            case MotionEvent.ACTION_UP:
                intercepted = false;
                break;
        }
        return intercepted;
    }*/

    //初始化
    private void init() {
        setOverScrollMode(OVER_SCROLL_NEVER);
        if (getChildAt(0) != null) {
            inner = getChildAt(0);//这个是底部收缩的view
            //头部收缩的
            ViewGroup vg = (ViewGroup) getChildAt(0);
            if (vg.getChildAt(0) != null) {
                dropZoomView = vg.getChildAt(0);
            }
        }
    }

    /***
     * 生成视图工作完成.该函数在生成视图的最后调用，在所有子视图添加完之后. 即使子类覆盖了 onFinishInflate
     * 方法，也应该调用父类的方法，使该方法得以执行.
     */
    @Override
    protected void onFinishInflate() {
        //初始化
        init();
        super.onFinishInflate();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //这里只是计算尾部坐标
        currentX = ev.getX();
        currentY = ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                distanceX = currentX - lastX;
                distanceY = currentY - lastY;
                if (Math.abs(distanceX) < Math.abs(distanceY) && Math.abs(distanceY) > 12) {
                    upDownSlide = true;
                }
                break;
        }
        lastX = currentX;
        lastY = currentY;
        if (upDownSlide && inner != null) commOnTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    /***
     * 触摸事件
     *
     * @param ev
     */
    public void commOnTouchEvent(MotionEvent ev) {
        //头部缩放计算
        if (dropZoomViewWidth <= 0 || dropZoomViewHeight <= 0) {
            dropZoomViewWidth = dropZoomView.getMeasuredWidth();
            dropZoomViewHeight = dropZoomView.getMeasuredHeight();
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                //手指离开后头部恢复图片
                mScaling = false;
                replyImage();
                // 手指松开尾部恢复
                if (isNeedAnimation()) {
                    animation();
                    isCount = false;
                }
                clear0();
                break;
            //这里头尾分开处理，互不干扰
            case MotionEvent.ACTION_MOVE:
                //尾部处理
                final float preY = y;// 按下时的y坐标
                float nowY = ev.getY();// 时时y坐标
                int deltaY = (int) (preY - nowY);// 滑动距离
                if (!isCount) {
                    deltaY = 0; // 在这里要归0.
                }
                y = nowY;
                // 当滚动到最上或者最下时就不会再滚动，这时移动布局
                if (isNeedMove()) {
                    // 初始化头部矩形
                    if (normal.isEmpty()) {
                        // 保存正常的布局位置
                        normal.set(inner.getLeft(), inner.getTop(),
                                inner.getRight(), inner.getBottom());
                    }
                    // 移动布局
                    inner.layout(inner.getLeft(), inner.getTop() - deltaY / 2,
                            inner.getRight(), inner.getBottom() - deltaY / 2);
                }
                isCount = true;
                //尾部处理end
                //头部处理
                if (!mScaling) {
                    if (getScrollY() == 0) {
                        mFirstPosition = ev.getY();// 滚动到顶部时记录位置，否则正常返回
                    } else {
                        break;
                    }
                }
                int distance = (int) ((ev.getY() - mFirstPosition) * 0.6); // 滚动距离乘以一个系数
                if (distance < 0) { // 当前位置比记录位置要小，正常返回
                    break;
                }
                // 处理放大
                mScaling = true;
                setZoom(1 + distance);
                //头部处理end
                break;
        }
    }

    /***
     * 回缩动画,尾部往下缩动画
     */
    public void animation() {
        // 开启移动动画
        TranslateAnimation ta = new TranslateAnimation(0, 0, inner.getTop(),
                normal.top);
        ta.setDuration(200);
        inner.startAnimation(ta);
        // 设置回到正常的布局位置
        inner.layout(normal.left, normal.top, normal.right, normal.bottom);
        normal.setEmpty();
    }

    // 是否需要开启动画
    public boolean isNeedAnimation() {
        return !normal.isEmpty();
    }

    // 回弹动画，header往上缩动画 (使用了属性动画)
    public void replyImage() {
        final float distance = dropZoomView.getMeasuredWidth() - dropZoomViewWidth;
        // 设置动画
        ValueAnimator anim = ObjectAnimator.ofFloat(0.0F, 1.0F).setDuration((long) (distance * 0.7));
        anim.addUpdateListener(animation -> {
            float cVal = (Float) animation.getAnimatedValue();
            setZoom(distance - ((distance) * cVal));
        });
        anim.start();
    }

    //头部缩放
    public void setZoom(float s) {
        if (dropZoomViewHeight <= 0 || dropZoomViewWidth <= 0) {
            return;
        }
        ViewGroup.LayoutParams lp = dropZoomView.getLayoutParams();
        lp.width = (int) (dropZoomViewWidth + s);
        lp.height = (int) (dropZoomViewHeight * ((dropZoomViewWidth + s) / dropZoomViewWidth));
        dropZoomView.setLayoutParams(lp);
    }

    /***
     * 是否需要移动布局 inner.getMeasuredHeight():获取的是控件的总高度
     *
     * getHeight()：获取的是屏幕的高度
     *
     * @return
     */
    public boolean isNeedMove() {
        int offset = inner.getMeasuredHeight() - getHeight();
        int scrollY = getScrollY();
        // 0是顶部，后面那个是底部
        if (scrollY == 0 || scrollY == offset) {
            return true;
        }
        return false;
    }

    //清理尾部属性值
    private void clear0() {
        lastX = 0;
        lastY = 0;
        distanceX = 0;
        distanceY = 0;
        upDownSlide = false;
    }
}
