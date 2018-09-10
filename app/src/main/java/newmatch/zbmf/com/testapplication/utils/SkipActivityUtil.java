package newmatch.zbmf.com.testapplication.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by **
 * on 2018/9/10.
 */

public class SkipActivityUtil {

    //跳转activity
    public static void skipActivity(Activity activity,Class clazz){
        activity.startActivity(new Intent(activity,clazz));
    }
    //携带数据跳转
    public static void skipDataActivity(Activity activity, Class clazz, Bundle bundle){
        Intent intent = new Intent(activity,clazz);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }




}
