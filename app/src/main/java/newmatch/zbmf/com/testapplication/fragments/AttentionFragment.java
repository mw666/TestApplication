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
import newmatch.zbmf.com.testapplication.callback.HomeRVIvClick;
import newmatch.zbmf.com.testapplication.callback.RecommendUser;
import newmatch.zbmf.com.testapplication.views.customViewPager.BannerPageClickListener;

/**
 * A simple {@link Fragment} subclass.
 * 关注Fragment
 */
public class AttentionFragment extends BaseFragment implements RecommendUser, HomeRVIvClick,
        BannerPageClickListener {

    private HomeGridAdapter mHomeGridAdapter;
    private int measuredHeight;

    private int[] banner={R.drawable.m2,R.drawable.m4,R.drawable.m5,R.drawable.m7,
            R.drawable.m1};

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

        List<Integer> imgs=new ArrayList<>();
        for (int i : banner) {
            imgs.add(i);
        }

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
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onPageClick(View view, int position) {

    }
}
