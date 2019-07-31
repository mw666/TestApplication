package newmatch.zbmf.com.testapplication.fragments.home_fragments;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import newmatch.zbmf.com.testapplication.GMClass.LikeGMClass;
import newmatch.zbmf.com.testapplication.GMClass.selector.GMSelector;
import newmatch.zbmf.com.testapplication.MainActivity;
import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.activitys.SearchActivity;
import newmatch.zbmf.com.testapplication.activitys.UserDetailActivity;
import newmatch.zbmf.com.testapplication.adapters.pager_fragment_adapters.MyFragmentStatePagerAdapter;
import newmatch.zbmf.com.testapplication.base.BaseFragment;
import newmatch.zbmf.com.testapplication.callback.DianZanClickListener;
import newmatch.zbmf.com.testapplication.callback.HomeRVIvClick;
import newmatch.zbmf.com.testapplication.component.BannerViewHolderType;
import newmatch.zbmf.com.testapplication.entity.BannerService;
import newmatch.zbmf.com.testapplication.fragments.main_menu_fragments.Main1Fragment;
import newmatch.zbmf.com.testapplication.fragments.main_menu_fragments.Main2Fragment;
import newmatch.zbmf.com.testapplication.permissions.PermissionC;
import newmatch.zbmf.com.testapplication.presenter.TestWanAndroidPresenter;
import newmatch.zbmf.com.testapplication.presenter.backview.TestView;
import newmatch.zbmf.com.testapplication.presenter.presenterIml.BasePresenter;
import newmatch.zbmf.com.testapplication.utils.SkipActivityUtil;
import newmatch.zbmf.com.testapplication.utils.ToastUtils;
import newmatch.zbmf.com.testapplication.utils.glidUtils.CollapsingToolbarLayoutState;
import newmatch.zbmf.com.testapplication.utils.glidUtils.GlideUtil;
import newmatch.zbmf.com.testapplication.views.customViewPager.BannerViewHolder;
import newmatch.zbmf.com.testapplication.views.customViewPager.MZBannerView;
import newmatch.zbmf.com.testapplication.views.customViewPager.MZHolderCreator;


/**
 * A simple {@link Fragment} subclass.
 * 首页Fragment
 * <p>
 * 测试网络结构
 */
public class HomeFragment extends BaseFragment implements HomeRVIvClick,
        TestView<BannerService, TestWanAndroidPresenter>, DianZanClickListener {

    private TextView mCurrentLocationTv;

    private TestWanAndroidPresenter mPresenter;
    //    private HomeGridAdapter mHomeGridAdapter;
    private int mToolTarH;

    //数据
    BannerService mResult;
    private RecyclerView mHomeRV;
    private MainActivity mainActivity;
    private AppCompatImageView headIv;
    //fragment的集合数据
    private List<Fragment> fragments;
    private MZBannerView homeBanner;
    private AppBarLayout homeAppBar;
    private Toolbar homeToolBar;
    private CollapsingToolbarLayoutState state;

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
        //获取宿主Activity
        mainActivity = (MainActivity) getActivity();
        //定位的控件
        mCurrentLocationTv = bindViewWithClick(R.id.locationIcon, true);
        Bundle bundle = getArguments();
        if (bundle != null) {
            String currentCity = bundle.getString(PermissionC.CURRENT_CIRT);
            mCurrentLocationTv.setText(currentCity);
        }
        mCurrentLocationTv.setVisibility(View.VISIBLE);

        //头像
        headIv = bindViewWithClick(R.id.headIv, true);
        AppCompatImageView searchIv = bindViewWithClick(R.id.searchIv, true);
        TextView searchBtn = bindViewWithClick(R.id.searchBtn, true);
        homeToolBar = bindView(R.id.homeToolBar);
        homeBanner = bindView(R.id.homeBanner);
        homeAppBar = bindView(R.id.homeAppBar);
        //搜索图标  男性用户隐藏  女性用户显示
        TabLayout mainTab = bindView(R.id.mainTab);
        ViewPager viewPager = bindView(R.id.viewPager);
        mainTab.setupWithViewPager(viewPager, true);


        //模拟设置tab的数据
        setTabContent(mainTab,viewPager);
        //模拟轮播的数据
        initBannerView(homeBanner);
        appBarLayoutSH();

    }

    //模拟准备设置tab的内容
    private void setTabContent(TabLayout mainTab,ViewPager viewPager){
        //准备fragment集合list的数据
        List<Fragment> fragmentList = initFragments();
        String[] titles = new String[]{"精华", "新人"};
        MyFragmentStatePagerAdapter mainFragMentAdapter =
                new MyFragmentStatePagerAdapter(getChildFragmentManager(),
                        fragmentList, Arrays.asList(titles));
        List<Fragment> mFragment = new ArrayList<>();
        if (mFragment.size() == 0) {
            mFragment.add(new Main2Fragment());
            mFragment.add(new Main1Fragment());
        }
        viewPager.setAdapter(mainFragMentAdapter);
        mainTab.setupWithViewPager(viewPager, true);
        viewPager.setCurrentItem(0);
    }

    //模拟准备banner的数据
    private void initBannerView(MZBannerView homeBanner) {
        int[] imgs = {R.drawable.m3, R.drawable.m2, R.drawable.m6, R.drawable.m4};

        List<Integer> imgList;
        imgList = new ArrayList<>();
        for (int i = 0; i < imgs.length; i++) {
            imgList.add(imgs[i]);
        }
        homeBanner.addPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) { }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        homeBanner.setIndicatorVisible(true);
        // 代码中更改indicator 的位置
        homeBanner.setIndicatorAlign(MZBannerView.IndicatorAlign.LEFT);
        homeBanner.setIndicatorPadding(15, 0, 0, 15);
        homeBanner.setPages(imgList, (MZHolderCreator<BannerViewHolder>)
                () -> new BannerViewHolder(imgList.size(), BannerViewHolderType.ConnerViewHolder));
        //Banner的点击事件
        homeBanner.setBannerPageClickListener((view, position) -> {
            ToastUtils.showSquareTvToast(getContext(), "点击了Banner页面：" + position);
        });


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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.locationIcon:
                //跳转选择地址页面  区域定位
                GMSelector.instance(getActivity()).showPickerView((province, city, area) -> {
                    //返回的依次是省市区
                    mCurrentLocationTv.setText(area);
                });
