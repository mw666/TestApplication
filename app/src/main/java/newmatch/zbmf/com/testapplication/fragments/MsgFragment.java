package newmatch.zbmf.com.testapplication.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.adapters.MsgTabAdapter;
import newmatch.zbmf.com.testapplication.base.BaseFragment;
import newmatch.zbmf.com.testapplication.presenter.BasePresenter;

/**
 * A simple {@link Fragment} subclass.
 * 消息Fragment
 */
public class MsgFragment extends BaseFragment {
    private List<String> msgTabTitles = new ArrayList<>();
    private List<Fragment> fragmentList = new ArrayList<>();

    public MsgFragment() {

    }

    public static MsgFragment msgInstance() {
        MsgFragment msgFragment = new MsgFragment();
        Bundle bundle = new Bundle();
        msgFragment.setArguments(bundle);
        return msgFragment;
    }

    @Override
    protected Integer layoutId() {
        return R.layout.fragment_msg;
    }

    @Override
    protected void initView() {
//        TextView toolbar_title = bindView(R.id.toolbar_title);
//        toolbar_title.setText(getString(R.string.linkmans));
        TabLayout msgFGTabLayout = bindView(R.id.msgFGTabLayout);
        ViewPager msgViewPager = bindView(R.id.msgViewPager);

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addFG();
    }

    private void addFG(){
        msgTabTitles.add(getString(R.string.msg));//消息
        msgTabTitles.add( getString(R.string.good_friends));//好友
        //msgTabTitles.add(0,getString(R.string.group));//群组
        fragmentList.add(ChatListFragment.instance());
        fragmentList.add( GoodFriendsOrGroupFragment.instance());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        /*msgTabTitles.clear();
        fragmentList.clear();*/
    }
}
