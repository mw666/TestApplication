package newmatch.zbmf.com.testapplication;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.ielse.imagewatcher.ImageWatcherHelper;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.ui.MatisseActivity;

import java.security.Permissions;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import newmatch.zbmf.com.testapplication.GMClass.GMCopy;
import newmatch.zbmf.com.testapplication.GMClass.GMPermissions;
import newmatch.zbmf.com.testapplication.GMClass.GMSelectImg;
import newmatch.zbmf.com.testapplication.activitys.InvateActivity;
import newmatch.zbmf.com.testapplication.activitys.MySpaceActivity;
import newmatch.zbmf.com.testapplication.activitys.OptionsActivity;
import newmatch.zbmf.com.testapplication.activitys.UserDetailActivity;
import newmatch.zbmf.com.testapplication.activitys.VIPActivity;
import newmatch.zbmf.com.testapplication.activitys.WalletActivity;
import newmatch.zbmf.com.testapplication.adapters.MainFragMentAdapter;
import newmatch.zbmf.com.testapplication.adapters.MenuAdapter;
import newmatch.zbmf.com.testapplication.adapters.ViewPagerAdapter;
import newmatch.zbmf.com.testapplication.assist.GlideUtil;
import newmatch.zbmf.com.testapplication.base.BaseActivity;
import newmatch.zbmf.com.testapplication.base.MyApplication;
import newmatch.zbmf.com.testapplication.callback.DialogActCallBack;
import newmatch.zbmf.com.testapplication.callback.EtCallBack;
import newmatch.zbmf.com.testapplication.dialogs.MyDialogUtil;
import newmatch.zbmf.com.testapplication.fragments.DynamicFragment;
import newmatch.zbmf.com.testapplication.fragments.HomeFragment;
import newmatch.zbmf.com.testapplication.fragments.MsgFragment;
import newmatch.zbmf.com.testapplication.fragments.main_menu_fragments.Main1Fragment;
import newmatch.zbmf.com.testapplication.fragments.main_menu_fragments.Main2Fragment;
import newmatch.zbmf.com.testapplication.permissions.PermissionActivity;
import newmatch.zbmf.com.testapplication.permissions.PermissionC;
import newmatch.zbmf.com.testapplication.utils.ActivityAnimUtils;
import newmatch.zbmf.com.testapplication.utils.GetUIDimens;
import newmatch.zbmf.com.testapplication.utils.MyActivityManager;
import newmatch.zbmf.com.testapplication.utils.ShowImgUtils;
import newmatch.zbmf.com.testapplication.utils.SkipActivityUtil;
import newmatch.zbmf.com.testapplication.utils.TianShareUtil;
import newmatch.zbmf.com.testapplication.utils.Util;
import newmatch.zbmf.com.testapplication.views.GenericDrawerLayout;
import newmatch.zbmf.com.testapplication.views.NoScrollViewPager;
import newmatch.zbmf.com.testapplication.views.RotationPageTransformer;

/**
 * 取名甜甜圈吧
 * 首页
 */
public class MainActivity extends BaseActivity {

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected Integer layoutId() {
        return R.layout.activity_main;
    }

    private List<Fragment> mFragment = null;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initView() {
        ViewPager viewPager = bindView(R.id.mainViewPager);
        if (mFragment == null) {
            mFragment = new ArrayList<>();
        }
        if (mFragment.size() == 0) {
            mFragment.add(new Main2Fragment());
            mFragment.add(new Main1Fragment());
        }
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), mFragment);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);
    }

    @Override
    protected void initData() {
    }

    @Override
    protected String initTitle() {
        return getString(R.string.application_title);
    }

    @Override
    protected Boolean showBackBtn() {
        return false;
    }

    @Override
    protected int topBarColor() {
        return MyApplication.getInstance().getResources().getColor(R.color.colorPrimary);
    }

    @Override
    protected void onViewClick(View view) {

    }


}
