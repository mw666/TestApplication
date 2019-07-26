package newmatch.zbmf.com.testapplication.fragments;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import newmatch.zbmf.com.testapplication.GMClass.GMCopy;
import newmatch.zbmf.com.testapplication.GMClass.GMSelectImg;
import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.activitys.UserCenterActivity;
import newmatch.zbmf.com.testapplication.adapters.pager_fragment_adapters.MsgTabAdapter;
import newmatch.zbmf.com.testapplication.utils.glidUtils.GlideUtil;
import newmatch.zbmf.com.testapplication.base.BaseFragment;
import newmatch.zbmf.com.testapplication.base.MyApplication;
import newmatch.zbmf.com.testapplication.callback.PermissionResultCallBack;
import newmatch.zbmf.com.testapplication.dialogs.DialogUtils;
import newmatch.zbmf.com.testapplication.events.RVScrollEvent;
import newmatch.zbmf.com.testapplication.fragments.mine_under_fragment.MyProductionFragment;
import newmatch.zbmf.com.testapplication.fragments.mine_under_fragment.MySettingFragment;
import newmatch.zbmf.com.testapplication.listeners.OnceClickListener;
import newmatch.zbmf.com.testapplication.permissions.PermissionC;
import newmatch.zbmf.com.testapplication.presenter.presenterIml.BasePresenter;
import newmatch.zbmf.com.testapplication.utils.ContainsEmojiEditText;
import newmatch.zbmf.com.testapplication.utils.PermissionUtils;
import newmatch.zbmf.com.testapplication.utils.SkipActivityUtil;
import newmatch.zbmf.com.testapplication.utils.ToastUtils;
import newmatch.zbmf.com.testapplication.views.PersonalScrollView;
import newmatch.zbmf.com.testapplication.views.circleImageView.RoundImageView;

/**
 * A simple {@link Fragment} subclass.
 * 我的Fragment
 */
public class MineFragment extends BaseFragment {

    private RoundImageView mAvatarIv;
    private Toolbar mToolbar;
    private List<Fragment> fragmentList;
    private List<String> mMsgTabTitles;
    private PersonalScrollView mPersonalSc;


    public MineFragment() {
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

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initView() {
        //随着scrollView的滑动逐渐显示
        mToolbar = bindView(R.id.toolbar);
        mToolbar.setVisibility(View.VISIBLE);
        mToolbar.setAlpha(0);
        TextView toolbar_title = bindView(R.id.toolbar_title);
        toolbar_title.setVisibility(View.VISIBLE);
        toolbar_title.setText(getString(R.string.mine));
        TextView userName = bindViewWithClick(R.id.userName, true);
        TextView userSexAndAge = bindView(R.id.userSexAndAge);
        TextView userAccount = bindView(R.id.userAccount);
        mAvatarIv = bindViewWithClick(R.id.avatarIv, true);
        bindViewWithClick(R.id.goToSpace, true).setVisibility(View.VISIBLE);
        RelativeLayout mine_user_view = bindViewWithClick(R.id.mine_user_view, true);
//        ll = bindView(R.id.ll);
        /**************************************************/
        mPersonalSc = bindView(R.id.personalSc);
        ImageView iv_personinfo_bg = bindView(R.id.iv_personinfo_bg);
        View stateBarView = bindView(R.id.stateBarView);
        RelativeLayout headRv = bindView(R.id.headRv);
        TabLayout userCenterTab = bindView(R.id.userCenterTab);
        ViewPager userCenterViewPager = bindView(R.id.userCenterViewPager);

        //准备数据--->tabLayout和viewPager
        addFG();
        userCenterTab.setTabTextColors(R.color.black, R.color.colorPrimary);
        userCenterTab.setupWithViewPager(userCenterViewPager);
        MsgTabAdapter adapter = new MsgTabAdapter(getChildFragmentManager(), mMsgTabTitles, fragmentList);
        userCenterViewPager.setAdapter(adapter);
        userCenterViewPager.setCurrentItem(0);

        EventBus.getDefault().register(this);//注册事件
        mPersonalSc.setTabLayout(userCenterTab, mToolbar, mine_user_view, headRv);
        mPersonalSc.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener)
                (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
//                    PLog.LogD("==  scrollY : "+scrollY+"   ===  oldScrollY : "+oldScrollY);

                });

