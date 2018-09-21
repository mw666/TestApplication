package newmatch.zbmf.com.testapplication.activitys;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.Calendar;
import java.util.List;

import newmatch.zbmf.com.testapplication.MainActivity;
import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.assist.GifSizeFilter;
import newmatch.zbmf.com.testapplication.assist.Glide4Engine;
import newmatch.zbmf.com.testapplication.assist.GlideUtil;
import newmatch.zbmf.com.testapplication.base.BaseActivity;
import newmatch.zbmf.com.testapplication.base.MyApplication;
import newmatch.zbmf.com.testapplication.component.PLog;
import newmatch.zbmf.com.testapplication.custom_view.CircleImageView;
import newmatch.zbmf.com.testapplication.dialogs.MyDialogUtil;
import newmatch.zbmf.com.testapplication.listeners.DialogCallBack;
import newmatch.zbmf.com.testapplication.permissions.PermissionC;
import newmatch.zbmf.com.testapplication.utils.MyActivityManager;
import newmatch.zbmf.com.testapplication.utils.SkipActivityUtil;
import newmatch.zbmf.com.testapplication.utils.TimeUtil;
import newmatch.zbmf.com.testapplication.utils.ToastUtils;

/**
 * 圈友信息填写页面
 */
public class UserInfoActivity extends BaseActivity {

    private TextInputLayout mPhoneLayout;
//    private TextInputLayout mLocationLayout;
//    private TextInputLayout mBirthdayLayout;
    //用户同意所需的全部权限的标志
    private Boolean allGrantedPermission = false;
    private CircleImageView mAvatarIv;
    private TextView mLocationTv;
    private TextView mBirthdayTv;


    @Override
    protected Integer layoutId() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void initView() {
        MyActivityManager.getMyActivityManager().pushAct(UserInfoActivity.this);
        bindViewWithClick(R.id.avatarL, true);
        mAvatarIv = bindViewWithClick(R.id.avatarIv, true);
        bindViewWithClick(R.id.selectPic, true);
        RadioGroup sexRG = bindView(R.id.sexRG);
        mPhoneLayout = bindView(R.id.phoneLayout);
        ImageView clearPhone = bindViewWithClick(R.id.clearPhone, true);
//        mLocationLayout = bindView(R.id.locationLayout);
//        ImageView clearLocation = bindViewWithClick(R.id.clearLocation, true);
//        mBirthdayLayout = bindView(R.id.birthdayLayout);
//        ImageView clearBirthday = bindViewWithClick(R.id.clearBirthday, true);
        Button submitUserInfoBtn = bindViewWithClick(R.id.submitUserInfoBtn,true);
        mLocationTv = bindViewWithClick(R.id.locationTv, true);
        bindViewWithClick(R.id.selectLocationIv, true);
        mBirthdayTv = bindViewWithClick(R.id.birthdayTv, true);
        bindViewWithClick(R.id.selectBirthdayIv, true);

        submitUserInfoBtn.setText(getString(R.string.confirm));

        textChangeListener(mPhoneLayout, clearPhone);
//        textChangeListener(mLocationLayout, clearLocation);
//        textChangeListener(mBirthdayLayout, clearBirthday);
        //权限
        wrFilesPermission();

        sexRG.setOnCheckedChangeListener((group, checkedId) -> {
            //获取用户的性别
            RadioButton radioButton = (RadioButton) UserInfoActivity.this.findViewById(group.getCheckedRadioButtonId());
            CharSequence sex = radioButton.getText();
            PLog.LogD("--   选择的性别  :" + sex);

        });

    }

    @Override
    protected void initData() {

    }

