package newmatch.zbmf.com.testapplication.activitys;

import android.view.View;

import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.base.BaseActivity;
import newmatch.zbmf.com.testapplication.base.MyApplication;

/**
 * 忘记密码页面
 */
public class ForgetPassWordActivity extends BaseActivity {

    @Override
    protected Integer layoutId() {
        return R.layout.activity_forget_pass_word;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected String initTitle() {
        return getString(R.string.find_password);
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

    }


}
