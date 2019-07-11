package newmatch.zbmf.com.testapplication.activitys;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
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
import com.zhihu.matisse.MimeType;

import java.util.List;

import newmatch.zbmf.com.testapplication.GMClass.GMCalendar;
import newmatch.zbmf.com.testapplication.GMClass.GMSelectImg;
import newmatch.zbmf.com.testapplication.MainActivity;
import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.assist.GlideUtil;
import newmatch.zbmf.com.testapplication.base.BaseActivity;
import newmatch.zbmf.com.testapplication.base.MyApplication;
import newmatch.zbmf.com.testapplication.callback.PermissionResultCallBack;
import newmatch.zbmf.com.testapplication.component.PLog;
import newmatch.zbmf.com.testapplication.custom_view.RoundImageView;
import newmatch.zbmf.com.testapplication.permissions.PermissionC;
import newmatch.zbmf.com.testapplication.utils.MyActivityManager;
import newmatch.zbmf.com.testapplication.utils.PermissionUtils;
import newmatch.zbmf.com.testapplication.utils.SkipActivityUtil;
import newmatch.zbmf.com.testapplication.utils.ToastUtils;

/**
 * 圈友信息填写页面
 */
public class UserInfoActivity extends BaseActivity {

    private TextInputLayout mPhoneLayout;
    //用户同意所需的全部权限的标志
    private RoundImageView mAvatarIv;
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

        sexRG.setOnCheckedChangeListener((group, checkedId) -> {
            //获取用户的性别
            RadioButton radioButton = UserInfoActivity.this.findViewById(group.getCheckedRadioButtonId());
            CharSequence sex = radioButton.getText();
            PLog.LogD("--   选择的性别  :" + sex);

        });

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
                PermissionUtils.instance().requestPermission(this,
                        getString(R.string.get_img_tip),PermissionC.WR_FILES_PERMISSION,
                        new PermissionResultCallBack() {
                            @Override
                            public void permissionCallBack() {
                                //选择图片
                                new GMSelectImg().picImgsOrVideo(UserInfoActivity.this
                                        , MimeType.ofImage(),
                                        PermissionC.PIC_IMG_VIDEO_CODE, 1);
                            }
                        });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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

}
