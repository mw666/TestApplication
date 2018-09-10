package newmatch.zbmf.com.testapplication.listeners;

import android.view.View;

import java.util.Calendar;

/**
 * Created by **
 * on 2017/9/30.
 * <p>防止连续点击两次事件触发</p>
 */

public abstract class OnceClickListener implements View.OnClickListener {

    private static final int MIN_CLICK_DELAY_TIME = 800;
    private long lastClickTime = 0;
    public abstract void onNoDoubleClick(View v);

    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            onNoDoubleClick(v);
        }
    }
}