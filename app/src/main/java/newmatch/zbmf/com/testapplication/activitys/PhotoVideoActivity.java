package newmatch.zbmf.com.testapplication.activitys;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.adapters.pager_fragment_adapters.MyFragmentStatePagerAdapter;
import newmatch.zbmf.com.testapplication.base.BaseActivity;
import newmatch.zbmf.com.testapplication.fragments.photo_video_frags.PhotoVideoFragment;
import newmatch.zbmf.com.testapplication.utils.ActivityAnimUtils;

/**
 * 相册，短视频空间页面
 */
public class PhotoVideoActivity extends BaseActivity {

    @Override
    protected Integer layoutId() {
        return R.layout.activity_photo_video;
    }

    @Override
    protected void initView() {
        //全屏，内容延伸至状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        TabLayout pvTab = bindView(R.id.pvTab);
        ViewPager viewPager = bindView(R.id.viewPager);
        bindViewWithClick(R.id.backBtn, true);

        //设置Tab
        setTabContent(pvTab, viewPager);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected String initTitle() {
        return "";
    }

    @Override
    protected Boolean showBackBtn() {
        return false;
    }

    @Override
    protected int topBarColor() {
        return 0;
    }

    @Override
    protected void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.backBtn:
                //返回
                ActivityAnimUtils.instance().activityOut(this);
                break;

        }
    }

    //模拟准备设置tab的内容
    private void setTabContent(TabLayout dynamicTab, ViewPager viewPager) {
        //准备fragment集合list的数据
        String[] dynamicTabTitles = getResources().getStringArray(R.array.photo_video_titles);
        List<Fragment> fragmentList = initFragments(dynamicTabTitles);
        MyFragmentStatePagerAdapter mainFragMentAdapter =
                new MyFragmentStatePagerAdapter(getSupportFragmentManager(),
                        fragmentList, Arrays.asList(dynamicTabTitles));
        viewPager.setAdapter(mainFragMentAdapter);
        dynamicTab.setupWithViewPager(viewPager, true);
        viewPager.setCurrentItem(0);
        dynamicTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    //fragment的集合数据
    private List<Fragment> fragments;

    //准备fragment的数据
    private List<Fragment> initFragments(String[] dynamicTabTitles) {
        if (fragments == null) {
            fragments = new ArrayList<>();
        }
        if (fragments.size() > 0) {
            fragments.clear();
        }
        for (String dynamicTabTitle : dynamicTabTitles) {
            fragments.add(PhotoVideoFragment.instance());
        }
        return fragments;
    }

}
