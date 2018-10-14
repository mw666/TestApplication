package newmatch.zbmf.com.testapplication.activitys;

import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.base.BaseActivity;
import newmatch.zbmf.com.testapplication.base.MyApplication;
import newmatch.zbmf.com.testapplication.utils.MyActivityManager;

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
        MyActivityManager.getMyActivityManager().pushAct(SettledActivity.this);
        //设置内容顶进状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        TextView settleTitle = bindView(R.id.settleTitle);
        settleTitle.setVisibility(View.VISIBLE);
        settleTitle.setText(getString(R.string.settle_show));
//        bindView(R.id.toolbar).setVisibility(View.GONE);

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
