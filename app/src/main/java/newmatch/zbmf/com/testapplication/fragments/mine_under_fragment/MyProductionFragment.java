package newmatch.zbmf.com.testapplication.fragments.mine_under_fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.greenrobot.eventbus.EventBus;

import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.adapters.ProductionAdapter;
import newmatch.zbmf.com.testapplication.base.BaseFragment;
import newmatch.zbmf.com.testapplication.base.MyApplication;
import newmatch.zbmf.com.testapplication.events.RVScrollEvent;
import newmatch.zbmf.com.testapplication.presenter.presenterIml.BasePresenter;
import newmatch.zbmf.com.testapplication.utils.ToastUtils;

/**
 * A simple {@link Fragment} subclass.
 * 我的作品fragment
 */
public class MyProductionFragment extends BaseFragment implements ProductionAdapter.ProductionIvClick {


    private RVScrollEvent mRvScrollEvent;

    public MyProductionFragment() {
    }

    public static MyProductionFragment instance() {
        MyProductionFragment fragment = new MyProductionFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected Integer layoutId() {
        return R.layout.fragment_my_production;
    }

    @Override
    protected void initView() {
        RecyclerView mineProductionRV = bindView(R.id.mineProductionRV);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3,
                OrientationHelper.VERTICAL, false);
        mineProductionRV.setLayoutManager(layoutManager);
//        mineProductionRV.setHasFixedSize(true);
//        mineProductionRV.setNestedScrollingEnabled(false);
        ProductionAdapter productionAdapter = new ProductionAdapter(getActivity());
        productionAdapter.setProductionIvClick(this);
        mineProductionRV.setAdapter(productionAdapter);
        mineProductionRV.requestLayout();

        mRvScrollEvent = new RVScrollEvent();
        //监听recyclerView的滑动
        mineProductionRV.addOnScrollListener(new RecyclerView.OnScrollListener() {

            private boolean mScrollVertically;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
//                PLog.LogD("=====   状态 newState:" + newState);

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    //向上滑动
                    //recyclerView.canScrollVertically(1);返回false表示不能往上滑动，即代表到底部了；
                    mScrollVertically = mineProductionRV.canScrollVertically(1);
                    if (!mScrollVertically) {
                        //已经上滑到底部了
                        mRvScrollEvent.setRVState(RVScrollEvent.UP_REACH_BOTTOM);
                        EventBus.getDefault().post(mRvScrollEvent);
                    } else {
                        //向上滑，还未到达底部
                        mRvScrollEvent.setRVState(RVScrollEvent.UP_NO_REACH_BOTTOM);
                        EventBus.getDefault().post(mRvScrollEvent);
                    }
                } else if (dy < 0) {
                    //向下滑
                    //recyclerView.canScrollVertically(-1);返回false表示不能往下滑动，即代表到顶部了；
                    mScrollVertically = mineProductionRV.canScrollVertically(-1);
                    if (!mScrollVertically) {
                        //向下滑已经到达顶部
                        mRvScrollEvent.setRVState(RVScrollEvent.DOWN_REACH_TOP);
                        EventBus.getDefault().post(mRvScrollEvent);
                    } else {
                       //向下滑未到达顶部
                        mRvScrollEvent.setRVState(RVScrollEvent.DOWN_NO_REACH_TOP);
                        EventBus.getDefault().post(mRvScrollEvent);
                    }
                }

//                PLog.LogD("=====      RV滑动  :  dy : " + dy + "       mScrollVertically  : " + mScrollVertically);
            }
        });

    }

    @Override
    protected void initData() {

    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void onViewClick(View view) {

    }

    @Override
    protected Boolean setViewEnterStatuBar() {
        return false;//没有toolBar和状态栏
    }


    @Override
    public void productionIvClick() {
        // TODO: 2018/11/13 跳转到短视频播放页面
        ToastUtils.showSingleToast(MyApplication.getInstance(), "跳转到短视频播放页面");
    }
}
