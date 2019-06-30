package newmatch.zbmf.com.testapplication.fragments.home_fragments;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import newmatch.zbmf.com.testapplication.GMClass.LikeGMClass;
import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.adapters.HomeGridAdapter;
import newmatch.zbmf.com.testapplication.base.BaseFragment;
import newmatch.zbmf.com.testapplication.interfaces.DianZanClickListener;
import newmatch.zbmf.com.testapplication.interfaces.HomeRVIvClick;
import newmatch.zbmf.com.testapplication.presenter.presenterIml.BasePresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home1Fragment extends BaseFragment implements HomeRVIvClick,
        DianZanClickListener {

    private SmartRefreshLayout refreshLayout;
    private RecyclerView homeRecyclerView;
    private HomeGridAdapter mHomeGridAdapter;

    public Home1Fragment() {
        // Required empty public constructor
    }


    @Override
    protected Integer layoutId() {
        return R.layout.fragment_home1;
    }

    private List<Drawable> imgs = new ArrayList<>();

    @Override
    protected void initView() {
        refreshLayout = bindView(R.id.refreshLayout);
        homeRecyclerView = bindView(R.id.homeRecyclerView);
        homeRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2,
                OrientationHelper.VERTICAL, false));
        mHomeGridAdapter = new HomeGridAdapter(getContext(), getActivity());
        mHomeGridAdapter.setHomeRVIvClick(this);
        mHomeGridAdapter.setDianZan(this);
        homeRecyclerView.setAdapter(mHomeGridAdapter);

        //模拟数据
        for (int i = 0; i < 30; i++) {
            imgs.add(ContextCompat.getDrawable(getActivity(), R.drawable.mn9));
        }
        mHomeGridAdapter.addImgList1(imgs);

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


    @Override
    public void dianZanClick(ImageView view, TextView moodTv) {
        //设置点赞
        LikeGMClass.clickLike(getActivity(), view);
    }

    @Override
    public void rvIvCallBack(int position) {
        //首页图片点击的回调
        Bundle bundle = new Bundle();
        //        ArrayList<BannerService.Data> data = mResult.getData();
        //        bundle.putParcelableArrayList(PermissionC.USER_PIC, data);
        //        SkipActivityUtil.skipDataActivity(getActivity(), UserDetailActivity.class, bundle);
    }
}
