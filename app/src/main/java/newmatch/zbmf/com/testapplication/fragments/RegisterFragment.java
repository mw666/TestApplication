package newmatch.zbmf.com.testapplication.fragments;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.activitys.ForgetPassWordActivity;
import newmatch.zbmf.com.testapplication.activitys.RegisterActivity;
import newmatch.zbmf.com.testapplication.activitys.UserInfoActivity;
import newmatch.zbmf.com.testapplication.base.BaseFragment;
import newmatch.zbmf.com.testapplication.base.MyApplication;
import newmatch.zbmf.com.testapplication.component.BuildConfig;
import newmatch.zbmf.com.testapplication.component.PLog;
import newmatch.zbmf.com.testapplication.entity.RegisterBean;
import newmatch.zbmf.com.testapplication.presenter.RegisterOrLoginPresenter;
import newmatch.zbmf.com.testapplication.presenter.backview.TestView;
import newmatch.zbmf.com.testapplication.presenter.presenterIml.BasePresenter;
import newmatch.zbmf.com.testapplication.utils.PhoneFormatCheckUtils;
import newmatch.zbmf.com.testapplication.utils.SkipActivityUtil;
import newmatch.zbmf.com.testapplication.utils.TimeCount;
import newmatch.zbmf.com.testapplication.utils.ToastUtils;

/**
 * A simple {@link Fragment} subclass.
 * 注册或登录
 */
