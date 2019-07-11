package newmatch.zbmf.com.testapplication.activitys;

import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.base.MyApplication;
import newmatch.zbmf.com.testapplication.callback.PermissionResultCallBack;
import newmatch.zbmf.com.testapplication.permissions.PermissionC;
import newmatch.zbmf.com.testapplication.services.LocationMonitor;
import newmatch.zbmf.com.testapplication.utils.PermissionUtils;
import newmatch.zbmf.com.testapplication.utils.StatusBarUtil;
import newmatch.zbmf.com.testapplication.utils.TianShareUtil;

/**
 * 欢迎页
 */
public class SplashActivity extends AppCompatActivity implements
        LocationMonitor.LocationCallBack {

    private boolean hasNeedPermissi = false;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //是否全屏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        //设置状态栏半透明
//        StatusBarUtil.setTranslucent(SplashActivity.this,
//                ContextCompat.getColor(SplashActivity.this,R.color.plum_2));

        StatusBarUtil.setStatuBar(SplashActivity.this, Color.TRANSPARENT);
//        if (Build.VERSION.SDK_INT>21){
//            View decorView = getWindow().getDecorView();
//            int options=View.SYSTEM_UI_FLAG_FULLSCREEN
//                    |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                    |View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    |View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//            decorView.setSystemUiVisibility(options);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//            getWindow().setNavigationBarColor(Color.TRANSPARENT);
//        }

        PermissionUtils.instance().requestPermission(this,
                getString(R.string.permission_name_location), PermissionC.LOCATION_PERMISSION,
                (PermissionResultCallBack) () -> {
                    skipNextAct();
                    LocationMonitor.getInstance(SplashActivity.this,
                            SplashActivity.this).getLocation();
                });


//        LocationMonitor.getInstance(SplashActivity.this).setLocationCallBack(this);
//        //检查权限
//        String[] initPermissions = PermissionC.INIT_PERMISSIONS;
//        //只要有一个权限没有同意就全部传到申请权限的页面进行权限申请
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            for (String initPermission : initPermissions) {
//                int selfPermission = ContextCompat.checkSelfPermission(SplashActivity.this,
//                        initPermission);
//                if (selfPermission == PackageManager.PERMISSION_DENIED) {
//                    hasNeedPermissi = true;
//                    Intent intent = new Intent();
//                    intent.setAction("android.testApplication.permissions.permissionActivity");
//                    intent.addCategory("android.intent.category.DEFAULT");
//                    intent.putExtra(PermissionC.init_permis, initPermissions);
//                    startActivityForResult(intent, 0xf12);
//                    break;
//                }
//            }
//            if (!hasNeedPermissi) {
//

//            }
//        } else {
//            //直接执行
//            LocationMonitor.getInstance(SplashActivity.this).getLocation();
//            skipNextAct();
//        }
        //该页面会展示广告,延时2.5秒跳转


    }

    private void skipNextAct() {
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this,
                    RegisterActivity.class));
            SplashActivity.this.finish();
        }, 2500);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private Address getCity(Location location) {
        Geocoder geocoder = new Geocoder(MyApplication.getInstance(), Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(),
                    location.getLongitude(), 1);
            if (addresses != null && addresses.size() > 0) {
                Address address = addresses.get(0);
                TianShareUtil.saveAddress(address);
                return address;
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void locationChanged(Location location) {
        if (location != null)
            getCity(location);
    }
}
