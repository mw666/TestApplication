package newmatch.zbmf.com.testapplication.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.base.BaseFragment;
import newmatch.zbmf.com.testapplication.presenter.BasePresenter;

/**
 * A simple {@link Fragment} subclass.
 * 关注Fragment
 */
public class AttentionFragment extends BaseFragment {


    public AttentionFragment() {

    }

    public static AttentionFragment attentionInstance(){
        AttentionFragment attentionFragment = new AttentionFragment();
        Bundle bundle = new Bundle();
        attentionFragment.setArguments(bundle);
        return attentionFragment;
    }

    @Override
    protected Integer layoutId() {
        return R.layout.fragment_attention;
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
