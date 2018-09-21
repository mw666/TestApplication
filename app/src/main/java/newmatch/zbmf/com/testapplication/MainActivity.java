package newmatch.zbmf.com.testapplication;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import newmatch.zbmf.com.testapplication.adapters.MainFragMentAdapter;
import newmatch.zbmf.com.testapplication.assist.BottomNavigationViewHelper;
import newmatch.zbmf.com.testapplication.base.BaseActivity;
import newmatch.zbmf.com.testapplication.base.MyApplication;
import newmatch.zbmf.com.testapplication.fragments.AttentionFragment;
import newmatch.zbmf.com.testapplication.fragments.DynamicFragment;
import newmatch.zbmf.com.testapplication.fragments.HomeFragment;
import newmatch.zbmf.com.testapplication.fragments.MineFragment;
import newmatch.zbmf.com.testapplication.fragments.MsgFragment;
import newmatch.zbmf.com.testapplication.utils.MyActivityManager;
import newmatch.zbmf.com.testapplication.utils.TianShareUtil;

/**
 * 取名甜甜圈吧
 * 首页
 */
public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener,
        BottomNavigationView.OnNavigationItemSelectedListener{

    private BottomNavigationView mBottomNavigationView;

    private ViewPager mViewPager;

    @Override
    protected Integer layoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        MyActivityManager.getMyActivityManager().pushAct(MainActivity.this);
        mBottomNavigationView = bindView(R.id.bottomNavigationView);
        //取消自带平移的动画效果
        BottomNavigationViewHelper.disableShiftMode(mBottomNavigationView);
        mViewPager = bindView(R.id.viewPager);

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(0, HomeFragment.homgInstance(TianShareUtil.getCity()));
        fragmentList.add(1, DynamicFragment.dynamicInstance());
        fragmentList.add(2, MsgFragment.msgInstance());
        fragmentList.add(3, AttentionFragment.attentionInstance());
        fragmentList.add(4, MineFragment.mineInstance());
        MainFragMentAdapter mainFragMentAdapter = new MainFragMentAdapter(getSupportFragmentManager()
                , fragmentList);
        mViewPager.setAdapter(mainFragMentAdapter);
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
        mViewPager.addOnPageChangeListener(this);

        mViewPager.setCurrentItem(0);



    }

    @Override
    protected void initData() {}

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
        return MyApplication.getInstance().getResources().getColor(R.color.colorPrimary);
    }

    @Override
    protected void onViewClick(View view) {

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
         mBottomNavigationView.getMenu().getItem(position).setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.dynamic:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.msg:
                mViewPager.setCurrentItem(2);
                break;
            case R.id.attention:
                mViewPager.setCurrentItem(3);
                break;
            case R.id.mime:
                mViewPager.setCurrentItem(4);
                break;
        }
        return true;
    }
}
