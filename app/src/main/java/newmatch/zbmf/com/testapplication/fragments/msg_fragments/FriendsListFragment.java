package newmatch.zbmf.com.testapplication.fragments.msg_fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import newmatch.zbmf.com.testapplication.GMClass.GMRvSetLayoutManager;
import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.adapters.FriendsListAdapter;
import newmatch.zbmf.com.testapplication.base.BaseFragment;
import newmatch.zbmf.com.testapplication.presenter.presenterIml.BasePresenter;

/**
 * A simple {@link Fragment} subclass.
 * 好友，群组列表共用的fragment
 *
 *  群组功能暂时不开通
 *
 */
public class FriendsListFragment extends BaseFragment implements
        FriendsListAdapter.ClickFdArrow{


    public FriendsListFragment() {

    }

    public static FriendsListFragment instance(){
        FriendsListFragment friendsListFragment = new FriendsListFragment();
        Bundle bundle = new Bundle();
        friendsListFragment.setArguments(bundle);
        return friendsListFragment;
    }

    @Override
    protected Integer layoutId() {
        return R.layout.fragment_friends_list;
    }

    @Override
    protected void initView() {
        RecyclerView rv = bindView(R.id.rv);
        GMRvSetLayoutManager.setLinearLayoutManager(getActivity(),rv);
        FriendsListAdapter friendsListAdapter = new FriendsListAdapter(getActivity());
        friendsListAdapter.setClickFdArrow(this);
        rv.setAdapter(friendsListAdapter);


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
    public void extendList(Boolean extendAble) {
        //获取列表的展开，关闭状态


    }
}
