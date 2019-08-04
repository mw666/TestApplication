package newmatch.zbmf.com.testapplication.mvp.model;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import newmatch.zbmf.com.testapplication.mvp.YeHiBean.EmptyData;
import newmatch.zbmf.com.testapplication.mvp.contract.ResetPassWordContract;
import newmatch.zbmf.com.testapplication.mvp.model.baseModel.ModelCallback;
import newmatch.zbmf.com.testapplication.mvp.requests.ErrorCallBack;
import newmatch.zbmf.com.testapplication.mvp.requests.YeHiRequests;
import newmatch.zbmf.com.testapplication.net.RetrofitSingleton;
import newmatch.zbmf.com.testapplication.net.exiception.ResponseTransformer;
import newmatch.zbmf.com.testapplication.net.schedules.SchedulerProvider;

/**
 * Create By Administrator
 * on 2019/8/4
 */
public class ResetPassWordModel implements ResetPassWordContract.ResetPassWordModel<EmptyData> {

    private ModelCallback.Http<EmptyData> resultCallBack;
    private Disposable resetPassWordDisPose;

    @Override
    public void resetPassWordData(String appkey, String phone, String code,
                                  String userId, String password,
                                  ModelCallback.Http<EmptyData> resultCallBack) {
        this.resultCallBack = resultCallBack;
        resetPassWordDisPose = RetrofitSingleton.getInstance().create(YeHiRequests.class)
                .resetPassword(appkey, phone, code, userId, password)
                .compose(ResponseTransformer.handleResult(new ErrorCallBack() {
                    @Override
                    public void errorCallBack(int code, String errorMsg) {
                        resultCallBack.onError(code, errorMsg);
                    }
                }))
                .compose(SchedulerProvider.getInstance().applySchedulers())
                .subscribe(new Consumer<EmptyData>() {
                               @Override
                               public void accept(EmptyData emptyData) throws Exception {
                                   resultCallBack.onSuccess(emptyData);
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
        if (resetPassWordDisPose != null && !resetPassWordDisPose.isDisposed()) {
            resetPassWordDisPose.dispose();
            resultCallBack.onCancel();
        }
    }
}
