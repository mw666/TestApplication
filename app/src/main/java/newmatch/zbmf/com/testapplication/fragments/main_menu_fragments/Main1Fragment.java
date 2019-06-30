package newmatch.zbmf.com.testapplication.fragments.main_menu_fragments;


import android.annotation.TargetApi;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;

import com.github.ielse.imagewatcher.ImageWatcherHelper;

import java.util.ArrayList;
import java.util.List;

import newmatch.zbmf.com.testapplication.MainActivity;
import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.adapters.pager_fragment_adapters.MainFragMentAdapter;
import newmatch.zbmf.com.testapplication.base.BaseFragment;
import newmatch.zbmf.com.testapplication.fragments.dynamic_fragments.DynamicFragment;
import newmatch.zbmf.com.testapplication.fragments.MsgFragment;
import newmatch.zbmf.com.testapplication.fragments.home_fragments.HomeFragment;
import newmatch.zbmf.com.testapplication.presenter.presenterIml.BasePresenter;
import newmatch.zbmf.com.testapplication.utils.GetUIDimens;
import newmatch.zbmf.com.testapplication.utils.ShowImgUtils;
import newmatch.zbmf.com.testapplication.utils.TianShareUtil;
import newmatch.zbmf.com.testapplication.views.NoScrollViewPager;

/**
 * A simple {@link Fragment} subclass.
 */
@RequiresApi(api = Build.VERSION_CODES.M)
public class Main1Fragment extends BaseFragment implements
        ViewPager.OnPageChangeListener
        , BottomNavigationView.OnNavigationItemSelectedListener {

    private MainActivity mainActivity;
    private NoScrollViewPager mViewPager;
    public ImageWatcherHelper mIwHelper;
    private BottomNavigationView bottomNavigationView;
    private HomeFragment homeFragment;


    public Main1Fragment() {
        // Required empty public constructor
    }


    @Override
    protected Integer layoutId() {
        return R.layout.fragment_main1;
    }

    @Override
    protected void initView() {
        //获取宿主Activity对象
        mainActivity = (MainActivity) getActivity();
        boolean isTranslucentStatus = true;
        //图片放大所需
        mIwHelper = ShowImgUtils.init(getActivity());
        bottomNavigationView = bindView(R.id.bottomNavigationView);
        mViewPager = bindView(R.id.viewPager);


        List<Fragment> fragmentList = new ArrayList<>();
        homeFragment = HomeFragment.homgInstance(TianShareUtil.getCity());
        fragmentList.add(homeFragment);
        fragmentList.add(DynamicFragment.dynamicInstance());
        fragmentList.add(MsgFragment.msgInstance());
        MainFragMentAdapter mainFragMentAdapter = new MainFragMentAdapter(getActivity()
                .getSupportFragmentManager(), fragmentList);
        mViewPager.setAdapter(mainFragMentAdapter);
        addListener();
        //初始ViewPager和bottomNavigationView的位置
        mViewPager.setCurrentItem(0);
        bottomNavigationView.setSelectedItemId(0);
        // 如果不是透明状态栏，你需要给ImageWatcher标记 一个偏移值，以修正点击ImageView查看的启动动画的Y轴起点的不正确
        mIwHelper.setTranslucentStatus(!isTranslucentStatus ?
                GetUIDimens.calcStatusBarHeight(getActivity()) : 0);
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
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = 0;
        switch (item.getItemId()) {
            case R.id.home:
                id = 0;
                break;
            case R.id.dynamic:
                id = 1;
                break;
            case R.id.msg:
                id = 2;
                break;
        }
        mViewPager.setCurrentItem(id, false);
        return true;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    @Override
    public void onPageSelected(int position) {
        bottomNavigationView.getMenu().getItem(position).setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    //添加监听事件
    private void addListener() {
        mViewPager.addOnPageChangeListener(this);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    //更新首页子fragment的用户头像
    public void updateUserHeader(Uri uri) {
        if (uri != null && homeFragment != null)
            homeFragment.updateUserHeader(uri);
    }

    //切换MainActivity的ViewPager的选择项
    public void selectViewPager(int pos) {
        if (mainActivity != null)
            mainActivity.selectViewPager(pos);
    }


}
