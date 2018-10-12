package newmatch.zbmf.com.testapplication;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;

import com.github.ielse.imagewatcher.ImageWatcherHelper;

import java.util.ArrayList;
import java.util.List;

import newmatch.zbmf.com.testapplication.adapters.MainFragMentAdapter;
import newmatch.zbmf.com.testapplication.assist.BottomNavigationViewHelper;
import newmatch.zbmf.com.testapplication.base.BaseActivity;
import newmatch.zbmf.com.testapplication.base.MyApplication;
import newmatch.zbmf.com.testapplication.fragments.DynamicFragment;
import newmatch.zbmf.com.testapplication.fragments.HomeFragment;
import newmatch.zbmf.com.testapplication.fragments.MineFragment;
import newmatch.zbmf.com.testapplication.fragments.MsgFragment;
import newmatch.zbmf.com.testapplication.fragments.OuterAttentionFragment;
import newmatch.zbmf.com.testapplication.permissions.PermissionC;
import newmatch.zbmf.com.testapplication.utils.GetUIDimens;
import newmatch.zbmf.com.testapplication.utils.MyActivityManager;
import newmatch.zbmf.com.testapplication.utils.ShowImgUtils;
import newmatch.zbmf.com.testapplication.utils.TianShareUtil;

/**
 * 取名甜甜圈吧
 * 首页
 */
public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener,
        BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView mBottomNavigationView;

    private ViewPager mViewPager;
    public ImageWatcherHelper mIwHelper;

    @Override
    protected Integer layoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        boolean isTranslucentStatus = true;
        MyActivityManager.getMyActivityManager().pushAct(MainActivity.this);
        //图片放大所需
        mIwHelper = ShowImgUtils.init(this);
        mBottomNavigationView = bindView(R.id.bottomNavigationView);
        //取消自带平移的动画效果
        BottomNavigationViewHelper.disableShiftMode(mBottomNavigationView);
        mViewPager = bindView(R.id.viewPager);

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(HomeFragment.homgInstance(TianShareUtil.getCity()));
        fragmentList.add(DynamicFragment.dynamicInstance());
        fragmentList.add(MsgFragment.msgInstance());
        fragmentList.add(OuterAttentionFragment.outAttentionInstance());
        fragmentList.add(MineFragment.mineInstance());
        MainFragMentAdapter mainFragMentAdapter = new MainFragMentAdapter(getSupportFragmentManager()
                , fragmentList);
        mViewPager.setAdapter(mainFragMentAdapter);
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
        mViewPager.addOnPageChangeListener(this);

        mViewPager.setCurrentItem(0);
       //监听viewpager的滑动
        viewPagerScrollListener(mViewPager);
        // 如果不是透明状态栏，你需要给ImageWatcher标记 一个偏移值，以修正点击ImageView查看的启动动画的Y轴起点的不正确
        mIwHelper.setTranslucentStatus(!isTranslucentStatus ? GetUIDimens.calcStatusBarHeight(this) : 0);

        // 长按图片的回调，你可以显示一个框继续提供一些复制，发送等功能
//        mIwHelper.setOnPictureLongPressListener(this);
//        mIwHelper.setLoader(new SimpleLoader());


    }

    @Override
    protected void initData() {
    }

    @Override
    protected String initTitle() {
        return getString(R.string.application_title);
    }

    @Override
    protected Boolean showBackBtn() {
        return false;
    }

    @Override
    protected int topBarColor() {
        return MyApplication.getInstance().getResources().getColor(R.color.colorPrimary);
    }

    @Override
    protected void onViewClick(View view) {

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mBottomNavigationView.getMenu().getItem(position).setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                mViewPager.setCurrentItem(0);
                mBottomNavigationView.setVisibility(View.VISIBLE);
                break;
            case R.id.dynamic:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.msg:
                mViewPager.setCurrentItem(2);
                mBottomNavigationView.setVisibility(View.VISIBLE);
                break;
            case R.id.attention:
                mViewPager.setCurrentItem(3);
                mBottomNavigationView.setVisibility(View.VISIBLE);
                break;
            case R.id.mime:
                mViewPager.setCurrentItem(4);
                mBottomNavigationView.setVisibility(View.VISIBLE);
                break;
        }
        return true;
    }

    //监听ViewPager滑动
    private void viewPagerScrollListener(ViewPager viewPager){
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                    if (mBottomNavigationView.getVisibility()==View.GONE){
                        mBottomNavigationView.setAlpha(1f);
                        mBottomNavigationView.setVisibility(View.VISIBLE);
                    }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (!mIwHelper.handleBackPressed()) {
            super.onBackPressed();
        }
    }

    /**
     * 设置mBottomNavigationView的可见性
     *
     * @param isShow 可见性
     * @param alpha  透明度   1:不透明   0:全透明
     */
    public void setBottomViewVisible(Integer isShow, Float alpha) {
        if (isShow == PermissionC.BOTTOM_TAB_SHOW) {
            mBottomNavigationView.setAlpha(alpha);
            if (alpha <= 0.3f) {
                mBottomNavigationView.setVisibility(View.GONE);
            }
        } else if (isShow == PermissionC.BOTTOM_TAB_GONE) {
            mBottomNavigationView.setAlpha(alpha);
            if (alpha >= 1f) {
                mBottomNavigationView.setVisibility(View.VISIBLE);
            }
        }
    }

}
