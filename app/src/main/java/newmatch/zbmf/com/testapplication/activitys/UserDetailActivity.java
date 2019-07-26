package newmatch.zbmf.com.testapplication.activitys;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.github.ielse.imagewatcher.ImageWatcherHelper;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import java.util.ArrayList;
import java.util.List;

import newmatch.zbmf.com.testapplication.GMClass.GMCopy;
import newmatch.zbmf.com.testapplication.GMClass.GMSelectImg;
import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.adapters.myPagerAdapters.MenuAdapter;
import newmatch.zbmf.com.testapplication.adapters.dynamic.DynamicAdapter;
import newmatch.zbmf.com.testapplication.utils.glidUtils.CollapsingToolbarLayoutState;
import newmatch.zbmf.com.testapplication.utils.glidUtils.GlideUtil;
import newmatch.zbmf.com.testapplication.base.BaseActivity;
import newmatch.zbmf.com.testapplication.base.MyApplication;
import newmatch.zbmf.com.testapplication.callback.CommentArrowCallBack;
import newmatch.zbmf.com.testapplication.callback.LikeCallBack;
import newmatch.zbmf.com.testapplication.callback.PermissionResultCallBack;
import newmatch.zbmf.com.testapplication.callback.ShowClickIv;
import newmatch.zbmf.com.testapplication.component.BannerViewHolderType;
import newmatch.zbmf.com.testapplication.dialogs.DialogUtils;
import newmatch.zbmf.com.testapplication.entity.BannerService;
import newmatch.zbmf.com.testapplication.listeners.OnceClickListener;
import newmatch.zbmf.com.testapplication.permissions.PermissionC;
import newmatch.zbmf.com.testapplication.utils.ActivityAnimUtils;
import newmatch.zbmf.com.testapplication.utils.PermissionUtils;
import newmatch.zbmf.com.testapplication.utils.ShowImgUtils;
import newmatch.zbmf.com.testapplication.utils.ToastUtils;
import newmatch.zbmf.com.testapplication.views.RotationPageTransformer;
import newmatch.zbmf.com.testapplication.views.customViewPager.BannerViewHolder;
import newmatch.zbmf.com.testapplication.views.customViewPager.MZBannerView;
import newmatch.zbmf.com.testapplication.views.customViewPager.MZHolderCreator;

