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
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.ielse.imagewatcher.ImageWatcherHelper;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import java.util.ArrayList;
import java.util.List;

import newmatch.zbmf.com.testapplication.GMClass.GMSelectImg;
import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.adapters.MenuAdapter;
import newmatch.zbmf.com.testapplication.adapters.dynamic.DynamicAdapter;
import newmatch.zbmf.com.testapplication.assist.CollapsingToolbarLayoutState;
import newmatch.zbmf.com.testapplication.assist.GlideUtil;
import newmatch.zbmf.com.testapplication.base.BaseActivity;
import newmatch.zbmf.com.testapplication.callback.CommentArrowCallBack;
import newmatch.zbmf.com.testapplication.callback.LikeCallBack;
import newmatch.zbmf.com.testapplication.callback.PermissionResultCallBack;
import newmatch.zbmf.com.testapplication.callback.ShowClickIv;
import newmatch.zbmf.com.testapplication.component.BannerViewHolderType;
import newmatch.zbmf.com.testapplication.entity.BannerService;
import newmatch.zbmf.com.testapplication.permissions.PermissionC;
import newmatch.zbmf.com.testapplication.utils.PermissionUtils;
import newmatch.zbmf.com.testapplication.utils.ShowImgUtils;
import newmatch.zbmf.com.testapplication.utils.SkipActivityUtil;
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
        //昵称
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
        //童虎动态列表
        RecyclerView userSpaceRV = bindView(R.id.userSpaceRV);
        userSpaceRV.setLayoutManager(new LinearLayoutManager(this,
                OrientationHelper.VERTICAL, false));

        DynamicAdapter dynamicAdapter = new DynamicAdapter(this);
        dynamicAdapter.setCommentDynamic(this);
        dynamicAdapter.setLikeCallBack(this);
        dynamicAdapter.setCommentArrowCallBack(this);
        userSpaceRV.setAdapter(dynamicAdapter);


        //设置画廊效果的ViewPager
        galleryPager(userViewBanner);
        //监听callapsingToolBarLayout的延展状态
        appBarLayoutSH();
        //测量userAvatarIv的高度
        //userAvatarExChangeShow(userAppBar);

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
                Bundle bundle = new Bundle();

                SkipActivityUtil.skipDataActivity(UserDetailActivity.this,
                        AddFirendActivity.class, bundle);
                break;
            case R.id.sendMsgBtn:
                //发送好友消息

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
        ToastUtils.showSquareTvToast(UserDetailActivity.this, "弹出对话框");
    }

    @Override
    public void likeCallBack(int position, TextView likeTv) {
        //点赞的回调
        ToastUtils.showSquareTvToast(UserDetailActivity.this, "点赞");
        //点赞
        //        LikeGMClass.clickLike(this, likeTv);
    }

    private int[] imgs = {R.drawable.j5, R.drawable.j4, R.drawable.j1,R.drawable.j3};

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


}
