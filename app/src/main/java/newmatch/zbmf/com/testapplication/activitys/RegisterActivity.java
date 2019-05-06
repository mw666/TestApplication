package newmatch.zbmf.com.testapplication.activitys;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.base.BaseActivity;
import newmatch.zbmf.com.testapplication.base.MyApplication;
import newmatch.zbmf.com.testapplication.fragments.RegisterFragment;
import newmatch.zbmf.com.testapplication.utils.MyActivityManager;

/**
 * 注册&登录页面
 */
public class RegisterActivity extends BaseActivity {

    @Override
    protected Integer layoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        //是否全屏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        TabLayout tabLayout = bindView(R.id.tabLayout);
        ViewPager viewPager = bindView(R.id.viewPager);

        List<Fragment> mFragments = new ArrayList<>();
        mFragments.add(0, RegisterFragment.getInstance(0));
        mFragments.add(1, RegisterFragment.getInstance(1));
        String[] titles = new String[]{getString(R.string.login), getString(R.string.register)};
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager(), mFragments, titles));

        tabLayout.setupWithViewPager(viewPager);

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
        return true;
    }

    @Override
    protected int topBarColor() {
        return MyApplication.getInstance().getResources().getColor(R.color.deepPurple);
    }

    @Override
    protected void onViewClick(View view) {

    }

    private class MyAdapter extends FragmentPagerAdapter {

        List<String> mTitles;
        List<Fragment> mFragments;

        public MyAdapter(FragmentManager fm, List<Fragment> fragments, String[] titles) {
            super(fm);
            mFragments = fragments;
            mTitles = Arrays.asList(titles);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