public class UserDetailActivity extends BaseActivity implements ShowClickIv
        , DynamicAdapter.CommentDynamic, LikeCallBack, CommentArrowCallBack {

    private ArrayList<BannerService.Data> mData;
    private CollapsingToolbarLayoutState state;
    private ImageView mBackBtn;
    private Toolbar mToolbar;
    private RelativeLayout mTopBarView;
    private AppBarLayout mUserAppBar;
    private AppCompatImageView userAvatarIv;
    private ImageWatcherHelper mIwHelper;
    private int mUserAvatarH;
    private AppCompatImageView addUserBtn;
    private AppCompatImageView sendMsgBtn;

    @Override
    protected Integer layoutId() {
        return R.layout.activity_user_detail;
    }

    @Override
    protected void initView() {
        mIwHelper = ShowImgUtils.init(this);
        //设置内容顶进状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        //获取上一个页面传递过来的数据
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mData = extras.getParcelableArrayList(PermissionC.USER_PIC);
        }
        mUserAppBar = bindView(R.id.userAppBar);
        mTopBarView = bindView(R.id.topBarView);
        AppBarLayout userAppBar = bindView(R.id.userAppBar);
        mToolbar = bindView(R.id.toolbar);
        mBackBtn = bindViewWithClick(R.id.backBtn, true);
        userAvatarIv = bindViewWithClick(R.id.userAvatarIv, true);
        bindViewWithClick(R.id.addUserBtn, true);
        bindViewWithClick(R.id.sendMsgBtn, true);
        //关注按钮
        bindViewWithClick(R.id.guanZhuBtn1, true);
        bindViewWithClick(R.id.guanZhuBtn, true);
        bindViewWithClick(R.id.goToUserPhotoSpace, true);
        bindViewWithClick(R.id.goToUserPhotoSpaceParent, true);
        //昵称
        RelativeLayout appBarRL = bindView(R.id.appBarRL);
        TextView userNick = bindView(R.id.userNick);
        //用户账号
        TextView userAccount = bindView(R.id.userAccount);
        //他关注的人数
        TextView careTv = bindView(R.id.careTv);
        //粉丝数量
        TextView fansTv = bindView(R.id.fansTv);
        //今日的访客数量
        TextView toDayBrowse = bindView(R.id.toDayBrowse);
        //历史总访客数量
        TextView conutBrowse = bindView(R.id.conutBrowse);
        //该用户相册的展示图片
        LinearLayout userVpOuter = bindView(R.id.userVpOuter);
        ViewPager userViewBanner = bindView(R.id.userViewPager);
        //浮动的按钮，添加好友，先好友发送消息
        addUserBtn = bindViewWithClick(R.id.addUserBtn, true);
        sendMsgBtn = bindViewWithClick(R.id.sendMsgBtn, true);
        //童虎动态列表
        RecyclerView userSpaceRV = bindView(R.id.userSpaceRV);
        userSpaceRV.setLayoutManager(new LinearLayoutManager(this,
                OrientationHelper.VERTICAL, false));

        DynamicAdapter dynamicAdapter = new DynamicAdapter(this);
        dynamicAdapter.setCommentDynamic(this);
        dynamicAdapter.setLikeCallBack(this);
        dynamicAdapter.setCommentArrowCallBack(this);
        userSpaceRV.setAdapter(dynamicAdapter);

        //监听recyclerView的滚动
        moniteRvScroll(userSpaceRV);
        //设置画廊效果的ViewPager
        galleryPager(userViewBanner);
        //监听callapsingToolBarLayout的延展状态
        appBarLayoutSH();
        //设置要复制的数据
        copyContent(userNick, userAccount, appBarRL);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected String initTitle() {
        return getString(R.string.user_infomation);
    }

    @Override
    protected Boolean showBackBtn() {
        return true;
    }

    @Override
    protected int topBarColor() {
        return ActivityCompat.getColor(this, R.color.deepPurple);
    }


    @Override
    protected void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.backBtn:
                ActivityAnimUtils.instance().activityOut(this);
                UserDetailActivity.this.finish();
                break;
            case R.id.userAvatarIv:
                //读取SD卡的权限
                PermissionUtils.instance().requestPermission(this,
                        getString(R.string.get_img_tip), PermissionC.WR_FILES_PERMISSION,
                        (PermissionResultCallBack) () -> {
                            //选择图片
                            new GMSelectImg().picImgsOrVideo(UserDetailActivity.this,
                                    MimeType.ofImage(),
                                    PermissionC.PIC_IMG_VIDEO_CODE,
                                    1);
                        });
                break;
            case R.id.addUserBtn:
                //添加好友
                showPopupDialog();
                break;
            case R.id.sendMsgBtn:
                //发送好友消息
                ToastUtils.showSingleToast(this,"跳转聊天页面");
                break;
            case R.id.goToUserPhotoSpaceParent:
            case R.id.goToUserPhotoSpace:
                //前往用户相册，需要开通会员方能查看用户私密相册-->弹出dialog
                showVipDialogEntrance();
                break;
        }
    }

    private void appBarLayoutSH() {
        mUserAppBar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            if (verticalOffset == 0) {
                if (state != CollapsingToolbarLayoutState.EXPANDED) {
                    state = CollapsingToolbarLayoutState.EXPANDED;//修改状态标记为展开
                    mTopBarView.setVisibility(View.GONE);
                    mToolbar.setVisibility(View.GONE);
                    mBackBtn.setVisibility(View.VISIBLE);
                }
            } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                if (state != CollapsingToolbarLayoutState.COLLAPSED) {
                    mTopBarView.setVisibility(View.VISIBLE);
                    mToolbar.setVisibility(View.VISIBLE);
                    mBackBtn.setVisibility(View.GONE);
                    mToolbar.setBackgroundResource(R.drawable.topbar_bg);
                    state = CollapsingToolbarLayoutState.COLLAPSED;//修改状态标记为折叠
                }
            } else {
                if (state != CollapsingToolbarLayoutState.INTERNEDIATE) {
                    if (state == CollapsingToolbarLayoutState.COLLAPSED) {
                        mTopBarView.setVisibility(View.GONE);
                        mToolbar.setVisibility(View.GONE);
                        mBackBtn.setVisibility(View.VISIBLE);
                    }
                    state = CollapsingToolbarLayoutState.INTERNEDIATE;//修改状态标记为中间
                }
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
    public void showClickIv(Integer pos, ImageView imageView, List<Uri> dataList) {
        ShowImgUtils.showImgs(pos, imageView, dataList);
    }

    //设置userAvatar是否可见
    private void userAvatarExChangeShow(AppBarLayout userAppBar) {
        ViewTreeObserver treeObserver = userAvatarIv.getViewTreeObserver();
        treeObserver.addOnGlobalLayoutListener(() -> {
            mUserAvatarH = userAvatarIv.getMeasuredHeight();
        });
        //        userAppBar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
        //            if (Math.abs(UnitUtils.pxToDp(this, verticalOffset)) >=
        //                    (UnitUtils.pxToDp(this, mUserAvatarH + 25))) {
        //                mUserAvatarRv.setVisibility(View.VISIBLE);
        //            } else {
        //                mUserAvatarRv.setVisibility(View.INVISIBLE);
        //            }
        //        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //获取结果
        switch (requestCode) {
            case PermissionC.PIC_IMG_VIDEO_CODE:
                //选择图片的结果
                if (resultCode == Activity.RESULT_OK) {
                    List<Uri> mSelected = Matisse.obtainResult(data);
                    //设置选择的图片
                    GlideUtil.loadCircleImage(UserDetailActivity.this,
                            R.drawable.touxiang_icon, mSelected.get(0), userAvatarIv);
                    // TODO: 2018/10/9 上传选择的图片 --->用户图片

                }
                break;
        }
    }

    @Override
    public void commentDynamic(int position) {
        //动态评论的回调
        ToastUtils.showSquareTvToast(UserDetailActivity.this, "动态评论");
    }

    @Override
    public void arrowClickCallBack(int position) {
        //点击弹出对话框的按钮
        showTypeDialog();
    }

    @Override
    public void likeCallBack(int position, TextView likeTv) {
        //点赞的回调
        ToastUtils.showSquareTvToast(UserDetailActivity.this, "点赞");
        //点赞
        //        LikeGMClass.clickLike(this, likeTv);
    }

    //监听RecyclerView的滑动
    private void moniteRvScroll(RecyclerView rv) {
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int rvState = -1;
            int correctYCount = 0;
            int bearYCount = 0;
            float alpha;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //状态有三种：0：停止  1：拖动  2：滑翔
                rvState = newState;
                if (newState == 1 || newState == 2) {
                    addUserBtn.setVisibility(View.GONE);
                    sendMsgBtn.setVisibility(View.GONE);
                } else if (newState == 0) {
                    addUserBtn.setVisibility(View.VISIBLE);
                    sendMsgBtn.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy < 0) {
                    //向下滑动
                    correctYCount += dy;
                    //------>不透明
                    float hh = (80 + correctYCount);
                    if (hh <= 1f) {
                        alpha = 1;
                    } else {
                        alpha = (Math.abs(hh)) / 100;
                    }
                }
                if (dy > 0) {
                    //向上滑动
                    bearYCount += dy;
                    if ((Math.abs(bearYCount)) >= 80) {
                        alpha = 1 - (float) 80 / 100;
                    } else {
                        alpha = (1 - Math.abs((float) bearYCount) / 100);
                    }
                }
                if (rvState == 0) {
                    //静止  ---》correctYCount=0;
                    correctYCount = 0;
                    bearYCount = 0;
                }
            }
        });
    }

    private int[] imgs = {R.drawable.j5, R.drawable.j4, R.drawable.j1, R.drawable.j3};

    //ViewPager的3D画廊效果
    private void galleryPager(ViewPager menuViewPager) {
        List<Integer> imgList = new ArrayList<>();
        for (int i = 0; i < imgs.length; i++) {
            imgList.add(imgs[i]);
        }
        MenuAdapter menuAdapter = new MenuAdapter(imgList, null, R.layout.user_page_view);
        menuViewPager.setAdapter(menuAdapter);
        menuViewPager.setOffscreenPageLimit(imgList.size());//设置预加载的数量
        menuViewPager.setPageMargin(5);
        //ViewPager默认选择中间的那个
        if (imgList.size() > 2)
            menuViewPager.setCurrentItem(1);
        //添加3D画廊效果
        menuViewPager.setPageTransformer(true, new RotationPageTransformer());
        menuViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    //模拟准备banner的数据
    private void initBannerView(MZBannerView homeBanner) {
        int[] imgs = {R.drawable.card5, R.drawable.card3, R.drawable.card4, R.drawable.j4};

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
            public void onPageSelected(int position) {
            }

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
            ToastUtils.showSquareTvToast(this, "点击了Banner页面：" + position);
        });

    }

    //复制
    private void copyContent(TextView userNick, TextView userAccount, View parent) {
        //复制,昵称
        GMCopy.instance().copyGetXY(userNick, this, parent);
        //复制,用户账号
        GMCopy.instance().copyGetXY(userAccount, this, parent);
    }

    //弹出添加好友对话框
    private void showPopupDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.add_user_dialog_view, null);
        AppCompatImageView newUserAvatar = view.findViewById(R.id.newUserAvatar);
        TextView userNick = view.findViewById(R.id.userNick);
        TextView userSex = view.findViewById(R.id.userSex);
        TextView userAge = view.findViewById(R.id.userAge);
        EditText verifyMsg = view.findViewById(R.id.verifyMsg);
        EditText remark = view.findViewById(R.id.remark);
        LinearLayout groupParent = view.findViewById(R.id.groupParent);
        Switch seeMyDynamicSwitch = view.findViewById(R.id.seeMyDynamicSwitch);
        Button sendAddUserBtn = view.findViewById(R.id.sendAddUserBtn);
        DialogUtils.instance().setView(view)
                .setIsCancel(true)
                .setHasMargin(true)
                .setDialogStyle(R.style.dialog)
                .setGravity(Gravity.CENTER)
                .setDialogDecoeViewBg(R.drawable.add_friend_et_bg)
                .gMDialog(this, this);
        //设置要添加的好友的头像
        GlideUtil.loadCircleImage(this, R.drawable.place_holder_img, R.drawable.j4, newUserAvatar);
        //设置要添加的用户的昵称
        userNick.setText("铁蛋儿");
        //设置性别
        userSex.setText("男");
        //设置年龄
        userAge.setText("24岁");
        //获取添加好友的验证信息
        String addUserVerifyMsg = verifyMsg.getText().toString().trim();
        //获取备注
        String addUserRemark = remark.getText().toString().trim();
        //设置分组
        groupParent.setOnClickListener(new OnceClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                //spanner弹出选择分组
                ToastUtils.showSingleToast(UserDetailActivity.this, "设置分组");
            }
        });
        //设置不让他看我的动态
        seeMyDynamicSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                ToastUtils.showSingleToast(UserDetailActivity.this, "不让他查看我的动态");
            }
        });
        //发送添加好友的请求
        sendAddUserBtn.setOnClickListener(new OnceClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                ToastUtils.showSingleToast(UserDetailActivity.this, "发送添加好友的请求");
            }
        });
    }

    //弹出开通会员的dialog
    private void showVipDialogEntrance(){
        View view = LayoutInflater.from(this).inflate(R.layout.open_vip_dialog, null);
        AppCompatImageView vipIcon = view.findViewById(R.id.vipIcon);
        TextView vipTitle = view.findViewById(R.id.vipTitle);
        TextView vipTipContent = view.findViewById(R.id.vipTipContent);
        AppCompatImageView rmbIcon = view.findViewById(R.id.rmbIcon);
        TextView rmbValue = view.findViewById(R.id.rmbValue);
        Button openVipBtn = view.findViewById(R.id.openVipBtn);
        DialogUtils.instance().setView(view)
                .setIsCancel(true)
                .setHasMargin(true)
                .setDialogStyle(R.style.dialog)
                .setGravity(Gravity.CENTER)
                .setDialogDecoeViewBg(R.drawable.add_friend_et_bg)
                .gMDialog(this, this);
        //确认开通VIP
        openVipBtn.setOnClickListener(new OnceClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                ToastUtils.showSingleToast(UserDetailActivity.this,"确认开通VIP");
            }
        });
    }

    //弹出对话框
    private void showTypeDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_dynamic_arrow_down_view, null);
        TextView reprint = view.findViewById(R.id.reprint);
        TextView collect = view.findViewById(R.id.collect);
        TextView report = view.findViewById(R.id.report);
        DialogUtils.instance().setView(view)
                .setIsCancel(true)
                .setHasMargin(true)
                .setDialogStyle(R.style.dialog)
                .setGravity(Gravity.CENTER)
                .setDialogDecoeViewBg(R.drawable.add_friend_et_bg)
                //                .setDialogAnimStyle(R.style.dialogAnimator01)
                .gMDialog(this, this);
        reprint.setOnClickListener(new OnceClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                //转载
                ToastUtils.showSingleToast(MyApplication.getInstance(), "转载");
            }
        });
        collect.setOnClickListener(new OnceClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                //收藏
                ToastUtils.showSingleToast(MyApplication.getInstance(), "收藏");
            }
        });
        report.setOnClickListener(new OnceClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                //举报
                ToastUtils.showSingleToast(MyApplication.getInstance(), "举报");
            }
        });
    }

}
