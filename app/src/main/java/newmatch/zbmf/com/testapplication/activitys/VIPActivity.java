package newmatch.zbmf.com.testapplication.activitys;

import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.base.BaseActivity;
import newmatch.zbmf.com.testapplication.base.MyApplication;
import newmatch.zbmf.com.testapplication.utils.MyActivityManager;

/**
 * VIP会员开通页面
 */
public class VIPActivity extends BaseActivity {

    @Override
    protected Integer layoutId() {
        return R.layout.activity_vip;
    }

    @Override
    protected void initView() {
        MyActivityManager.getMyActivityManager().pushAct(VIPActivity.this);
        //设置内容顶进状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);



    }

    @Override
    protected void initData() {

    }

    @Override
    protected String initTitle() {
        return getString(R.string.open_vip);
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
