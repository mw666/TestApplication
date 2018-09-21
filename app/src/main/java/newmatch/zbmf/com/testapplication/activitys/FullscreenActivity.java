package newmatch.zbmf.com.testapplication.activitys;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.base.MyApplication;
import newmatch.zbmf.com.testapplication.component.PLog;
import newmatch.zbmf.com.testapplication.dialogs.MyDialogUtil;
import newmatch.zbmf.com.testapplication.listeners.DialogCallBack;
import newmatch.zbmf.com.testapplication.permissions.PermissionC;
import newmatch.zbmf.com.testapplication.utils.TianShareUtil;
import newmatch.zbmf.com.testapplication.utils.ToastUtils;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends AppCompatActivity {

    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };

    private Handler mHandler = new Handler();
    private LocationManager myLocationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);
        //是否全屏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        Location location = getLocation();
        getCity(location);
        mHandler.postDelayed(() -> startActivity(
                new Intent(FullscreenActivity.this,
                        RegisterActivity.class)), 2000);
        FullscreenActivity.this.finish();
    }

    Location gpsLocation = null;
    Location netLocation = null;

    private Location getLocation() {
        //获取位置管理服务
        myLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        //查找服务信息
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE); //定位精度: 最高
        criteria.setAltitudeRequired(false); //海拔信息：不需要
        criteria.setBearingRequired(false); //方位信息: 不需要
        criteria.setCostAllowed(true);  //是否允许付费
        criteria.setPowerRequirement(Criteria.POWER_LOW); //耗电量: 低功耗
        if (!netWorkIsOpen() && !gpsIsOpen()) {
            ToastUtils.showSingleToast(this, "请开启移动网络或GPS");
            return null;
        }
        //检查权限---->Android版本大于6.0的时候，检查动态权限的申请
        resum();
        if (gpsLocation == null && netLocation == null) {
            return null;
        }
        if (gpsLocation != null && netLocation != null) {
            if (gpsLocation.getTime() < netLocation.getTime()) {
                gpsLocation = null;
                return netLocation;
            } else {
                netLocation = null;
                return gpsLocation;
            }
        }
        if (gpsLocation == null) {
            return netLocation;
        } else {
            return gpsLocation;
        }
    }

    private void resum() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, PermissionC.LOCATION_PERMISSION[0])
                    != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this
                    , PermissionC.LOCATION_PERMISSION[1]) != PackageManager.PERMISSION_GRANTED) {
                PLog.LogD("---   执行位置信息的权限   ---");
                //没有同意授予权限
                ActivityCompat.requestPermissions(this, PermissionC.LOCATION_PERMISSION, PermissionC.LOCATION_CODE);
            } else {
                if (netWorkIsOpen()) {
                    myLocationManager.requestLocationUpdates("network", 2000, 5, locationListener);
                    netLocation = myLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }
                if (gpsIsOpen()) {
                    myLocationManager.requestLocationUpdates("gps", 2000, 5, locationListener);
                    gpsLocation = myLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }
            }
        } else {
            //用户同意了定位的所有权限--->执行获取定位的操作
            if (netWorkIsOpen()) {
                myLocationManager.requestLocationUpdates("network", 2000, 5, locationListener);
                netLocation = myLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
            if (gpsIsOpen()) {
                myLocationManager.requestLocationUpdates("gps", 2000, 5, locationListener);
                gpsLocation = myLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PermissionC.LOCATION_CODE:
                for (int i = 0; i < grantResults.length; i++) {
                    //当用户全部同意定位权限，才执行定位代码
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        //想用户展示给权限的作用
                        MyDialogUtil.getInstance().setDialogCallBack(new DialogCallBack() {
                            @Override
                            public void positiveClick(DialogInterface dialog) {
                                //确定，表示用户同意权限-->重新申请权限
                                //确定，重新申请权限
                                ActivityCompat.requestPermissions(FullscreenActivity.this,
                                        PermissionC.LOCATION_PERMISSION
                                        , PermissionC.LOCATION_CODE);
                                dialog.dismiss();
                            }

                            @Override
                            public void negativeClick(DialogInterface dialog) {
                                //表示用户拒绝位置权限-->不作处理
                                dialog.dismiss();
                            }
                        }).showPermissionDialog(FullscreenActivity.this, getString(R.string.permission_name_location));
                    }
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED && i == grantResults.length - 1) {
                        //这样写强制用户同意位置信息权限,体验有点不好
                        resum();
                    }
                }
                break;
        }
    }

    private boolean gpsIsOpen() {
        boolean isOpen = true;
        if (!myLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {//没有开启GPS
            isOpen = false;
        }
        return isOpen;
    }

    private boolean netWorkIsOpen() {
        boolean netIsOpen = true;
        if (!myLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {//没有开启网络定位
            netIsOpen = false;
        }
        return netIsOpen;
    }

    private Location mLocation;
    //监听GPS位置改变后得到新的经纬度
    private LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(final Location location) {
            mLocation = location;
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    };

    private Address getCity(Location location) {
        Geocoder geocoder = new Geocoder(MyApplication.getInstance(), Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(),
                    location.getLongitude(), 1);
            Address address = addresses.get(0);
            TianShareUtil.saveAddress(address);
            return address;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
