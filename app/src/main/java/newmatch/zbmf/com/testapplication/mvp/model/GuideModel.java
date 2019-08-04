package newmatch.zbmf.com.testapplication.mvp.model;

import java.util.List;

import io.reactivex.disposables.Disposable;
import newmatch.zbmf.com.testapplication.mvp.contract.GuideContract;
import newmatch.zbmf.com.testapplication.mvp.model.baseModel.ModelCallback;
import newmatch.zbmf.com.testapplication.mvp.requests.YeHiRequests;
import newmatch.zbmf.com.testapplication.net.RetrofitSingleton;
import newmatch.zbmf.com.testapplication.net.exiception.ResponseTransformer;
import newmatch.zbmf.com.testapplication.net.schedules.SchedulerProvider;
import newmatch.zbmf.com.testapplication.mvp.YeHiBean.GuideBanner;

/**
 * Create By Administrator
 * on 2019/8/3
 */
public class GuideModel implements GuideContract.GuideModel<List<GuideBanner>> {


    private ModelCallback.Http<List<GuideBanner>> resultCallBack;
    private Disposable guideDispose;

    @Override
    public void guideData(ModelCallback.Http<List<GuideBanner>> resultCallBack) {
        this.resultCallBack = resultCallBack;
        guideDispose = RetrofitSingleton.getInstance()
                .create(YeHiRequests.class)
                .guidePic()
                .compose(ResponseTransformer.handleResult(resultCallBack::onError))
                .compose(SchedulerProvider.getInstance().applySchedulers())
                .subscribe(resultCallBack::onSuccess
                        , throwable ->
                                resultCallBack.onError(0, throwable.getMessage()));
    }

    @Override
    public void cancel() {
        //取消请求
        if (guideDispose != null && !guideDispose.isDisposed()) {
            guideDispose.dispose();
            this.resultCallBack.onCancel();
        }
    }


}
