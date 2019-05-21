package newmatch.zbmf.com.testapplication.activitys;

import android.annotation.SuppressLint;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.adapters.MenuAdapter;
import newmatch.zbmf.com.testapplication.base.BaseActivity;
import newmatch.zbmf.com.testapplication.base.MyApplication;
import newmatch.zbmf.com.testapplication.utils.ActivityAnimUtils;
import newmatch.zbmf.com.testapplication.utils.MyActivityManager;
import newmatch.zbmf.com.testapplication.views.RotationPageTransformer;

/**
 * VIP会员开通页面
 */
public class VIPActivity extends BaseActivity {

    private int[] imgs = {R.mipmap.green_ge_bg, R.mipmap.pink_flower_bg};
    private int[] pay_imgs = {R.mipmap.weichat_pay, R.mipmap.ali_pay};
    private List<Integer> imgList;
    private List<Integer> Pay_imgList;
    //标记menuViewPager的选中
    private int selectPosition = 0;

    @Override
    protected Integer layoutId() {
        return R.layout.activity_vip;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initView() {
        MyActivityManager.getMyActivityManager().pushAct(VIPActivity.this);
        //设置内容顶进状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        ViewPager menuViewPager = bindView(R.id.menuViewPager);
        bindViewWithClick(R.id.back, true);

        imgList = new ArrayList<>();
        Pay_imgList = new ArrayList<>();
        for (int i = 0; i < imgs.length; i++) {
            imgList.add(imgs[i]);
            Pay_imgList.add(pay_imgs[i]);
        }
        MenuAdapter menuAdapter = new MenuAdapter(imgList, Pay_imgList, R.layout.wallet_menu_view);
        menuViewPager.setAdapter(menuAdapter);
        menuViewPager.setOffscreenPageLimit(imgList.size());//设置预加载的数量
        menuViewPager.setPageMargin(12);
        //添加3D画廊效果
        menuViewPager.setPageTransformer(true, new RotationPageTransformer());
        //viewPager左右两边滑动无效的处理
        findViewById(R.id.vp_outer).setOnTouchListener((view, motionEvent)
                -> menuViewPager.dispatchTouchEvent(motionEvent));
        //menuViewPager默认选中的是0
        menuViewPager.setCurrentItem(0);
        menuViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                selectPosition = i;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


    }

    @Override
    protected void initData() {

    }

    @Override
    protected String initTitle() {
        return getString(R.string.open_vip);
    }

    @Override
    protected Boolean showBackBtn() {
        return true;
    }

    @Override
    protected int topBarColor() {
        return MyApplication.getInstance().getResources().getColor(R.color.deepPurple);
    }

    @Override
    protected void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;

        }
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
                    ActivityAnimUtils.instance().activityOut(VIPActivity.this);
                }
                break;
        }
        return super.onTouchEvent(event);
    }


}
