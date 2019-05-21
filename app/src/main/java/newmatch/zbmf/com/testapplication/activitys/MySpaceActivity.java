package newmatch.zbmf.com.testapplication.activitys;

import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.base.BaseActivity;
import newmatch.zbmf.com.testapplication.utils.ActivityAnimUtils;

public class MySpaceActivity extends BaseActivity {

    @Override
    protected Integer layoutId() {
        return R.layout.activity_my_space;
    }

    @Override
    protected void initView() {
        //全屏，内容延伸至状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected String initTitle() {
        return null;
    }

    @Override
    protected Boolean showBackBtn() {
        return null;
    }

    @Override
    protected int topBarColor() {
        return 0;
    }

    @Override
    protected void onViewClick(View view) {

    }

    private int x = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x = (int) event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                int nowX = (int) event.getX();
                int i = nowX - x;
                if (i > 100) {
                    x = 0;
                    ActivityAnimUtils.instance().activityOut(MySpaceActivity.this);
                }
                break;
        }
        return super.onTouchEvent(event);
    }

}
