package newmatch.zbmf.com.testapplication.activitys;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.adapters.GlobalAdapter;
import newmatch.zbmf.com.testapplication.base.BaseActivity;
import newmatch.zbmf.com.testapplication.component.C;

/**
 * 我的关注，我的粉丝页面
 */
public class MyLikeFansActivity extends BaseActivity {

    private int type;

    @Override
    protected Integer layoutId() {
        return R.layout.activity_my_like_fans;
    }

    @Override
    protected void initView() {
        //全屏，内容延伸至状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setSupportActionBar(bindView(R.id.toolbar));

        type = getIntent().getIntExtra(C.TYPE, 1);

        TextView toolBarTitle = bindView(R.id.toolbar_title);
        SmartRefreshLayout fansRefreshLayout = bindView(R.id.fansRefreshLayout);
        RecyclerView fansRV = bindView(R.id.fansRV);
        TextView emptyView = bindView(R.id.emptyView);
        //如果没有粉丝就显示emptyView
        /*此处模拟显示*/
        if (type == 1) {
            toolBarTitle.setText(getString(R.string.my_fans));
            fansRV.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }

        /*模拟设置rv的数据*/
        setRvData(type, fansRV);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected String initTitle() {
        if (type == 1) {
            return getString(R.string.my_fans);
        } else {
            return getString(R.string.my_like);
        }
    }

    @Override
    protected Boolean showBackBtn() {
        return true;
    }

    @Override
    protected int topBarColor() {
        return 0;
    }

    @Override
    protected void onViewClick(View view) {

    }

    //设置rv的数据
    private void setRvData(int type, RecyclerView rv) {
        rv.setLayoutManager(new LinearLayoutManager(this,
                OrientationHelper.VERTICAL, false));
        GlobalAdapter globalAdapter = new GlobalAdapter();
        rv.setAdapter(globalAdapter);

    }


}
