package newmatch.zbmf.com.testapplication.activitys;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import java.util.ArrayList;
import java.util.List;

import newmatch.zbmf.com.testapplication.GMClass.GMSelectImg;
import newmatch.zbmf.com.testapplication.GMClass.GMTextSetIcon;
import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.adapters.MyCircleAdapter;
import newmatch.zbmf.com.testapplication.adapters.dynamic.DynamicAdapter;
import newmatch.zbmf.com.testapplication.assist.CollapsingToolbarLayoutState;
import newmatch.zbmf.com.testapplication.base.BaseActivity;
import newmatch.zbmf.com.testapplication.base.MyApplication;
import newmatch.zbmf.com.testapplication.callback.CommentArrowCallBack;
import newmatch.zbmf.com.testapplication.callback.DialogActCallBack;
import newmatch.zbmf.com.testapplication.callback.LikeCallBack;
import newmatch.zbmf.com.testapplication.callback.PermissionResultCallBack;
import newmatch.zbmf.com.testapplication.dialogs.MyDialogUtil;
import newmatch.zbmf.com.testapplication.dialogs.MyDiaog;
import newmatch.zbmf.com.testapplication.permissions.PermissionC;
import newmatch.zbmf.com.testapplication.utils.ActivityAnimUtils;
import newmatch.zbmf.com.testapplication.utils.AnimatSpecialEffectUtil;
import newmatch.zbmf.com.testapplication.utils.PermissionUtils;
import newmatch.zbmf.com.testapplication.utils.ToastUtils;

