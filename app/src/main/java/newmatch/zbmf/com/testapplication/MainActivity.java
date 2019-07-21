package newmatch.zbmf.com.testapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import java.util.ArrayList;
import java.util.List;

import newmatch.zbmf.com.testapplication.GMClass.GMSelectImg;
import newmatch.zbmf.com.testapplication.adapters.pager_fragment_adapters.ViewPagerAdapter;
import newmatch.zbmf.com.testapplication.base.BaseActivity;
import newmatch.zbmf.com.testapplication.callback.DialogActCallBack;
import newmatch.zbmf.com.testapplication.dialogs.MyDialogUtil;
import newmatch.zbmf.com.testapplication.fragments.main_menu_fragments.Main1Fragment;
import newmatch.zbmf.com.testapplication.fragments.main_menu_fragments.Main2Fragment;
import newmatch.zbmf.com.testapplication.permissions.PermissionC;

/**
 * 取名甜甜圈吧
 * 首页
 */
public class MainActivity extends BaseActivity {

    private Main2Fragment main2Fragment;
    private Main1Fragment main1Fragment;

    private List<Fragment> mFragment = null;

    //存放权限的list
    private List<String> pers = new ArrayList<>();
    private ViewPager viewPager;

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected Integer layoutId() {
        return R.layout.activity_main;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initView() {
        //全屏，内容顶入状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        viewPager = bindView(R.id.mainViewPager);
        if (mFragment == null) {
            mFragment = new ArrayList<>();
        }
        if (mFragment.size() == 0) {
            main2Fragment = new Main2Fragment();
            main1Fragment = new Main1Fragment();
            mFragment.add(main2Fragment);
            mFragment.add(main1Fragment);
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
        return ContextCompat.getColor(MainActivity.this, R.color.colorPrimary);
    }

    @Override
    protected void onViewClick(View view) {

    }


    //更新用户的头像
    @SuppressLint("ObsoleteSdkInt")
    public void updateUserHeaderImg() {
        pers.clear();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            for (int i = 0; i < PermissionC.WR_FILES_PERMISSION.length; i++) {
                int checkI = checkSelfPermission(PermissionC.WR_FILES_PERMISSION[i]);
                if (checkI == PackageManager.PERMISSION_DENIED) {
                    pers.add(PermissionC.WR_FILES_PERMISSION[i]);
                }
            }
            if (pers.size() > 0) {
                //申请权限
                int size = pers.size();
                ActivityCompat.requestPermissions(MainActivity.this,
                        pers.toArray(new String[size]),
                        0x12);
            } else {
                //选择图片
                new GMSelectImg().picImgsOrVideo(this, MimeType.ofImage(),
                        PermissionC.PIC_IMG_VIDEO_CODE, 1);
            }
        }
    }

    //权限申请后的回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0x12) {
            pers.clear();
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    pers.add(permissions[i]);
                }
            }
            if (pers.size() > 0) {
                int size = pers.size();
                showPerDialog(pers.toArray(new String[size]));
            } else {
                //权限已经通过
                //选择图片
                new GMSelectImg().picImgsOrVideo(this, MimeType.ofImage(),
                        PermissionC.PIC_IMG_VIDEO_CODE, 1);
            }
        }
    }

    //跳转页面后的回调方法
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //获取结果
        switch (requestCode) {
            case PermissionC.PIC_IMG_VIDEO_CODE:
                //选择图片的结果
                if (resultCode == Activity.RESULT_OK) {
                    List<Uri> mSelected = Matisse.obtainResult(data);
                    //更新mainActivity下的两个fragment的用户头像
                    main2Fragment.updateUserHeader(mSelected.get(0));
                    main1Fragment.updateUserHeader(mSelected.get(0));
                }
                break;
        }
    }

    //展示申请权限的dialog
    private void showPerDialog(String[] pers) {
        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(this).inflate(R.layout.permission_dialog_bg, null);
        MyDialogUtil.showPermissionAlert(this, this, "",
                view, R.drawable.dialog_bg,
                false, new DialogActCallBack() {
                    @Override
                    public void cancelActCallBack(AlertDialog alertDialog) {
                        alertDialog.dismiss();
                    }

                    @Override
                    public void positionActCallBack(AlertDialog alertDialog) {
                        //再次申请权限
                        ActivityCompat.requestPermissions(MainActivity.this
                                , pers, PermissionC.init_permis_code);
                        alertDialog.dismiss();
                    }
                });
    }

    //切换ViewPager的选择项
    public void selectViewPager(int pos){
        viewPager.setCurrentItem(pos);
    }



}
