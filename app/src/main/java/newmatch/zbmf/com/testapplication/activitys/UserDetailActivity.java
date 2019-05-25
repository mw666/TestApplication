package newmatch.zbmf.com.testapplication.activitys;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.github.ielse.imagewatcher.ImageWatcherHelper;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import java.util.ArrayList;
import java.util.List;

import newmatch.zbmf.com.testapplication.GMClass.GMPermissions;
import newmatch.zbmf.com.testapplication.GMClass.GMSelectImg;
import newmatch.zbmf.com.testapplication.GMClass.LikeGMClass;
import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.adapters.UserDetailAdapter;
import newmatch.zbmf.com.testapplication.assist.CollapsingToolbarLayoutState;
import newmatch.zbmf.com.testapplication.assist.GlideUtil;
import newmatch.zbmf.com.testapplication.base.BaseActivity;
import newmatch.zbmf.com.testapplication.base.MyApplication;
import newmatch.zbmf.com.testapplication.custom_view.RoundImageView;
import newmatch.zbmf.com.testapplication.entity.BannerService;
import newmatch.zbmf.com.testapplication.interfaces.ShowClickIv;
import newmatch.zbmf.com.testapplication.permissions.PermissionC;
import newmatch.zbmf.com.testapplication.utils.MyActivityManager;
import newmatch.zbmf.com.testapplication.utils.ShowImgUtils;
import newmatch.zbmf.com.testapplication.utils.SkipActivityUtil;
import newmatch.zbmf.com.testapplication.utils.UnitUtils;

public class UserDetailActivity extends BaseActivity implements ShowClickIv,GMPermissions.PermissionCallBackExcute {

    private ArrayList<BannerService.Data> mData;
    private CollapsingToolbarLayoutState state;
    private ImageView mBackBtn;
    private Toolbar mToolbar;
    private RelativeLayout mTopBarView;
    private AppBarLayout mUserAppBar;
    private RoundImageView userAvatarIv;
    private ImageWatcherHelper mIwHelper;
    private RoundImageView mUserAvatarRv;
    private int mUserAvatarH;
    private GMPermissions mGmPermissions;

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
        mUserAvatarRv = bindView(R.id.userAvatarRv);
        mBackBtn = bindViewWithClick(R.id.backBtn, true);
        userAvatarIv = bindViewWithClick(R.id.userAvatarIv, true);
        bindViewWithClick(R.id.addUserBtn, true);
        bindViewWithClick(R.id.sendMsgBtn, true);
        bindViewWithClick(R.id.dianZanIvBtn, true);

        //申请权所需要的对象
        mGmPermissions = GMPermissions.instance().setParameter(this, this, PermissionC.WR_FILE_CODE);
        mGmPermissions.setPermissionCallBackExcute(this);

        //监听callapsingToolBarLayout的延展状态
        appBarLayoutSH();
        //这里Rv的行数，这设置为固定的三列
        RecyclerView userDetailRv = bindView(R.id.userDetailRv);
        userDetailRv.setLayoutManager(new GridLayoutManager(UserDetailActivity.this,
                3, OrientationHelper.VERTICAL, false));
        UserDetailAdapter userDetailAdapter = new UserDetailAdapter(UserDetailActivity.this);
        userDetailAdapter.setShowClickIv(this);
        userDetailAdapter.addImgList(mData);
        userDetailRv.setAdapter(userDetailAdapter);

        //测量userAvatarIv的高度
        userAvatarExChangeShow(userAppBar);

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
        return MyApplication.getInstance().getResources().getColor(R.color.deepPurple);
    }


    @Override
    protected void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.backBtn:
                UserDetailActivity.this.finish();
                break;
            case R.id.userAvatarIv:
                 //读取SD卡的权限
                //对应的是Build.Version_code  16
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    GMPermissions.skipPermissionActivity(this,
                            PermissionC.WR_FILES_PERMISSION,PermissionC.PIC_IMG_VIDEO_CODE,
                            getString(R.string.get_img_tip));
                }else {
                    //选择图片
                    new GMSelectImg().picImgsOrVideo(this, MimeType.ofImage(),
                            PermissionC.PIC_IMG_VIDEO_CODE,1);
                }
                break;
            case R.id.addUserBtn:
                Bundle bundle = new Bundle();

                SkipActivityUtil.skipDataActivity(UserDetailActivity.this,
                        AddFirendActivity.class, bundle);
                break;
            case R.id.dianZanIvBtn:
                //点赞
                LikeGMClass.clickLike(this, (ImageView) view);
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
        ShowImgUtils.showImgs(imageView, dataList);
    }

    //设置userAvatar是否可见
    private void userAvatarExChangeShow(AppBarLayout userAppBar) {
        ViewTreeObserver treeObserver = userAvatarIv.getViewTreeObserver();
        treeObserver.addOnGlobalLayoutListener(() -> {
            mUserAvatarH = userAvatarIv.getMeasuredHeight();
        });
        userAppBar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            if (Math.abs(UnitUtils.pxToDp(this, verticalOffset)) >=
                    (UnitUtils.pxToDp(this, mUserAvatarH + 25))) {
                mUserAvatarRv.setVisibility(View.VISIBLE);
            } else {
                mUserAvatarRv.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //获取结果
        switch (requestCode) {
            case PermissionC.PIC_IMG_VIDEO_CODE:
                //选择图片的结果
                if (resultCode== Activity.RESULT_OK){
                    List<Uri> mSelected = Matisse.obtainResult(data);
                    //设置选择的图片
                    GlideUtil.loadCircleImage(UserDetailActivity.this,
                            R.drawable.touxiang_icon, mSelected.get(0), userAvatarIv);
                    GlideUtil.loadCircleImage(UserDetailActivity.this,
                            R.drawable.touxiang_icon, mSelected.get(0), mUserAvatarRv);
                    // TODO: 2018/10/9 上传选择的图片 --->用户图片

                }
                break;
        }
    }

//    //权限申请的回调
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case PermissionC.WR_FILE_CODE:
//                for (int i = 0; i < grantResults.length; i++) {
//                    //如果某一个权限用户没有同意--->申请权限
//                    if (grantResults[i]!= PackageManager.PERMISSION_GRANTED){
//                        //向用户展示该权限的dialog--->权限用途
//                        mGmPermissions.showPermissionDialog(getString(R.string.get_img_tip));
//                    }
//                    if (i==grantResults.length-1&&grantResults[i]==PackageManager.PERMISSION_GRANTED){
//                        //表示用户已经同意所有的权限--->执行需要权限后的操作
//                        //选择图片
//                        new GMSelectImg().picImgsOrVideo(this, PermissionC.PIC_IMG_VIDEO_CODE,1);
//                    }
//                }
//                break;
//        }
//    }

    //执行权限通过后的代码
    @Override
    public void excutePermissionCodes() {
        //选择图片
        new GMSelectImg().picImgsOrVideo(this,MimeType.ofImage(),
                PermissionC.PIC_IMG_VIDEO_CODE,1);
    }
}
