package newmatch.zbmf.com.testapplication.mvp.model;

import java.io.File;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import newmatch.zbmf.com.testapplication.mvp.YeHiBean.EmptyData;
import newmatch.zbmf.com.testapplication.mvp.YeHiBean.UserInfo;
import newmatch.zbmf.com.testapplication.mvp.contract.UpUserInfoContract;
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
public class UpUserInfoModel implements UpUserInfoContract.UpUserInfoModel<EmptyData> {

    private ModelCallback.Http<EmptyData> callBack;
    private Disposable upUserInfoDisPose;


    @Override
    public void upUserInfoModel(List<File> list, UserInfo userInfo,
                                ModelCallback.Http<EmptyData> callBack) {
        this.callBack = callBack;
        upUserInfoDisPose = RetrofitSingleton.getInstance()
                .create(YeHiRequests.class)
                .upUserInfo(list, userInfo)
                .compose(ResponseTransformer.handleResult(new ErrorCallBack() {
                    @Override
                    public void errorCallBack(int code, String errorMsg) {
                        callBack.onError(code, errorMsg);
                    }
                }))
                .compose(SchedulerProvider.getInstance().applySchedulers())
                .subscribe(
                        new Consumer<EmptyData>() {
                            @Override
                            public void accept(EmptyData emptyData) throws Exception {
                                callBack.onSuccess(emptyData);
                            }
                        }
                        , new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                callBack.onError(0, throwable.getMessage());
                            }
                        });
    }

    @Override
    public void cancel() {
        if (upUserInfoDisPose != null && !upUserInfoDisPose.isDisposed()) {
            upUserInfoDisPose.dispose();
            callBack.onCancel();
        }
    }
}
