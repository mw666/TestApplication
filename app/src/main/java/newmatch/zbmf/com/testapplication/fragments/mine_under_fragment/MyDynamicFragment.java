package newmatch.zbmf.com.testapplication.fragments.mine_under_fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 * 我的动态的fragment
 */
public class MyDynamicFragment extends BaseFragment {


    public MyDynamicFragment() {}

    public static MyDynamicFragment instance(){
        MyDynamicFragment myDynamicFragment = new MyDynamicFragment();
        Bundle bundle = new Bundle();
        myDynamicFragment.setArguments(bundle);
        return myDynamicFragment;
    }

    @Override
    protected Integer layoutId() {
        return R.layout.fragment_my_dynamic;
    }

    @Override
    protected void initView() {
        RecyclerView mineDynamicRV = bindView(R.id.mineDynamicRV);


    }

    @Override
    protected void initData() {

    }



    @Override
    protected void onViewClick(View view) {

    }

    @Override
    protected Boolean setViewEnterStatuBar() {
        return null;
    }



}
