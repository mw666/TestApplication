package newmatch.zbmf.com.testapplication.fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import newmatch.zbmf.com.testapplication.GMClass.LikeGMClass;
import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.activitys.SelectCityActivity;
import newmatch.zbmf.com.testapplication.activitys.SettledActivity;
import newmatch.zbmf.com.testapplication.activitys.UserDetailActivity;
import newmatch.zbmf.com.testapplication.adapters.HomeGridAdapter;
import newmatch.zbmf.com.testapplication.base.BaseFragment;
import newmatch.zbmf.com.testapplication.base.MyApplication;
import newmatch.zbmf.com.testapplication.component.PLog;
import newmatch.zbmf.com.testapplication.entity.BannerService;
import newmatch.zbmf.com.testapplication.interfaces.DianZanClickListener;
import newmatch.zbmf.com.testapplication.interfaces.HomeRVIvClick;
import newmatch.zbmf.com.testapplication.permissions.PermissionC;
import newmatch.zbmf.com.testapplication.presenter.BasePresenter;
import newmatch.zbmf.com.testapplication.presenter.TestView;
import newmatch.zbmf.com.testapplication.presenter.TestWanAndroidPresenter;
import newmatch.zbmf.com.testapplication.utils.GetUIDimens;
import newmatch.zbmf.com.testapplication.utils.SkipActivityUtil;
import newmatch.zbmf.com.testapplication.utils.ToastUtils;
import newmatch.zbmf.com.testapplication.utils.UnitUtils;

/**
 * A simple {@link Fragment} subclass.
 * 首页Fragment
 * <p>
 * 测试网络结构
 */
public class HomeFragment extends BaseFragment implements HomeRVIvClick, TestView<BannerService, TestWanAndroidPresenter>
,DianZanClickListener{

    private TextView mCurrentLocationTv;

    private TestWanAndroidPresenter mPresenter;
    private HomeGridAdapter mHomeGridAdapter;
    private int mToolTarH;
    //最初透明度设置为0.2
    private double i = 0.2;
    private double dynamicX = 4.5;
    private int minTopBtnX = 550;
    private Button mTopBtn;
    //可改变图片背景
    private ImageView mTopIv;

    //数据
    BannerService mResult;
    private RecyclerView mHomeRV;
    private View mView;
    private Toolbar mHomeToolBar;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment homgInstance(String currentLocation) {
        HomeFragment homeFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PermissionC.CURRENT_CIRT, currentLocation);
        homeFragment.setArguments(bundle);
        return homeFragment;
    }

    @Override
    protected Integer layoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        mCurrentLocationTv = bindViewWithClick(R.id.leftToolbarTv, true);
        Bundle bundle = getArguments();
        if (bundle != null) {
            String currentCity = bundle.getString(PermissionC.CURRENT_CIRT);
            mCurrentLocationTv.setText(currentCity);
        }
        mHomeToolBar = bindView(R.id.homeToolBar);
        mHomeToolBar.setTitle("");

        mHomeToolBar.setAlpha((float) i);
        mView = bindViewWithClick(R.id.view, true);
        mView.setAlpha((float) i);
        mCurrentLocationTv.setVisibility(View.VISIBLE);
