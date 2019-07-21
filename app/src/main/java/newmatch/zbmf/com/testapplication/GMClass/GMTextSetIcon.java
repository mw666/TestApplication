package newmatch.zbmf.com.testapplication.GMClass;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

/**
 * Created by **
 * on 2018/10/10.
 * 给textView设置Icon图标全部写在这个类中
 */

public class GMTextSetIcon {

    /**
     * 设置textView的左边的icon
     *
     * @param context      当前根视图的引用
     * @param drawableIcon 传进的图标
     * @param tv           textView
     */
    public static void setTvLeftIcon(Context context, Integer drawableIcon, int drawablePadding, TextView tv) {
        Drawable drawable = ActivityCompat.getDrawable(context, drawableIcon);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tv.setCompoundDrawables(drawable, null, null, null);
        tv.setCompoundDrawablePadding(drawablePadding);
    }

    /**
     * 设置textView的右边的icon
     *
     * @param context
     * @param drawableIcon
     * @param drawablePadding
     * @param tv
     */
    public static void setTvRightIcon(Context context, Integer drawableIcon, int drawablePadding, TextView tv) {
        Drawable drawable = ActivityCompat.getDrawable(context, drawableIcon);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tv.setCompoundDrawables(null, null, drawable, null);
        tv.setCompoundDrawablePadding(drawablePadding);
    }

    /**
     * 设置textView的上边的icon
     *
     * @param context
     * @param drawableIcon
     * @param drawablePadding
     * @param tv
     */
    public static void setTvTopIcon(Context context, Integer drawableIcon, int drawablePadding, TextView tv) {
        Drawable drawable = ActivityCompat.getDrawable(context, drawableIcon);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tv.setCompoundDrawables(null, drawable, null, null);
        tv.setCompoundDrawablePadding(drawablePadding);
    }

    /**
     * 设置textView的下边的icon
     *
     * @param context
     * @param drawableIcon
     * @param drawablePadding
     * @param tv
     */
    public static void setTvBottomIcon(Context context, Integer drawableIcon, int drawablePadding, TextView tv) {
        Drawable drawable = ActivityCompat.getDrawable(context, drawableIcon);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tv.setCompoundDrawables(null, null, null, drawable);
        tv.setCompoundDrawablePadding(drawablePadding);
    }

    /**
     * 给textView设置icon和颜色
     *
     * @param context
     * @param textView
     * @param drawableRes
     * @param colorRes
     */
    public static void setTvLeftIconColor(Context context, TextView textView, int drawableRes, int colorRes) {
        textView.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(
                context, drawableRes), null, null, null);
        textView.setTextColor(ContextCompat.getColor(context, colorRes));
    }
}
