package newmatch.zbmf.com.testapplication.fragments.main_menu_fragments;


import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import newmatch.zbmf.com.testapplication.GMClass.GMCopy;
import newmatch.zbmf.com.testapplication.MainActivity;
import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.activitys.InvateActivity;
import newmatch.zbmf.com.testapplication.activitys.MySpaceActivity;
import newmatch.zbmf.com.testapplication.activitys.OptionsActivity;
import newmatch.zbmf.com.testapplication.activitys.VIPActivity;
import newmatch.zbmf.com.testapplication.activitys.WalletActivity;
import newmatch.zbmf.com.testapplication.adapters.MenuAdapter;
import newmatch.zbmf.com.testapplication.assist.GlideUtil;
import newmatch.zbmf.com.testapplication.base.BaseFragment;
import newmatch.zbmf.com.testapplication.dialogs.MyDialogUtil;
import newmatch.zbmf.com.testapplication.presenter.presenterIml.BasePresenter;
import newmatch.zbmf.com.testapplication.utils.ActivityAnimUtils;
import newmatch.zbmf.com.testapplication.views.RotationPageTransformer;

/**
 * A simple {@link Fragment} subclass.
 */
public class Main2Fragment extends BaseFragment {


    private MainActivity mainActivity;
    private int[] imgs = {R.drawable.card3, R.drawable.card2, R.drawable.card4};

    private List<Integer> imgList;
    private AppCompatImageView menuHeadIv;
    private TextView userNick;

    public Main2Fragment() {

    }


    @Override
    protected Integer layoutId() {
        return R.layout.fragment_main2;
    }


    @Override
    protected void initView() {
        //获取宿主Activity对象
        mainActivity = (MainActivity) getActivity();
        ViewPager menuViewPager = bindView(R.id.menuViewPager);
        //获取控件
        RelativeLayout rL = bindView(R.id.rL);
        menuHeadIv = bindViewWithClick(R.id.menuHeadIv, true);
        userNick = bindViewWithClick(R.id.userNick, true);
        TextView careTv = bindView(R.id.careTv);//长按复制
        TextView userAccount = bindView(R.id.userAccount);//长按复制
        TextView fansTv = bindView(R.id.fansTv);
        TextView likeTv = bindView(R.id.likeTv);
        TextView rankTv = bindView(R.id.rankTv);

        TextView userRmb = bindViewWithClick(R.id.userRmb, true);
        TextView vipTv = bindViewWithClick(R.id.vipTv, true);
        TextView inviteTv = bindViewWithClick(R.id.inviteTv, true);
        TextView myArea = bindViewWithClick(R.id.myArea, true);
        TextView taskRewards = bindViewWithClick(R.id.taskRewards, true);
        TextView optionBackTv = bindViewWithClick(R.id.optionBackTv, true);
        TextView versionUpdateTv = bindViewWithClick(R.id.versionUpdateTv, true);
        TextView accountTv = bindViewWithClick(R.id.accountTv, true);
        TextView loginOutTv = bindViewWithClick(R.id.loginOutTv, true);

        //设置画廊效果的ViewPager
        gallerlyPager(menuViewPager);
        //设置textView的复制操作
        GMCopy.instance().copyGetXY(userNick, getActivity(), rL);
        GMCopy.instance().copyGetXY(userAccount, getActivity(), rL);

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
            case R.id.menuHeadIv:
                //选择或更换头像
                if (mainActivity != null) {
                    mainActivity.updateUserHeaderImg();
                }
                break;
            case R.id.userNick:
                //用户昵称
                updateUserNick();
                break;
            case R.id.userRmb:
                //用户钱包页面，资金余额，绑定的提现账号
                ActivityAnimUtils.instance().activityIn(getActivity(),
                        WalletActivity.class);
                break;
            case R.id.vipTv:
                //开通VIP页面
                ActivityAnimUtils.instance().activityIn(getActivity(),
                        VIPActivity.class);
                break;
            case R.id.inviteTv:
                //邀请会员页面
                ActivityAnimUtils.instance().activityIn(getActivity(),
                        InvateActivity.class);
                break;
            case R.id.myArea:
                //跳转个人空间页面
                ActivityAnimUtils.instance().activityIn(getActivity(),
                        MySpaceActivity.class);
                break;
            case R.id.taskRewards:
                //待完成任务的页面

                break;
            case R.id.optionBackTv:
                //意见反馈页面
                ActivityAnimUtils.instance().activityIn(getActivity(),
                        OptionsActivity.class);
                break;
            case R.id.versionUpdateTv:
                //版本更新页面

                break;
            case R.id.accountTv:
                //账户切换页面

                break;
            case R.id.loginOutTv:
                //退出登录

                break;
        }
    }

    @Override
    protected Boolean setViewEnterStatuBar() {
        return true;
    }


    //更新用户昵称
    private void updateUserNick() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.update_user_nick_view, null);
        MyDialogUtil.showEtDialog(getActivity(),
                getActivity(), "", view, R.drawable.dialog_bg,
                true, (content, alertDialog) -> {
                    alertDialog.dismiss();
                    userNick.setText(content);
                });
    }

    //更新用户头像
    public void updateUserHeader(Uri headUri) {
        if (headUri != null)
            GlideUtil.loadCircleImage(getActivity(), R.drawable.ic_head_portrait_icon,
                    headUri, menuHeadIv);
    }

    //ViewPager的3D画廊效果
    private void gallerlyPager(ViewPager menuViewPager) {
        imgList = new ArrayList<>();
        for (int i = 0; i < imgs.length; i++) {
            imgList.add(imgs[i]);
        }
        MenuAdapter menuAdapter = new MenuAdapter(imgList, null, R.layout.menu_view);
        menuViewPager.setAdapter(menuAdapter);
        menuViewPager.setOffscreenPageLimit(imgList.size());//设置预加载的数量
        menuViewPager.setPageMargin(5);
        //ViewPager默认选择中间的那个
        if (imgList.size() > 2)
            menuViewPager.setCurrentItem(1);
        //添加3D画廊效果
        menuViewPager.setPageTransformer(true, new RotationPageTransformer());
        menuViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }


}
