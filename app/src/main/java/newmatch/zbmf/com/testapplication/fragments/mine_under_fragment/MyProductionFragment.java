package newmatch.zbmf.com.testapplication.fragments.mine_under_fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.adapters.ProductionAdapter;
import newmatch.zbmf.com.testapplication.base.BaseFragment;
import newmatch.zbmf.com.testapplication.base.MyApplication;
import newmatch.zbmf.com.testapplication.presenter.BasePresenter;
import newmatch.zbmf.com.testapplication.utils.ToastUtils;

/**
 * A simple {@link Fragment} subclass.
 * 我的作品fragment
 */
public class MyProductionFragment extends BaseFragment implements ProductionAdapter.ProductionIvClick{


    public MyProductionFragment() {
    }

    public static MyProductionFragment instance() {
        MyProductionFragment fragment = new MyProductionFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected Integer layoutId() {
        return R.layout.fragment_my_production;
    }

    @Override
    protected void initView() {
        RecyclerView mineProductionRV = bindView(R.id.mineProductionRV);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3,
                OrientationHelper.VERTICAL, false);
        mineProductionRV.setLayoutManager(layoutManager);
//        mineProductionRV.setHasFixedSize(true);
//        mineProductionRV.setNestedScrollingEnabled(false);
        ProductionAdapter productionAdapter = new ProductionAdapter(getActivity());
        productionAdapter.setProductionIvClick(this);
        mineProductionRV.setAdapter(productionAdapter);
        mineProductionRV.requestLayout();

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
        return false;//没有toolBar和状态栏
    }


    @Override
    public void productionIvClick() {
        // TODO: 2018/11/13 跳转到短视频播放页面
        ToastUtils.showSingleToast(MyApplication.getInstance(),"跳转到短视频播放页面");
    }
}
