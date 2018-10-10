package newmatch.zbmf.com.testapplication.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.adapters.MineViewAdapter;
import newmatch.zbmf.com.testapplication.base.BaseFragment;
import newmatch.zbmf.com.testapplication.interfaces.MineViewClick;
import newmatch.zbmf.com.testapplication.presenter.BasePresenter;

/**
 * A simple {@link Fragment} subclass.
 * 我的Fragment
 */
public class MineFragment extends BaseFragment implements MineViewClick{

    /*private String[] titles = {getActivity().getString(R.string.open_vip),
            getActivity().getString(R.string.update_pass_word),
            getActivity().getString(R.string.options_back),
            getActivity().getString(R.string.version_update),
            getActivity().getString(R.string.login_out)
    };
*/
    public MineFragment() {
        // Required empty public constructor
    }

    public static MineFragment mineInstance() {
        MineFragment mineFragment = new MineFragment();
        Bundle bundle = new Bundle();
        mineFragment.setArguments(bundle);
        return mineFragment;
    }

    @Override
    protected Integer layoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView() {
        TextView toolbar_title = bindView(R.id.toolbar_title);
        toolbar_title.setVisibility(View.VISIBLE);
        toolbar_title.setText(getString(R.string.mine));
        TextView userName = bindView(R.id.userName);
        TextView userSexAndAge = bindView(R.id.userSexAndAge);
        userName.setVisibility(View.VISIBLE);
        userSexAndAge.setVisibility(View.VISIBLE);

        userName.setText("二狗子");
        userSexAndAge.setText("女 23岁");

        String[] titles = {getActivity().getString(R.string.open_vip),
                getActivity().getString(R.string.update_pass_word),
                getActivity().getString(R.string.options_back),
                getActivity().getString(R.string.version_update),
                getActivity().getString(R.string.login_out)
        };

        RecyclerView userMineRV = bindView(R.id.userMineRV);
        userMineRV.setLayoutManager(new LinearLayoutManager(getActivity(),
                OrientationHelper.VERTICAL, false));
        MineViewAdapter mineViewAdapter = new MineViewAdapter(titles, getActivity());
        mineViewAdapter.setMineViewClick(this);
        userMineRV.setAdapter(mineViewAdapter);


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
    public void clickVip() {

    }

    @Override
    public void clickUpdatePassWord() {

    }

    @Override
    public void clickOptionsUp() {

    }

    @Override
    public void clickVertionUpdate() {

    }

    @Override
    public void clickLoginOut() {

    }
}
