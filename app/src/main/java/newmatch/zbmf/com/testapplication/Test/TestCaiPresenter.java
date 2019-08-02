package newmatch.zbmf.com.testapplication.Test;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import newmatch.zbmf.com.testapplication.net.RetrofitSingleton;
import newmatch.zbmf.com.testapplication.net.beans.BaseResponse;
import newmatch.zbmf.com.testapplication.net.exiception.ResponseTransformer;
import newmatch.zbmf.com.testapplication.net.schedules.SchedulerProvider;
import newmatch.zbmf.com.testapplication.presenter.YeHiBean.GuideBanner;
import newmatch.zbmf.com.testapplication.presenter.backview.YeHiView;
import newmatch.zbmf.com.testapplication.presenter.presenterIml.YeHiPresenter;
import newmatch.zbmf.com.testapplication.services.WanAndroidBanner;
import newmatch.zbmf.com.testapplication.utils.LogUtils;

/**
 * Created By pq
 * on 2019/8/1
 */
public class TestCaiPresenter implements YeHiPresenter {

    private YeHiView<BaseResponse<List<GuideBanner>>, TestCaiPresenter> mBaseView;
    private Observable<BaseResponse<List<GuideBanner>>> mObservable;

    /**
     * 在构造方法中传入presenter
     *
     * @param view
     */
    public TestCaiPresenter(YeHiView<BaseResponse<List<GuideBanner>>, TestCaiPresenter> view) {
        this.mBaseView = view;
        view.setPresenter(this);
    }

    @Override
    public void doLoadData(String... params) {
        WanAndroidBanner wanAndroidBanner1 = RetrofitSingleton.getInstance().create(WanAndroidBanner.class);
        Disposable disposable = wanAndroidBanner1.getLottery()
                .compose(ResponseTransformer.handleResult())
                .compose(SchedulerProvider.getInstance().applySchedulers())
                .subscribe(resultDataBeans ->
                        LogUtils.D("结果："+resultDataBeans.get(0).toString())
                        , throwable -> {
                            String message = throwable.getMessage();
                            LogUtils.D("错误：" + message);
                        });

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }
}
