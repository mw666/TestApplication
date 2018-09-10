package newmatch.zbmf.com.testapplication.base;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.listeners.OnceClickListener;
import newmatch.zbmf.com.testapplication.utils.MyActivityManager;
import newmatch.zbmf.com.testapplication.utils.StatusBarUtil;
import newmatch.zbmf.com.testapplication.utils.ToastUtils;

/**
 * Created by **
 * on 2018/9/6.
 */

public abstract class BaseActivity extends RxAppCompatActivity /*implements ITestContent.View*/ {

    private Bundle savedInstanceState;

    protected abstract Integer layoutId();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract String initTitle();

    protected abstract Boolean showBackBtn();

    protected abstract int topBarColor();

    //点击事件
    protected abstract void onViewClick(View view);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        setContentView(layoutId());
        String title = initTitle();
        int topBarColor = topBarColor();
        Log.d("==TAG", "--   颜色纸 回传: " + topBarColor);
        Boolean showBackBtn = showBackBtn();
        initToolBar(title, topBarColor, showBackBtn);
        initView();
//        statusBar();

        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    //activity渲染完成后会调用的方法
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

    }

    //获取保存数据的bundle
    public Bundle getSaveBundle() {
        return savedInstanceState;
    }

    /**
     * 初始化 Toolbar
     */
    protected void initToolBar(String title, Integer topBarColor, Boolean homeAsUpEnabled) {
        Toolbar toolBar = bindView(R.id.toolbar);
        TextView toolBarTitle = bindView(R.id.toolbar_title);
        if (toolBar != null) {
            //int deepPurple = MyApplication.getInstance().getResources().getColor(R.color.deepPurple);
            Log.d("==TAG", "--  颜色值:" + topBarColor);
            //设置状态栏颜色
            StatusBarUtil.setColor(this, topBarColor, 36);
            toolBar.setTitle("");
            toolBarTitle.setText(title);
            setSupportActionBar(toolBar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            if (homeAsUpEnabled) {
                toolBar.setNavigationIcon(R.drawable.back_icon);
                toolBar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //ToastUtils.showSingleToast(BaseActivity.this, "点击了");
                        onBackPressed();
                    }
                });
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // Fragment 逐个出栈
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            super.onBackPressed();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastUtils.showSquareTvToast(this, "再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                MyActivityManager.getMyActivityManager().removeAllAct();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //状态栏
    private void statusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
    }

    //给控件绑定点击事件
    @SuppressWarnings("unchecked")
    public <T extends View> T bindViewWithClick(int resID, boolean isBindClick) {
        View view = bindView(resID);
        if (isBindClick) {
            view.setOnClickListener(clickListener);
        }
        return (T) view;
    }

    //获取布局控件
    @SuppressWarnings("unchecked")
    protected <T extends View> T bindView(int resourcesId) {
        try {
            return (T) findViewById(resourcesId);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected OnceClickListener clickListener = new OnceClickListener() {
        @Override
        public void onNoDoubleClick(View v) {
            onViewClick(v);
        }
    };


}