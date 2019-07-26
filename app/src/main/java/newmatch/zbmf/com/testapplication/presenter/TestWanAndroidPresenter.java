package newmatch.zbmf.com.testapplication.presenter;

import android.annotation.SuppressLint;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import newmatch.zbmf.com.testapplication.entity.BannerService;
import newmatch.zbmf.com.testapplication.net.RetrofitSingleton;
import newmatch.zbmf.com.testapplication.net.utils.RxUtil;
import newmatch.zbmf.com.testapplication.presenter.backview.TestView;
import newmatch.zbmf.com.testapplication.presenter.presenterIml.TestPresenter;
import newmatch.zbmf.com.testapplication.services.WanAndroidBanner;

/**
 * Created by **
 * on 2018/9/14.
 * 测试获取玩Android的数据
 */

public class TestWanAndroidPresenter implements TestPresenter {

    private TestView<BannerService,TestWanAndroidPresenter> mBaseView;
    private Observable<BannerService> mObservable;

    /**
     * 在构造方法中传入presenter
     * @param view
     */
    public TestWanAndroidPresenter(TestView<BannerService,TestWanAndroidPresenter> view) {
        this.mBaseView = view;
        view.setPresenter(this);
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @SuppressLint("CheckResult")
    @Override
    public void doLoadData(String... params) {
        WanAndroidBanner wanAndroidBanner = RetrofitSingleton.getInstance().create(WanAndroidBanner.class);
        wanAndroidBanner.getBannerRx()
                .subscribeOn(Schedulers.io())//指定事件发布的线程,并执行操作
                /*.doOnNext(bannerService -> {
                    //doOnNext将获取到的结果先做一次操作，下面可以编写您的操作代码
                    //并且不改变数据的传递
                })*/
        /*.flatMap(bannerService -> {//拿到结果--->并将结果返回
            List<BannerService.Data> data = bannerService.getData();
            PLog.LogD("--   将拿到的结果返回   :"+data);
            mObservable = Observable.just(bannerService);
            return mObservable;
        })*/
                .observeOn(AndroidSchedulers.mainThread())//指定接收结果的线程
                .doOnError(RetrofitSingleton::disposeFailureInfo)//错误处理
        .compose(RxUtil.io())//重用代码--->管理生命周期
        .subscribe(bannerService -> {//在指定线程接收最终结果
            mBaseView.resultCallBack(bannerService);//回调方法，将结果传回需要的页面
        });
    }
}
