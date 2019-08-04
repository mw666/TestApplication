package newmatch.zbmf.com.testapplication.mvp.presenter;

import newmatch.zbmf.com.testapplication.mvp.YeHiBean.EmptyData;
import newmatch.zbmf.com.testapplication.mvp.contract.ResetPassWordContract;
import newmatch.zbmf.com.testapplication.mvp.model.ResetPassWordModel;
import newmatch.zbmf.com.testapplication.mvp.model.baseModel.ModelCallback;
import newmatch.zbmf.com.testapplication.mvp.model.baseModel.ModelFactory;
import newmatch.zbmf.com.testapplication.mvp.presenter.basePresenter.MvpPresenter;
import newmatch.zbmf.com.testapplication.utils.LogUtils;

/**
 * Create By Administrator
 * on 2019/8/4
 */
public class ResetPasswordPresenter extends
        MvpPresenter<ResetPassWordContract.ResetPassWordView<EmptyData>> {

    private static ResetPasswordPresenter instance = null;

    public static ResetPasswordPresenter DEFAULT(
            ResetPassWordContract.ResetPassWordView<EmptyData> view) {
        if (instance == null) {
            synchronized (ResetPasswordPresenter.class) {
                if (instance == null) {
                    instance = new ResetPasswordPresenter(view);
                }
            }
        }
        return instance;
    }

    private ResetPasswordPresenter(ResetPassWordContract.ResetPassWordView<EmptyData> view) {
        attachView(view);
    }


    //重置密码
    public void resetPassword(String appkey, String phone, String code,
                              String userId, String password) {
        ModelFactory.getModel(ResetPassWordModel.class)
                .resetPassWordData(appkey, phone, code, userId,
                        password, new ModelCallback.Http<EmptyData>() {
                            @Override
                            public void onSuccess(EmptyData object) {
                                getView().resetPassWordSuccess(object);
                            }

                            @Override
                            public void onError(int code, String desc) {
                                getView().showFail(code, desc);
                            }

                            @Override
                            public void onCancel() {
                                LogUtils.D("重置密码取消");
                            }
                        });
    }

    @Override
    public void destroy() {
        detachView();
    }
}