//        TextView topbar_title = bindView(R.id.topbar_title);
//        topbar_title.setAlpha((float) i);
//        topbar_title.setVisibility(View.INVISIBLE);
//        topbar_title.setText(getString(R.string.application_title));
        mTopIv = bindViewWithClick(R.id.topIv, true);

        mTopBtn = bindViewWithClick(R.id.topBtn, true);
        mTopBtn.setVisibility(View.VISIBLE);
        mTopBtn.setAlpha((float) (0.7));

        // TODO: 2018/9/13 根据性别来呈现搜索入口或者入驻首页入口
        mHomeRV = bindView(R.id.homeRecyclerView);
        mHomeRV.setLayoutManager(new GridLayoutManager(getActivity(), 2,
                OrientationHelper.VERTICAL, false));
        mHomeGridAdapter = new HomeGridAdapter(getContext(),getActivity());
        mHomeGridAdapter.setHomeRVIvClick(this);
        mHomeGridAdapter.setDianZan(this);
        mHomeRV.setAdapter(mHomeGridAdapter);

        //监听recyclerView的滑动
        rvScroll();
    }

    private void rvScroll() {
        mHomeRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int totalDy = 0;
            private int lastY = 0;
            private int distanceL = 0;
            private int topBtnWidth = 0;
            private int topBtnWidth_start = 0;
            private int dynamic = 0;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //滑动的单位是dp
                if (mToolTarH == 0) {
                    mToolTarH = UnitUtils.pxToDp(getActivity(), mHomeToolBar.getMeasuredHeight());
                    //获取topBtn的长度
                    topBtnWidth = topBtnWidth_start = mTopBtn.getMeasuredWidth();
//                    PLog.LogD("--   topBtnWidth  :" + topBtnWidth);
                    distanceL = mToolTarH + UnitUtils.pxToDp(getActivity(), GetUIDimens.getStatusH(getActivity()));
                }
                totalDy -= dy;
                int deltaY = UnitUtils.pxToDp(getActivity(), totalDy);
                int deltaL = deltaY - lastY;
                lastY = deltaY;
                int absY = Math.abs(deltaY);
//                PLog.LogD("--   滑动的距离 :" + absY + "   distanceL :" + distanceL);
                if (deltaL > 0) {
//                    PLog.LogD("-- 向下滑动 ");
                    /*if (absY >= 25) {
                        topbar_title.setVisibility(View.INVISIBLE);
                        mTopBtn.setVisibility(View.VISIBLE);
                    }*/
                    if (absY >= distanceL) {
                        i = (float) dynamic / 100;
                        if (topBtnWidth_start < topBtnWidth) {
                            topBtnWidth_start += dynamicX;
                            if (topBtnWidth_start >= topBtnWidth) {
                                topBtnWidth_start = topBtnWidth;
                            }
                            btnTranslate(topBtnWidth_start);
                        }
                    } else {
                        i = (float) absY / 100;
                        if (topBtnWidth_start < topBtnWidth) {
                            topBtnWidth_start += dynamicX;
                            if (topBtnWidth_start >= topBtnWidth) {
                                topBtnWidth_start = topBtnWidth;
                            }
                            btnTranslate(topBtnWidth_start);
                        }
                        if (i <= 0.2f) {
                            i = 0.2f;
                        }
                    }
                } else if (deltaL < 0) {
//                    PLog.LogD("-- 向上滑动 ");
                    /*if (absY < 25) {
                        topbar_title.setVisibility(View.VISIBLE);
                        mTopBtn.setVisibility(View.INVISIBLE);
                    }*/
                    if (absY < distanceL) {
                        i = (float) absY / 100 + 0.2f;
                        topBtnWidth_start -= dynamicX;
                        if (topBtnWidth_start <= minTopBtnX) {
                            //最小长度
                            topBtnWidth_start = minTopBtnX;
                        }
                        btnTranslate(topBtnWidth_start);
                        if (i > 1f) {
                            i = 1;
                        }
                        dynamic = absY;
                    } else if (absY >= distanceL) {
                        i = 1;
                        dynamic = distanceL;

                    }
                }
                //0 是全透明
                mHomeToolBar.setAlpha((float) i);
                mView.setAlpha((float) i);
//                topbar_title.setAlpha((float) i);
//                mTopBtn.setAlpha((float) (1 - i));
            }
        });
    }

    private void btnTranslate(int x/*, int y*/) {
        PLog.LogD("--   传入的长度  :" + x);
        ViewGroup.LayoutParams lp = mTopBtn.getLayoutParams();
        lp.width = x;//注意:单位是像素
        mTopBtn.setLayoutParams(lp);
    }

    @Override
    protected void initData() {
        mPresenter.doLoadData();
    }

    @Override
    protected BasePresenter initPresenter() {
        //初始化presenter
        mPresenter = new TestWanAndroidPresenter(this);
        return mPresenter;
    }

    @Override
    protected void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.leftToolbarTv:
                //跳转选择地址页面
                startActivityForResult(new Intent(getActivity(), SelectCityActivity.class), PermissionC.CURRENT_CITY_CODE);
                break;
            case R.id.topBtn:
                //跳转搜索页面或者开通入驻首页展示页面
                ToastUtils.showSingleToast(MyApplication.getInstance(), "点击了topBtn");
                /**
                 * 此处暂时展示搜索跳转页面--->当接入接口后，再分情况是跳转搜索还是跳转入驻首页页面
                 */
//                SkipActivityUtil.skipDataActivity(getActivity(), SearchActivity.class, new Bundle());
                SkipActivityUtil.skipDataActivity(getActivity(), SettledActivity.class, new Bundle());
                break;
            case R.id.topIv:
                //点击事件传递不进来
                ToastUtils.showSingleToast(MyApplication.getInstance(), "点击了背景图片");
                break;
            case R.id.view:
                ToastUtils.showSingleToast(MyApplication.getInstance(), "点击了view");
                break;
        }
    }

    @Override
    protected Boolean setViewEnterStatuBar() {
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PermissionC.CURRENT_CITY_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    String city = data.getStringExtra(PermissionC.USER_CITY);
                    mCurrentLocationTv.setText(city);
                    // TODO: 2018/9/13 获取当前所选城市的数据 ---


                }
                break;
        }
    }

    @Override
    public void rvIvCallBack(int position) {
        //首页图片点击的回调
        Bundle bundle = new Bundle();
        ArrayList<BannerService.Data> data = mResult.getData();
        bundle.putParcelableArrayList(PermissionC.USER_PIC, data);
        SkipActivityUtil.skipDataActivity(getActivity(), UserDetailActivity.class, bundle);
    }

    @Override
    public void setPresenter(TestWanAndroidPresenter presenter) {
        //设置presenter
        this.mPresenter = presenter;
    }

    @Override
    public void resultCallBack(BannerService result) {
        this.mResult = result;
        //返回结果
        mHomeGridAdapter.addImgList(result.getData());
    }

    @Override
    public void dianZanClick(ImageView view,TextView moodTv) {
        //设置点赞
        LikeGMClass.clickLike(getActivity(),view);
    }
}
