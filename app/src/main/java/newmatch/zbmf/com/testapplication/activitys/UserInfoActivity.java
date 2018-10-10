package newmatch.zbmf.com.testapplication.activitys;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.zhihu.matisse.Matisse;

import java.util.List;

import newmatch.zbmf.com.testapplication.GMClass.GMCalendar;
import newmatch.zbmf.com.testapplication.GMClass.GMPermissions;
import newmatch.zbmf.com.testapplication.GMClass.GMSelectImg;
import newmatch.zbmf.com.testapplication.MainActivity;
import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.assist.GlideUtil;
import newmatch.zbmf.com.testapplication.base.BaseActivity;
import newmatch.zbmf.com.testapplication.base.MyApplication;
import newmatch.zbmf.com.testapplication.component.PLog;
import newmatch.zbmf.com.testapplication.custom_view.RoundImageView;
import newmatch.zbmf.com.testapplication.permissions.PermissionC;
import newmatch.zbmf.com.testapplication.utils.MyActivityManager;
import newmatch.zbmf.com.testapplication.utils.SkipActivityUtil;
import newmatch.zbmf.com.testapplication.utils.ToastUtils;

/**
 * 圈友信息填写页面
 */
public class UserInfoActivity extends BaseActivity implements GMPermissions.PermissionCallBackExcute{

    private TextInputLayout mPhoneLayout;
    //    private TextInputLayout mLocationLayout;
//    private TextInputLayout mBirthdayLayout;
    //用户同意所需的全部权限的标志
    private Boolean allGrantedPermission = false;
    private RoundImageView mAvatarIv;
    private TextView mLocationTv;
    private TextView mBirthdayTv;
    private GMPermissions mGmPermissions;

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
        Button submitUserInfoBtn = bindViewWithClick(R.id.submitUserInfoBtn, true);
        mLocationTv = bindViewWithClick(R.id.locationTv, true);
        bindViewWithClick(R.id.selectLocationIv, true);
        mBirthdayTv = bindViewWithClick(R.id.birthdayTv, true);
        bindViewWithClick(R.id.selectBirthdayIv, true);

        submitUserInfoBtn.setText(getString(R.string.confirm));

        textChangeListener(mPhoneLayout, clearPhone);
//        textChangeListener(mLocationLayout, clearLocation);
//        textChangeListener(mBirthdayLayout, clearBirthday);
        //权限
//        wrFilesPermission();
        //申请权所需要的对象
        mGmPermissions = GMPermissions.instance().setParameter(this, this, PermissionC.WR_FILE_CODE);
        mGmPermissions.setPermissionCallBackExcute(this);

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

    /**
     * 封装好动态权限
     */
    public void wrFilesPermission() {
        //对应的是Build.Version_code  16
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            GMPermissions.skipPermissionActivity(this,
                    PermissionC.WR_FILES_PERMISSION,PermissionC.PIC_IMG_VIDEO_CODE
            ,getString(R.string.get_img_tip));
        }else {
            //选择图片
            new GMSelectImg().picImgsOrVideo(this, PermissionC.PIC_IMG_VIDEO_CODE,1);
        }
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
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
        }*/
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
//                if (!allGrantedPermission) {
                    //检查权限
                    wrFilesPermission();
//                } else {
                    //执行选择图片视频
//                    picImgsOrVideo(1);
//                    new GMSelectImg().picImgsOrVideo(this, PermissionC.PIC_IMG_VIDEO_CODE,1);
//                }
                break;
            case R.id.birthdayTv:
            case R.id.selectBirthdayIv:
                //选择生日
                String calendar = new GMCalendar().showCalemdar(UserInfoActivity.this).getCalendar();
                mBirthdayTv.setText(calendar);
//                showDataPicker();
                break;
            case R.id.locationTv:
            case R.id.selectLocationIv:
                //选择城市
                SkipActivityUtil.skipActivityForResult(UserInfoActivity.this,
                        SelectCityActivity.class, PermissionC.USER_INFO_CITY_CODE);
                break;
            case R.id.submitUserInfoBtn:
                //提交用户资料
                ToastUtils.showSingleToast(MyApplication.getInstance(), "提交用户资料");
                SkipActivityUtil.skipActivity(UserInfoActivity.this, MainActivity.class);
                break;
        }
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case PermissionC.WR_FILE_CODE:
//                for (int i = 0; i < grantResults.length; i++) {
//                    //某一个权限没有同意--->dialog,向用户展示说明需要该权限的用途
//                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
//                        MyDialogUtil.getInstance().setDialogCallBack(new DialogCallBack() {
//                            @Override
//                            public void positiveClick(DialogInterface dialog) {
//                                //用户同意权限--->重新申请权限
//                                ActivityCompat.requestPermissions(UserInfoActivity.this
//                                        , permissions, PermissionC.WR_FILE_CODE);
//                                dialog.dismiss();
//                            }
//
//                            @Override
//                            public void negativeClick(DialogInterface dialog) {
//                                //用户不同意权限--->不做处理
//                                dialog.dismiss();
//                            }
//                        })
//                                .showPermissionDialog(UserInfoActivity.this, getString(R.string.wr_permission_tip));
//                    }
//                    //用户同意了所有权限
//                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED && i == grantResults.length - 1) {
//                        allGrantedPermission = true;
//                    }
//                }
//                break;
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        PLog.LogD("---  请求码  :"+requestCode+"--   结果码 ： "+resultCode);
        switch (requestCode) {
            case PermissionC.PIC_IMG_VIDEO_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    List<Uri> mSelected = Matisse.obtainResult(data);
                    GlideUtil.loadCircleImage(UserInfoActivity.this,
                            R.drawable.touxiang_icon, mSelected.get(0), mAvatarIv);
                }
                break;
            case PermissionC.USER_INFO_CITY_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    String seeCity = data.getStringExtra(PermissionC.USER_CITY);
                    //选择到的城市
                    mLocationTv.setText(seeCity);
                }
                break;
        }
    }

//    //系统的时间选择器
//    public void showDataPicker() {
//        final Calendar calendar = Calendar.getInstance();
////        mTvDate.setText(DateUtils.date2String(calendar.getTime(), DateUtils.YMD_FORMAT));
//        DatePickerDialog dialog = new DatePickerDialog(UserInfoActivity.this,
//                (view, year, month, dayOfMonth) -> {
//                    PLog.LogD("onDateSet: year: " + year + ", month: " + month + ", dayOfMonth: " + dayOfMonth);
//                    calendar.set(Calendar.YEAR, year);
//                    calendar.set(Calendar.MONTH, month);
//                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                    mBirthdayTv.setText(TimeUtil.getYMD(calendar.getTime()/*, DateUtils.YMD_FORMAT*/));
//                },
//                calendar.get(Calendar.YEAR),
//                calendar.get(Calendar.MONTH),
//                calendar.get(Calendar.DAY_OF_MONTH));
//        dialog.show();
//    }


    @Override
    public void excutePermissionCodes() {
        //选择图片
        new GMSelectImg().picImgsOrVideo(this, PermissionC.PIC_IMG_VIDEO_CODE,1);
    }
}
