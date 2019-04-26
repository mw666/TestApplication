package newmatch.zbmf.com.testapplication.utils;

import android.location.Address;

import newmatch.zbmf.com.testapplication.base.MyApplication;
import newmatch.zbmf.com.testapplication.component.BuildConfig;
import newmatch.zbmf.com.testapplication.component.PLog;

/**
 * Created by **
 * on 2018/9/7.
 */

public class TianShareUtil {

    public static final String DEFAULT_STR="";
    private static SharedpreferencesUtil instance(){
        return SharedpreferencesUtil.getInstance();
    }
    public static void saveAddress(Address address){
        PLog.d(BuildConfig.TAG_D,"----  存储地址  ");
        ToastUtils.showSingleToast(MyApplication.getInstance(),"存储了城市："+address.getLocality());
        instance().putString(BuildConfig.PROVINCE,address.getAdminArea());//存储省
        instance().putString(BuildConfig.CITY,address.getLocality());//存储市
        instance().putString(BuildConfig.SUB_LOCALITY,address.getSubLocality());//存储区/镇
        instance().putString(BuildConfig.FEATURE_NAME,address.getFeatureName());//存储街道详细地址
    }

    public static String getCity(){
        return instance().getString(BuildConfig.CITY,DEFAULT_STR);
    }
    public static String getProvince(){
        return instance().getString(BuildConfig.PROVINCE,DEFAULT_STR);
    }

    //存储正在查看的城市
    public static void setSeeCity(String seeCity){
        instance().putString(BuildConfig.GET_CITY,seeCity);
    }
    //获取正在查看的城市
    public static String getSeeCity(){
        return instance().getString(BuildConfig.GET_CITY,DEFAULT_STR);
    }


}
