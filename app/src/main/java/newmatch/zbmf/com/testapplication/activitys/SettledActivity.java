package newmatch.zbmf.com.testapplication.activitys;

import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.base.BaseActivity;
import newmatch.zbmf.com.testapplication.base.MyApplication;
import newmatch.zbmf.com.testapplication.utils.MyActivityManager;
import newmatch.zbmf.com.testapplication.utils.ToastUtils;

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
        bindViewWithClick(R.id.settleBtn_1, true);
        bindViewWithClick(R.id.settleBtn_2, true);
        bindViewWithClick(R.id.settleBtn_3, true);


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
        switch (view.getId()) {
            case R.id.settleBtn_1:
                ToastUtils.showSingleToast(MyApplication.getInstance(),"--   点击订阅 1");

                break;
            case R.id.settleBtn_2:
                ToastUtils.showSingleToast(MyApplication.getInstance(),"--   点击订阅 2");

                break;
            case R.id.settleBtn_3:
                ToastUtils.showSingleToast(MyApplication.getInstance(),"--   点击订阅 3");

                break;
        }
    }


}
