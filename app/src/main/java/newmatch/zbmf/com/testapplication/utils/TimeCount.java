package newmatch.zbmf.com.testapplication.utils;

import android.annotation.SuppressLint;
import android.os.CountDownTimer;
import android.widget.Button;

import newmatch.zbmf.com.testapplication.R;

/**
 * Created by pq
 * on 2018/11/13.
 * 计时器
 */

public class TimeCount extends CountDownTimer {
    private Button tv_yzm;
    public TimeCount(long millisInFuture, long countDownInterval,Button codeBtn) {
        super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        this.tv_yzm=codeBtn;
    }

    @Override
    public void onFinish() {//计时完毕时触发
        if (tv_yzm != null) {
            tv_yzm.setText("重新验证");
            tv_yzm.setEnabled(true);
            tv_yzm.setBackgroundResource(R.drawable.select_login_btn_bg);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onTick(long millisUntilFinished) {//计时过程显示
        if (tv_yzm != null) {
            tv_yzm.setEnabled(false);
            long l = millisUntilFinished / 1000;
            tv_yzm.setText(l + "S");
            tv_yzm.setBackgroundResource(R.drawable.gray_code_btn_bg);
        }
    }
}
