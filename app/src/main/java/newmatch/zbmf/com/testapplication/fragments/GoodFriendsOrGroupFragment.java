package newmatch.zbmf.com.testapplication.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.adapters.MsgTabAdapter;
import newmatch.zbmf.com.testapplication.base.BaseFragment;
import newmatch.zbmf.com.testapplication.presenter.presenterIml.BasePresenter;
import newmatch.zbmf.com.testapplication.utils.GetUIDimens;

/**
 * A simple {@link Fragment} subclass.
 * 好友管理或群组管理的fragment
 */
public class GoodFriendsOrGroupFragment extends BaseFragment {
    private List<String> msgTabTitles = new ArrayList<>();
    private List<Fragment> fragmentList = new ArrayList<>();

    public GoodFriendsOrGroupFragment() {}

    public static GoodFriendsOrGroupFragment instance(){
        GoodFriendsOrGroupFragment goodFriendsOrGroupFragment = new GoodFriendsOrGroupFragment();
        Bundle bundle = new Bundle();
        goodFriendsOrGroupFragment.setArguments(bundle);
        return goodFriendsOrGroupFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addFG();
    }

    @Override
    protected Integer layoutId() {
        return R.layout.good_friends_group_fragment;
    }

    @Override
    protected void initView() {
        TabLayout msgFGTabLayout = bindView(R.id.msgFGTabLayout);
        ViewPager msgViewPager = bindView(R.id.msgViewPager);

        msgFGTabLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.no_corner_white_bg));
//        msgFGTabLayout.setMinimumHeight(R.dimen.dp_45);
        msgFGTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        msgFGTabLayout.setTabTextColors(R.color.black,R.color.colorPrimary);
        ViewGroup.LayoutParams lp = msgFGTabLayout.getLayoutParams();
        lp.height= GetUIDimens.dpToPx(getActivity(),40f);
        lp.width= ViewGroup.LayoutParams.MATCH_PARENT;
        msgFGTabLayout.setLayoutParams(lp);
        msgFGTabLayout.setSelected(true);

        MsgTabAdapter adapter = new MsgTabAdapter(getChildFragmentManager(), msgTabTitles, fragmentList);
        msgViewPager.setAdapter(adapter);
        msgFGTabLayout.setupWithViewPager(msgViewPager,true);
        msgFGTabLayout.setTabsFromPagerAdapter(adapter);
        msgViewPager.setCurrentItem(0);

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

    private void addFG(){
        msgTabTitles.add( getString(R.string.good_friends));//好友
        msgTabTitles.add(getString(R.string.group));//群组
        fragmentList.add(FriendsListFragment.instance());
        fragmentList.add(FriendsListFragment.instance());
    }


}
