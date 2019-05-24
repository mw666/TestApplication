package newmatch.zbmf.com.testapplication.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.plattysoft.leonids.ParticleSystem;

import newmatch.zbmf.com.testapplication.R;

/**
 * Created by **
 * on 2018/10/8.
 * 通用的动画特效都写在这里
 *
 */

public class AnimatSpecialEffectUtil {

    /**
     * 同时释放两种星星特效
     */
    public static void releaseTwoStars(Activity activity,View view){
        //红星
        ParticleSystem ps = new ParticleSystem(activity, 100, R.drawable.star_pink, 800);
        ps.setScaleRange(0.7f, 1.3f);
        ps.setSpeedRange(0.1f, 0.25f);
        ps.setRotationSpeedRange(90, 180);
        ps.setFadeOut(200, new AccelerateInterpolator());
        ps.oneShot(view, 70);
        //白星
        ParticleSystem ps2 = new ParticleSystem(activity, 100, R.drawable.star_white, 800);
        ps2.setScaleRange(0.7f, 1.3f);
        ps2.setSpeedRange(0.1f, 0.25f);
        ps.setRotationSpeedRange(90, 180);
        ps2.setFadeOut(200, new AccelerateInterpolator());
        ps2.oneShot(view, 70);
    }

    /**
     * 给点赞icon设置点头动画
     *
     * View 动画
     */
    public static void setNodAnim(Context context,View view){
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.zan_anim);
        animation.setFillAfter(false);
        view.startAnimation(animation);
    }

    /**
     * 返回每个按钮应该出现的角度(弧度单位)
     * @param index
     * @return double 角度(弧度单位)
     */
    public static double getAngle(int total,int index){
        return Math.toRadians(90/(total-1)*index+90);
    }

}
