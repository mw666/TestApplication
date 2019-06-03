package newmatch.zbmf.com.testapplication.fragments;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.AppCompatImageView;
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
import newmatch.zbmf.com.testapplication.MainActivity;
import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.activitys.SearchActivity;
import newmatch.zbmf.com.testapplication.activitys.SelectCityActivity;
import newmatch.zbmf.com.testapplication.activitys.SettledActivity;
import newmatch.zbmf.com.testapplication.activitys.UserDetailActivity;
import newmatch.zbmf.com.testapplication.adapters.HomeGridAdapter;
import newmatch.zbmf.com.testapplication.assist.GlideUtil;
import newmatch.zbmf.com.testapplication.base.BaseFragment;
import newmatch.zbmf.com.testapplication.base.MyApplication;
import newmatch.zbmf.com.testapplication.component.PLog;
import newmatch.zbmf.com.testapplication.entity.BannerService;
import newmatch.zbmf.com.testapplication.interfaces.DianZanClickListener;
import newmatch.zbmf.com.testapplication.interfaces.HomeRVIvClick;
import newmatch.zbmf.com.testapplication.permissions.PermissionC;
import newmatch.zbmf.com.testapplication.presenter.presenterIml.BasePresenter;
import newmatch.zbmf.com.testapplication.presenter.backview.TestView;
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
public class HomeFragment extends BaseFragment implements HomeRVIvClick,
        TestView<BannerService, TestWanAndroidPresenter>,DianZanClickListener{

    private TextView mCurrentLocationTv;

    private TestWanAndroidPresenter mPresenter;
    private HomeGridAdapter mHomeGridAdapter;
    private int mToolTarH;
    //最初透明度设置为0.2
    private double i = 0.2;
    private double dynamicX = 4.5;
    private int minTopBtnX = 550;

    //数据
    BannerService mResult;
    private RecyclerView mHomeRV;
    private MainActivity mainActivity;
    private AppCompatImageView headIv;
    private TabLayout mainTab;


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
        TextView searchBtn = bindViewWithClick(R.id.searchBtn,true);
        TextView subArea = bindViewWithClick(R.id.subArea,true);
        //搜索图标  男性用户隐藏  女性用户显示
        mainTab = bindView(R.id.mainTab);
//        main_tab.setInlineLabel(true);
        mainTab.addTab(new TabLayout.Tab().setText("所有"));
        mainTab.addTab(new TabLayout.Tab().setText(" 新人"));


        mainActivity = (MainActivity)getActivity();


        // TODO: 2018/9/13 根据性别来呈现搜索入口或者入驻首页入口
        mHomeRV = bindView(R.id.homeRecyclerView);
        mHomeRV.setLayoutManager(new GridLayoutManager(getActivity(), 2,
                OrientationHelper.VERTICAL, false));
        mHomeGridAdapter = new HomeGridAdapter(getContext(),getActivity());
        mHomeGridAdapter.setHomeRVIvClick(this);
        mHomeGridAdapter.setDianZan(this);
        mHomeRV.setAdapter(mHomeGridAdapter);

    }

    //更新用户头像
    public void updateHeadImg(Uri uri){
        GlideUtil.loadCircleImage(getActivity(),
                R.drawable.ic_head_portrait_icon, uri,headIv);
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
                startActivityForResult(new Intent(getActivity(), SelectCityActivity.class), PermissionC.CURRENT_CITY_CODE);
                break;
            case R.id.searchBtn:
                //男性用户点击跳转搜索页面，女性用户点击跳转入驻页面
                SkipActivityUtil.skipDataActivity(getActivity(), SearchActivity.class, new Bundle());

//                SkipActivityUtil.skipDataActivity(getActivity(), SettledActivity.class, new Bundle());
                break;
            case R.id.searchIv:
                //搜索图标只对女性用户显示，点击跳转搜索页面
                SkipActivityUtil.skipDataActivity(getActivity(), SearchActivity.class, new Bundle());
                break;
            case R.id.subArea:
                //区域定位
                ToastUtils.showSingleToast(getContext(),"区域定位");
                break;
            case R.id.headIv:
                //打开侧滑菜单
                if (mainActivity!=null){
                    mainActivity.showMainMenu();
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
        mHomeGridAdapter.addImgList(result.getData());
    }

    @Override
    public void dianZanClick(ImageView view,TextView moodTv) {
        //设置点赞
        LikeGMClass.clickLike(getActivity(),view);
    }
}
