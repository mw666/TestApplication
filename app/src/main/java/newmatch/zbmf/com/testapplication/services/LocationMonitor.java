package newmatch.zbmf.com.testapplication.services;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.callback.PermissionResultCallBack;
import newmatch.zbmf.com.testapplication.permissions.PermissionC;
import newmatch.zbmf.com.testapplication.utils.PermissionUtils;
import newmatch.zbmf.com.testapplication.utils.ToastUtils;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created By pq
 * on 2019/4/26
 */
public class LocationMonitor {

    private static LocationMonitor instance = null;
    private Activity activity;
    private Context mContext;
    private Location gpsLocation = null;
    private Location netLocation = null;
    private LocationManager myLocationManager;
    private LocationCallBack locationCallBack;

    public void setLocationCallBack(LocationCallBack locationCallBack) {
        this.locationCallBack = locationCallBack;
    }

    private LocationMonitor(Context context, Activity activity) {
        this.mContext = context;
        this.activity = activity;
    }

    public static LocationMonitor getInstance(Context context, Activity activity) {
        if (instance == null) {
            synchronized (LocationMonitor.class) {
                if (instance == null) {
                    instance = new LocationMonitor(context, activity);
                }
            }
        }
        return instance;
    }

    public Location getLocation() {
        //获取位置管理服务
        myLocationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
        //查找服务信息
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE); //定位精度: 最高
        criteria.setAltitudeRequired(false); //海拔信息：不需要
        criteria.setBearingRequired(false); //方位信息: 不需要
        criteria.setCostAllowed(true);  //是否允许付费
        criteria.setPowerRequirement(Criteria.POWER_LOW); //耗电量: 低功耗
        if (!netWorkIsOpen() && !gpsIsOpen()) {
            //这里最好改成弹出对话框，引导用户跳转到设置界面
            ToastUtils.showSingleToast(mContext, "请开启移动网络或GPS");
            return null;
        }
        //检查权限---->Android版本大于5.0的时候，检查动态权限的申请
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

    //权限肯定已经同意，如果用户没有同意权限是不允许用户进入App的
    private void resum() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(mContext, PermissionC.LOCATION_PERMISSION[0])
                    != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(mContext
                    , PermissionC.LOCATION_PERMISSION[1]) != PackageManager.PERMISSION_GRANTED) {
                //没有同意授予权限
                PermissionUtils.instance().requestPermission(activity,
                        mContext.getString(R.string.permission_name_location), PermissionC.LOCATION_PERMISSION,
                        (PermissionResultCallBack) () -> {
                            if (netWorkIsOpen()) {
                                myLocationManager.requestLocationUpdates("network", 2000, 60, locationListener);
                                netLocation = myLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                                locationListener.onLocationChanged(netLocation);
                            }
                            if (gpsIsOpen()) {
                                myLocationManager.requestLocationUpdates("gps", 2000, 60, locationListener);
                                gpsLocation = myLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                                locationListener.onLocationChanged(gpsLocation);
                            }
                        });
            } else {
                //用户同意了定位的所有权限--->执行获取定位的操作
                if (netWorkIsOpen()) {
                    myLocationManager.requestLocationUpdates("network", 2000, 60, locationListener);
                    netLocation = myLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    locationListener.onLocationChanged(netLocation);
                }
                if (gpsIsOpen()) {
                    myLocationManager.requestLocationUpdates("gps", 2000, 60, locationListener);
                    gpsLocation = myLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    locationListener.onLocationChanged(gpsLocation);
                }
            }
        } else {
            //用户同意了定位的所有权限--->执行获取定位的操作
            if (netWorkIsOpen()) {
                myLocationManager.requestLocationUpdates("network", 2000, 60, locationListener);
                netLocation = myLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                locationListener.onLocationChanged(netLocation);
            }
            if (gpsIsOpen()) {
                myLocationManager.requestLocationUpdates("gps", 2000, 60, locationListener);
                gpsLocation = myLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                locationListener.onLocationChanged(gpsLocation);
            }
        }
    }


    //检测GPS是否开启
    public boolean gpsIsOpen() {
        boolean isOpen = true;
        //没有开启GPS
        if (!myLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            isOpen = false;
        }
        return isOpen;
    }

    //检测移动数据网络是否开启
    public boolean netWorkIsOpen() {
        boolean netIsOpen = true;
        //没有开启网络定位
        if (!myLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            netIsOpen = false;
        }
        return netIsOpen;
    }

    //监听GPS位置改变后得到新的经纬度
    private android.location.LocationListener locationListener = new android.location.LocationListener() {
        public void onLocationChanged(final Location location) {
            if (locationCallBack != null) {
                locationCallBack.locationChanged(location);
            }
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

    public interface LocationCallBack {
        void locationChanged(Location location);
    }


}
