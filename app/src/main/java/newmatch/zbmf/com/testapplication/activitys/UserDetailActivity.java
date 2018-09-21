package newmatch.zbmf.com.testapplication.activitys;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;

import java.util.ArrayList;

import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.adapters.UserDetailAdapter;
import newmatch.zbmf.com.testapplication.assist.CollapsingToolbarLayoutState;
import newmatch.zbmf.com.testapplication.assist.GifSizeFilter;
import newmatch.zbmf.com.testapplication.base.BaseActivity;
import newmatch.zbmf.com.testapplication.base.MyApplication;
import newmatch.zbmf.com.testapplication.custom_view.CircleImageView;
import newmatch.zbmf.com.testapplication.entity.BannerService;
import newmatch.zbmf.com.testapplication.permissions.PermissionC;
import newmatch.zbmf.com.testapplication.utils.MyActivityManager;
import newmatch.zbmf.com.testapplication.utils.SkipActivityUtil;

public class UserDetailActivity extends BaseActivity {

    private ArrayList<BannerService.Data> mData;
    private CollapsingToolbarLayoutState state;
    private ImageView mBackBtn;
    private Toolbar mToolbar;
    private RelativeLayout mTopBarView;
    private AppBarLayout mUserAppBar;
    private CircleImageView userAvatarIv;

    @Override
    protected Integer layoutId() {
        return R.layout.activity_user_detail;
    }

    @Override
    protected void initView() {
        MyActivityManager.getMyActivityManager().pushAct(UserDetailActivity.this);
        //设置内容顶进状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        //获取上一个页面传递过来的数据
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mData = extras.getParcelableArrayList(PermissionC.USER_PIC);
        }
        mUserAppBar = bindView(R.id.userAppBar);
        mTopBarView = bindView(R.id.topBarView);
        mToolbar = bindView(R.id.toolbar);
        mBackBtn = bindViewWithClick(R.id.backBtn, true);
        userAvatarIv = bindViewWithClick(R.id.userAvatarIv, true);
        bindViewWithClick(R.id.addUserBtn, true);
        bindViewWithClick(R.id.sendMsgBtn, true);

        appBarLayoutSH();
        RecyclerView userDetailRv = bindView(R.id.userDetailRv);
        //这里Rv的行数，不一定为3列
        userDetailRv.setLayoutManager(new GridLayoutManager(UserDetailActivity.this,
                3, OrientationHelper.VERTICAL, false));
        UserDetailAdapter userDetailAdapter = new UserDetailAdapter(UserDetailActivity.this);
        userDetailAdapter.addImgList(mData);
        userDetailRv.setAdapter(userDetailAdapter);


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
                Matisse.from(UserDetailActivity.this)
                        .choose(MimeType.ofAll())
                        .countable(true)
                        .maxSelectable(9)
                        .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                        .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .imageEngine(new GlideEngine())
                        .forResult(0);
                break;
            case R.id.addUserBtn:
                Bundle bundle = new Bundle();

                SkipActivityUtil.skipDataActivity(UserDetailActivity.this,
                        AddFirendActivity.class, bundle);
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
}
