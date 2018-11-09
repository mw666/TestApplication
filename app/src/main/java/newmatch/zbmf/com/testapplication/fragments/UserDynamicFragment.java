package newmatch.zbmf.com.testapplication.fragments;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.adapters.UserDynamicAdapter;
import newmatch.zbmf.com.testapplication.base.BaseFragment;
import newmatch.zbmf.com.testapplication.presenter.BasePresenter;

/**
 * 用户动态的fragment
 */
public class UserDynamicFragment extends BaseFragment {

    public static UserDynamicFragment newInstance(String param1, String param2) {
        UserDynamicFragment fragment = new UserDynamicFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected Integer layoutId() {
        return R.layout.fragment_user_dynamic;
    }

    @Override
    protected void initView() {
        RecyclerView recyclerView = bindView(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3,
                OrientationHelper.VERTICAL,false));
        recyclerView.setNestedScrollingEnabled(true);
        UserDynamicAdapter userDynamicAdapter = new UserDynamicAdapter(getActivity());
        recyclerView.setAdapter(userDynamicAdapter);

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
        return false;
    }



}