        userName.setVisibility(View.VISIBLE);
        userSexAndAge.setVisibility(View.VISIBLE);
        userAccount.setVisibility(View.VISIBLE);

        userName.setText("二狗子");
        userSexAndAge.setText("女 23岁");
        userAccount.setText("wind_143u9145u91");

        //设置textView的复制操作
        GMCopy.instance().copyGetXY(userName, getActivity(), mPersonalSc);
        GMCopy.instance().copyGetXY(userAccount, getActivity(), mPersonalSc);


    }

    //接收事件
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void redeiveRVEvent(RVScrollEvent rvScrollEvent) {
        mPersonalSc.setRVState(rvScrollEvent.getRVState());
    }

    private void addFG() {
        String[] tabTitles = getActivity().getResources().getStringArray(R.array.mine_tab_titles);
        mMsgTabTitles = Arrays.asList(tabTitles);
        if (fragmentList == null) {
            fragmentList = new ArrayList<>();
        } else if (fragmentList.size() > 0) {
            fragmentList.clear();
        }
        fragmentList.add(MyProductionFragment.instance());
        fragmentList.add(MyProductionFragment.instance());
        fragmentList.add(MyProductionFragment.instance());
        fragmentList.add(MySettingFragment.instance());
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
        switch (view.getId()) {
            case R.id.avatarIv:
                PermissionUtils.instance().requestPermission(getActivity(),
                        getString(R.string.get_img_tip),PermissionC.WR_FILES_PERMISSION,
                        new PermissionResultCallBack() {
                            @Override
                            public void permissionCallBack() {
                                //选择图片
                                new GMSelectImg().picImgsOrVideo(getActivity(), MimeType.ofImage(),
                                        PermissionC.PIC_IMG_VIDEO_CODE, 1);
                            }
                        });
                break;
            case R.id.userName:
                View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.update_et_view, null);
                ContainsEmojiEditText nickEt = inflate.findViewById(R.id.nickEt);
                ImageView sendNick = inflate.findViewById(R.id.sendNick);
                DialogUtils.instance()
                        .setDialogAnimStyle(R.style.ActionSheetDialogAnimation)
                        .setDialogStyle(R.style.dialog)
                        .setGravity(Gravity.BOTTOM)
                        .setView(inflate)
                        .showAlertDialog(getActivity(), getActivity());
                sendNick.setOnClickListener(new OnceClickListener() {
                    @Override
                    public void onNoDoubleClick(View v) {
                        String nick = nickEt.getText().toString();
                        ToastUtils.showSingleToast(MyApplication.getInstance(), "获取到的昵称：" + nick);

                        nickEt.getText().clear();
                    }
                });
                break;
            case R.id.goToSpace:
            case R.id.mine_user_view:
                //跳转用户个人空间
                SkipActivityUtil.skipActivity(getActivity(), UserCenterActivity.class);
                break;
        }
    }

    @Override
    protected Boolean setViewEnterStatuBar() {
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //获取结果
        switch (requestCode) {
            case PermissionC.PIC_IMG_VIDEO_CODE:
                //选择图片的结果
                if (resultCode == Activity.RESULT_OK) {
                    List<Uri> mSelected = Matisse.obtainResult(data);
                    //设置选择的图片
                    GlideUtil.loadCircleImage(getActivity(),
                            R.drawable.touxiang_icon, mSelected.get(0), mAvatarIv);
                    // TODO: 2018/10/9 上传选择的图片 --->用户图片

                }
                break;
        }
    }

    public void updateMyAvatar(List<Uri> mSelected) {
        //设置选择的图片
        GlideUtil.loadCircleImage(getActivity(),
                R.drawable.touxiang_icon, mSelected.get(0), mAvatarIv);
        // TODO: 2018/10/9 上传选择的图片 --->用户图片

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//注销事件
    }
}
