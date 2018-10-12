package newmatch.zbmf.com.testapplication.utils;

import android.content.Context;
import android.util.Log;
import android.view.View;

/**
 * Created by **
 * on 2018/9/6.
 * <p>
 * 测量View的大小的所有方法都放在这个类里边
 */

public class GetUIDimens {

    private int sMeasuredHeight;

    //获取状态栏的高度
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

    public static void fitsSystemWindows(boolean isTranslucentStatus, View view) {
        if (isTranslucentStatus) {
            view.getLayoutParams().height = calcStatusBarHeight(view.getContext());
        }
    }

    //获取状态栏的高度
    public static int calcStatusBarHeight(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }




    public static int dpToPx(Context context, float dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5);
    }

    public static int spToPx(Context context, float sp) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * scaledDensity + 0.5);
    }

    public static int pxToDp(Context context, int px) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (px / density + 0.5);
    }

    public static int pxToSp(Context context, int px) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (px / scaledDensity + 0.5);
    }


}
