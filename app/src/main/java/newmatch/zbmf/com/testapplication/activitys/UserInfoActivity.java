package newmatch.zbmf.com.testapplication.activitys;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.model.AspectRatio;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import java.io.File;
import java.util.List;

import newmatch.zbmf.com.testapplication.GMClass.GMCalendar;
import newmatch.zbmf.com.testapplication.GMClass.GMSelectImg;
import newmatch.zbmf.com.testapplication.MainActivity;
import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.base.BaseActivity;
import newmatch.zbmf.com.testapplication.base.MyApplication;
import newmatch.zbmf.com.testapplication.callback.PermissionResultCallBack;
import newmatch.zbmf.com.testapplication.component.PLog;
import newmatch.zbmf.com.testapplication.permissions.PermissionC;
import newmatch.zbmf.com.testapplication.utils.LogUtils;
import newmatch.zbmf.com.testapplication.utils.MyActivityManager;
import newmatch.zbmf.com.testapplication.utils.PermissionUtils;
import newmatch.zbmf.com.testapplication.utils.SkipActivityUtil;
import newmatch.zbmf.com.testapplication.utils.ToastUtils;
import newmatch.zbmf.com.testapplication.utils.YeHiShareUtil;
import newmatch.zbmf.com.testapplication.utils.fileUtils.FileUtils;
import newmatch.zbmf.com.testapplication.utils.glidUtils.GlideUtil;

/**
 * 嗨友信息填写页面
 */
public class UserInfoActivity extends BaseActivity {

    private TextInputLayout mPhoneLayout;
    private TextView mLocationTv;
    private TextView mBirthdayTv;
    private AppCompatImageView userAvatar;
    private AppCompatImageView userShowImg;
    private TextView selectUserShowImg;
    private Button submitUserInfoBtn;

    @Override
    protected Integer layoutId() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void initView() {
        //设置内容顶进状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        MyActivityManager.getMyActivityManager().pushAct(UserInfoActivity.this);

        //初始化设置ToolBar
        initGoneToolBar();

        RadioGroup sexRG = bindView(R.id.sexRG);
        mPhoneLayout = bindView(R.id.phoneLayout);
        ImageView clearPhone = bindViewWithClick(R.id.clearPhone, true);
        submitUserInfoBtn = bindViewWithClick(R.id.submitUserInfoBtn, true);
        mLocationTv = bindViewWithClick(R.id.locationTv, true);
        bindViewWithClick(R.id.selectLocationIv, true);
        mBirthdayTv = bindViewWithClick(R.id.birthdayTv, true);
        bindViewWithClick(R.id.selectBirthdayIv, true);
        //用户头像
        userAvatar = bindViewWithClick(R.id.userAvatar, true);
        selectUserShowImg = bindViewWithClick(R.id.selectUserShowImg, true);
        userShowImg = bindViewWithClick(R.id.userShowImg, true);

        String province = YeHiShareUtil.getProvince();
        String city = YeHiShareUtil.getCity();
        String seeCity = YeHiShareUtil.getLocality();
        mLocationTv.setText(String.format("%s-%s-%s", province, city, seeCity));

        textChangeListener(mPhoneLayout, clearPhone);

        sexRG.setOnCheckedChangeListener((group, checkedId) -> {
            //获取用户的性别
            RadioButton radioButton = UserInfoActivity.this.findViewById(group.getCheckedRadioButtonId());
            CharSequence sex = radioButton.getText();
            if (!TextUtils.isEmpty(sex)) {
                rgSelected = true;
                setSubmitBtnColor();
            }
        });
    }

