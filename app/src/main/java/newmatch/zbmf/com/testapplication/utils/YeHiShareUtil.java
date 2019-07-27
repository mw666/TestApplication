package newmatch.zbmf.com.testapplication.utils;

import android.location.Address;
import android.text.TextUtils;

import java.util.Locale;

import newmatch.zbmf.com.testapplication.component.BuildConfig;

/**
 * Created by **
 * on 2018/9/7.
 */

public class YeHiShareUtil {

    public static final String DEFAULT_STR = "";

    private static SharedpreferencesUtil instance() {
        return SharedpreferencesUtil.getInstance();
    }

    public static void saveAddress(Address address) {
        Locale locale = address.getLocale();
        putCountry(address.getCountryName());//存储国家名称
        putCountryCode(address.getCountryCode());//存储国家代码
        putProvince(address.getAdminArea());//存储省
        String subAdminArea = address.getSubAdminArea();
        if (TextUtils.isEmpty(subAdminArea)) {
            subAdminArea = address.getLocality();
        }
        putCity(subAdminArea);//存储市
        String locality = address.getLocality();
        if (TextUtils.isEmpty(locality)) {
            locality = address.getSubLocality();
        }
        putLocality(locality);//存储区/县级市
        putAreaPhone(address.getPhone());//存储所在位置的电话区号
        putPostalCode(address.getPostalCode());//存储所在位置的邮政编码
        String subLocality = address.getSubLocality();
        putSubLocality(subLocality);
        putThoroughfare(address.getThoroughfare());//存储所在主道路
        putSubThoroughfare(address.getSubThoroughfare());//存储所在副道路
        putFeatureName(address.getFeatureName());//存储街道详细地址
        putLongitude((float) address.getLongitude());//存储所在位置经度
        putLatitude((float) address.getLatitude());//存储所在位置纬度
    }

    //获取国家
    public static String getCountry() {
        return instance().getString(BuildConfig.COUNTRY_NAME, DEFAULT_STR);
    }

    //存储国家
    public static void putCountry(String country) {
        instance().putString(BuildConfig.COUNTRY_NAME, country);//存储国家名称
    }

    //获取国家代码
    public static String getCountryCode() {
        return instance().getString(BuildConfig.COUNTRY_CODE, DEFAULT_STR);
    }

    //存储国家代码
    public static void putCountryCode(String countryCode) {
        instance().putString(BuildConfig.COUNTRY_CODE, countryCode);
    }

    //获取国家代号
    public static String getCountryLocale() {
        return instance().getString(BuildConfig.LOCALE, DEFAULT_STR);
    }

    //存储国家代号
    public static void putCountryLocale(String countryCode) {
        instance().putString(BuildConfig.LOCALE, countryCode);
    }

    //获取省
    public static String getProvince() {
        return instance().getString(BuildConfig.PROVINCE, DEFAULT_STR);
    }

    //存储省份
    public static void putProvince(String province) {
        instance().putString(BuildConfig.PROVINCE, province);
    }

    //获取地级市
    public static String getCity() {
        return instance().getString(BuildConfig.SUB_ADMIN_AREA, DEFAULT_STR);
    }

    //存储地级市
    public static void putCity(String subArea) {
        instance().putString(BuildConfig.SUB_ADMIN_AREA, subArea);
    }

    //获取县级市或区
    public static String getLocality() {
        return instance().getString(BuildConfig.LOCALITY, DEFAULT_STR);
    }

    //存储县级市或区
    public static void putLocality(String locality) {
        instance().putString(BuildConfig.LOCALITY, locality);
    }

    //获取所在位置的电话区号
    public static String getAreaPhone() {
        return instance().getString(BuildConfig.AREA_PHONE, DEFAULT_STR);
    }

    //存储所在地区的电话区号
    public static void putAreaPhone(String areaPhone) {
        instance().putString(BuildConfig.AREA_PHONE, areaPhone);
    }

    //获取所在地区的邮政编码
    public static String getPostalCode() {
        return instance().getString(BuildConfig.POSTAL_CODE, DEFAULT_STR);
    }

    //存储所在地区的邮政编码
    public static void putPostalCode(String postalCode) {
        instance().putString(BuildConfig.POSTAL_CODE, postalCode);
    }

    //
    public static String getSubLocality() {
        return instance().getString(BuildConfig.SUB_LOCALITY, DEFAULT_STR);
    }

    public static void putSubLocality(String subLocality) {
        instance().putString(BuildConfig.SUB_LOCALITY, subLocality);
    }

    //获取所在主道路
    public static String getThoroughfare() {
        return instance().getString(BuildConfig.THOROUGHFARE, DEFAULT_STR);
    }

    //存储所在的主要道路
    public static void putThoroughfare(String thoroughfare) {
        instance().putString(BuildConfig.THOROUGHFARE, thoroughfare);
    }

    //获取所在的副道路
    public static String getSubThoroughfare() {
        return instance().getString(BuildConfig.SUB_THOROUGHFARE, DEFAULT_STR);
    }

    //存储所在的副道路
    public static void putSubThoroughfare(String subThoroughfare) {
        instance().putString(BuildConfig.SUB_THOROUGHFARE, subThoroughfare);
    }

    //获取所在街区地址
    public static String getFeatureName() {
        return instance().getString(BuildConfig.SUB_LOCALITY, DEFAULT_STR);
    }

    //存储所在街区地址
    public static void putFeatureName(String featureName) {
        instance().putString(BuildConfig.SUB_LOCALITY, featureName);
    }

    //获取所在位置的经度
    public static float getLongitude() {
        return instance().getFloat(BuildConfig.LONGITUDE, 0);
    }

    //存储所在的位置经度
    public static void putLongitude(float longitude) {
        instance().putFloat(BuildConfig.LONGITUDE, longitude);
    }

    //获取所在位置的纬度
    public static float getLatitude() {
        return instance().getFloat(BuildConfig.LATITUDE, 0);
    }

    //存储所在的位置纬度
    public static void putLatitude(float latutude) {
        instance().putFloat(BuildConfig.LATITUDE, latutude);
    }

//    //存储正在查看的城市
//    public static void setSeeCity(String seeCity) {
//        instance().putString(BuildConfig.GET_CITY, seeCity);
//    }
//
//    //获取正在查看的城市
//    public static String getSeeCity() {
//        return instance().getString(BuildConfig.GET_CITY, DEFAULT_STR);
//    }


}
