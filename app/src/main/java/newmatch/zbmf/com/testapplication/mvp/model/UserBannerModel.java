package newmatch.zbmf.com.testapplication.mvp.model;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import newmatch.zbmf.com.testapplication.mvp.YeHiBean.UserBanner;
import newmatch.zbmf.com.testapplication.mvp.contract.UserBannerContract;
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
public class UserBannerModel implements UserBannerContract.UserBannerModel<List<UserBanner>> {

    private ModelCallback.Http<List<UserBanner>> resultCallBack;
    private Disposable userBannerDispose;

    @Override
    public void userBannerData(ModelCallback.Http<List<UserBanner>> resultCallBack) {
        this.resultCallBack = resultCallBack;
        userBannerDispose = RetrofitSingleton.getInstance().create(YeHiRequests.class)
                .userBanner()
                .compose(ResponseTransformer.handleResult(new ErrorCallBack() {
                    @Override
                    public void errorCallBack(int code, String errorMsg) {
                        resultCallBack.onError(code, errorMsg);
                    }
                }))
                .compose(SchedulerProvider.getInstance().applySchedulers())
                .subscribe(new Consumer<List<UserBanner>>() {
                               @Override
                               public void accept(List<UserBanner> userBanners) throws Exception {
                                   resultCallBack.onSuccess(userBanners);
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
        if (userBannerDispose != null && !userBannerDispose.isDisposed()) {
            userBannerDispose.dispose();
            resultCallBack.onCancel();
        }
    }
}

