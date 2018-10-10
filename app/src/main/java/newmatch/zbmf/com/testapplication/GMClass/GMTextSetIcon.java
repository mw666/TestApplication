package newmatch.zbmf.com.testapplication.GMClass;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.TextView;

/**
 * Created by **
 * on 2018/10/10.
 * 给textView设置Icon图标全部写在这个类中
 */

public class GMTextSetIcon {

    /**
     * 设置textView的左边的icon
     * @param context  当前根视图的引用
     * @param drawacleIcon  传进的图标
     * @param tv  textView
     */
    public static void setTvLeftIcon(Context context, Integer drawacleIcon, TextView tv){
        Drawable drawable = context.getResources().getDrawable(drawacleIcon);
        drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
        tv.setCompoundDrawables(drawable,null,null,null);
    }

}
