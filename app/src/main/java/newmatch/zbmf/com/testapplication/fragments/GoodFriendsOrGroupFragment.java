package newmatch.zbmf.com.testapplication.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.base.BaseFragment;
import newmatch.zbmf.com.testapplication.presenter.BasePresenter;

/**
 * A simple {@link Fragment} subclass.
 * 好友管理或群组管理的fragment
 */
public class GoodFriendsOrGroupFragment extends BaseFragment {


    public GoodFriendsOrGroupFragment() {}

    public static GoodFriendsOrGroupFragment instance(){
        GoodFriendsOrGroupFragment goodFriendsOrGroupFragment = new GoodFriendsOrGroupFragment();
        Bundle bundle = new Bundle();
        goodFriendsOrGroupFragment.setArguments(bundle);
        return goodFriendsOrGroupFragment;
    }

    @Override
    protected Integer layoutId() {
        return R.layout.fragment_good_friends_or_group;
    }

    @Override
    protected void initView() {

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


}
