package newmatch.zbmf.com.testapplication.activitys;

import android.annotation.SuppressLint;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import it.sephiroth.android.library.easing.Linear;
import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.adapters.MenuAdapter;
import newmatch.zbmf.com.testapplication.base.BaseActivity;
import newmatch.zbmf.com.testapplication.base.MyApplication;
import newmatch.zbmf.com.testapplication.dialogs.MyDialogUtil;
import newmatch.zbmf.com.testapplication.utils.ActivityAnimUtils;
import newmatch.zbmf.com.testapplication.utils.MyActivityManager;
import newmatch.zbmf.com.testapplication.utils.ToastUtils;
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
    //标记当前用户的性别 0 男  1 女
    private int sex = 0;

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

        LinearLayout menVipView = bindView(R.id.menVipView);
        bindViewWithClick(R.id.menWeekVip, true);
        bindViewWithClick(R.id.menMonthVip, true);
        bindViewWithClick(R.id.menQuarterVip, true);
        bindViewWithClick(R.id.menYearVip, true);
        LinearLayout womenVipView = bindView(R.id.womenVipView);
        bindViewWithClick(R.id.womanMonthVip, true);
        bindViewWithClick(R.id.womenQuarterVip, true);
        bindViewWithClick(R.id.womanYearVip, true);
        if (sex == 0) {
            menVipView.setVisibility(View.VISIBLE);
            womenVipView.setVisibility(View.GONE);
        } else if (sex == 1) {
            menVipView.setVisibility(View.GONE);
            womenVipView.setVisibility(View.VISIBLE);
        }

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
            case R.id.menWeekVip:
                getVipShowDialog("周度会员", "10");
                break;
            case R.id.menMonthVip:
                getVipShowDialog("月度会员", "29");
                break;
            case R.id.menQuarterVip:
                getVipShowDialog("季度会员", "59");
                break;
            case R.id.menYearVip:
                getVipShowDialog("年度会员", "290");
                break;
            case R.id.womanMonthVip:
                getVipShowDialog("月度会员", "590");
                break;
            case R.id.womenQuarterVip:
                getVipShowDialog("季度会员", "1200");
                break;
            case R.id.womanYearVip:
                getVipShowDialog("年度会员", "4988");
                break;
        }
    }

    //开通会员套餐
    private void getVipShowDialog(String vipContentTitle, String vipPrice) {
        String payDialogTitle = "";
        if (selectPosition == 0) {
            payDialogTitle = "微信支付";
        } else if (selectPosition == 1) {
            payDialogTitle = "支付宝支付";
        }
        View awardView = LayoutInflater.from(VIPActivity.this)
                .inflate(R.layout.vip_item_get_view, null);
        MyDialogUtil.showVipDialog(VIPActivity.this, this, awardView,
                true, R.drawable.dialog_bg, payDialogTitle,
                vipContentTitle, vipPrice, (content, alertDialog) -> {
                    //要提现的现金
                    alertDialog.dismiss();
                    ToastUtils.showSquareTvToast(VIPActivity.this, "调用支付");
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
                if (i > 120) {
                    x = 0;
                    ActivityAnimUtils.instance().activityOut(VIPActivity.this);
                }
                break;
        }
        return super.onTouchEvent(event);
    }


}
