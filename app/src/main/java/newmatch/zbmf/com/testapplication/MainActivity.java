package newmatch.zbmf.com.testapplication;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.ielse.imagewatcher.ImageWatcherHelper;
import com.zhihu.matisse.Matisse;

import java.util.ArrayList;
import java.util.List;

import newmatch.zbmf.com.testapplication.adapters.MainFragMentAdapter;
import newmatch.zbmf.com.testapplication.base.BaseActivity;
import newmatch.zbmf.com.testapplication.base.MyApplication;
import newmatch.zbmf.com.testapplication.fragments.DynamicFragment;
import newmatch.zbmf.com.testapplication.fragments.HomeFragment;
import newmatch.zbmf.com.testapplication.fragments.MineFragment;
import newmatch.zbmf.com.testapplication.fragments.MsgFragment;
import newmatch.zbmf.com.testapplication.permissions.PermissionC;
import newmatch.zbmf.com.testapplication.utils.GetUIDimens;
import newmatch.zbmf.com.testapplication.utils.MyActivityManager;
import newmatch.zbmf.com.testapplication.utils.ShowImgUtils;
import newmatch.zbmf.com.testapplication.utils.TianShareUtil;
import newmatch.zbmf.com.testapplication.utils.UnitUtils;

/**
 * 取名甜甜圈吧
 * 首页
 */
