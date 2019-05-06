package newmatch.zbmf.com.testapplication.activitys;

import android.view.View;
import android.view.WindowManager;

import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.base.BaseActivity;
import newmatch.zbmf.com.testapplication.base.MyApplication;
import newmatch.zbmf.com.testapplication.utils.MyActivityManager;

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
        //是否全屏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


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
