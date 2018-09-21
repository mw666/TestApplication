package newmatch.zbmf.com.testapplication.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.activitys.UserDetailActivity;
import newmatch.zbmf.com.testapplication.adapters.ChatRecommendAdapter;
import newmatch.zbmf.com.testapplication.adapters.ChatRecordListAdapter;
import newmatch.zbmf.com.testapplication.base.BaseFragment;
import newmatch.zbmf.com.testapplication.base.MyApplication;
import newmatch.zbmf.com.testapplication.interfaces.HomeRVIvClick;
import newmatch.zbmf.com.testapplication.interfaces.MsgChatItemClick;
import newmatch.zbmf.com.testapplication.presenter.BasePresenter;
import newmatch.zbmf.com.testapplication.utils.SkipActivityUtil;
import newmatch.zbmf.com.testapplication.utils.ToastUtils;

/**
 * A simple {@link Fragment} subclass.
 * 聊天记录的Fragment
 */
public class ChatListFragment extends BaseFragment implements HomeRVIvClick,MsgChatItemClick {


    public ChatListFragment() {}

    public static ChatListFragment instance(){
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
        RecyclerView chatRecommendRecyclerView = bindView(R.id.chatRecommendRecyclerView);
        chatRecommendRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                OrientationHelper.HORIZONTAL,false));
        ChatRecommendAdapter chatRecommendAdapter = new ChatRecommendAdapter();
        chatRecommendAdapter.setHomeRVIvClick(this);
        chatRecommendRecyclerView.setAdapter(chatRecommendAdapter);
        //聊天列表
        RecyclerView chatRecyclerView = bindView(R.id.chatRecyclerView);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                OrientationHelper.VERTICAL,false));
        ChatRecordListAdapter chatRecordListAdapter = new ChatRecordListAdapter();
        chatRecordListAdapter.setMsgChatItemClick(this);
        chatRecyclerView.setAdapter(chatRecordListAdapter);

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
    public void rvIvCallBack(int position) {
        ToastUtils.showSingleToast(MyApplication.getInstance(),"跳转用户资料页");
        //携带用户数据跳转用户详情页
        SkipActivityUtil.skipDataActivity(getActivity(),UserDetailActivity.class,new Bundle());

    }

    @Override
    public void chatItemClick(int position) {
        //跳转用户聊天页面
        ToastUtils.showSingleToast(MyApplication.getInstance(),"跳转用户聊天页面");

    }
}
