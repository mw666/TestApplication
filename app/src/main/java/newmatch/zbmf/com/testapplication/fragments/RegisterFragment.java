package newmatch.zbmf.com.testapplication.fragments;


import android.os.Bundle;
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

import newmatch.zbmf.com.testapplication.MainActivity;
import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.activitys.ForgetPassWordActivity;
import newmatch.zbmf.com.testapplication.activitys.UserInfoActivity;
import newmatch.zbmf.com.testapplication.base.MyApplication;
import newmatch.zbmf.com.testapplication.component.BuildConfig;
import newmatch.zbmf.com.testapplication.component.PLog;
import newmatch.zbmf.com.testapplication.utils.SkipActivityUtil;
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
            TextInputEditText zc_verifyCodeInputEt = bindView(view, R.id.zc_verifyCodeInputEt);
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
    private boolean validateAccount(String account,TextInputLayout til) {
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
    private boolean validatePassword(String password,TextInputLayout til) {
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
                if (validateAccount(account,mAccountTextLayout) && validatePassword(password,mPasswordTextLayout)) {
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
                //获取验证码接口
                // TODO: 2018/9/10 获取验证码

                break;
            case R.id.zc_btn:
                // TODO: 2018/9/10 注册
                String zcAccount = mZc_accountTextLayout.getEditText().getText().toString();
                String zcPassword = mZc_passwordTextLayout.getEditText().getText().toString();

                mZc_accountTextLayout.setErrorEnabled(false);
                mZc_passwordTextLayout.setErrorEnabled(false);

                //验证用户名和密码
                if (validateAccount(zcAccount,mZc_accountTextLayout) && validatePassword(zcPassword,mZc_passwordTextLayout)) {
                    // TODO: 2018/9/10 调用登录接口

                    ToastUtils.showSingleToast(MyApplication.getInstance(), getString(R.string.register_success));
                    //跳转圈友信息填写页面
                    SkipActivityUtil.skipActivity(getActivity(), UserInfoActivity.class);

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
}
