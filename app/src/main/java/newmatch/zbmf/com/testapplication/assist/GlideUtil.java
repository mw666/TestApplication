package newmatch.zbmf.com.testapplication.assist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CenterInside;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.utils.UnitUtils;

/**
 * Created by **
 * on 2018/9/11.
 */

public class GlideUtil {

    /**
     * 加载网络图片
     *
     * @param mContext
     * @param path
     * @param imageview
     */
    @SuppressLint("CheckResult")
    public static void loadImage(Context mContext, Integer placeHolder,
                                 String path, ImageView imageview) {
        RequestOptions options = new RequestOptions();
        options.placeholder(placeHolder);
        options.error(R.drawable.place_holder_img);
        options.centerCrop();
        options.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(mContext).load(path).apply(options).into(imageview);
    }

    @SuppressLint("CheckResult")
    public static void loadImage(Context mContext, Integer placeHolder,
                                 Integer path, ImageView imageview) {
        RequestOptions options = new RequestOptions();
        options.placeholder(placeHolder);
        options.error(R.drawable.place_holder_img);
        options.centerCrop();
        options.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(mContext).load(path).apply(options).into(imageview);
    }

    /**
     * 加载带尺寸的图片
     *
     * @param mContext
     * @param path
     * @param Width
     * @param Height
     * @param imageview
     */
    @SuppressLint("CheckResult")
    public static void loadImageWithSize(Context mContext, Integer placeHolder, String path,
                                         int Width, int Height, ImageView imageview) {
        RequestOptions options = new RequestOptions();
        options.placeholder(placeHolder);
        options.override(Width, Height);
        options.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(mContext).load(path).apply(options).into(imageview);
    }

    /**
     * 加载本地图片
     *
     * @param mContext
     * @param path
     * @param imageview
     */
    @SuppressLint("CheckResult")
    public static void loadImageWithLocation(Context mContext, Integer path, ImageView imageview) {
        RequestOptions options = new RequestOptions();
        options.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(mContext).load(path).apply(options).into(imageview);
    }

    /**
     * 带圆形外框的图片加载
     *
     * @param mContext
     * @param path
     * @param imageview
     */
    @SuppressLint("CheckResult")
    public static void loadCircleImage(Context mContext, Integer placeHolder,
                                       Uri path, ImageView imageview) {
        RequestOptions options = new RequestOptions();
        options.centerCrop();
        options.placeholder(placeHolder);
        options.error(R.drawable.place_holder_img);
        options.transform(new GlideCircleTransform(mContext, 2,
                mContext.getResources().getColor(R.color.white)));
        options.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(mContext).load(path).apply(options).into(imageview);
    }

    @SuppressLint("CheckResult")
    public static void loadCircleImage(Context mContext, Integer placeHolder,
                                       Drawable drawable, ImageView imageview) {
        RequestOptions options = new RequestOptions();
        options.centerCrop();
        options.placeholder(placeHolder);
        options.error(R.drawable.place_holder_img);
        options.transform(new GlideCircleTransform(mContext, 2,
                mContext.getResources().getColor(R.color.white)));
        options.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(mContext).load(drawable).apply(options).into(imageview);
    }

    /**
     * 设置圆形ImageView
     *
     * @param mContext    上下文
     * @param placeHolder 占位图
     * @param path        资源
     * @param imageview   图片空间
     */
    @SuppressLint("CheckResult")
    public static void loadCircleImage(Context mContext, Integer placeHolder,
                                       Integer path, ImageView imageview) {
        RequestOptions options = new RequestOptions();
        options.centerCrop();
        options.placeholder(placeHolder);
        options.error(R.drawable.place_holder_img);
        options.transform(new GlideCircleTransform(mContext, 2,
                mContext.getResources().getColor(R.color.white)));
        options.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(mContext).load(path).apply(options).into(imageview);
    }

    @SuppressLint("CheckResult")
    public static void loadCornerdImg(Context mContext, Integer path, Integer placeHolder,
                                      ImageView imageView, boolean leftTop, boolean leftBottom,
                                      boolean rightTop, boolean rightBottom) {
        GlideCircle2Transformer glideCircle2Transformer = new GlideCircle2Transformer(mContext,
                -1, 20);
        glideCircle2Transformer.setExceptCorner(leftTop, rightTop, leftBottom, rightBottom);
        RequestOptions.centerCropTransform();
        RequestOptions requestOptions = RequestOptions.bitmapTransform(glideCircle2Transformer);
        requestOptions.centerCrop();
        requestOptions.placeholder(placeHolder);
        Glide.with(mContext).asBitmap().load(path).apply(requestOptions).into(imageView);
    }

    @SuppressLint("CheckResult")
    public static void loadCornerdImg(Context mContext, Uri path, Integer placeHolder,
                                      ImageView imageView, boolean leftTop, boolean leftBottom,
                                      boolean rightTop, boolean rightBottom) {
        CornerTransform transform = new CornerTransform(mContext, UnitUtils.dpToPx(mContext, 5));
        transform.setNeedCorner(leftTop, rightTop, leftBottom, rightBottom);
        RequestOptions requestOptions = new RequestOptions().placeholder(placeHolder).transform(new CenterCrop(),transform);

//        RequestOptions requestOptions = new RequestOptions().transform(new GlideRoundTransform(5));
//        requestOptions.error(R.drawable.place_holder_img);
//        requestOptions.centerCrop();
        Glide.with(mContext).asBitmap().load(path).apply(requestOptions).into(imageView);
    }

}
