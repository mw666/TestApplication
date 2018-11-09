package newmatch.zbmf.com.testapplication.activitys;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.adapters.MsgTabAdapter;
import newmatch.zbmf.com.testapplication.base.BaseActivity;
import newmatch.zbmf.com.testapplication.base.MyApplication;
import newmatch.zbmf.com.testapplication.fragments.UserDynamicFragment;
import newmatch.zbmf.com.testapplication.utils.MyActivityManager;

/**
 * 用户个人空间
 */
public class UserCenterActivity extends BaseActivity {

    private List<String> msgTabTitles = new ArrayList<>();
    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected Integer layoutId() {
        return R.layout.activity_user_center;
    }

    @Override
    protected void initView() {
        //是否全屏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        MyActivityManager.getMyActivityManager().pushAct(UserCenterActivity.this);
        TabLayout userCenterTab = bindView(R.id.userCenterTab);
        ViewPager userCenterViewPager = bindView(R.id.userCenterViewPager);
        bindViewWithClick(R.id.backBtn,true);
        //准备数据
        addFG();

//        userCenterTab.setBackgroundDrawable(getResources().getDrawable(R.drawable.no_corner_white_bg));
//        msgFGTabLayout.setMinimumHeight(R.dimen.dp_45);
//        userCenterTab.setTabMode(TabLayout.MODE_SCROLLABLE);
        userCenterTab.setTabTextColors(R.color.black,R.color.colorPrimary);
//        ViewGroup.LayoutParams lp = userCenterTab.getLayoutParams();
//        lp.height= GetUIDimens.dpToPx(UserCenterActivity.this,40f);
//        lp.width= ViewGroup.LayoutParams.MATCH_PARENT;
//        userCenterTab.setLayoutParams(lp);
        userCenterTab.setSelected(true);

        userCenterTab.setupWithViewPager(userCenterViewPager,false);
        MsgTabAdapter adapter = new MsgTabAdapter(getSupportFragmentManager(), msgTabTitles, fragmentList);
        userCenterViewPager.setAdapter(adapter);
//        userCenterTab.setTabsFromPagerAdapter(adapter);
        userCenterViewPager.setCurrentItem(0);


    }

    @Override
    protected void initData() {

    }

    @Override
    protected String initTitle() {
        return getString(R.string.user_centetr);
    }

    @Override
    protected Boolean showBackBtn() {
        return true;
    }

    @Override
    protected int topBarColor() {
        return MyApplication.getInstance().getResources().getColor(R.color.middlePurple);
    }

    @Override
    protected void onViewClick(View view) {
        switch (view.getId()){
            case R.id.backBtn:
                UserCenterActivity.this.finish();
                break;

        }

    }

    private void addFG(){
        msgTabTitles.add(getString(R.string.photo));
        msgTabTitles.add(getString(R.string.dynamic));
        msgTabTitles.add(getString(R.string.video));
        fragmentList.add(UserDynamicFragment.newInstance("",""));
        fragmentList.add(UserDynamicFragment.newInstance("",""));
        fragmentList.add(UserDynamicFragment.newInstance("",""));
    }


}
