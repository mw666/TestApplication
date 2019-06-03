package newmatch.zbmf.com.testapplication.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.base.BaseFragment;
import newmatch.zbmf.com.testapplication.presenter.presenterIml.BasePresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeOldFragment extends BaseFragment {


    public HomeOldFragment() {
        // Required empty public constructor
    }


    @Override
    protected Integer layoutId() {
        return R.layout.fragment_home_old;
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
