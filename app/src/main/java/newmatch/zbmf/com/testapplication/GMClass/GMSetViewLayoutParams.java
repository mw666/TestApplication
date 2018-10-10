package newmatch.zbmf.com.testapplication.GMClass;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by **
 * on 2018/10/10.
 * 在这里根据屏幕的尺寸集中设置View的LayoutParams
 */

public class GMSetViewLayoutParams {

    private static GMSetViewLayoutParams sGMSetViewLayoutParams;
    private static final int MARGIN = 30;
    private int sScreenWidth;
    private int sScreenHeight;

    public static GMSetViewLayoutParams instance() {
        if (sGMSetViewLayoutParams == null) {
            synchronized (GMRvSetLayoutManager.class) {
                if (sGMSetViewLayoutParams == null) {
                    sGMSetViewLayoutParams = new GMSetViewLayoutParams();
                }
            }
        }
        return sGMSetViewLayoutParams;
    }

    //获取当前屏幕的宽度,单位px
    public GMSetViewLayoutParams gainScreenW(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        sScreenWidth = dm.widthPixels;
        return this;
    }

    //获取当前屏幕的高度,单位px
    public GMSetViewLayoutParams gainScreenH(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        sScreenHeight = dm.heightPixels;
        return this;
    }

    /**
     * 根据屏幕，为view设置宽高的比例
     *
     * @param context
     * @param imageView
     * @param count     根据屏幕分成几等分？
     */
    public void setImageviewLp(Context context, ImageView imageView, Integer count) {
        ViewGroup.LayoutParams lp = imageView.getLayoutParams();
        lp.height = lp.width = (sScreenWidth - MARGIN) / count;
        imageView.setLayoutParams(lp);
    }

}