public class MySpaceActivity extends BaseActivity implements DynamicAdapter.CommentDynamic
        , MyDiaog.PositiveBtnClick, LikeCallBack, CommentArrowCallBack/*, DialogActCallBack*/ {

    private SmartRefreshLayout mySpaceRefreshLayout;
    private RecyclerView mySpaceRV;
    private View statusView;
    private AppBarLayout mySpaceAppBar;
    private Toolbar mToolbar;
    private ImageView mBackBtn;
    private AppCompatImageView mySpaceFloatBtn, addDynationBtn;
    private TextView dynamicSpaceBtn, photoSpaceBtn;
    //子按钮列表

    private List<AppCompatImageView> buttonItems = new ArrayList<AppCompatImageView>();
    // 标识当前按钮弹出与否，1代表已经未弹出，-1代表已弹出
    private int flag = -1;

    @Override
    protected Integer layoutId() {
        return R.layout.activity_my_space;
    }

    @Override
    protected void initView() {
        //全屏，内容延伸至状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        mySpaceAppBar = bindView(R.id.mySpaceAppBar);
        mToolbar = bindView(R.id.toolbar);
        mBackBtn = bindViewWithClick(R.id.backBtn, true);
        addDynationBtn = bindViewWithClick(R.id.addDynationBtn, true);
        bindViewWithClick(R.id.mySpaceAdd, true);
        mySpaceFloatBtn = bindViewWithClick(R.id.mySpaceFloatBtn, true);
        photoSpaceBtn = bindViewWithClick(R.id.photoSpaceBtn, true);
        dynamicSpaceBtn = bindViewWithClick(R.id.dynamicSpaceBtn, true);
        mySpaceRefreshLayout = bindView(R.id.mySpaceRefreshLayout);
        //支持嵌套滑动
        mySpaceRefreshLayout.setEnableNestedScroll(true);
        ClassicsHeader mySpaceHeader = bindView(R.id.mySpaceHeader);
        ClassicsHeader mySpaceFooter = bindView(R.id.mySpaceFooter);
        statusView = bindView(R.id.statusView);
        mySpaceRV = bindView(R.id.mySpaceRV);


        mySpaceRV.setLayoutManager(new LinearLayoutManager(this,
                OrientationHelper.VERTICAL, false));

        DynamicAdapter dynamicAdapter = new DynamicAdapter(this);
        dynamicAdapter.setCommentDynamic(this);
        dynamicAdapter.setLikeCallBack(this);
        dynamicAdapter.setCommentArrowCallBack(this);
        mySpaceRV.setAdapter(dynamicAdapter);

        //        showExplodBtns();
        //刷新
        mySpaceRefreshLayout.setOnRefreshListener(refreshLayout -> {
            isRefresh = true;
            mHandler.postDelayed(mRunnable, 2000);
        });
        //加载更多
        mySpaceRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            isLoad = true;
            mHandler.postDelayed(mRunnable, 2000);
        });
        //监听RecyclerView的滑动
        moniteRvScroll(mySpaceRV);
        appBarLayoutSH();
        setPlrTouchLisenler();

    }

    //爆炸式按钮效果
    //    private void showExplodBtns() {
    //        AppCompatImageView mySpacePhotoBtn = bindView(R.id.mySpacePhotoBtn);
    //        AppCompatImageView mySpaceVideoBtn = bindView(R.id.mySpaceVideoBtn);
    //        buttonItems.add(mySpacePhotoBtn);
    //        buttonItems.add(mySpaceVideoBtn);
    //
    //    }

    /**
     * 按钮移动动画
     *
     * @params 子按钮列表
     * @params 弹出时圆形半径radius
     */
    public void buttonAnimation(List<AppCompatImageView> buttonList, int radius) {
        for (int i = 0; i < buttonList.size(); i++) {
            ObjectAnimator objAnimatorX;
            ObjectAnimator objAnimatorY;
            ObjectAnimator objAnimatorRotate;
            // 将按钮设为可见
            buttonList.get(i).setVisibility(View.VISIBLE);
            // 按钮在X、Y方向的移动距离
            float distanceX = (float) (flag * radius *
                    (Math.cos(AnimatSpecialEffectUtil.getAngle(buttonList.size(), i))));
            float distanceY = -(float) (flag * radius *
                    (Math.sin(AnimatSpecialEffectUtil.getAngle(buttonList.size(), i))));
            // X方向移动
            objAnimatorX = ObjectAnimator.ofFloat(buttonList.get(i), "x",
                    buttonList.get(i).getX(), buttonList.get(i).getX() + distanceX);
            objAnimatorX.setDuration(300);
            objAnimatorX.setStartDelay(100);
            objAnimatorX.start();
            // Y方向移动
            objAnimatorY = ObjectAnimator.ofFloat(buttonList.get(i), "y",
                    buttonList.get(i).getY(), buttonList.get(i).getY() + distanceY);
            objAnimatorY.setDuration(300);
            objAnimatorY.setStartDelay(100);
            objAnimatorY.start();
            // 按钮旋转
            objAnimatorRotate = ObjectAnimator.ofFloat(buttonList.get(i),
                    "rotation", 0, 360);
            objAnimatorRotate.setDuration(300);
            objAnimatorY.setStartDelay(100);
            objAnimatorRotate.start();
            Log.d("===TTT", "   ==  flag:" + flag);
            //            if (flag == -1) {
            //                showOrHideBtn(objAnimatorX, View.VISIBLE);
            //            } else if (flag == 1) {
            showOrHideBtn(objAnimatorX, View.VISIBLE);
            //            }
        }
    }

    //按钮设置为可见或者不可见
    private void showOrHideBtn(ObjectAnimator objAnimatorX, int vG) {
        objAnimatorX.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // 将按钮设为可见
                for (int i = 0; i < buttonItems.size(); i++) {
                    buttonItems.get(i).setVisibility(vG);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // TODO Auto-generated method stub
            }
        });
    }

    private int x = 0;

    @SuppressLint("ClickableViewAccessibility")
    private void setPlrTouchLisenler() {
        mySpaceRefreshLayout.setOnTouchListener((view, motionEvent) -> {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    x = (int) motionEvent.getX();
                    break;
                case MotionEvent.ACTION_MOVE:
                    int nowX = (int) motionEvent.getX();
                    int i = nowX - x;
                    if (i > 120) {
                        x = 0;
                        ActivityAnimUtils.instance().activityOut(MySpaceActivity.this);
                    }
                    break;
            }
            return false;
        });
    }

    private Handler mHandler = new Handler();
    private Boolean isRefresh = false;
    private Boolean isLoad = false;
    //模拟数据加载刷新
    private Runnable mRunnable = () -> {
        if (isRefresh) {
            ToastUtils.showSingleToast(MyApplication.getInstance(), "数据刷新中....");
            mySpaceRefreshLayout.finishRefresh(true);
            isRefresh = false;
        } else if (isLoad) {
            ToastUtils.showSingleToast(MyApplication.getInstance(), "数据加载中....");
            mySpaceRefreshLayout.finishLoadMore(true);
            isLoad = false;
        }
    };

    @Override
    protected void initData() {

    }

    @Override
    protected String initTitle() {
        return "我的主场";
    }

    @Override
    protected Boolean showBackBtn() {
        return true;
    }

    @Override
    protected int topBarColor() {
        return 0;
    }

    @Override
    protected void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.addDynationBtn:
            case R.id.mySpaceFloatBtn:
            case R.id.mySpaceAdd:
                //弹出发表视频或图文的动态对话框
                //                if (flag == 1) {
                //                    flag = -1;
                //                    buttonAnimation(buttonItems, -300);
                //                } else if (flag == -1) {
                //                    flag = 1;
                //                    buttonAnimation(buttonItems, 300);
                //                }
                setSendDynamicView();
                break;
            case R.id.backBtn:
                ActivityAnimUtils.instance().activityOut(MySpaceActivity.this);
                break;
            case R.id.dynamicSpaceBtn:
                //跳转动态广场

                break;
            case R.id.photoSpaceBtn:
                //跳转照片短视频空间

                break;
        }
    }

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
                    mySpaceFloatBtn.setVisibility(View.GONE);
                } else if (newState == 0) {
                    mySpaceFloatBtn.setVisibility(View.VISIBLE);
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

    @Override
    public void commentDynamic(int position) {

    }

    @Override
    public void dialogFgPositiveBtnClick() {

    }

    @Override
    public void arrowClickCallBack(int position) {

    }

    @Override
    public void likeCallBack(int position, TextView likeTv) {

    }

    private CollapsingToolbarLayoutState state;

    private void appBarLayoutSH() {
        mySpaceAppBar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            if (verticalOffset == 0) {
                if (state != CollapsingToolbarLayoutState.EXPANDED) {
                    state = CollapsingToolbarLayoutState.EXPANDED;//修改状态标记为展开
                    mToolbar.setVisibility(View.GONE);
                    mBackBtn.setVisibility(View.VISIBLE);
                }
            } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                if (state != CollapsingToolbarLayoutState.COLLAPSED) {
                    mToolbar.setVisibility(View.VISIBLE);
                    mBackBtn.setVisibility(View.GONE);
                    mToolbar.setBackgroundResource(R.drawable.topbar_bg);
                    state = CollapsingToolbarLayoutState.COLLAPSED;//修改状态标记为折叠
                }
            } else {
                if (state != CollapsingToolbarLayoutState.INTERNEDIATE) {
                    if (state == CollapsingToolbarLayoutState.COLLAPSED) {
                        mToolbar.setVisibility(View.GONE);
                        mBackBtn.setVisibility(View.VISIBLE);
                    }
                    state = CollapsingToolbarLayoutState.INTERNEDIATE;//修改状态标记为中间
                }
            }
        });
    }

    private MyCircleAdapter myCircleAdapter;

    private void setSendDynamicView() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.push_dynamic_view, null);
        final AlertDialog[] mAlertDialog = new AlertDialog[1];
        MyDialogUtil.showBottomDynamicDialog(this, this, dialogView
                , true, R.drawable.dialog_bg, R.style.dialogTheme1, new DialogActCallBack() {
                    @Override
                    public void cancelActCallBack(AlertDialog alertDialog) {
                        mAlertDialog[0] = alertDialog;
                    }

                    @Override
                    public void positionActCallBack(AlertDialog alertDialog) {

                    }
                });
        EditText dynamicContentEt = dialogView.findViewById(R.id.dynamicContentEt);
        TextView sendTv = dialogView.findViewById(R.id.sendTv);
        RecyclerView recyclerView = dialogView.findViewById(R.id.recyclerView);
        LinearLayout dynamicSendBtn = dialogView.findViewById(R.id.dynamicSendBtn);
        LinearLayout videoDynamicBtn = dialogView.findViewById(R.id.videoDynamicBtn);
        LinearLayout photoDynamicBtn = dialogView.findViewById(R.id.photoDynamicBtn);
        recyclerView.setLayoutManager(new GridLayoutManager(this,
                3, OrientationHelper.VERTICAL, false));
        myCircleAdapter = new MyCircleAdapter(MySpaceActivity.this);
        recyclerView.setAdapter(myCircleAdapter);
        videoDynamicBtn.setOnClickListener(btnView -> {
            //选择视频
            PermissionUtils.instance().requestPermission(this,
                    getString(R.string.get_img_tip), PermissionC.WR_FILES_PERMISSION,
                    (PermissionResultCallBack) () -> new GMSelectImg().
                            picImgsOrVideo(MySpaceActivity.this,
                                    MimeType.ofVideo(),
                                    PermissionC.PIC_IMG_VIDEO_CODE,
                                    9));
        });
        photoDynamicBtn.setOnClickListener(btnView -> {
            //选择照片
            PermissionUtils.instance().requestPermission(this,
                    getString(R.string.get_img_tip), PermissionC.WR_FILES_PERMISSION,
                    (PermissionResultCallBack) () ->
                            new GMSelectImg().picImgsOrVideo(this, MimeType.ofImage(),
                                    PermissionC.PIC_IMG_VIDEO_CODE, 9));
        });
        dynamicSendBtn.setOnClickListener(btnView -> {
            //发布的文字
            String contentCharacter = dynamicContentEt.getText().toString().trim();
            ToastUtils.showSingleToast(MySpaceActivity.this, contentCharacter);

            //销毁alertDialog
            AlertDialog alertDialog = mAlertDialog[0];
            if (alertDialog != null && alertDialog.isShowing())
                alertDialog.dismiss();
        });
        dynamicContentEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String trim = editable.toString().trim();
                if (trim.length() > 0) {
                    GMTextSetIcon.setTvLeftIconColor(MySpaceActivity.this, sendTv,
                            R.drawable.ic_send_pulle, R.color.plum_3);
                } else {
                    GMTextSetIcon.setTvLeftIconColor(MySpaceActivity.this, sendTv,
                            R.drawable.ic_send_gray, R.color.black_4);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PermissionC.PIC_IMG_VIDEO_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    List<Uri> mSelected = Matisse.obtainResult(data);
                    Log.d("===TAG", "   size:" + mSelected.size());

                    //                    GlideUtil.loadCornerdImg(MySpaceActivity.this, mSelected.get(0),
                    //                            R.drawable.loading1,
                    //                            iv, true, true, false, false);
                    myCircleAdapter.addData(mSelected);

                }
                break;
        }
    }
}
