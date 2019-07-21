package newmatch.zbmf.com.testapplication.GMClass;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import newmatch.zbmf.com.testapplication.utils.GetUIDimens;
import newmatch.zbmf.com.testapplication.utils.UnitUtils;

/**
 * Created by **
 * on 2018/10/10.
 * 在这里根据屏幕的尺寸集中设置View的LayoutParams
 */

public class GMSetViewLayoutParams {

    private static GMSetViewLayoutParams sGMSetViewLayoutParams;
    private static final int MARGIN = 36;

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
//    public GMSetViewLayoutParams gainScreenW(Context context) {
//        DisplayMetrics dm = context.getResources().getDisplayMetrics();
//        sScreenWidth = dm.widthPixels;
//        return this;
//    }
//
//    //获取当前屏幕的高度,单位px
//    public GMSetViewLayoutParams gainScreenH(Context context) {
//        DisplayMetrics dm = context.getResources().getDisplayMetrics();
//        int sScreenHeight = dm.heightPixels;
//        return this;
//    }

    /**
     * 根据屏幕，为view设置宽高的比例
     *
     * @param context
     * @param imageView
     * @param count     根据屏幕分成几等分？
     */
    public void setImageviewLp(Context context, ImageView imageView, float count) {
        ViewGroup.LayoutParams lp = imageView.getLayoutParams();
        if (count == 1) {
            lp.height = lp.width = (GetUIDimens.getWindowW(context) - MARGIN) * 2 / 3;
        } else{
            lp.width = (int) ((GetUIDimens.getWindowW(context) - MARGIN) / count);
            lp.height = (int) ((GetUIDimens.getWindowW(context) - MARGIN
                    - UnitUtils.dpToPx(context, 46)) / count);
        }
        imageView.setLayoutParams(lp);
    }

}