    //初始化隐藏ToolBar
    private void initGoneToolBar() {
        Toolbar toolbar = bindView(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayShowTitleEnabled(false);
        supportActionBar.setDisplayHomeAsUpEnabled(false);
    }

    @Override
    protected void initData() {

    }

    /**
     * 监听文字的变化
     *
     * @param til
     */
    private void textChangeListener(TextInputLayout til, ImageView clearIv) {
        til.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                PLog.LogD("--  文字的长度  :" + s.length());
                if (s.length() > 0) {
                    clearIv.setVisibility(View.VISIBLE);
                } else {
                    clearIv.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected String initTitle() {
        return "个人资料";
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
            case R.id.selectUserShowImg:
            case R.id.userShowImg:
                //选取用户展示的照片
                PermissionUtils.instance().requestPermission(this,
                        getString(R.string.get_img_tip), PermissionC.WR_FILES_PERMISSION,
                        (PermissionResultCallBack) () -> {
                            //选择图片
                            new GMSelectImg().picImgsOrVideo(UserInfoActivity.this
                                    , MimeType.ofImage(),
                                    PermissionC.SHWO_USER_IMG, 1);
                        });
                break;
            case R.id.clearPhone:
                mPhoneLayout.getEditText().getText().clear();
                break;
            case R.id.userAvatar:
                PermissionUtils.instance().requestPermission(this,
                        getString(R.string.get_img_tip), PermissionC.WR_FILES_PERMISSION,
                        (PermissionResultCallBack) () -> {
                            //选择图片
                            new GMSelectImg().picImgsOrVideo(UserInfoActivity.this
                                    , MimeType.ofImage(),
                                    PermissionC.PIC_IMG_VIDEO_CODE, 1);
                        });
                break;
            case R.id.birthdayTv:
            case R.id.selectBirthdayIv:
                //选择生日
                new GMCalendar().showCalemdar(UserInfoActivity.this,
                        date -> {
                            mBirthdayTv.setText(date);
                            setSubmitBtnColor();
                        });
                break;
            case R.id.locationTv:
            case R.id.selectLocationIv:
                //选择城市
                SkipActivityUtil.skipActivityForResult(UserInfoActivity.this,
                        SelectCityActivity.class, PermissionC.USER_INFO_CITY_CODE);
                break;
            case R.id.submitUserInfoBtn:
                //提交用户资料
                submitUserInfo();
                break;
        }
    }

    private boolean hasHead = false;
    private boolean hasShowCover = false;
    private boolean rgSelected = false;
    private int type = 0;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PermissionC.PIC_IMG_VIDEO_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    List<Uri> mSelected = Matisse.obtainResult(data);
                    if (mSelected.get(0) != null) {
                        this.type = 1;
                        selectImgCrop(mSelected.get(0), type);
                    }
                }
                break;
            case PermissionC.USER_INFO_CITY_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    String seeCity = data.getStringExtra(PermissionC.USER_CITY);
                    //选择到的城市
                    mLocationTv.setText(seeCity);
                    setSubmitBtnColor();
                }
                break;
            case PermissionC.SHWO_USER_IMG:
                if (resultCode == Activity.RESULT_OK) {
                    List<Uri> mSelected = Matisse.obtainResult(data);
                    this.type = 2;
                    selectImgCrop(mSelected.get(0), type);
                }
                break;
            case UCrop.REQUEST_CROP:
                //返回裁剪的图片
                if (resultCode == RESULT_OK) {
                    Uri resultUri = UCrop.getOutput(data);
                    if (resultUri != null) {
                        if (this.type == 1) {
                            //用户头像
                            GlideUtil.loadCircleImage(UserInfoActivity.this,
                                    R.drawable.ic_head_portrait_icon, resultUri, userAvatar);
                            hasHead = true;
                        } else if (this.type == 2) {
                            //用户展示封面
                            GlideUtil.loadImage(UserInfoActivity.this,
                                    R.drawable.place_holder_img, resultUri, userShowImg);
                            selectUserShowImg.setVisibility(View.GONE);
                            hasShowCover = true;
                        }
                        setSubmitBtnColor();
                    }
                } else {
                    if (data != null) {
                        Throwable cropError = UCrop.getError(data);
                        String errorMessage = cropError.getMessage();
                        LogUtils.E(errorMessage);
                    }
                }
                break;
        }
    }

    //判断 用户生日定位都不为空
    private boolean birthDayLocationEmpty() {
        String location = mLocationTv.getText().toString().trim();
        String birthDay = mBirthdayTv.getText().toString().trim();
        if (!TextUtils.isEmpty(location) && !TextUtils.isEmpty(birthDay)) {
            return true;
        } else {
            return false;
        }
    }

    //设置按钮的颜色
    private void setSubmitBtnColor() {
        if (hasShowCover && hasHead && rgSelected && birthDayLocationEmpty()) {
            submitUserInfoBtn.setBackgroundResource(R.drawable.login_btn1_bg_pressed);
        } else {
            submitUserInfoBtn.setBackgroundResource(R.drawable.login_btn0_bg);
        }
    }

    //提交用户资料
    private void submitUserInfo() {
        String location = mLocationTv.getText().toString().trim();
        String birthDay = mBirthdayTv.getText().toString().trim();
        if (!hasHead) {
            ToastUtils.showSingleToast(this, "请选择用户头像");
            return;
        }
        if (!hasShowCover) {
            ToastUtils.showSingleToast(this, "请选择本人展示照片");
            return;
        }
        if (!rgSelected) {
            ToastUtils.showSingleToast(this, "请选择您的性别");
            return;
        }
        if (TextUtils.isEmpty(location)) {
            ToastUtils.showSingleToast(this, "请选择您的所在城市地区");
            return;
        }
        if (TextUtils.isEmpty(birthDay)) {
            ToastUtils.showSingleToast(this, "请选择您的生日");
            return;
        }
        SkipActivityUtil.skipActivity(UserInfoActivity.this, MainActivity.class);
        finish();
    }

    //选择图片并裁剪
    public void selectImgCrop(Uri sourceUri, int type) {
        String yeHiImgPath = FileUtils.getYeHiImgUserPath();
        FileUtils.checkDir(yeHiImgPath);
        String timeStamp = String.valueOf(System.currentTimeMillis());
        File file = new File(FileUtils.getUserShowCover(timeStamp));
        Uri destinationUri = Uri.fromFile(file);
        advancedConfig(sourceUri, destinationUri, type);
    }

    private void advancedConfig(Uri sourceUri, Uri destinationUri, int type) {
        //裁剪图片
        UCrop uCrop = UCrop.of(sourceUri, destinationUri);
        UCrop.Options options = new UCrop.Options();
        options.setToolbarColor(ActivityCompat.getColor(this, R.color.deepPurple));
        options.setStatusBarColor(ActivityCompat.getColor(this, R.color.deepPurple));
        options.setToolbarTitle(getString(R.string.crop_img));
        options.setFreeStyleCropEnabled(true);
        options.setCompressionFormat(Bitmap.CompressFormat.PNG);
        options.setShowCropFrame(true);
        options.setMaxBitmapSize(480);
        options.setAspectRatioOptions(1,
//                new AspectRatio("WOW", 1, 2),
                new AspectRatio("MUCH", 3, 4),
//                new AspectRatio("RATIO", CropImageView.DEFAULT_ASPECT_RATIO,
//                        CropImageView.DEFAULT_ASPECT_RATIO),
                new AspectRatio("ASPECT", 1, 1),
                new AspectRatio("SO", 16, 9));
        uCrop.withAspectRatio(1, 1)
                .withMaxResultSize(640, 640)
                .withOptions(options)
                .useSourceImageAspectRatio()
                .start(UserInfoActivity.this);
    }

}
