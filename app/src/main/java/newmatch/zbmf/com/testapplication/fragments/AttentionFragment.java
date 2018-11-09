package newmatch.zbmf.com.testapplication.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.adapters.HomeGridAdapter;
import newmatch.zbmf.com.testapplication.base.BaseFragment;
import newmatch.zbmf.com.testapplication.entity.BannerService;
import newmatch.zbmf.com.testapplication.interfaces.HomeRVIvClick;
import newmatch.zbmf.com.testapplication.interfaces.RecommendUser;
import newmatch.zbmf.com.testapplication.listeners.BannerPageClickListener;
import newmatch.zbmf.com.testapplication.presenter.BasePresenter;
import newmatch.zbmf.com.testapplication.presenter.TestView;
import newmatch.zbmf.com.testapplication.presenter.TestWanAndroidPresenter;
import newmatch.zbmf.com.testapplication.utils.MZBannerViewUtils;
import newmatch.zbmf.com.testapplication.views.MZBannerView;

/**
 * A simple {@link Fragment} subclass.
 * 关注Fragment
 */
public class AttentionFragment extends BaseFragment implements RecommendUser, HomeRVIvClick,
        TestView<BannerService, TestWanAndroidPresenter> ,BannerPageClickListener {

    private TestWanAndroidPresenter mPresenter;
    private HomeGridAdapter mHomeGridAdapter;
    //数据
    BannerService mResult;
    private int measuredHeight;
    private MZBannerView mMZBanner;
    private int[] banner={R.drawable.mn9,R.drawable.mn9,R.drawable.mn9,R.drawable.mn9,
            R.drawable.mn9,R.drawable.mn9,R.drawable.mn9,R.drawable.mn9,R.drawable.mn9};

    public AttentionFragment() {}

    public static AttentionFragment attentionInstance() {
        AttentionFragment attentionFragment = new AttentionFragment();
        Bundle bundle = new Bundle();
        attentionFragment.setArguments(bundle);
        return attentionFragment;
    }

    @Override
    protected Integer layoutId() {
        return R.layout.fragment_chat_list;
    }

    @Override
    protected void initView() {
        TextView toolbar_title = bindView(R.id.toolbar_title);
        toolbar_title.setVisibility(View.VISIBLE);
        toolbar_title.setText(getString(R.string.my_attention));
        bindView(R.id.toolbar).setVisibility(View.GONE);

        TextView recommendLinkMansTitle = bindView(R.id.recommendLinkMansTitle);
        TextView chatMansTitle = bindView(R.id.chatMansTitle);
        RecyclerView attentionRecyclerView = bindView(R.id.chatRecyclerView);

        recommendLinkMansTitle.setText(getString(R.string.same_city));
        chatMansTitle.setText(getString(R.string.attention_list));
        //同城推荐
        mMZBanner = bindView(R.id.m_banner);
        List<Integer> imgs=new ArrayList<>();
        for (int i : banner) {
            imgs.add(i);
        }
        MZBannerViewUtils.bannerPageClick(false,mMZBanner,imgs);
        /*recommendRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                OrientationHelper.HORIZONTAL, false));
        ChatRecommendAdapter chatRecommendAdapter = new ChatRecommendAdapter(getActivity());
        chatRecommendAdapter.setMsgChatItemClick(this);
        recommendRecyclerView.setAdapter(chatRecommendAdapter);*/
        //关注列表
        attentionRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2,
                OrientationHelper.VERTICAL, false));
        mHomeGridAdapter = new HomeGridAdapter(getContext(),getActivity());
        mHomeGridAdapter.setHomeRVIvClick(this);
        attentionRecyclerView.setAdapter(mHomeGridAdapter);

        mMZBanner.setBannerClickListener(this);

    }

    public Integer getViewSize(LinearLayout view) {
        ViewTreeObserver vto = view.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(() -> {
            //执行获取view大小的方法
            measuredHeight = view.getMeasuredHeight();
        });
        return measuredHeight;
    }

    @Override
    protected void initData() {
        mPresenter.doLoadData();
    }

    @Override
    protected BasePresenter initPresenter() {
        //初始化presenter
        mPresenter = new TestWanAndroidPresenter(this);
        return mPresenter;
    }

    @Override
    protected void onViewClick(View view) {

    }

    @Override
    protected Boolean setViewEnterStatuBar() {
        return true;
    }

    @Override
    public void recommendUser(int position) {
        //跳转用户资料页面

    }

    @Override
    public void rvIvCallBack(int position) {
        //跳转用户首资料页面

    }

    @Override
    public void setPresenter(TestWanAndroidPresenter presenter) {
        //设置presenter
        this.mPresenter = presenter;
    }

    @Override
    public void resultCallBack(BannerService result) {
        this.mResult = result;
        //返回结果
        mHomeGridAdapter.addImgList(result.getData());
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mMZBanner != null) {
            mMZBanner.isPause();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mMZBanner != null) {
            mMZBanner.isPause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMZBanner != null) {
            mMZBanner.isPause();
        }
    }

    @Override
    public void onPageClick(View view, int position) {

    }
}
