package newmatch.zbmf.com.testapplication.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.DrawerLayout;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created By pq
 * on 2019/5/27
 * drawable,bitmap,byte之间的相互转换
 */
public class DrawableByteBitmapExUtils {

    /**
     * bitmap转drawable
     *
     * @param context
     * @param bitmap
     * @return
     */
    public static Drawable bitmap2Drawable(Context context, Bitmap bitmap) {
        return new BitmapDrawable(context.getResources(), bitmap);
    }

    /**
     * @param drawable
     * @return
     */
    public static Bitmap drawable2Bitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), drawable.getOpacity() != PixelFormat.OPAQUE
                        ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * bitmap转byte数据
     *
     * @param bitmap
     * @return
     */
    public static byte[] bitmap2Byte(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //将bitmap100%高质量压缩到baos
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        //设置自由可回收
        bitmap.recycle();
        byte[] result = baos.toByteArray();
        try {
            if (baos != null) baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Drawable转byte数据
     *
     * @param drawable
     * @return
     */
    public static byte[] drawable2Byte(Drawable drawable) {
        Bitmap bitmap = drawable2Bitmap(drawable);
        byte[] bytes = bitmap2Byte(bitmap);
        return bytes;
    }

    /**
     * byte数据转Drawable
     *
     * @param data
     * @return
     */
    public static Drawable byte2Drawable(byte[] data) {
        return Drawable.createFromStream(new ByteArrayInputStream(data), null);
    }

    /**
     * byte数据转Bitmap
     *
     * @param data
     * @return
     */
    public static Bitmap byte2Bitmap(byte[] data) {
        return BitmapFactory.decodeByteArray(data, 0, data.length);
    }

    /**
     * 文件资源转bitmap
     *
     * @param context
     * @param resID
     * @return
     */
    public static Bitmap resource2Bitmap(Context context, int resID) {
        return BitmapFactory.decodeResource(context.getResources(), resID);
    }

}
