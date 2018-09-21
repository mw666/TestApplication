package newmatch.zbmf.com.testapplication.fragments;


import android.os.Bundle;
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
import newmatch.zbmf.com.testapplication.utils.ConvertStructureUtil;

/**
 * A simple {@link Fragment} subclass.
 * 消息Fragment
 */
public class MsgFragment extends BaseFragment {


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

        List<String> msgTabTitles=new ArrayList<>();
        List<Fragment> fragmentList=new ArrayList<>();
        addViewPagerContent(msgTabTitles,fragmentList);
        String[] tabtitles = ConvertStructureUtil.list2Array(msgTabTitles);

        MsgTabAdapter adapter = new MsgTabAdapter(getActivity().getSupportFragmentManager(),
                tabtitles, fragmentList);
        msgViewPager.setAdapter(adapter);
        msgFGTabLayout.setupWithViewPager(msgViewPager);

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

    private void addViewPagerContent(List<String> msgTabTitles,List<Fragment> fragmentList){
        msgTabTitles.add(0,getString(R.string.msg));//消息
        msgTabTitles.add(1,getString(R.string.good_friends));//好友
        //msgTabTitles.add(0,getString(R.string.group));//群组
        fragmentList.add(0,ChatListFragment.instance());
        fragmentList.add(1,GoodFriendsOrGroupFragment.instance());
    }


}