    public void wrFilesPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //如果Android版本大于或等于6.0
            boolean b = ActivityCompat.checkSelfPermission(this, PermissionC.WR_FILES_PERMISSION[0])
                    != PackageManager.PERMISSION_GRANTED;
            boolean b1 = ActivityCompat.checkSelfPermission(
                    this, PermissionC.WR_FILES_PERMISSION[1])
                    != PackageManager.PERMISSION_GRANTED;
            if (b || b1) {
                PLog.LogD("---    执行获取文件读写权限  ----");
                //请求权限
                ActivityCompat.requestPermissions(this, PermissionC.WR_FILES_PERMISSION
                        , PermissionC.WR_FILE_CODE);
            } else {
                allGrantedPermission = true;
            }
        } else {
            allGrantedPermission = true;
        }
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
        return getString(R.string.user_info);
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
            case R.id.clearPhone:
                mPhoneLayout.getEditText().getText().clear();
                break;
            /*case R.id.clearLocation:
                mLocationLayout.getEditText().getText().clear();
                break;
            case R.id.clearBirthday:
                mBirthdayLayout.getEditText().getText().clear();
                break;*/
            case R.id.avatarL:
            case R.id.avatarIv:
            case R.id.selectPic:
                if (!allGrantedPermission) {
                    //检查权限
                    wrFilesPermission();
                } else {
                    //执行选择图片视频
                    picImgsOrVideo(1);
                }
                break;
            case R.id.birthdayTv:
            case R.id.selectBirthdayIv:
                //选择生日
                showDataPicker();
                break;
            case R.id.locationTv:
            case R.id.selectLocationIv:
                //选择城市
                SkipActivityUtil.skipActivityForResult(UserInfoActivity.this,
                        SelectCityActivity.class, PermissionC.USER_INFO_CITY_CODE);
                break;
            case R.id.submitUserInfoBtn:
                //提交用户资料
                ToastUtils.showSingleToast(MyApplication.getInstance(),"提交用户资料");
                SkipActivityUtil.skipActivity(UserInfoActivity.this,MainActivity.class);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PermissionC.WR_FILE_CODE:
                for (int i = 0; i < grantResults.length; i++) {
                    //某一个权限没有同意--->dialog,向用户展示说明需要该权限的用途
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        MyDialogUtil.getInstance().setDialogCallBack(new DialogCallBack() {
                            @Override
                            public void positiveClick(DialogInterface dialog) {
                                //用户同意权限--->重新申请权限
                                ActivityCompat.requestPermissions(UserInfoActivity.this
                                        , permissions, PermissionC.WR_FILE_CODE);
                                dialog.dismiss();
                            }

                            @Override
                            public void negativeClick(DialogInterface dialog) {
                                //用户不同意权限--->不做处理
                                dialog.dismiss();
                            }
                        })
                                .showPermissionDialog(UserInfoActivity.this, getString(R.string.wr_permission_tip));
                    }
                    //用户同意了所有权限
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED && i == grantResults.length - 1) {
                        allGrantedPermission = true;
                    }
                }
                break;
        }
    }

    //取文件
    public void picImgsOrVideo(int maxSelectable) {
        Matisse.from(UserInfoActivity.this)
                .choose(MimeType.ofAll())
                .countable(true)//true:选中后显示数字;false:选中后显示对号
                .maxSelectable(maxSelectable)//可选的最大数
                .spanCount(3)//网格数
                .capture(true)//选择照片时，是否显示拍照
                //参数1 true表示拍照存储在共有目录，false表示存储在私有目录；参数2与 AndroidManifest中authorities值相同，用于适配7.0系统 必须设置
                .captureStrategy(new CaptureStrategy(true, "newmatch.zbmf.com.testapplication"))
                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)//图片的缩放比例
                .imageEngine(new Glide4Engine())//图片加载引擎
                .theme(R.style.Matisse_MyZhihu /*| R.style.Matisse_Dracula*/)
                .forResult(PermissionC.PIC_IMG_VIDEO_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        PLog.LogD("---  请求码  :"+requestCode+"--   结果码 ： "+resultCode);
        switch (requestCode){
            case PermissionC.PIC_IMG_VIDEO_CODE:
                if (resultCode== Activity.RESULT_OK){
                    List<Uri> mSelected = Matisse.obtainResult(data);
                    GlideUtil.loadCircleImage(UserInfoActivity.this,
                            R.drawable.touxiang_icon, mSelected.get(0), mAvatarIv);
                }
                break;
            case PermissionC.USER_INFO_CITY_CODE:
                if (resultCode==Activity.RESULT_OK){
                    String seeCity = data.getStringExtra(PermissionC.USER_CITY);
                    //选择到的城市
                    mLocationTv.setText(seeCity);
                }
                break;
        }
    }

    //系统的时间选择器
    public void showDataPicker(){
        final Calendar calendar = Calendar.getInstance();
//        mTvDate.setText(DateUtils.date2String(calendar.getTime(), DateUtils.YMD_FORMAT));
        DatePickerDialog dialog = new DatePickerDialog(UserInfoActivity.this,
                (view, year, month, dayOfMonth) -> {
                    PLog.LogD("onDateSet: year: " + year + ", month: " + month + ", dayOfMonth: " + dayOfMonth);
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    mBirthdayTv.setText(TimeUtil.getYMD(calendar.getTime()/*, DateUtils.YMD_FORMAT*/));
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }


}
