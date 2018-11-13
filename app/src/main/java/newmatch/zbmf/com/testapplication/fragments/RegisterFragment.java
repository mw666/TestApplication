package newmatch.zbmf.com.testapplication.fragments;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import newmatch.zbmf.com.testapplication.MainActivity;
import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.activitys.ForgetPassWordActivity;
import newmatch.zbmf.com.testapplication.base.MyApplication;
import newmatch.zbmf.com.testapplication.component.BuildConfig;
import newmatch.zbmf.com.testapplication.component.PLog;
import newmatch.zbmf.com.testapplication.utils.PhoneFormatCheckUtils;
import newmatch.zbmf.com.testapplication.utils.SkipActivityUtil;
import newmatch.zbmf.com.testapplication.utils.TimeCount;
import newmatch.zbmf.com.testapplication.utils.ToastUtils;

/**
 * A simple {@link Fragment} subclass.
 * 注册或登录
 */
public class RegisterFragment extends Fragment implements View.OnClickListener {


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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int tabPosition = getArguments().getInt(BuildConfig.TAB_POSITION);

        // 在尝试读取通信录时以弹窗提示用户（可选功能）
//        SMSSDK.setAskPermisionOnReadContact(true);
        // 注册一个事件回调，用于处理SMSSDK接口请求的结果
        SMSSDK.registerEventHandler(eventHandler);

        View view = null;
        if (tabPosition == 0) {
            view = inflater.inflate(R.layout.fragment_login, container, false);
            mAccountTextLayout = bindView(view, R.id.accountTextLayout);
            TextInputEditText accountInputEt = bindView(view, R.id.accountInputEt);
            mClearAccount = bindView(view, R.id.clearAccount);
            mPasswordTextLayout = bindView(view, R.id.passwordTextLayout);
            TextInputEditText passwordInputEt = bindView(view, R.id.passwordInputEt);
            mClearPassword = bindView(view, R.id.clearPassword);
            mLoginBtn = view.findViewById(R.id.loginBtn);
            mForgetPassword = view.findViewById(R.id.forgetPassword);
            mLoginProtocolContent = view.findViewById(R.id.loginProtocolContent);

            mLoginBtn.setText(getString(R.string.login));
//            initNoFocus(mAccountTextLayout);
//            initNoFocus(mPasswordTextLayout);
            clearListener(mAccountTextLayout, mClearAccount);
            clearListener(mPasswordTextLayout, mClearPassword);
            setLoginViewClick();

            return view;
        } else if (tabPosition == 1) {
            view = inflater.inflate(R.layout.fragment_register, container, false);
            mZc_accountTextLayout = bindView(view, R.id.zc_accountTextLayout);
            TextInputEditText zc_accountInputEt = bindView(view, R.id.zc_accountInputEt);
            mZc_clearAccount = bindView(view, R.id.zc_clearAccount);
            TextInputLayout zc_verifyCodeTextLayout = bindView(view, R.id.zc_verifyCodeTextLayout);
            mZc_verifyCodeInputEt = bindView(view, R.id.zc_verifyCodeInputEt);
            mZc_verifyCodeBtn = bindView(view, R.id.zc_verifyCodeBtn);
            mZc_passwordTextLayout = bindView(view, R.id.zc_passwordTextLayout);
            TextInputEditText zc_passwordInputEt = bindView(view, R.id.zc_passwordInputEt);
            mZc_clearPassword = bindView(view, R.id.zc_clearPassword);
            mZc_btn = bindView(view, R.id.zc_btn);
            mRegisterRule = bindView(view, R.id.registerRule);
            mRegisterProtocol = bindView(view, R.id.registerProtocol);

            mZc_btn.setText(getString(R.string.register));
            clearListener(mZc_accountTextLayout, mZc_clearAccount);
            clearListener(mZc_passwordTextLayout, mZc_clearPassword);
            setRegisterViewClick();
            return view;
        }
        return view;
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

