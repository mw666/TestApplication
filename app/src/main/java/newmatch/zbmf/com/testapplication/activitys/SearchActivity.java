package newmatch.zbmf.com.testapplication.activitys;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.base.BaseActivity;
import newmatch.zbmf.com.testapplication.base.MyApplication;
import newmatch.zbmf.com.testapplication.utils.TextContentUtil;

public class SearchActivity extends BaseActivity {

    @Override
    protected Integer layoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView() {
        //全屏，内容延伸至状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        //设置返回箭头的隐藏
        Toolbar toolBar = bindView(R.id.toolbar);
        setSupportActionBar(toolBar);
//        getSupportActionBar().setDisplayShowHomeEnabled(false);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayShowTitleEnabled(false);
        supportActionBar.setDisplayHomeAsUpEnabled(false);

//        bindView(R.id.toolbar_title).setVisibility(View.INVISIBLE);//隐藏顶部标题
        EditText searchUserEt = bindView(R.id.searchUserEt);
        ImageView searchResultIv = bindView(R.id.searchResultIv);
        RecyclerView searchRecommendRv = bindView(R.id.searchRecommendRv);
        ImageView clearSearchEt = bindView(R.id.clearSearchEt);

        TextContentUtil.showOrHideClearIv(searchUserEt, searchResultIv);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected String initTitle() {
        return getString(R.string.search);
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
