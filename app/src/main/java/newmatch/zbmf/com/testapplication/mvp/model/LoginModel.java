package newmatch.zbmf.com.testapplication.mvp.model;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import newmatch.zbmf.com.testapplication.mvp.YeHiBean.UserInfo;
import newmatch.zbmf.com.testapplication.mvp.contract.LoginContract;
import newmatch.zbmf.com.testapplication.mvp.model.baseModel.ModelCallback;
import newmatch.zbmf.com.testapplication.mvp.requests.YeHiRequests;
import newmatch.zbmf.com.testapplication.net.RetrofitSingleton;
import newmatch.zbmf.com.testapplication.net.exiception.ResponseTransformer;
import newmatch.zbmf.com.testapplication.net.schedules.SchedulerProvider;

/**
 * Create By Administrator
 * on 2019/8/4
 */
public class LoginModel implements LoginContract.LoginModel<UserInfo> {

    private ModelCallback.Http<UserInfo> resultCallBack;
    private Disposable loginDispose;

    @Override
    public void loginData(String phone, String password,
                          ModelCallback.Http<UserInfo> resultCallBack) {
        this.resultCallBack = resultCallBack;
        loginDispose = RetrofitSingleton.getInstance()
                .create(YeHiRequests.class)
                .login(phone, password)
                .compose(ResponseTransformer.handleResult(resultCallBack::onError))
                .compose(SchedulerProvider.getInstance().applySchedulers())
                .subscribe(new Consumer<UserInfo>() {
                               @Override
                               public void accept(UserInfo userInfo) throws Exception {
                                   resultCallBack.onSuccess(userInfo);
                               }
                           }
                        , throwable -> resultCallBack.onError(0, throwable.getMessage()));
    }

    @Override
    public void cancel() {
        if (loginDispose != null && !loginDispose.isDisposed()) {
            loginDispose.dispose();
            resultCallBack.onCancel();
        }
    }

}
