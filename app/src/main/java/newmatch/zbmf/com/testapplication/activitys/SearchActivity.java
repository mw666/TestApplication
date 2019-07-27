package newmatch.zbmf.com.testapplication.activitys;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.adapters.staggerAdapters.SearchRecommendAdapter;
import newmatch.zbmf.com.testapplication.base.BaseActivity;
import newmatch.zbmf.com.testapplication.base.MyApplication;
import newmatch.zbmf.com.testapplication.callback.HomeRVIvClick;
import newmatch.zbmf.com.testapplication.utils.SkipActivityUtil;
import newmatch.zbmf.com.testapplication.utils.TextContentUtil;
import newmatch.zbmf.com.testapplication.utils.ToastUtils;

/**
 * 搜索页面的要求：
 * 1.点击输入框呈现最近2条搜索记录
 * 2.在搜索输入框输入昵称文字：将会出现与昵称相关的列表结果
 * 3.输入数字点击搜索才会出现搜索结果
 * 4.若无搜索结果则呈现暂无搜索结果的页面
 * 5.搜索页面底部会呈现9条推荐的用户数据:这9条数据每次调用后台接口,由后台随机产生.
 * 页面右上角有换一批按钮,点击可更换推荐数据
 */
public class SearchActivity extends BaseActivity implements HomeRVIvClick {

    private EditText searchUserEt;

    @Override
    protected Integer layoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView() {
        //全屏，内容延伸至状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        //隐藏toolBar的返回icon
        setGoneToolBar();

        searchUserEt = bindView(R.id.searchUserEt);
        AppCompatImageView searchResultIv = bindView(R.id.searchResultIv);
        RecyclerView searchRecommendRv = bindView(R.id.searchRecommendRv);
        ImageView clearSearchEt = bindViewWithClick(R.id.clearSearchEt, true);
        TextView emptyView = bindView(R.id.emptyView);
        bindViewWithClick(R.id.updateRecommendUser, true);
        //呈现最近两条搜索记录
        RecyclerView searchRecordRv = bindView(R.id.searchRecordRv);
        //夜嗨热搜
        RecyclerView yeHiRedSearchRv = bindView(R.id.yeHiRedSearchRv);
        //检索关键字列表
        RecyclerView searchListRv = bindView(R.id.searchListRv);



        //隐藏或显示清空EditText的icon
        TextContentUtil.showOrHideClearIv(searchUserEt, searchResultIv);
        //监听搜索框的内容
        watcherEditTextContent(clearSearchEt);
        //模拟设置Rv数据
        setRecommendData(searchRecommendRv);

    }

    //设置toolBar
    private void setGoneToolBar() {
        //设置返回箭头的隐藏
        Toolbar toolBar = bindView(R.id.toolbar);
        setSupportActionBar(toolBar);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayShowTitleEnabled(false);
        supportActionBar.setDisplayHomeAsUpEnabled(false);
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
        switch (view.getId()) {
            case R.id.updateRecommendUser:
                ToastUtils.showSingleToast(this, "更新推荐的用户数据");
                break;
            case R.id.clearSearchEt:
                //清空搜索框的数据
                searchUserEt.getText().clear();
                break;
        }
    }

    //模拟设置推荐列表的数据
    private void setRecommendData(RecyclerView searchRecommendRv) {
        searchRecommendRv.setHasFixedSize(true);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        searchRecommendRv.setLayoutManager(manager);
        //模拟数据
        SearchRecommendAdapter testAdapter = new SearchRecommendAdapter(this);
        searchRecommendRv.setAdapter(testAdapter);
        testAdapter.setHomeRVIvClick(this);

        List<Integer> imgs = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            imgs.add(R.drawable.j1);
            imgs.add(R.drawable.j4);
            imgs.add(R.drawable.j5);
        }
        testAdapter.addData(imgs);
    }

    //监听搜索框的内容
    private void watcherEditTextContent(ImageView clearSearchEt){
        searchUserEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().trim().length() > 0) {
                    clearSearchEt.setVisibility(View.VISIBLE);
                } else {
                    clearSearchEt.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void rvIvCallBack(int position) {
        //首页图片点击的回调
        Bundle bundle = new Bundle();
        SkipActivityUtil.skipDataActivity(this, UserDetailActivity.class, bundle);
    }
}
