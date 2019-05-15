package newmatch.zbmf.com.testapplication.activitys;

import android.annotation.SuppressLint;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.adapters.MenuAdapter;
import newmatch.zbmf.com.testapplication.base.BaseActivity;
import newmatch.zbmf.com.testapplication.views.RotationPageTransformer;

/**
 * 我的钱包
 */
public class WalletActivity extends BaseActivity {

    @Override
    protected Integer layoutId() {
        return R.layout.activity_wallet;
    }

    private int[] imgs = {R.mipmap.green_ge_bg, R.mipmap.pink_flower_bg};
    private List<Integer> imgList;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initView() {
        //全屏，内容延伸至状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        ViewPager menuViewPager = bindView(R.id.menuViewPager);
        ImageView back = bindViewWithClick(R.id.back, true);

        imgList = new ArrayList<>();
        for (int i = 0; i < imgs.length; i++) {
            imgList.add(imgs[i]);
        }
        MenuAdapter menuAdapter = new MenuAdapter(imgList, R.layout.wallet_menu_view);
        menuViewPager.setAdapter(menuAdapter);
        menuViewPager.setOffscreenPageLimit(imgList.size());//设置预加载的数量
        menuViewPager.setPageMargin(12);
        //添加3D画廊效果
        menuViewPager.setPageTransformer(true, new RotationPageTransformer());
        //viewPager左右两边滑动无效的处理
        findViewById(R.id.vp_outer).setOnTouchListener((view, motionEvent)
                -> menuViewPager.dispatchTouchEvent(motionEvent));
        //menuViewPager默认选中的是0
        menuViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                Log.d("===LLL", "  onPageSelected  索引：" + i);

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
        return "我的钱包";
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
            case R.id.back:
                finish();
                break;
        }
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_wallet);
//
//
//    }
}
