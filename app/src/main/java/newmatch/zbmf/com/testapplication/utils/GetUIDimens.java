package newmatch.zbmf.com.testapplication.utils;

import android.content.Context;
import android.util.Log;

/**
 * Created by **
 * on 2018/9/6.
 */

public class GetUIDimens {

    public static int getStatusH(Context context) {
        int result = 0;
        //根据resource的Id 获取
        int resultId = context.getResources().getIdentifier("status_bar_height",
                "dimen", "android");
        if (resultId > 0) {
            result = context.getResources().getDimensionPixelSize(resultId);
        }
        Log.d("==TAG", "   方法二获取的状态栏的高度：h " + result);
        return result;
    }
}
