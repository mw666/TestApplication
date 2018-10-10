package newmatch.zbmf.com.testapplication.GMClass;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.utils.AnimatSpecialEffectUtil;

/**
 * Created by **
 * on 2018/10/8.
 * 这里放置的是点赞的通用方法
 */

public class LikeGMClass {

    /**
     * 有接口时将点赞的判断换回来
     */


    //当前没有调接口，使用isLike标记
    private static Boolean isLike = false;
    public static void clickLike(Activity activity, ImageView view) {
        if (!isLike) {//点赞
            //这里处理点赞和取消点赞
            AnimatSpecialEffectUtil.releaseTwoStars(activity, view);
            isLike = true;
            //更换点赞成点亮的图标
            view.setImageResource(R.drawable.dian_zan_grey_icon);
            //设置动画
            AnimatSpecialEffectUtil.setNodAnim(activity, view);

            // TODO: 2018/10/8 点赞成功后  moodTv +1 ，取消点赞后 moodTv -1


        } else {//取消点赞
            isLike = false;
            //更换点赞成点亮的图标
            view.setImageResource(R.drawable.dian_zan_purple_icon);
            //设置动画
            AnimatSpecialEffectUtil.setNodAnim(activity, view);
        }
    }

    //当前没有调接口，使用isDianLike标记
    private static Boolean isDianLike=false;
    public static void clickTvLike(Activity activity, Context context,TextView view) {
        if (!isDianLike) {//点赞
            //这里处理点赞和取消点赞
            AnimatSpecialEffectUtil.releaseTwoStars(activity, view);
            isLike = true;
            //更换点赞成点亮的图标
            GMTextSetIcon.setTvLeftIcon(context,R.drawable.dian_zan_grey_icon,view);
            //设置动画
            AnimatSpecialEffectUtil.setNodAnim(activity, view);

            // TODO: 2018/10/8 点赞成功后  moodTv +1 ，取消点赞后 moodTv -1


        } else {//取消点赞
            isDianLike = false;
            //更换点赞成点亮的图标
            GMTextSetIcon.setTvLeftIcon(context,R.drawable.dian_zan_purple_icon,view);
            //设置动画
            AnimatSpecialEffectUtil.setNodAnim(activity, view);
        }
    }

}
