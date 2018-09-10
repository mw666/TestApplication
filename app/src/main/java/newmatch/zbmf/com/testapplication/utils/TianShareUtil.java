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

    private static final String DEFAULT_STR="";
    private static SharedpreferencesUtil instance(){
        return SharedpreferencesUtil.getInstance();
    }
    public static void saveAddress(Address address){
        PLog.d(BuildConfig.TAG_D,"----  存储地址  ");
        ToastUtils.showSingleToast(MyApplication.getInstance(),"存储了城市："+address.getLocality());
        instance().putString(BuildConfig.PROVINCE,address.getAdminArea());//存储省
        instance().putString(BuildConfig.CITY,address.getLocality());//存储市
    }

    public static String getCity(){
        return instance().getString(BuildConfig.PROVINCE,DEFAULT_STR);
    }
    public static String getProvince(){
        return instance().getString(BuildConfig.PROVINCE,DEFAULT_STR);
    }


}
