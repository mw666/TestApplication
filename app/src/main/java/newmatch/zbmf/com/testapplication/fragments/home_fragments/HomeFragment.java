package newmatch.zbmf.com.testapplication.fragments.home_fragments;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import newmatch.zbmf.com.testapplication.GMClass.LikeGMClass;
import newmatch.zbmf.com.testapplication.MainActivity;
import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.activitys.SearchActivity;
import newmatch.zbmf.com.testapplication.activitys.SelectCityActivity;
import newmatch.zbmf.com.testapplication.activitys.UserDetailActivity;
import newmatch.zbmf.com.testapplication.adapters.pager_fragment_adapters.ViewPagerAdapter;
import newmatch.zbmf.com.testapplication.assist.GlideUtil;
import newmatch.zbmf.com.testapplication.base.BaseFragment;
import newmatch.zbmf.com.testapplication.entity.BannerService;
import newmatch.zbmf.com.testapplication.fragments.main_menu_fragments.Main1Fragment;
import newmatch.zbmf.com.testapplication.fragments.main_menu_fragments.Main2Fragment;
import newmatch.zbmf.com.testapplication.interfaces.DianZanClickListener;
import newmatch.zbmf.com.testapplication.interfaces.HomeRVIvClick;
import newmatch.zbmf.com.testapplication.permissions.PermissionC;
import newmatch.zbmf.com.testapplication.presenter.TestWanAndroidPresenter;
import newmatch.zbmf.com.testapplication.presenter.backview.TestView;
import newmatch.zbmf.com.testapplication.presenter.presenterIml.BasePresenter;
import newmatch.zbmf.com.testapplication.utils.SkipActivityUtil;
import newmatch.zbmf.com.testapplication.utils.ToastUtils;


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
    private TabLayout mainTab;
    private ViewPager viewPager;
    //fragment的集合数据
    private List<Fragment> fragments;


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
        TextView subArea = bindViewWithClick(R.id.subArea, true);
        //搜索图标  男性用户隐藏  女性用户显示
        mainTab = bindView(R.id.mainTab);
        viewPager = bindView(R.id.viewPager);
        mainTab.setupWithViewPager(viewPager, true);
        //准备fragment集合list的数据
        List<Fragment> fragmentList = initFragments();
        String[] titles = new String[]{"精华", "新人"};
        //        MyFragmentStatePagerAdapter mainFragMentAdapter = new MyFragmentStatePagerAdapter(getActivity()
        //                .getSupportFragmentManager(), fragmentList, Arrays.asList(titles));

        List<Fragment> mFragment = new ArrayList<>();
        if (mFragment.size() == 0) {
            mFragment.add(new Main2Fragment());
            mFragment.add(new Main1Fragment());
        }
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager()
                , mFragment);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);


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
                //跳转选择地址页面
                startActivityForResult(new Intent(getActivity(), SelectCityActivity.class),
                        PermissionC.CURRENT_CITY_CODE);
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
            case R.id.subArea:
                //区域定位
                ToastUtils.showSingleToast(getContext(), "区域定位");
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
}
