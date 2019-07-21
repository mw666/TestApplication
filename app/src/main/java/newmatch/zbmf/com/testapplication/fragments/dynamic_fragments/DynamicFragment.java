package newmatch.zbmf.com.testapplication.fragments.dynamic_fragments;


import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.facebook.stetho.common.LogUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import newmatch.zbmf.com.testapplication.MainActivity;
import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.adapters.pager_fragment_adapters.MyFragmentStatePagerAdapter;
import newmatch.zbmf.com.testapplication.assist.CollapsingToolbarLayoutState;
import newmatch.zbmf.com.testapplication.base.BaseFragment;
import newmatch.zbmf.com.testapplication.component.BannerViewHolderType;
import newmatch.zbmf.com.testapplication.fragments.main_menu_fragments.Main1Fragment;
import newmatch.zbmf.com.testapplication.fragments.main_menu_fragments.Main2Fragment;
import newmatch.zbmf.com.testapplication.presenter.presenterIml.BasePresenter;
import newmatch.zbmf.com.testapplication.utils.StatusBarUtil;
import newmatch.zbmf.com.testapplication.utils.ToastUtils;
import newmatch.zbmf.com.testapplication.views.customViewPager.BannerViewHolder;
import newmatch.zbmf.com.testapplication.views.customViewPager.MZBannerView;
import newmatch.zbmf.com.testapplication.views.customViewPager.MZHolderCreator;

/**
 * A simple {@link Fragment} subclass.
 * 动态Fragment
 * <p>
 * <p>
 * 文件上传：https://blog.csdn.net/a992036795/article/details/74738474
 */
public class DynamicFragment extends BaseFragment  {


    private MainActivity activity;
    private Toolbar dynamicToolBar;
    private AppBarLayout dynamicAppbar;
    private CollapsingToolbarLayoutState state;
    private View statusView;
    private MZBannerView dynamicBanner;

    public DynamicFragment() {

    }



    public static DynamicFragment dynamicInstance() {
        DynamicFragment dynamicFragment = new DynamicFragment();
        Bundle bundle = new Bundle();
        dynamicFragment.setArguments(bundle);
        return dynamicFragment;
    }

    @Override
    protected Integer layoutId() {
        return R.layout.fragment_dynamic;
    }

    @Override
    protected void initView() {
        //        TextView topbarTitle = bindView(R.id.topbar_title);
        //        topbarTitle.setText(getString(R.string.qy_dynamic));
        //获取宿主activity的对象
        activity = (MainActivity) getActivity();
        StatusBarUtil.setTransparent(activity);
        //获取布局上的控件

        dynamicToolBar = bindView(R.id.dynamicToolBar);
        statusView = bindView(R.id.statusView);
        dynamicBanner = bindView(R.id.dynamicBanner);
        dynamicAppbar = bindView(R.id.dynamicAppbar);
        TabLayout dynamicTab = bindView(R.id.dynamicTab);
        ViewPager dynamicViewPager = bindView(R.id.dynamicViewPager);


        //模拟设置tab的数据
        setTabContent(dynamicTab, dynamicViewPager);

        //模拟轮播的数据
        initBannerView(dynamicBanner);
        appBarLayoutSH();

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
            fragments.add(DynamicTypeFragment.newInstance(dynamicTabTitle));
        }
        return fragments;
    }

    //模拟准备设置tab的内容
    private void setTabContent(TabLayout dynamicTab, ViewPager viewPager) {
        //准备fragment集合list的数据
        String[] dynamicTabTitles = getResources().getStringArray(R.array.dynamic_tab_titles);
        List<Fragment> fragmentList = initFragments(dynamicTabTitles);
        MyFragmentStatePagerAdapter mainFragMentAdapter =
                new MyFragmentStatePagerAdapter(getChildFragmentManager(),
                        fragmentList, Arrays.asList(dynamicTabTitles));
        List<Fragment> mFragment = new ArrayList<>();
        if (mFragment.size() == 0) {
            mFragment.add(new Main2Fragment());
            mFragment.add(new Main1Fragment());
        }
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



    @Override
    protected void initData() {

    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void onViewClick(View view) {

    }

    @Override
    protected Boolean setViewEnterStatuBar() {
        return true;
    }



    //模拟设置轮播广告
    private void initBannerView(MZBannerView homeBanner) {
        int[] imgs = {R.drawable.j1, R.drawable.j2, R.drawable.j3, R.drawable.j4};

        List<Integer> imgList;
        imgList = new ArrayList<>();
        for (int i = 0; i < imgs.length; i++) {
            imgList.add(imgs[i]);
        }
        homeBanner.addPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                LogUtil.e("----->addPageChangeLisnter:" + position +
                        "positionOffset:" + positionOffset + "positionOffsetPixels:"
                        + positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                LogUtil.e("addPageChangeLisnter:" + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        homeBanner.setIndicatorVisible(true);
        // 代码中更改indicator 的位置
        homeBanner.setIndicatorAlign(MZBannerView.IndicatorAlign.LEFT);
        homeBanner.setIndicatorPadding(15, 0, 0, 15);
        homeBanner.setPages(imgList, (MZHolderCreator<BannerViewHolder>)
                () -> new BannerViewHolder(imgList.size(), BannerViewHolderType.ConnerViewHolder));
        //Banner的点击事件
        homeBanner.setBannerPageClickListener((view, position) -> {
            ToastUtils.showSquareTvToast(getContext(), "点击了Banner页面：" + position);
        });
    }

    //设置appBar关合的效果
    private void appBarLayoutSH() {
        dynamicAppbar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            if (verticalOffset == 0) {
                if (state != CollapsingToolbarLayoutState.EXPANDED) {
                    state = CollapsingToolbarLayoutState.EXPANDED;//修改状态标记为展开
                    dynamicToolBar.setVisibility(View.GONE);
                }
            } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                if (state != CollapsingToolbarLayoutState.COLLAPSED) {
                    dynamicToolBar.setVisibility(View.VISIBLE);
                    state = CollapsingToolbarLayoutState.COLLAPSED;//修改状态标记为折叠
                }
                StatusBarUtil.setStatusBarUpperAPI21(activity);
            } else {
                if (state != CollapsingToolbarLayoutState.INTERNEDIATE) {
                    if (state == CollapsingToolbarLayoutState.COLLAPSED) {
                        dynamicToolBar.setVisibility(View.GONE);
                    }
                    state = CollapsingToolbarLayoutState.INTERNEDIATE;//修改状态标记为中间
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        dynamicBanner.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        dynamicBanner.pause();
    }

}

