package newmatch.zbmf.com.testapplication.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.base.BaseFragment;
import newmatch.zbmf.com.testapplication.presenter.BasePresenter;

/**
 * A simple {@link Fragment} subclass.
 * 动态Fragment
 */
public class DynamicFragment extends BaseFragment {


    public DynamicFragment() {

    }

    public static DynamicFragment dynamicInstance(){
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
