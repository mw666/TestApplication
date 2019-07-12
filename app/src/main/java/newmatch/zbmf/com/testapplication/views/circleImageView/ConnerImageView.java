package newmatch.zbmf.com.testapplication.views.circleImageView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import newmatch.zbmf.com.testapplication.R;

/**
 * Create By Administrator
 * on 2019/7/12
 */
public class ConnerImageView extends android.support.v7.widget.AppCompatImageView {

    private int lbConner;
    private int ltConner;
    private int rtConner;
    private int rbConner;
    private int board;
    private int boardColor;
    private Paint paint;
    private PorterDuffXfermode xfermode;
    private RectF rectF;
    private Drawable drawable;
    private Path path;

    public ConnerImageView(Context context) {
        super(context);
    }

    public ConnerImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ConnerImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ConnerImageView);
        lbConner = ta.getDimensionPixelSize(R.styleable.ConnerImageView_lb_conner, 0);
        ltConner = ta.getDimensionPixelSize(R.styleable.ConnerImageView_lt_conner, 0);
        rbConner = ta.getDimensionPixelSize(R.styleable.ConnerImageView_rb_conner, 0);
        rtConner = ta.getDimensionPixelSize(R.styleable.ConnerImageView_rt_conner, 0);
        board = ta.getDimensionPixelSize(R.styleable.ConnerImageView_board, 0);
        boardColor = ta.getColor(R.styleable.ConnerImageView_board_color, 0);
        ta.recycle();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        path = new Path();
        if (boardColor != 0) {
            paint.setColor(boardColor);
        }
        //位图运算，显示两图交集部分，显示内容取决于下层
        xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
        rectF = new RectF();
        drawable = getDrawable();

    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.draw(canvas);
        int startAngle = 0;
        int sweetAngle = 0;
        if (rtConner != 0) {
            int rtAngle = rtConner / 2;
            startAngle = -(45 - rtAngle);
            sweetAngle = -(45 + rtAngle);
        } else if (ltConner != 0) {
            int ltAngle = ltConner / 2;
            startAngle = -(135 - ltAngle);
            sweetAngle = -(135 + ltAngle);
        } else if (lbConner != 0) {
            int lbAngle = lbConner / 2;
            startAngle = (135 - lbAngle);
            sweetAngle = (135 + lbAngle);
        } else if (rbConner != 0) {
            int rbAngle = rbConner / 2;
            startAngle = (45 - rbAngle);
            sweetAngle = (45 + rbAngle);
        }
        path.addArc(rectF, startAngle, sweetAngle);
//        canvas.drawArc(rectF, startAngle, sweetAngle, false, paint);
        path.addRect(rectF, Path.Direction.CCW);
        canvas.drawPath(path, paint);
        paint.setXfermode(null);
//        canvas.restoreToCount(layerId);

    }
}
