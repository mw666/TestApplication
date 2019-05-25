package newmatch.zbmf.com.testapplication;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.ielse.imagewatcher.ImageWatcherHelper;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.ui.MatisseActivity;

import java.security.Permissions;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import newmatch.zbmf.com.testapplication.GMClass.GMCopy;
import newmatch.zbmf.com.testapplication.GMClass.GMPermissions;
import newmatch.zbmf.com.testapplication.GMClass.GMSelectImg;
import newmatch.zbmf.com.testapplication.activitys.MySpaceActivity;
import newmatch.zbmf.com.testapplication.activitys.OptionsActivity;
import newmatch.zbmf.com.testapplication.activitys.UserDetailActivity;
import newmatch.zbmf.com.testapplication.activitys.VIPActivity;
import newmatch.zbmf.com.testapplication.activitys.WalletActivity;
import newmatch.zbmf.com.testapplication.adapters.MainFragMentAdapter;
import newmatch.zbmf.com.testapplication.adapters.MenuAdapter;
import newmatch.zbmf.com.testapplication.assist.GlideUtil;
import newmatch.zbmf.com.testapplication.base.BaseActivity;
import newmatch.zbmf.com.testapplication.base.MyApplication;
import newmatch.zbmf.com.testapplication.callback.DialogActCallBack;
import newmatch.zbmf.com.testapplication.callback.EtCallBack;
import newmatch.zbmf.com.testapplication.dialogs.MyDialogUtil;
import newmatch.zbmf.com.testapplication.fragments.DynamicFragment;
import newmatch.zbmf.com.testapplication.fragments.HomeFragment;
import newmatch.zbmf.com.testapplication.fragments.MsgFragment;
import newmatch.zbmf.com.testapplication.permissions.PermissionActivity;
import newmatch.zbmf.com.testapplication.permissions.PermissionC;
import newmatch.zbmf.com.testapplication.utils.ActivityAnimUtils;
import newmatch.zbmf.com.testapplication.utils.GetUIDimens;
import newmatch.zbmf.com.testapplication.utils.MyActivityManager;
import newmatch.zbmf.com.testapplication.utils.ShowImgUtils;
import newmatch.zbmf.com.testapplication.utils.SkipActivityUtil;
import newmatch.zbmf.com.testapplication.utils.TianShareUtil;
import newmatch.zbmf.com.testapplication.utils.Util;
import newmatch.zbmf.com.testapplication.views.GenericDrawerLayout;
import newmatch.zbmf.com.testapplication.views.RotationPageTransformer;

/**
 * 取名甜甜圈吧
 * 首页
 */