//                startActivityForResult(new Intent(getActivity(), SelectCityActivity.class),
//                        PermissionC.CURRENT_CITY_CODE);
                break;
            case R.id.searchBtn:
                //男性用户点击跳转搜索页面，女性用户点击跳转入驻页面
                SkipActivityUtil.skipDataActivity(getActivity(), SearchActivity.class, new Bundle());
                //女性用户跳珠入驻页面
                //                SkipActivityUtil.skipDataActivity(getActivity(), SettledActivity.class, new Bundle());
                break;
            case R.id.searchIv:
                //搜索图标只对女性用户显示，点击跳转搜索页面
                SkipActivityUtil.skipDataActivity(getActivity(), SearchActivity.class, new Bundle());
                break;
            case R.id.headIv:
                //打开侧滑菜单
                if (mainActivity != null) {
                    mainActivity.selectViewPager(0);
                }
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
        //        mHomeGridAdapter.addImgList(result.getData());
    }

    @Override
    public void dianZanClick(ImageView view, TextView moodTv) {
        //设置点赞
        LikeGMClass.clickLike(getActivity(), view);
    }

    //更新用户头像
    public void updateUserHeader(Uri headUri) {
        if (headUri != null)
            GlideUtil.loadCircleImage(getActivity(), R.drawable.ic_head_portrait_icon,
                    headUri, headIv);
    }

    //准备fragment的数据
    private List<Fragment> initFragments() {
        if (fragments == null) {
            fragments = new ArrayList<>();
        }
        if (fragments.size() > 0) {
            fragments.clear();
        }
        fragments.add(new Home1Fragment());
        fragments.add(new Home2Fragment());
        return fragments;
    }

    //设置appBar关合的效果
    private void appBarLayoutSH() {
        homeAppBar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            if (verticalOffset == 0) {
                if (state != CollapsingToolbarLayoutState.EXPANDED) {
                    state = CollapsingToolbarLayoutState.EXPANDED;//修改状态标记为展开
                    homeToolBar.setVisibility(View.GONE);
                }
            } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                if (state != CollapsingToolbarLayoutState.COLLAPSED) {
                    homeToolBar.setVisibility(View.VISIBLE);
                    state = CollapsingToolbarLayoutState.COLLAPSED;//修改状态标记为折叠
                }
            } else {
                if (state != CollapsingToolbarLayoutState.INTERNEDIATE) {
                    if (state == CollapsingToolbarLayoutState.COLLAPSED) {
                        homeToolBar.setVisibility(View.GONE);
                    }
                    state = CollapsingToolbarLayoutState.INTERNEDIATE;//修改状态标记为中间
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        homeBanner.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        homeBanner.pause();
    }
}
