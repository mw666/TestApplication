package newmatch.zbmf.com.testapplication.views.circleImageView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

/**
 * Created By pq
 * on 2019/5/9
 * 圆角ImageView
 */
public class CustomImageView extends android.support.v7.widget.AppCompatImageView {

    private Path mPath;

    private RectF mRectF;

    private Paint mPaint;

    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //PS:一定不要再draw里面新建RectF
        // 重要的事情说三遍，会严重消耗内存
        mRectF = new RectF();
        mPath = new Path();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.TRANSPARENT);
    }

    @Override
    public void draw(Canvas canvas) {
        mPath.reset();
        //将mRectF设置为imageview本身的宽高
        mRectF.set(0, 0, getWidth(), getHeight());
        //将path设置rect值
        mPath.addRoundRect(mRectF, 30, 30, Path.Direction.CW);
        //切割画布，只留下自己需要的部分
        canvas.clipPath(mPath);
        //保留imageview本身的绘制图片
        super.draw(canvas);
        //画出描边
        canvas.drawRoundRect(mRectF, 30, 30, mPaint);
    }


}