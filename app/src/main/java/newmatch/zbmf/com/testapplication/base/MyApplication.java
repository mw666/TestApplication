package newmatch.zbmf.com.testapplication.base;

import android.app.Application;

import com.mob.MobSDK;

import newmatch.zbmf.com.testapplication.utils.SharedpreferencesUtil;

/**
 * Created by **
 * on 2018/9/6.
 */

public class MyApplication extends Application {

    private static MyApplication instance;
    private static String sCacheDir;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        SharedpreferencesUtil.getInstance().initSharedUtil(this);
        //初始化Mob短信的配置
        MobSDK.init(this);

       //集成友盟统计,分享

        /*
         * 如果存在SD卡则将缓存写入SD卡,否则写入手机内存
         */
        if (getApplicationContext().getExternalCacheDir() != null && ExistSDCard()) {
            sCacheDir = getApplicationContext().getExternalCacheDir().toString();
        } else {
            sCacheDir = getApplicationContext().getCacheDir().toString();
        }
    }

    public static MyApplication getInstance() {
        return instance;
    }

    private boolean ExistSDCard() {
        return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }

    public static String getAppCacheDir() {
        return sCacheDir;
    }
}
