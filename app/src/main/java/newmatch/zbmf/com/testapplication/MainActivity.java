package newmatch.zbmf.com.testapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.github.ielse.imagewatcher.ImageWatcherHelper;

import java.util.ArrayList;
import java.util.List;

import newmatch.zbmf.com.testapplication.adapters.MainFragMentAdapter;
import newmatch.zbmf.com.testapplication.base.BaseActivity;
import newmatch.zbmf.com.testapplication.base.MyApplication;
import newmatch.zbmf.com.testapplication.fragments.DynamicFragment;
import newmatch.zbmf.com.testapplication.fragments.HomeFragment;
import newmatch.zbmf.com.testapplication.fragments.MsgFragment;
import newmatch.zbmf.com.testapplication.permissions.PermissionC;
import newmatch.zbmf.com.testapplication.utils.GetUIDimens;
import newmatch.zbmf.com.testapplication.utils.ShowImgUtils;
import newmatch.zbmf.com.testapplication.utils.TianShareUtil;
import newmatch.zbmf.com.testapplication.utils.Util;
import newmatch.zbmf.com.testapplication.views.GenericDrawerLayout;

/**
 * 取名甜甜圈吧
 * 首页
 */
public class MainActivity extends BaseActivity implements
        ViewPager.OnPageChangeListener
        , BottomNavigationView.OnNavigationItemSelectedListener {

    private ViewPager mViewPager;
    public ImageWatcherHelper mIwHelper;
    private BottomNavigationView bottomNavigationView;
    private GenericDrawerLayout genericdrawerlayout;

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected Integer layoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        boolean isTranslucentStatus = true;
        //图片放大所需
        mIwHelper = ShowImgUtils.init(this);
        bottomNavigationView = bindView(R.id.bottomNavigationView);
        mViewPager = bindView(R.id.viewPager);
        genericdrawerlayout = bindView(R.id.genericdrawerlayout);

        View menu = LayoutInflater.from(MainActivity.this).inflate(R.layout.main_menu_view, null);
        setMainMenu(genericdrawerlayout, menu);
        getMenuView(menu);


        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(HomeFragment.homgInstance(TianShareUtil.getCity()));
        fragmentList.add(DynamicFragment.dynamicInstance());
        fragmentList.add(MsgFragment.msgInstance());
        MainFragMentAdapter mainFragMentAdapter = new MainFragMentAdapter(getSupportFragmentManager()
                , fragmentList);
        mViewPager.setAdapter(mainFragMentAdapter);
        addListener();
        //初始ViewPager和bottomNavigationView的位置
        mViewPager.setCurrentItem(0);
        bottomNavigationView.setSelectedItemId(0);

        // 如果不是透明状态栏，你需要给ImageWatcher标记 一个偏移值，以修正点击ImageView查看的启动动画的Y轴起点的不正确
        mIwHelper.setTranslucentStatus(!isTranslucentStatus ? GetUIDimens.calcStatusBarHeight(this) : 0);


    }


    private void addListener() {
        mViewPager.addOnPageChangeListener(this);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
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
    public void onBackPressed() {
        if (!mIwHelper.handleBackPressed()) {
            super.onBackPressed();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //获取结果
        switch (requestCode) {
            case PermissionC.PIC_IMG_VIDEO_CODE:
//                //选择图片的结果
//                if (resultCode == Activity.RESULT_OK) {
//                    List<Uri> mSelected = Matisse.obtainResult(data);
//                    //更新mineFragment的用户头像
//                    mineFragment.updateMyAvatar(mSelected);
//                }
                break;
        }
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

    @Override
    public void onPageSelected(int position) {
        bottomNavigationView.getMenu().getItem(position).setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    //获取抽屉View的控件
    private void getMenuView(View menu) {

    }

    //设置侧滑抽屉
    private void setMainMenu(GenericDrawerLayout genericdrawerlayout, View menu) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        genericdrawerlayout.setContentLayout(menu);

        // 可以设置打开时响应Touch的区域范围
        if (mViewPager.getCurrentItem() == 0)
            genericdrawerlayout.setTouchSizeOfOpened(Util.dip2px(this, 120));
//        genericdrawerlayout.setTouchSizeOfClosed(Util.dip2px(this, 500));

        // 设置随着位置的变更，背景透明度也改变
        genericdrawerlayout.setOpaqueWhenTranslating(true);

        // 设置抽屉是否可以打开
//        genericdrawerlayout.setOpennable(false);

        // 设置抽屉的空白区域大小
        float v = /*getResources().getDisplayMetrics().density * 100 +*/ 0.5f; // 100DIP
        genericdrawerlayout.setDrawerEmptySize((int) v);
        // 设置黑色背景的最大透明度
        genericdrawerlayout.setMaxOpaque(0.4f);
        // 设置事件回调
        genericdrawerlayout.setDrawerCallback(new GenericDrawerLayout.DrawerCallbackAdapter() {
            @Override
            public void onStartOpen() {
                Log.i("===哈哈哈", "onStartOpen");
            }

            @Override
            public void onEndOpen() {
                Log.i("===哈哈哈", "onEndOpen");
            }

            @Override
            public void onStartClose() {
                Log.i("===哈哈哈", "onStartClose");
            }

            @Override
            public void onEndClose() {
                Log.i("===哈哈哈", "onEndClose");
            }

            @Override
            public void onPreOpen() {
                Log.i("===哈哈哈", "onPreOpen");
            }

            @Override
            public void onTranslating(int gravity, float translation, float fraction) {
                Log.i("===哈哈哈", "onTranslating gravity = " + gravity + "\n" +
                        " translation = " + translation + "\n fraction = " + fraction);
            }
        });
    }

    //显示侧滑菜单
    public void showMainMenu() {
        genericdrawerlayout.open();
    }
}
