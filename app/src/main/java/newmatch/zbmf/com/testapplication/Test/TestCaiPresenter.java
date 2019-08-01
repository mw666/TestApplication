package newmatch.zbmf.com.testapplication.Test;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import newmatch.zbmf.com.testapplication.net.RetrofitSingleton;
import newmatch.zbmf.com.testapplication.net.beans.BaseResponse;
import newmatch.zbmf.com.testapplication.net.utils.RxUtil;
import newmatch.zbmf.com.testapplication.presenter.backview.TestView;
import newmatch.zbmf.com.testapplication.presenter.presenterIml.TestPresenter;
import newmatch.zbmf.com.testapplication.services.WanAndroidBanner;
import newmatch.zbmf.com.testapplication.utils.LogUtils;

/**
 * Created By pq
 * on 2019/8/1
 */
public class TestCaiPresenter implements TestPresenter {

    private TestView<BaseResponse<TestBean>, TestCaiPresenter> mBaseView;
    private Observable<BaseResponse<TestBean>> mObservable;

    /**
     * 在构造方法中传入presenter
     *
     * @param view
     */
    public TestCaiPresenter(TestView<BaseResponse<TestBean>, TestCaiPresenter> view) {
        this.mBaseView = view;
        view.setPresenter(this);
    }

    @Override
    public void doLoadData(String... params) {
        WanAndroidBanner wanAndroidBanner = RetrofitSingleton.getInstance().create(WanAndroidBanner.class);
        Disposable subscribe = wanAndroidBanner.getLottery()
                .subscribeOn(Schedulers.io())//指定事件发布的线程,并执行操作
                /*.doOnNext(BaseResponse<TestBean> -> {
                    //doOnNext将获取到的结果先做一次操作，下面可以编写您的操作代码
                    //并且不改变数据的传递
                })
                .flatMap(BaseResponse<TestBean> -> {//拿到结果--->并将结果返回
                    List<BannerService.Data> data = BaseResponse<TestBean>.getData();
                    PLog.LogD("--   将拿到的结果返回   :"+data);
                    mObservable = Observable.just(bannerService);
                    return mObservable;
                })*/
                .observeOn(AndroidSchedulers.mainThread())//指定接收结果的线程
                .doOnError(RetrofitSingleton::disposeFailureInfo)//错误处理
                .compose(RxUtil.io())//重用代码--->管理生命周期
                .subscribe(testBeanBaseResponse -> {
                    int code = testBeanBaseResponse.getCode();
                    String msg = testBeanBaseResponse.getMsg();
                    TestBean data = testBeanBaseResponse.getData();

                }, throwable -> {
                    String message = throwable.getMessage();
                    LogUtils.D("信息：" + message);
                });

//        WanAndroidBanner wanAndroidBanner1 = RetrofitSingleton.getInstance().create(WanAndroidBanner.class);
//        Disposable disposable = wanAndroidBanner1.getLottery()
//                .compose(ResponseTransformer.handleResult())
//                .compose(SchedulerProvider.getInstance().applySchedulers())
//                .subscribe(new Consumer<TestBean>() {
//                    @Override
//                    public void accept(TestBean testBean) throws Exception {
//                        LogUtils.D("结果："+testBean.toString());
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        String message = throwable.getMessage();
//                        LogUtils.D("错误："+message);
//                    }
//                });

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }
}