public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener ,View.OnClickListener{

    private ViewPager mViewPager;
    public ImageWatcherHelper mIwHelper;
    private MineFragment mineFragment;
    private TextView mHomeBottomItem;
    private TextView mDynamicBottomItem;
    private ImageView mPushContent;
    private TextView mMsgBottomItem;
    private TextView mMineBottomItem;

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
//        LinearLayout bottomTabView = bindView(R.id.bottomTabView);
        mHomeBottomItem = findViewById(R.id.homeBottomItem);
        mDynamicBottomItem = findViewById(R.id.dynamicBottomItem);
        mPushContent = findViewById(R.id.pushContent);
        mMsgBottomItem = findViewById(R.id.msgBottomItem);
        mMineBottomItem = findViewById(R.id.mineBottomItem);
        addListener();
        mViewPager = bindView(R.id.viewPager);

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(HomeFragment.homgInstance(TianShareUtil.getCity()));
        fragmentList.add(DynamicFragment.dynamicInstance());
        fragmentList.add(MsgFragment.msgInstance());
        mineFragment = MineFragment.mineInstance();
        fragmentList.add(mineFragment);
        MainFragMentAdapter mainFragMentAdapter = new MainFragMentAdapter(getSupportFragmentManager()
                , fragmentList);
        mViewPager.setAdapter(mainFragMentAdapter);
        mViewPager.addOnPageChangeListener(this);

        mViewPager.setCurrentItem(0);
        initSelectHome();
        //监听viewpager的滑动
        viewPagerScrollListener(mViewPager);
        // 如果不是透明状态栏，你需要给ImageWatcher标记 一个偏移值，以修正点击ImageView查看的启动动画的Y轴起点的不正确
        mIwHelper.setTranslucentStatus(!isTranslucentStatus ? GetUIDimens.calcStatusBarHeight(this) : 0);

        // 长按图片的回调，你可以显示一个框继续提供一些复制，发送等功能
//        mIwHelper.setOnPictureLongPressListener(this);
//        mIwHelper.setLoader(new SimpleLoader());


    }

    private void addListener(){
        mHomeBottomItem.setOnClickListener(this);
        mDynamicBottomItem.setOnClickListener(this);
        mPushContent.setOnClickListener(this);
        mMsgBottomItem.setOnClickListener(this);
        mMineBottomItem.setOnClickListener(this);
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

    //设置icon
    private void setTvIcon(int flag) {
        switch (flag) {
            case 0:
                initSelectHome();
                break;
            case 1:
                mHomeBottomItem.setCompoundDrawablesWithIntrinsicBounds(null, getResources().
                        getDrawable(R.drawable.home_icon), null, null);
                mDynamicBottomItem.setCompoundDrawablesWithIntrinsicBounds(null, getResources().
                        getDrawable(R.drawable.dynamic_light_icon), null, null);
                mMsgBottomItem.setCompoundDrawablesWithIntrinsicBounds(null, getResources().
                        getDrawable(R.drawable.msg_icon), null, null);
                mMineBottomItem.setCompoundDrawablesWithIntrinsicBounds(null, getResources().
                        getDrawable(R.drawable.mine_icon), null, null);
                //设置字体的大小
                mHomeBottomItem.setTextSize(UnitUtils.spToPx(MainActivity.this, 4));
                mDynamicBottomItem.setTextSize(UnitUtils.spToPx(MainActivity.this, 5));
                mMsgBottomItem.setTextSize(UnitUtils.spToPx(MainActivity.this, 4));
                mMineBottomItem.setTextSize(UnitUtils.spToPx(MainActivity.this, 4));
                mHomeBottomItem.setTextColor(getResources().getColor(R.color.black));
                mDynamicBottomItem.setTextColor(getResources().getColor(R.color.middlePurple));
                mMsgBottomItem.setTextColor(getResources().getColor(R.color.black));
                mMineBottomItem.setTextColor(getResources().getColor(R.color.black));
                break;
            case 2:
                mHomeBottomItem.setCompoundDrawablesWithIntrinsicBounds(null, getResources().
                        getDrawable(R.drawable.home_icon), null, null);
                mDynamicBottomItem.setCompoundDrawablesWithIntrinsicBounds(null, getResources().
                        getDrawable(R.drawable.dynamic_icon), null, null);
                mMsgBottomItem.setCompoundDrawablesWithIntrinsicBounds(null, getResources().
                        getDrawable(R.drawable.msg_light_icon), null, null);
                mMineBottomItem.setCompoundDrawablesWithIntrinsicBounds(null, getResources().
                        getDrawable(R.drawable.mine_icon), null, null);
                //设置字体的大小
                mHomeBottomItem.setTextSize(UnitUtils.spToPx(MainActivity.this, 4));
                mDynamicBottomItem.setTextSize(UnitUtils.spToPx(MainActivity.this, 4));
                mMsgBottomItem.setTextSize(UnitUtils.spToPx(MainActivity.this, 5));
                mMineBottomItem.setTextSize(UnitUtils.spToPx(MainActivity.this, 4));
                mHomeBottomItem.setTextColor(getResources().getColor(R.color.black));
                mDynamicBottomItem.setTextColor(getResources().getColor(R.color.black));
                mMsgBottomItem.setTextColor(getResources().getColor(R.color.middlePurple));
                mMineBottomItem.setTextColor(getResources().getColor(R.color.black));
                break;
            case 3:
                mHomeBottomItem.setCompoundDrawablesWithIntrinsicBounds(null, getResources().
                        getDrawable(R.drawable.home_icon), null, null);
                mDynamicBottomItem.setCompoundDrawablesWithIntrinsicBounds(null, getResources().
                        getDrawable(R.drawable.dynamic_icon), null, null);
                mMsgBottomItem.setCompoundDrawablesWithIntrinsicBounds(null, getResources().
                        getDrawable(R.drawable.msg_icon), null, null);
                mMineBottomItem.setCompoundDrawablesWithIntrinsicBounds(null, getResources().
                        getDrawable(R.drawable.mine_light_icon), null, null);
                //设置字体的大小
                mHomeBottomItem.setTextSize(UnitUtils.spToPx(MainActivity.this, 4));
                mDynamicBottomItem.setTextSize(UnitUtils.spToPx(MainActivity.this, 4));
                mMsgBottomItem.setTextSize(UnitUtils.spToPx(MainActivity.this, 4));
                mMineBottomItem.setTextSize(UnitUtils.spToPx(MainActivity.this, 5));
                mHomeBottomItem.setTextColor(getResources().getColor(R.color.black));
                mDynamicBottomItem.setTextColor(getResources().getColor(R.color.black));
                mMsgBottomItem.setTextColor(getResources().getColor(R.color.black));
                mMineBottomItem.setTextColor(getResources().getColor(R.color.middlePurple));
                break;
        }
    }
    //初始化选择home
    private void initSelectHome(){
        mHomeBottomItem.setCompoundDrawablesWithIntrinsicBounds(null, getResources().
                getDrawable(R.drawable.home_light_icon), null, null);
        mDynamicBottomItem.setCompoundDrawablesWithIntrinsicBounds(null, getResources().
                getDrawable(R.drawable.dynamic_icon), null, null);
        mMsgBottomItem.setCompoundDrawablesWithIntrinsicBounds(null, getResources().
                getDrawable(R.drawable.msg_icon), null, null);
        mMineBottomItem.setCompoundDrawablesWithIntrinsicBounds(null, getResources().
                getDrawable(R.drawable.mine_icon), null, null);
        //设置字体的大小
        mHomeBottomItem.setTextSize(UnitUtils.spToPx(MainActivity.this, 5));
        mDynamicBottomItem.setTextSize(UnitUtils.spToPx(MainActivity.this, 4));
        mMsgBottomItem.setTextSize(UnitUtils.spToPx(MainActivity.this, 4));
        mMineBottomItem.setTextSize(UnitUtils.spToPx(MainActivity.this, 4));
        mHomeBottomItem.setTextColor(getResources().getColor(R.color.middlePurple));
        mDynamicBottomItem.setTextColor(getResources().getColor(R.color.black));
        mMsgBottomItem.setTextColor(getResources().getColor(R.color.black));
        mMineBottomItem.setTextColor(getResources().getColor(R.color.black));
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
//        mBottomNavigationView.getMenu().getItem(position).setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    //监听ViewPager滑动
    private void viewPagerScrollListener(ViewPager viewPager) {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //获取结果
        switch (requestCode) {
            case PermissionC.PIC_IMG_VIDEO_CODE:
                //选择图片的结果
                if (resultCode == Activity.RESULT_OK) {
                    List<Uri> mSelected = Matisse.obtainResult(data);
                    //更新mineFragment的用户头像
                    mineFragment.updateMyAvatar(mSelected);
                }
                break;
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
//            mBottomNavigationView.setAlpha(alpha);
//            if (alpha <= 0.3f) {
//                mBottomNavigationView.setVisibility(View.GONE);
//            }
        } else if (isShow == PermissionC.BOTTOM_TAB_GONE) {
//            mBottomNavigationView.setAlpha(alpha);
//            if (alpha >= 1f) {
//                mBottomNavigationView.setVisibility(View.VISIBLE);
//            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.homeBottomItem:
                mViewPager.setCurrentItem(0);
                setTvIcon(0);
                break;
            case R.id.dynamicBottomItem:
                mViewPager.setCurrentItem(1);
                setTvIcon(1);
                break;
            case R.id.pushContent:

                break;
            case R.id.msgBottomItem:
                mViewPager.setCurrentItem(2);
                setTvIcon(2);
                break;
            case R.id.mineBottomItem:
                mViewPager.setCurrentItem(3);
                setTvIcon(3);
                break;
        }
    }
}