@TargetApi(Build.VERSION_CODES.M)
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
public class MainActivity extends BaseActivity implements
        ViewPager.OnPageChangeListener
        , BottomNavigationView.OnNavigationItemSelectedListener {

    private ViewPager mViewPager;
    public ImageWatcherHelper mIwHelper;
    private BottomNavigationView bottomNavigationView;
    private GenericDrawerLayout genericdrawerlayout;
    private AppCompatImageView menuHeadIv;
    private TextView userNick;
    private HomeFragment homeFragment;

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
        homeFragment = HomeFragment.homgInstance(TianShareUtil.getCity());
        fragmentList.add(homeFragment);
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

    private int[] imgs = {R.mipmap.card3, R.mipmap.card2, R.mipmap.card4, R.mipmap.card5};
    private List<Integer> imgList;

    //获取抽屉View的控件
    @SuppressLint("ClickableViewAccessibility")
    private void getMenuView(View menu) {
        ViewPager menuViewPager = menu.findViewById(R.id.menuViewPager);
        imgList = new ArrayList<>();
        for (int i = 0; i < imgs.length; i++) {
            imgList.add(imgs[i]);
        }
        MenuAdapter menuAdapter = new MenuAdapter(imgList, null, R.layout.menu_view);
        menuViewPager.setAdapter(menuAdapter);
        menuViewPager.setOffscreenPageLimit(imgList.size());//设置预加载的数量
        menuViewPager.setPageMargin(12);
        //添加3D画廊效果
        menuViewPager.setPageTransformer(true, new RotationPageTransformer());
        //viewPager左右两边滑动无效的处理
        findViewById(R.id.vp_outer).setOnTouchListener((view, motionEvent)
                -> menuViewPager.dispatchTouchEvent(motionEvent));
        //menuViewPager默认选中的是0
        menuViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                Log.d("===LLL", "  onPageSelected  索引：" + i);

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        //获取控件
        RelativeLayout rL = menu.findViewById(R.id.rL);
        menuHeadIv = menu.findViewById(R.id.menuHeadIv);
        userNick = menu.findViewById(R.id.userNick);
        TextView careTv = menu.findViewById(R.id.careTv);//长按复制
        TextView userAccount = menu.findViewById(R.id.userAccount);//长按复制
        TextView fansTv = menu.findViewById(R.id.fansTv);
        TextView likeTv = menu.findViewById(R.id.likeTv);
        TextView rankTv = menu.findViewById(R.id.rankTv);

        TextView userRmb = menu.findViewById(R.id.userRmb);
        TextView vipTv = menu.findViewById(R.id.vipTv);
        TextView inviteTv = menu.findViewById(R.id.inviteTv);
        TextView myArea = menu.findViewById(R.id.myArea);
        TextView taskRewards = menu.findViewById(R.id.taskRewards);
        TextView optionBackTv = menu.findViewById(R.id.optionBackTv);
        TextView versionUpdateTv = menu.findViewById(R.id.versionUpdateTv);
        TextView accountTv = menu.findViewById(R.id.accountTv);
        TextView loginOutTv = menu.findViewById(R.id.loginOutTv);

        menuHeadIv.setOnClickListener(listener);
        userNick.setOnClickListener(listener);
        userRmb.setOnClickListener(listener);
        vipTv.setOnClickListener(listener);
        inviteTv.setOnClickListener(listener);
        myArea.setOnClickListener(listener);
        taskRewards.setOnClickListener(listener);
        optionBackTv.setOnClickListener(listener);
        versionUpdateTv.setOnClickListener(listener);
        accountTv.setOnClickListener(listener);
        loginOutTv.setOnClickListener(listener);
        //设置textView的复制操作
        GMCopy.instance().copyGetXY(userNick, this, rL);
        GMCopy.instance().copyGetXY(userAccount, this, rL);
    }

    private List<String> pers = new ArrayList<>();

    private View.OnClickListener listener = view -> {
        switch (view.getId()) {
            case R.id.menuHeadIv:
                //选择或更换头像
                selectImg();
                break;
            case R.id.userNick:
                //用户昵称
                updateUserNick();
                break;
            case R.id.userRmb:
                //用户钱包页面，资金余额，绑定的提现账号
                ActivityAnimUtils.instance().activityIn(MainActivity.this,
                        WalletActivity.class);
                break;
            case R.id.vipTv:
                //开通VIP页面
                ActivityAnimUtils.instance().activityIn(MainActivity.this,
                        VIPActivity.class);
                break;
            case R.id.inviteTv:
                //邀请会员页面

                break;
            case R.id.myArea:
                //跳转个人空间页面
                ActivityAnimUtils.instance().activityIn(MainActivity.this,
                        MySpaceActivity.class);
                break;
            case R.id.taskRewards:
                //待完成任务的页面

                break;
            case R.id.optionBackTv:
                //意见反馈页面
                ActivityAnimUtils.instance().activityIn(MainActivity.this,
                        OptionsActivity.class);
                break;
            case R.id.versionUpdateTv:
                //版本更新页面

                break;
            case R.id.accountTv:
                //账户切换页面

                break;
            case R.id.loginOutTv:
                //退出登录

                break;
        }
    };

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

    private void selectImg() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            for (int i = 0; i < PermissionC.WR_FILES_PERMISSION.length; i++) {
                int checkI = this.checkSelfPermission(PermissionC.WR_FILES_PERMISSION[i]);
                if (checkI == PackageManager.PERMISSION_DENIED) {
                    pers.add(PermissionC.WR_FILES_PERMISSION[i]);
                }
            }
            if (pers.size() > 0) {
                //申请权限
                int size = pers.size();
                ActivityCompat.requestPermissions(this,
                        pers.toArray(new String[size]),
                        0x12);
            } else {
                //选择图片
                new GMSelectImg().picImgsOrVideo(this,MimeType.ofImage(),
                        PermissionC.PIC_IMG_VIDEO_CODE, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0x12) {
            pers.clear();
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    pers.add(permissions[i]);
                }
            }
            if (pers.size() > 0) {
                int size = pers.size();
                showPerDialog(pers.toArray(new String[size]));
            } else {
                //权限已经通过
                //选择图片
                new GMSelectImg().picImgsOrVideo(this,MimeType.ofImage(),
                        PermissionC.PIC_IMG_VIDEO_CODE, 1);
            }
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
                    GlideUtil.loadCircleImage(MainActivity.this,
                            R.drawable.ic_head_portrait_icon, mSelected.get(0), menuHeadIv);
                    homeFragment.updateHeadImg(mSelected.get(0));
                }
                break;
        }
    }

    //展示申请权限的dialog
    private void showPerDialog(String[] pers) {
        View view = LayoutInflater.from(this).inflate(R.layout.permission_dialog_bg, null);
        MyDialogUtil.showPermissionAlert(MainActivity.this,
                MainActivity.this, "", view, R.drawable.dialog_bg,
                false, new DialogActCallBack() {
                    @Override
                    public void cancelActCallBack(AlertDialog alertDialog) {
                        alertDialog.dismiss();
                    }

                    @Override
                    public void positionActCallBack(AlertDialog alertDialog) {
                        //再次申请权限
                        ActivityCompat.requestPermissions(MainActivity.this
                                , pers, PermissionC.init_permis_code);
                        alertDialog.dismiss();
                    }
                });
    }

    //更新用户昵称
    private void updateUserNick() {
        View view = LayoutInflater.from(this).inflate(R.layout.update_user_nick_view, null);
        MyDialogUtil.showEtDialog(MainActivity.this,
                MainActivity.this, "", view, R.drawable.dialog_bg,
                true, (content, alertDialog) -> {
                    alertDialog.dismiss();
                    userNick.setText(content);
                });
    }


}
