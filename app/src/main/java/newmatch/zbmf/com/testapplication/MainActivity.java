package newmatch.zbmf.com.testapplication;

import android.view.View;

import newmatch.zbmf.com.testapplication.base.BaseActivity;
import newmatch.zbmf.com.testapplication.base.MyApplication;

/**
 * 取名甜甜圈吧
 */
public class MainActivity extends BaseActivity {

    @Override
    protected Integer layoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {


    }

    @Override
    protected void initData() {

    }

    @Override
    protected String initTitle() {
        return getString(R.string.application_title);
    }

    @Override
    protected Boolean showBackBtn() {
        return false;
    }

    @Override
    protected int topBarColor() {
        return MyApplication.getInstance().getResources().getColor(R.color.deepPurple);
    }

    @Override
    protected void onViewClick(View view) {

    }


}
