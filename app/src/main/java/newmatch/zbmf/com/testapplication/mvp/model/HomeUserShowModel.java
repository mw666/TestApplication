package newmatch.zbmf.com.testapplication.mvp.model;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import newmatch.zbmf.com.testapplication.mvp.YeHiBean.UserHomeShow;
import newmatch.zbmf.com.testapplication.mvp.contract.HomeUserShowContract;
import newmatch.zbmf.com.testapplication.mvp.model.baseModel.ModelCallback;
import newmatch.zbmf.com.testapplication.mvp.requests.ErrorCallBack;
import newmatch.zbmf.com.testapplication.mvp.requests.YeHiRequests;
import newmatch.zbmf.com.testapplication.net.RetrofitSingleton;
import newmatch.zbmf.com.testapplication.net.exiception.ResponseTransformer;
import newmatch.zbmf.com.testapplication.net.schedules.SchedulerProvider;

/**
 * Create By Administrator
 * on 2019/8/4
 * 获取用户的展示列表
 */
public class HomeUserShowModel implements HomeUserShowContract.HomeUserShowModel<UserHomeShow> {

    private ModelCallback.Http<UserHomeShow> resultCallBack;
    private Disposable homeUserShowDispose;

    @Override
    public void homeUserShowData(int pageNum, int pageSize, String address, int type,
                                 ModelCallback.Http<UserHomeShow> resultCallBack) {
        this.resultCallBack = resultCallBack;
        homeUserShowDispose = RetrofitSingleton.getInstance()
                .create(YeHiRequests.class)
                .homeUserShow(pageNum, pageSize, address, type)
                .compose(ResponseTransformer.handleResult(new ErrorCallBack() {
                    @Override
                    public void errorCallBack(int code, String errorMsg) {
                        resultCallBack.onError(code, errorMsg);
                    }
                }))
                .compose(SchedulerProvider.getInstance().applySchedulers())
                .subscribe(
                        new Consumer<UserHomeShow>() {
                            @Override
                            public void accept(UserHomeShow userHomeShow) throws Exception {
                                resultCallBack.onSuccess(userHomeShow);
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
        if (homeUserShowDispose != null && !homeUserShowDispose.isDisposed()) {
            homeUserShowDispose.dispose();
            resultCallBack.onCancel();
        }
    }


}
