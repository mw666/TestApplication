package newmatch.zbmf.com.testapplication.activitys;

import android.annotation.SuppressLint;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.adapters.myPagerAdapters.MenuAdapter;
import newmatch.zbmf.com.testapplication.base.BaseActivity;
import newmatch.zbmf.com.testapplication.dialogs.MyDialogUtil;
import newmatch.zbmf.com.testapplication.utils.ActivityAnimUtils;
import newmatch.zbmf.com.testapplication.utils.ToastUtils;
import newmatch.zbmf.com.testapplication.views.RotationPageTransformer;

/**
 * 我的钱包
 */
public class WalletActivity extends BaseActivity {

    @Override
    protected Integer layoutId() {
        return R.layout.activity_wallet;
    }

    private int[] imgs = {R.drawable.green_ge_bg, R.drawable.pink_flower_bg};
    private int[] pay_imgs = {R.drawable.weichat_pay, R.drawable.ali_pay};
    private List<Integer> imgList;
    private List<Integer> Pay_imgList;

    //标记menuViewPager的选中
    private int selectPosition = 0;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initView() {
        //全屏，内容延伸至状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        ViewPager menuViewPager = bindView(R.id.menuViewPager);
        bindViewWithClick(R.id.back, true);
        bindViewWithClick(R.id.getRMB, true);
        bindViewWithClick(R.id.getJifenToRMB, true);

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
            case R.id.getRMB:
                //奖励金提现，首先检测该用户是否绑定了当前提现方式的提现账号，若没有则提示绑定
                String payDialogTitle = "";
                if (selectPosition == 0) {
                    payDialogTitle = "提现到微信";
                } else if (selectPosition == 1) {
                    payDialogTitle = "提现到支付宝";
                }
                View awardView = LayoutInflater.from(WalletActivity.this)
                        .inflate(R.layout.award_get_view, null);
                MyDialogUtil.showPayDialog(WalletActivity.this, this, awardView,
                        true, R.drawable.dialog_bg, payDialogTitle, (content, alertDialog) -> {
                            //要提现的现金
                            alertDialog.dismiss();
                            ToastUtils.showSquareTvToast(WalletActivity.this, "要提现的现金");
                        });

                break;
            case R.id.getJifenToRMB:
                //夜钻积分提现

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
                if (i > 120) {
                    x = 0;
                    ActivityAnimUtils.instance().activityOut(WalletActivity.this);
                }
                break;
        }
        return super.onTouchEvent(event);
    }
}
