package newmatch.zbmf.com.testapplication.fragments.mine_under_fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.activitys.VIPActivity;
import newmatch.zbmf.com.testapplication.adapters.MineViewAdapter;
import newmatch.zbmf.com.testapplication.base.BaseFragment;
import newmatch.zbmf.com.testapplication.base.MyApplication;
import newmatch.zbmf.com.testapplication.dialogs.DialogUtils;
import newmatch.zbmf.com.testapplication.interfaces.MineViewClick;
import newmatch.zbmf.com.testapplication.listeners.DialogCallBack;
import newmatch.zbmf.com.testapplication.listeners.OnceClickListener;
import newmatch.zbmf.com.testapplication.presenter.presenterIml.BasePresenter;
import newmatch.zbmf.com.testapplication.utils.ContainsEmojiEditText;
import newmatch.zbmf.com.testapplication.utils.PhoneFormatCheckUtils;
import newmatch.zbmf.com.testapplication.utils.SkipActivityUtil;
import newmatch.zbmf.com.testapplication.utils.ToastUtils;

/**
 * A simple {@link Fragment} subclass.
 * 我的设置的fragment
 */
public class MySettingFragment extends BaseFragment implements MineViewClick {


    public MySettingFragment() {
    }

    public static MySettingFragment instance() {
        MySettingFragment mySettingFragment = new MySettingFragment();
        Bundle bundle = new Bundle();
        mySettingFragment.setArguments(bundle);
        return mySettingFragment;
    }

    @Override
    protected Integer layoutId() {
        return R.layout.fragment_my_setting;
    }

    @Override
    protected void initView() {
        String[] titles = {getActivity().getString(R.string.open_vip),
                getActivity().getString(R.string.update_pass_word),
                getActivity().getString(R.string.options_back),
                getActivity().getString(R.string.version_update),
                getActivity().getString(R.string.login_out)
        };

        RecyclerView userMineRV = bindView(R.id.userMineRV);
        userMineRV.setLayoutManager(new LinearLayoutManager(getActivity(),
                OrientationHelper.VERTICAL, false));
        MineViewAdapter mineViewAdapter = new MineViewAdapter(titles, getActivity());
        mineViewAdapter.setMineViewClick(this);
        userMineRV.setAdapter(mineViewAdapter);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void onViewClick(View view) {

    }

    @Override
    protected Boolean setViewEnterStatuBar() {
        return false;
    }

    @Override
    public void clickVip() {
        //跳转VIP页面
        SkipActivityUtil.skipActivity(getActivity(), VIPActivity.class);
    }

    @Override
    public void clickUpdatePassWord() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.update_password_et_view, null);
        TextInputLayout oldPasswordTextLayout = view.findViewById(R.id.oldPasswordTextLayout);
        TextInputLayout newPasswordTextLayout = view.findViewById(R.id.newPasswordTextLayout);
        ImageView clearOldPassword = view.findViewById(R.id.clearOldPassword);
        ImageView clearNewPassword = view.findViewById(R.id.clearNewPassword);
        Button confirmBtn = view.findViewById(R.id.confirmBtn);
        //修改密码
        DialogUtils.instance()
                .setGravity(Gravity.CENTER)
                .setHasMargin(true)
                .setIsCancel(true)
                .setDialogStyle(R.style.dialog)
                .setDialogDecoeViewBg(R.drawable.add_friend_et_bg)
                .setView(view)
                .gMDialog(getActivity(), getActivity());
        setBtnBg(confirmBtn, R.drawable.invalide_btn_view, false);
        etTextChangeListener(oldPasswordTextLayout, newPasswordTextLayout, confirmBtn, clearOldPassword);
        etTextChangeListener(newPasswordTextLayout, oldPasswordTextLayout, confirmBtn, clearNewPassword);

        confirmBtn.setOnClickListener(new OnceClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (PhoneFormatCheckUtils.validatePassword(getActivity(), oldPasswordTextLayout.getEditText())
                        && PhoneFormatCheckUtils.validatePassword(getActivity(),
                        newPasswordTextLayout.getEditText())) {
                    //密码格式验证通过
                    if (textEquelsText(oldPasswordTextLayout.getEditText().getText().toString().trim()
                            , newPasswordTextLayout.getEditText().getText().toString().trim())) {
                        ToastUtils.showSingleToast(MyApplication.getInstance(),
                                getString(R.string.new_password_equles_old));
                    } else {
                        //调用修改密码的接口

                    }
                }
            }
        });
    }

    @Override
    public void clickOptionsUp() {
        //意见反馈
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.option_back_view, null);
        ContainsEmojiEditText inputOptions = view.findViewById(R.id.inputOptions);
        ImageView sendOptions = view.findViewById(R.id.sendOptions);
        DialogUtils.instance()
                .setGravity(Gravity.CENTER)
                .setView(view)
                .setDialogDecoeViewBg(R.drawable.add_friend_et_bg)
                .setDialogStyle(R.style.dialog)
                .setIsCancel(true)
                .gMDialog(getActivity(), getActivity());
        sendOptions.setOnClickListener(new OnceClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                String options = inputOptions.getText().toString().trim();
                //提供的宝贵建议
                ToastUtils.showSingleToast(MyApplication.getInstance(), options + "");
                //调用接口返回

            }
        });
    }

    @Override
    public void clickVertionUpdate() {
        //版本更新
    }

    @Override
    public void clickLoginOut() {
        //退出登录
        DialogUtils.instance()
                /*.setDialogStyle(R.style.dialog)*/
                .setDialogCallBack(new DialogCallBack() {
                    @Override
                    public void positiveClick(DialogInterface dialog) {
                        //调用退出登录的接口

                    }

                    @Override
                    public void negativeClick(DialogInterface dialog) {

                    }
                })
                .showNormalAlertDialog(getActivity(), R.string.login_out_tip);
    }

    private Boolean textEquelsText(String text1, String text2) {
        return text1.equals(text2);
    }

    private void setBtnBg(Button btn, Integer res, Boolean isClick) {
        btn.setClickable(isClick);
        btn.setBackgroundResource(res);
    }

    private void etTextChangeListener(TextInputLayout et1, TextInputLayout et2, Button btn, ImageView clear) {
        EditText editText = et1.getEditText();
        assert editText != null;
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0) {
                    clear.setVisibility(View.VISIBLE);
                    clear.setOnClickListener(new OnceClickListener() {
                        @Override
                        public void onNoDoubleClick(View v) {
                            editText.getText().clear();
                            clear.setVisibility(View.GONE);
                        }
                    });
                } else {
                    clear.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (et1.getEditText().getText().toString().trim().length() > 0 &&
                        et2.getEditText().getText().toString().trim().length() > 0) {
                    if (et2.getEditText().getText().toString().trim().length() > 6) {
                        setBtnBg(btn, R.drawable.select_login_btn_bg, true);
                    } else {
                        ToastUtils.showSingleToast(MyApplication.getInstance(),
                                getString(R.string.update_password_tip));
                    }
                } else {
                    setBtnBg(btn, R.drawable.invalide_btn_view, false);
                }
            }
        });
    }

}
