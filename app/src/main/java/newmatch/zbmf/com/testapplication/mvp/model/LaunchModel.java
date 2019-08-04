package newmatch.zbmf.com.testapplication.mvp.model;

import io.reactivex.disposables.Disposable;
import newmatch.zbmf.com.testapplication.mvp.YeHiBean.YeHiLaunch;
import newmatch.zbmf.com.testapplication.mvp.contract.LaunchContract;
import newmatch.zbmf.com.testapplication.mvp.model.baseModel.ModelCallback;
import newmatch.zbmf.com.testapplication.mvp.requests.YeHiRequests;
import newmatch.zbmf.com.testapplication.net.RetrofitSingleton;
import newmatch.zbmf.com.testapplication.net.exiception.ResponseTransformer;
import newmatch.zbmf.com.testapplication.net.schedules.SchedulerProvider;

/**
 * Create By Administrator
 * on 2019/8/3
 */
public class LaunchModel implements LaunchContract.LaunchModel<YeHiLaunch> {


    private ModelCallback.Http<YeHiLaunch> resultCallBack;
    private Disposable launchDispose;


    @Override
    public void launchData(ModelCallback.Http<YeHiLaunch> resultCallBack) {
        this.resultCallBack = resultCallBack;
        launchDispose = RetrofitSingleton.getInstance()
                .create(YeHiRequests.class)
                .launchInfo()
                .compose(ResponseTransformer.handleResult(resultCallBack::onError))
                .compose(SchedulerProvider.getInstance().applySchedulers())
                .subscribe(resultCallBack::onSuccess
                        , throwable ->
                                resultCallBack.onError(0, throwable.getMessage())
                );
    }

    @Override
    public void cancel() {
        //取消请求
        if (launchDispose != null && !launchDispose.isDisposed()) {
            launchDispose.dispose();
            this.resultCallBack.onCancel();
        }
    }
}
