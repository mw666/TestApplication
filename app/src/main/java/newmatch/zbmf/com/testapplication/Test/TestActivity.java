package newmatch.zbmf.com.testapplication.Test;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.billy.android.loading.Gloading;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.facebook.stetho.common.LogUtil;

import java.util.List;
import java.util.Locale;

import newmatch.zbmf.com.testapplication.GMClass.loading.adapter.GlobalAdapter;
import newmatch.zbmf.com.testapplication.GMClass.selector.GMSelector;
import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.base.BaseActivity;
import newmatch.zbmf.com.testapplication.mvp.YeHiBean.GuideBanner;
import newmatch.zbmf.com.testapplication.mvp.YeHiBean.OfficialHomeBanner;
import newmatch.zbmf.com.testapplication.mvp.YeHiBean.YeHiLaunch;
import newmatch.zbmf.com.testapplication.mvp.contract.GuideContract;
import newmatch.zbmf.com.testapplication.mvp.contract.LaunchContract;
import newmatch.zbmf.com.testapplication.mvp.contract.OfficialContract;
import newmatch.zbmf.com.testapplication.mvp.presenter.GuidePresenter;
import newmatch.zbmf.com.testapplication.mvp.presenter.OfficialBannerPresenter;
import newmatch.zbmf.com.testapplication.utils.LogUtils;

/**
 * 用于测试的Activity
 */
public class TestActivity extends BaseActivity implements GuideContract.GuideView<List<GuideBanner>>,
        LaunchContract.LaunchView<YeHiLaunch>, OfficialContract.OfficialView<List<OfficialHomeBanner>> {

    private Button locationBtn, loadingBtn1, loadingBtn2;
    private ImageView imageView;
    private TextView testTv;


    //获取随机的图片资源
    public static String getRandomImage() {
        int id = (int) (Math.random() * 100000);
        return String.format(Locale.CHINA,
                "https://www.thiswaifudoesnotexist.net/example-%d.jpg", id);
    }

    @Override
    protected Integer layoutId() {
        return R.layout.activity_test3;
    }

    @Override
    protected void initView() {
        locationBtn = findViewById(R.id.locationBtn);
        loadingBtn1 = findViewById(R.id.loadingBtn1);
        loadingBtn2 = findViewById(R.id.loadingBtn2);
        testTv = findViewById(R.id.testTv);


        imageView = bindView(R.id.iv);


        Gloading.initDefault(new GlobalAdapter());
    }

    @Override
    protected void initData() {
        //                GuidePresenter.DEFAULT(this).guideData(true,this);
        //        LaunchPresenter.DEFAULT(this).getLaunchData();
        OfficialBannerPresenter.DEFAULT(this).getOfficialHomeData();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GuidePresenter.DEFAULT(this).destroy();
    }

    public void showTime(View view) {
        GMSelector.instance(this).showPickerView((province, city, area) ->
                locationBtn.setText(String.format("%s-%s-%s", province, city, area)));
    }

    public void showLoad1(View view) {
        loadData();
        //        //在Activity中显示, 父容器为: android.R.id.content
        //        Gloading.Holder holder = Gloading.getDefault().wrap(this);
        //        //显示加载中的状态，通常是显示一个加载动画
        //        holder.showLoading();
        //
        //        try {
        //            Thread.sleep(3000);
        //        } catch (InterruptedException e) {
        //            e.printStackTrace();
        //        }
        //        holder.showLoadingStatus(Gloading.STATUS_LOAD_FAILED);
    }

    public void showLoad2(View view) {
        //需要支持加载失败后点击重试
        Gloading.Holder holder = Gloading.getDefault().wrap(this).withRetry(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        //显示加载失败状态
        holder.showLoadFailed();

    }

    //重新加载数据
    @Override
    protected void onLoadRetry() {
        super.onLoadRetry();
        loadData();
    }

    private void loadData() {
        String picUrl = getRandomImage();
        showLoading();
        Glide.with(this)
                .load(picUrl)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                Target<Drawable> target, boolean isFirstResource) {
                        showLoadFailed();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model,
                                                   Target<Drawable> target, DataSource dataSource,
                                                   boolean isFirstResource) {
                        showLoadSuccess();
                        return false;
                    }
                })
                .into(imageView);
    }

    /*接收请求接口后的结果*/
    @Override
    public void showSuccess(List<GuideBanner> result) {
        if (result != null && result.size() > 0) {
            GuideBanner guideBanner = result.get(0);

            testTv.setText(guideBanner.toString());

            LogUtil.d("结果：" + guideBanner.toString());
        }
    }

    @Override
    public void launchSuccess(YeHiLaunch result) {
        if (result != null) {

            testTv.setText(result.toString());

            LogUtil.d("结果：" + result.toString());
        }
    }

    @Override
    public void showFail(int code, String failMsg) {
        LogUtils.D("结果错误：" + code + " == " + failMsg);
    }

    @Override
    public void officialSuccess(List<OfficialHomeBanner> result) {
        if (result != null && result.size() > 0) {

            testTv.setText(result.get(0).toString());

            LogUtil.d("结果：" + result.get(0).toString());
        }
    }
}
