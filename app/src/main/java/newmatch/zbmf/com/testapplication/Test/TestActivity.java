package newmatch.zbmf.com.testapplication.Test;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.billy.android.loading.Gloading;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.Locale;

import newmatch.zbmf.com.testapplication.GMClass.loading.adapter.GlobalAdapter;
import newmatch.zbmf.com.testapplication.GMClass.selector.GMSelector;
import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.base.BaseActivity;
import newmatch.zbmf.com.testapplication.net.beans.BaseResponse;
import newmatch.zbmf.com.testapplication.presenter.backview.TestView;

/**
 * 用于测试的Activity
 */
public class TestActivity extends BaseActivity implements TestView<BaseResponse<TestBean>, TestCaiPresenter> {

    private Button locationBtn, loadingBtn1, loadingBtn2;
    private ImageView imageView;
    private TestCaiPresenter testCaiPresenter;

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

        imageView = bindView(R.id.iv);


        Gloading.initDefault(new GlobalAdapter());
    }

    @Override
    protected void initData() {
        if (testCaiPresenter == null)
            testCaiPresenter = new TestCaiPresenter(this);
        testCaiPresenter.doLoadData();

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

    @Override
    public void resultCallBack(BaseResponse<TestBean> result) {

    }

    @Override
    public void setPresenter(TestCaiPresenter presenter) {

    }
}
