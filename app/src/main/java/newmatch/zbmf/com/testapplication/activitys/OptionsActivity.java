package newmatch.zbmf.com.testapplication.activitys;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.base.BaseActivity;
import newmatch.zbmf.com.testapplication.utils.ActivityAnimUtils;
import newmatch.zbmf.com.testapplication.utils.ToastUtils;

public class OptionsActivity extends BaseActivity {

    private EditText optionsEt;
    private Button commitBtn;

    @Override
    protected Integer layoutId() {
        return R.layout.activity_options;
    }

    @Override
    protected void initView() {
        //全屏，内容延伸至状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        optionsEt = bindView(R.id.optionsEt);
        commitBtn = bindViewWithClick(R.id.commitBtn, true);

        setBtnBgState();

    }

    @Override
    protected void initData() {

    }

    @Override
    protected String initTitle() {
        return "意见反馈";
    }

    @Override
    protected Boolean showBackBtn() {
        return true;
    }

    @Override
    protected int topBarColor() {
        return 0;
    }

    @Override
    protected void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.commitBtn:
                ToastUtils.showSingleToast(this, "提交建议");

                break;
        }
    }

    private void setBtnBgState(){
        optionsEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void afterTextChanged(Editable editable) {
                if (optionsEt.getText().length() > 0) {
                    commitBtn.setTextColor(Color.WHITE);
                    commitBtn.setBackground(ContextCompat.getDrawable(OptionsActivity.this,
                            R.drawable.login_btn1_bg_pressed));
                } else {
                    commitBtn.setTextColor(ContextCompat.getColor(OptionsActivity.this, R.color.black));
                    commitBtn.setBackground(ContextCompat.getDrawable(OptionsActivity.this,
                            R.drawable.add_friend_et_bg));
                }
            }
        });
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
                    ActivityAnimUtils.instance().activityOut(OptionsActivity.this);
                }
                break;
        }
        return super.onTouchEvent(event);
    }

}