public class RegisterFragment extends BaseFragment implements
        View.OnClickListener, TestView<RegisterBean, RegisterOrLoginPresenter> {

    private TextInputLayout mAccountTextLayout;
    private TextInputLayout mPasswordTextLayout;
    private ImageView mClearAccount;
    private ImageView mClearPassword;
    private Button mLoginBtn;
    private TextView mForgetPassword;
    private TextView mLoginProtocolContent;
    private ImageView mZc_clearAccount;
    private Button mZc_verifyCodeBtn;
    private Button mZc_btn;
    private ImageView mZc_clearPassword;
    private TextView mRegisterRule;
    private TextView mRegisterProtocol;
    private TextInputLayout mZc_accountTextLayout;
    private TextInputLayout mZc_passwordTextLayout;
    private TimeCount mTimecount;
    private String mPhone;
    private TextInputEditText mZc_verifyCodeInputEt;
    private int mTabPosition;
    private View mView;//布局的View
    private RegisterOrLoginPresenter mPresenter;
    private RegisterActivity registerActivity;

    public RegisterFragment() {
        PLog.LogD("RegisterFragment 的构造方法");
    }

    public static RegisterFragment getInstance(int position) {
        RegisterFragment registerFragment = new RegisterFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(BuildConfig.TAB_POSITION, position);
        registerFragment.setArguments(bundle);
        return registerFragment;
    }

    @Override
    protected Integer layoutId() {
        mTabPosition = getArguments().getInt(BuildConfig.TAB_POSITION);
        if (mTabPosition == 0) {
            return R.layout.fragment_login;
        } else if (mTabPosition == 1) {
            return R.layout.fragment_register;
        } else {
            return null;
        }
    }

    @Override
    protected void initView() {
        // 注册一个事件回调，用于处理SMSSDK接口请求的结果
//        SMSSDK.registerEventHandler(eventHandler);
        registerActivity = (RegisterActivity) getActivity();
        mView = getView();
        if (mTabPosition == 0) {
            mAccountTextLayout = bindView(mView, R.id.accountTextLayout);
            TextInputEditText accountInputEt = bindView(mView, R.id.accountInputEt);
            mClearAccount = bindView(mView, R.id.clearAccount);
            mPasswordTextLayout = bindView(mView, R.id.passwordTextLayout);
            TextInputEditText passwordInputEt = bindView(mView, R.id.passwordInputEt);
            mClearPassword = bindView(mView, R.id.clearPassword);
            mLoginBtn = mView.findViewById(R.id.loginBtn);
            mForgetPassword = mView.findViewById(R.id.forgetPassword);
            mLoginProtocolContent = mView.findViewById(R.id.loginProtocolContent);

            mLoginBtn.setText(getString(R.string.login));
//            initNoFocus(mAccountTextLayout);
//            initNoFocus(mPasswordTextLayout);
            clearListener(mAccountTextLayout, mClearAccount);
            clearListener(mPasswordTextLayout, mClearPassword);
            setLoginViewClick();
            //改变按钮的颜色
            btnChangeColor(passwordInputEt,mLoginBtn);
        } else if (mTabPosition == 1) {
            mZc_accountTextLayout = bindView(mView, R.id.zc_accountTextLayout);
            TextInputEditText zc_accountInputEt = bindView(mView, R.id.zc_accountInputEt);
            mZc_clearAccount = bindView(mView, R.id.zc_clearAccount);
            TextInputLayout zc_verifyCodeTextLayout = bindView(mView, R.id.zc_verifyCodeTextLayout);
            mZc_verifyCodeInputEt = bindView(mView, R.id.zc_verifyCodeInputEt);
            mZc_verifyCodeBtn = bindView(mView, R.id.zc_verifyCodeBtn);
            mZc_passwordTextLayout = bindView(mView, R.id.zc_passwordTextLayout);
            TextInputEditText zc_passwordInputEt = bindView(mView, R.id.zc_passwordInputEt);
            mZc_clearPassword = bindView(mView, R.id.zc_clearPassword);
            mZc_btn = bindView(mView, R.id.zc_btn);
            mRegisterRule = bindView(mView, R.id.registerRule);
            mRegisterProtocol = bindView(mView, R.id.registerProtocol);

            mZc_btn.setText(getString(R.string.register));
            clearListener(mZc_accountTextLayout, mZc_clearAccount);
            clearListener(mZc_passwordTextLayout, mZc_clearPassword);
            setRegisterViewClick();
            //改变按钮的颜色
            btnChangeColor(zc_passwordInputEt,mZc_btn);
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected BasePresenter initPresenter() {
        if (mPresenter == null) {
            mPresenter = new RegisterOrLoginPresenter(this);
        }
        return mPresenter;
    }

    @Override
    protected void onViewClick(View view) {

    }

    @Override
    protected Boolean setViewEnterStatuBar() {
        return false;
    }

    private void setLoginViewClick() {
        mClearAccount.setOnClickListener(this);
        mClearPassword.setOnClickListener(this);
        mLoginBtn.setOnClickListener(this);
        mForgetPassword.setOnClickListener(this);
        mLoginProtocolContent.setOnClickListener(this);
    }

    private void setRegisterViewClick() {
        mZc_clearAccount.setOnClickListener(this);
        mZc_verifyCodeBtn.setOnClickListener(this);
        mZc_clearPassword.setOnClickListener(this);
        mZc_btn.setOnClickListener(this);
        mRegisterRule.setOnClickListener(this);
        mRegisterProtocol.setOnClickListener(this);
    }

    //获取布局控件
    @SuppressWarnings("unchecked")
    protected <T extends View> T bindView(View view, int resourcesId) {
        try {
            return (T) view.findViewById(resourcesId);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param til
     * @param clearIv
     */
    private void clearListener(TextInputLayout til, ImageView clearIv) {
        til.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
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

    /**
     * 初始化默认不获取焦点
     *
     * @param til
     */
    private void initNoFocus(TextInputLayout til) {
        til.getEditText().setFocusable(false);
        til.getEditText().setFocusableInTouchMode(false);
        til.getEditText().requestFocus();
    }

    /**
     * 显示错误提示，并获取焦点
     *
     * @param textInputLayout
     * @param error
     */
    private void showError(TextInputLayout textInputLayout, String error) {
        textInputLayout.setError(error);
        textInputLayout.getEditText().setFocusable(true);
        textInputLayout.getEditText().setFocusableInTouchMode(true);
        textInputLayout.getEditText().requestFocus();
    }

    /**
     * 验证用户名
     *
     * @param account
     * @return
     */
    private boolean validateAccount(String account, TextInputLayout til) {
        if (TextUtils.isEmpty(account)) {
            showError(til, getString(R.string.account_no_empty));
            return false;
        }
        return true;
    }

    /**
     * 验证密码
     *
     * @param password
     * @return
     */
    private boolean validatePassword(String password, TextInputLayout til) {
        if (TextUtils.isEmpty(password)) {
            showError(til, getString(R.string.password_no_empty));
            return false;
        }
        if (password.length() < 6 || password.length() > 18) {
            showError(til, "密码长度为6-18位");
            return false;
        }

        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginBtn:
                String account = mAccountTextLayout.getEditText().getText().toString();
                String password = mPasswordTextLayout.getEditText().getText().toString();

                mAccountTextLayout.setErrorEnabled(false);
                mPasswordTextLayout.setErrorEnabled(false);

                //验证用户名和密码
                if (validateAccount(account, mAccountTextLayout) && validatePassword(password, mPasswordTextLayout)) {
                    // TODO: 2018/9/10 调用登录接口

                    ToastUtils.showSingleToast(MyApplication.getInstance(), getString(R.string.login_success));

//                    SkipActivityUtil.skipActivity(registerActivity, MainActivity.class);

                    SkipActivityUtil.skipActivity(registerActivity, UserInfoActivity.class);
                    registerActivity.finish();
                }
                break;
            case R.id.clearAccount:
                if (!TextUtils.isEmpty(mAccountTextLayout.getEditText().getText().toString()))
                    mAccountTextLayout.getEditText().getText().clear();
                break;
            case R.id.clearPassword:
                if (!TextUtils.isEmpty(mPasswordTextLayout.getEditText().getText().toString()))
                    mPasswordTextLayout.getEditText().getText().clear();
                break;

            case R.id.loginProtocolContent:
                // TODO: 2018/9/10 跳转登录协议展示

                break;
            case R.id.zc_clearAccount:
                if (!TextUtils.isEmpty(mZc_accountTextLayout.getEditText().getText().toString()))
                    mZc_accountTextLayout.getEditText().getText().clear();
                break;
            case R.id.zc_clearPassword:
                if (!TextUtils.isEmpty(mZc_passwordTextLayout.getEditText().getText().toString()))
                    mZc_passwordTextLayout.getEditText().getText().clear();
                break;
            case R.id.zc_verifyCodeBtn://获取验证码接口   phone下同zcAccount，都是用户账号
                mPhone = mZc_accountTextLayout.getEditText().getText().toString();
                boolean validateAccount = PhoneFormatCheckUtils.validateAccount(getActivity(),
                        mPhone, getString(R.string.phone_no_empty));
                // 请求验证码，其中country表示国家代码，如“86”；phone表示手机号码，如“13800138000”
                //中国国家代码为86
                if (!validateAccount) {
                    return;
                }
                //请求获取验证码
//                SMSSDK.getVerificationCode("86", mPhone);
                break;
            case R.id.zc_btn:
                String zcAccount = mZc_accountTextLayout.getEditText().getText().toString();
                String zcPassword = mZc_passwordTextLayout.getEditText().getText().toString();

                mZc_accountTextLayout.setErrorEnabled(false);
                mZc_passwordTextLayout.setErrorEnabled(false);
                //复制获取验证码
                String code = mZc_verifyCodeInputEt.getText().toString().trim();
                //验证用户名和密码
                if (validateAccount(zcAccount, mZc_accountTextLayout) && validatePassword(zcPassword,
                        mZc_passwordTextLayout)) {
                    if (!TextUtils.isEmpty(code)) {
                        //提交到开发者服务器--->mob的服务器,进行短信验证
                        mPresenter.register(BuildConfig.MOB_APPKEY, zcAccount, "86", code);
//                        SMSSDK.submitVerificationCode("86", mPhone, code);//这个是直接发送到mob服务器进行的验证
                    }
                }
                break;
            case R.id.registerRule:
                // TODO: 2018/9/10 使用条款
                SkipActivityUtil.skipActivity(registerActivity, ForgetPassWordActivity.class);
                break;
            case R.id.registerProtocol:
                //隐私协议
                SkipActivityUtil.skipActivity(registerActivity, ForgetPassWordActivity.class);
                break;
            case R.id.forgetPassword:
                SkipActivityUtil.skipActivity(registerActivity, ForgetPassWordActivity.class);
                break;
        }
    }

//    private EventHandler eventHandler = new EventHandler() {
//        public void afterEvent(int event, int result, Object data) {
//            // afterEvent会在子线程被调用，因此如果后续有UI相关操作，需要将数据发送到UI线程
//            Message msg = new Message();
//            msg.arg1 = event;
//            msg.arg2 = result;
//            msg.obj = data;
//            new Handler(Looper.getMainLooper(), msg1 -> {
//                int event1 = msg1.arg1;
//                int result1 = msg1.arg2;
//                Object data1 = msg1.obj;
//                if (event1 == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
//                    if (result1 == SMSSDK.RESULT_COMPLETE) {
//                        //发送验证码的请求完成--->发送验证码的按钮开始计时，变灰，不可点击
//                        setCodeBtn();
//                    } else {
//                        //处理错误的结果
//                        ((Throwable) data1).printStackTrace();
//                    }
//                } else if (event1 == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
//                    if (result1 == SMSSDK.RESULT_COMPLETE) {
//                        //处理验证码验证通过的结果
//                    } else {
//                        //处理错误的结果
//                        ((Throwable) data1).printStackTrace();
//                    }
//                }
//                //其他接口的返回结果也类似，根据event判断当前数据属于哪个接口
//                return false;
//            }).sendMessage(msg);
//        }
//    };

    //改变发送验证码按钮的状态
    private void setCodeBtn() {
        if (mTimecount == null) {
            mTimecount = new TimeCount(60000, 1000, mZc_verifyCodeBtn);
        }
        mTimecount.start();
    }

    // 使用完EventHandler需注销，否则可能出现内存泄漏
    @Override
    public void onDestroy() {
        super.onDestroy();
//        SMSSDK.unregisterEventHandler(eventHandler);
    }

    @Override
    public void setPresenter(RegisterOrLoginPresenter presenter) {
        //设置presenter
        this.mPresenter = presenter;
    }

    @Override
    public void resultCallBack(RegisterBean result) {
        //跳转圈友信息填写页面
        SkipActivityUtil.skipActivity(registerActivity, UserInfoActivity.class);
    }

    //设置EditText监听长度的变化，按钮的变色
    private void btnChangeColor(TextInputEditText tiEditText,Button btn){
        tiEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(tiEditText.getText().toString().trim())) {
                    if (tiEditText.getText().toString().trim().length() > 6) {
                        btn.setBackground(ContextCompat.getDrawable(getActivity(),
                                R.drawable.login_btn1_bg_pressed));
                    } else {
                        btn.setBackground(ContextCompat.getDrawable(getActivity(),
                                R.drawable.login_btn0_bg));
                    }
                }
            }
        });
    }
}