                    SkipActivityUtil.skipActivity(getActivity(), MainActivity.class);
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
            case R.id.forgetPassword:
                SkipActivityUtil.skipActivity(getActivity(), ForgetPassWordActivity.class);
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
            case R.id.zc_verifyCodeBtn:
                //获取验证码接口   phone下同zcAccount，都是用户账号
                mPhone = mZc_accountTextLayout.getEditText().getText().toString();
                boolean validateAccount = PhoneFormatCheckUtils.validateAccount(getActivity(),
                        mPhone, getString(R.string.phone_no_empty));
                // 请求验证码，其中country表示国家代码，如“86”；phone表示手机号码，如“13800138000”
                //中国国家代码为86
                if (!validateAccount) {
                    return;
                }
                SMSSDK.getVerificationCode("86", mPhone);
                break;
            case R.id.zc_btn:
                // TODO: 2018/9/10 注册
                String zcAccount = mZc_accountTextLayout.getEditText().getText().toString();
                String zcPassword = mZc_passwordTextLayout.getEditText().getText().toString();

                mZc_accountTextLayout.setErrorEnabled(false);
                mZc_passwordTextLayout.setErrorEnabled(false);
                //复制获取验证码
                String code = mZc_verifyCodeInputEt.getText().toString().trim();
                //验证用户名和密码
                if (validateAccount(zcAccount, mZc_accountTextLayout) && validatePassword(zcPassword,
                        mZc_passwordTextLayout)) {
                    if (!TextUtils.isEmpty(code)){
                        //提交到开发者服务器--->mob的服务器,进行短信验证
                        SMSSDK.submitVerificationCode("86", mPhone, code);
                        //跳转圈友信息填写页面
                        SkipActivityUtil.skipActivity(getActivity(), MainActivity.class);
                    }
                    // TODO: 2018/9/10 调用登录接口

//                    ToastUtils.showSingleToast(MyApplication.getInstance(), getString(R.string.register_success));
//                    //跳转圈友信息填写页面
//                    SkipActivityUtil.skipActivity(getActivity(), UserInfoActivity.class);

                }
                break;
            case R.id.registerRule:
                // TODO: 2018/9/10 使用条款
                SkipActivityUtil.skipActivity(getActivity(), ForgetPassWordActivity.class);
                break;
            case R.id.registerProtocol:
                //隐私协议
                SkipActivityUtil.skipActivity(getActivity(), ForgetPassWordActivity.class);
                break;
        }
    }

    private EventHandler eventHandler = new EventHandler() {
        public void afterEvent(int event, int result, Object data) {
            // afterEvent会在子线程被调用，因此如果后续有UI相关操作，需要将数据发送到UI线程
            Message msg = new Message();
            msg.arg1 = event;
            msg.arg2 = result;
            msg.obj = data;
            new Handler(Looper.getMainLooper(), msg1 -> {
                int event1 = msg1.arg1;
                int result1 = msg1.arg2;
                Object data1 = msg1.obj;
                if (event1 == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    if (result1 == SMSSDK.RESULT_COMPLETE) {
                        // TODO 处理成功得到验证码的结果
                        // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
                        // 提交验证码，其中的code表示验证码，如“1357”

                        //发送验证码的请求完成--->发送验证码的按钮开始计时，变灰，不可点击
                        setCodeBtn();
                    } else {
                        // TODO 处理错误的结果
                        ((Throwable) data1).printStackTrace();
                    }
                } else if (event1 == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    if (result1 == SMSSDK.RESULT_COMPLETE) {
                        // TODO 处理验证码验证通过的结果
                    } else {
                        // TODO 处理错误的结果
                        ((Throwable) data1).printStackTrace();
                    }
                }
                // TODO 其他接口的返回结果也类似，根据event判断当前数据属于哪个接口
                return false;
            }).sendMessage(msg);
        }
    };

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
        SMSSDK.unregisterEventHandler(eventHandler);
    }
}
