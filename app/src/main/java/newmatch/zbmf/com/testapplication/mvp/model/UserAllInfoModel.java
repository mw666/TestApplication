package newmatch.zbmf.com.testapplication.mvp.model;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import newmatch.zbmf.com.testapplication.mvp.YeHiBean.UserAllInfo;
import newmatch.zbmf.com.testapplication.mvp.contract.UserAllInfoContract;
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
public class UserAllInfoModel implements UserAllInfoContract.UserAllInfoModel<UserAllInfo> {

    private ModelCallback.Http<UserAllInfo> resultCallBack;
    private Disposable userAllInfoDisPose;

    @Override
    public void userAllInfoContract(int userid, ModelCallback.Http<UserAllInfo> resultCallBack) {
        this.resultCallBack = resultCallBack;
        userAllInfoDisPose = RetrofitSingleton.getInstance()
                .create(YeHiRequests.class)
                .getUserAllInfo(userid)
                .compose(ResponseTransformer.handleResult(new ErrorCallBack() {
                    @Override
                    public void errorCallBack(int code, String errorMsg) {
                        resultCallBack.onError(code, errorMsg);
                    }
                }))
                .compose(SchedulerProvider.getInstance().applySchedulers())
                .subscribe(new Consumer<UserAllInfo>() {
                               @Override
                               public void accept(UserAllInfo userAllInfo) throws Exception {
                                   resultCallBack.onSuccess(userAllInfo);
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
        if (userAllInfoDisPose != null && !userAllInfoDisPose.isDisposed()) {
            userAllInfoDisPose.dispose();
            resultCallBack.onCancel();
        }
    }
}
