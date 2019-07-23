package newmatch.zbmf.com.testapplication.fragments.photo_video_frags;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.adapters.PvAdapter;
import newmatch.zbmf.com.testapplication.base.BaseFragment;
import newmatch.zbmf.com.testapplication.presenter.presenterIml.BasePresenter;

/**
 * Created By pq
 * on 2019/7/23
 */
public class PhotoVideoFragment extends BaseFragment {


    public static PhotoVideoFragment instance() {
        PhotoVideoFragment photoVideoFragment = new PhotoVideoFragment();
        Bundle bundle = new Bundle();
        photoVideoFragment.setArguments(bundle);
        return photoVideoFragment;
    }

    @Override
    protected Integer layoutId() {
        return R.layout.fragment_user_dynamic;
    }

    @Override
    protected void initView() {
        TextView emptyView = bindView(R.id.emptyView);
        RecyclerView pvRV = bindView(R.id.dynamicRV);
        SmartRefreshLayout dynamicRefreshLayout = bindView(R.id.dynamicRefreshLayout);
        //如果没有数据，则展示emptyView

        //设置RecyclerView的数据
        setRvContent(pvRV);
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

    //设置RecyclerView的数据
    private void setRvContent(RecyclerView rv) {
        rv.setLayoutManager(new LinearLayoutManager(getContext(),
                OrientationHelper.VERTICAL, false));
        PvAdapter pvAdapter = new PvAdapter();
        rv.setAdapter(pvAdapter);
    }

}
