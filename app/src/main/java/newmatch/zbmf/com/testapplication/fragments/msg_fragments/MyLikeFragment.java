package newmatch.zbmf.com.testapplication.fragments.msg_fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.base.BaseFragment;
import newmatch.zbmf.com.testapplication.presenter.presenterIml.BasePresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyLikeFragment extends BaseFragment {


    public MyLikeFragment() {
        // Required empty public constructor
    }

    public static MyLikeFragment instance() {
        MyLikeFragment myLikeFragment = new MyLikeFragment();
        Bundle bundle = new Bundle();
        myLikeFragment.setArguments(bundle);
        return myLikeFragment;
    }


    @Override
    protected Integer layoutId() {
        return R.layout.fragment_my_like;
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
