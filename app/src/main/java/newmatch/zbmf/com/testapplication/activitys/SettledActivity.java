package newmatch.zbmf.com.testapplication.activitys;

import android.view.View;

import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.base.BaseActivity;
import newmatch.zbmf.com.testapplication.base.MyApplication;

/**
 * 入驻的页面
 */
public class SettledActivity extends BaseActivity {

    @Override
    protected Integer layoutId() {
        return R.layout.activity_settled;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected String initTitle() {
        return getString(R.string.settle_show);
    }

    @Override
    protected Boolean showBackBtn() {
        return true;
    }

    @Override
    protected int topBarColor() {
        return MyApplication.getInstance().getResources().getColor(R.color.colorPrimary);
    }

    @Override
    protected void onViewClick(View view) {

    }


}
