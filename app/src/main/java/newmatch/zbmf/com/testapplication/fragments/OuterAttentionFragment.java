package newmatch.zbmf.com.testapplication.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.adapters.pager_fragment_adapters.MsgTabAdapter;
import newmatch.zbmf.com.testapplication.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 * 外层的关注Fragment
 */
public class OuterAttentionFragment extends BaseFragment {
    private List<String> msgTabTitles = new ArrayList<>();
    private List<Fragment> fragmentList = new ArrayList<>();

    public OuterAttentionFragment() {

    }

    public static OuterAttentionFragment outAttentionInstance() {
        OuterAttentionFragment msgFragment = new OuterAttentionFragment();
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
        TextView toolbar_title = bindView(R.id.toolbar_title);
        toolbar_title.setVisibility(View.VISIBLE);
        toolbar_title.setText(getString(R.string.my_attention));
        TabLayout msgFGTabLayout = bindView(R.id.msgFGTabLayout);
        ViewPager msgViewPager = bindView(R.id.msgViewPager);

        //隐藏tabLayout
        msgFGTabLayout.setVisibility(View.GONE);
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
//        msgTabTitles.add(getString(R.string.msg));//消息
//        msgTabTitles.add( getString(R.string.good_friends));//好友
        //msgTabTitles.add(0,getString(R.string.group));//群组
        fragmentList.add(AttentionFragment.attentionInstance());
//        fragmentList.add( GoodFriendsOrGroupFragment.instance());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        /*msgTabTitles.clear();
        fragmentList.clear();*/
    }
}
