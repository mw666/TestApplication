package newmatch.zbmf.com.testapplication.mvp.model;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import newmatch.zbmf.com.testapplication.mvp.YeHiBean.OfficialHomeBanner;
import newmatch.zbmf.com.testapplication.mvp.contract.OfficialContract;
import newmatch.zbmf.com.testapplication.mvp.model.baseModel.ModelCallback;
import newmatch.zbmf.com.testapplication.mvp.requests.ErrorCallBack;
import newmatch.zbmf.com.testapplication.mvp.requests.YeHiRequests;
import newmatch.zbmf.com.testapplication.net.RetrofitSingleton;
import newmatch.zbmf.com.testapplication.net.exiception.ResponseTransformer;
import newmatch.zbmf.com.testapplication.net.schedules.SchedulerProvider;

/**
 * Create By Administrator
 * on 2019/8/3
 */
public class OfficialBannerModel implements
        OfficialContract.OfficialModel<List<OfficialHomeBanner>> {

    private ModelCallback.Http<List<OfficialHomeBanner>> resultCallBack;
    private Disposable officialDispose;


    @Override
    public void officialData(ModelCallback.Http<List<OfficialHomeBanner>> resultCallBack) {
        this.resultCallBack = resultCallBack;
        officialDispose = RetrofitSingleton.getInstance().create(YeHiRequests.class)
                .officialBanner()
                .compose(ResponseTransformer.handleResult(new ErrorCallBack() {
                    @Override
                    public void errorCallBack(int code, String errorMsg) {
                        resultCallBack.onError(code, errorMsg);
                    }
                }))
                .compose(SchedulerProvider.getInstance().applySchedulers())
                .subscribe(new Consumer<List<OfficialHomeBanner>>() {
                               @Override
                               public void accept(List<OfficialHomeBanner> officialHomeBanners) throws Exception {
                                   resultCallBack.onSuccess(officialHomeBanners);
                               }
                           }
                        , new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                resultCallBack.onError(0, throwable.getMessage());
                            }
                        });
    }

    @Override
    public void cancel() {
        if (officialDispose != null && !officialDispose.isDisposed()) {
            officialDispose.dispose();
            resultCallBack.onCancel();
        }
    }
}
