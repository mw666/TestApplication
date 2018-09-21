package newmatch.zbmf.com.testapplication.custom_view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import newmatch.zbmf.com.testapplication.R;

/**
 * Created by **
 * on 2018/9/20.
 */

public class CircleImgView extends android.support.v7.widget.AppCompatImageView {


    private Paint mPaint;
    private int mCircleImgViewSrc;
    private Canvas mCanvas;
    private Bitmap mImgViewBT;
    private Bitmap mBitmapMask;

    public CircleImgView(Context context) {
        super(context);
    }

    public CircleImgView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CircleImgView);
        mCircleImgViewSrc = ta.getResourceId(R.styleable.CircleImgView_circle_img_view_src, -1);
        ta.recycle();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mImgViewBT = BitmapFactory.decodeResource(getResources(), mCircleImgViewSrc);
        int minL = Math.min(mImgViewBT.getWidth(), mImgViewBT.getHeight());
        mBitmapMask = Bitmap.createBitmap(minL, minL, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmapMask);
        int r = minL / 2-1;
        mCanvas.drawCircle(r, r, r, mPaint);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(2);
        mCanvas.drawCircle(r+1, r+1, r+1, mPaint);
    }

    public CircleImgView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = mBitmapMask.getWidth();
        int layer = canvas.saveLayer(0, 0, width, width, null, Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(mImgViewBT, 0, 0, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawBitmap(mBitmapMask, 0, 0, mPaint);
        canvas.restoreToCount(layer);

    }
}
