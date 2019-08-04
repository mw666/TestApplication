package newmatch.zbmf.com.testapplication.fragments.msg_fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.facebook.stetho.common.LogUtil;

import java.util.ArrayList;
import java.util.List;

import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.activitys.UserDetailActivity;
import newmatch.zbmf.com.testapplication.adapters.ChatRecordListAdapter;
import newmatch.zbmf.com.testapplication.base.BaseFragment;
import newmatch.zbmf.com.testapplication.base.MyApplication;
import newmatch.zbmf.com.testapplication.callback.MsgChatItemClick;
import newmatch.zbmf.com.testapplication.callback.RecommendUser;
import newmatch.zbmf.com.testapplication.component.BannerViewHolderType;
import newmatch.zbmf.com.testapplication.utils.SkipActivityUtil;
import newmatch.zbmf.com.testapplication.utils.ToastUtils;
import newmatch.zbmf.com.testapplication.views.customViewPager.BannerViewHolder;
import newmatch.zbmf.com.testapplication.views.customViewPager.MZBannerView;
import newmatch.zbmf.com.testapplication.views.customViewPager.MZHolderCreator;

/**
 * A simple {@link Fragment} subclass.
 * 聊天记录的Fragment
 */
public class ChatListFragment extends BaseFragment implements RecommendUser, MsgChatItemClick {


    private MZBannerView mMZBanner;
    private int[] banner = {R.drawable.m1, R.drawable.m6, R.drawable.m3, R.drawable.m8, R.drawable.m2};
    private MZBannerView recommendBanner;

    public ChatListFragment() {
    }

    public static ChatListFragment instance() {
        ChatListFragment chatListFragment = new ChatListFragment();
        Bundle bundle = new Bundle();
        chatListFragment.setArguments(bundle);
        return chatListFragment;
    }

    @Override
    protected Integer layoutId() {
        return R.layout.fragment_chat_list;
    }

    @Override
    protected void initView() {
        //联系人推荐列表
        /*RecyclerView chatRecommendRecyclerView = bindView(R.id.chatRecommendRecyclerView);
        chatRecommendRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                OrientationHelper.HORIZONTAL,false));
        ChatRecommendAdapter chatRecommendAdapter = new ChatRecommendAdapter(getActivity());
        chatRecommendAdapter.setMsgChatItemClick(this);
        chatRecommendRecyclerView.setAdapter(chatRecommendAdapter);*/
        /*使用MZBanner*/
        recommendBanner = bindView(R.id.recommendBanner);
        //聊天列表
        RecyclerView chatRecyclerView = bindView(R.id.chatRecyclerView);

        //模拟bannerView轮播
        initBannerView(recommendBanner);
        //模拟聊天
        initChatList(chatRecyclerView);

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
    public void chatItemClick(int position) {
        //跳转用户聊天页面
        ToastUtils.showSingleToast(MyApplication.getInstance(), "跳转用户聊天页面");

    }

    @Override
    public void recommendUser(int position) {
        ToastUtils.showSingleToast(MyApplication.getInstance(), "跳转用户资料页");
        //携带用户数据跳转用户详情页
        SkipActivityUtil.skipDataActivity(getActivity(), UserDetailActivity.class, new Bundle());
    }

    @Override
    public void onResume() {
        super.onResume();
        recommendBanner.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        recommendBanner.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    //模拟聊天
    private void initChatList(RecyclerView chatRecyclerView) {
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                OrientationHelper.VERTICAL, false));
        ChatRecordListAdapter chatRecordListAdapter = new ChatRecordListAdapter(getActivity());
        chatRecordListAdapter.setMsgChatItemClick(this);
        chatRecyclerView.setAdapter(chatRecordListAdapter);
    }

    //模拟设置轮播广告
    private void initBannerView(MZBannerView homeBanner) {
        int[] imgs = {R.drawable.m4, R.drawable.m5, R.drawable.m2, R.drawable.m7};

        List<Integer> imgList;
        imgList = new ArrayList<>();
        for (int i = 0; i < imgs.length; i++) {
            imgList.add(imgs[i]);
        }
        homeBanner.addPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                LogUtil.e("----->addPageChangeLisnter:" + position +
                        "positionOffset:" + positionOffset + "positionOffsetPixels:"
                        + positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                LogUtil.e("addPageChangeLisnter:" + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        homeBanner.setIndicatorVisible(true);
        // 代码中更改indicator 的位置
        homeBanner.setIndicatorAlign(MZBannerView.IndicatorAlign.LEFT);
        homeBanner.setIndicatorPadding(15, 0, 0, 15);
        homeBanner.setPages(imgList, (MZHolderCreator<BannerViewHolder>)
                () -> new BannerViewHolder(imgList.size(), BannerViewHolderType.ConnerViewHolder));
        //Banner的点击事件
        homeBanner.setBannerPageClickListener((view, position) -> {
            ToastUtils.showSquareTvToast(getContext(), "点击了Banner页面：" + position);
        });
    }
}
