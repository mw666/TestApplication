package newmatch.zbmf.com.testapplication.mvp.presenter;

import newmatch.zbmf.com.testapplication.mvp.YeHiBean.UserInfo;
import newmatch.zbmf.com.testapplication.mvp.contract.LoginContract;
import newmatch.zbmf.com.testapplication.mvp.model.LoginModel;
import newmatch.zbmf.com.testapplication.mvp.model.baseModel.ModelCallback;
import newmatch.zbmf.com.testapplication.mvp.model.baseModel.ModelFactory;
import newmatch.zbmf.com.testapplication.mvp.presenter.basePresenter.MvpPresenter;
import newmatch.zbmf.com.testapplication.utils.LogUtils;

/**
 * Create By Administrator
 * on 2019/8/4
 */
public class LoginPresenter extends MvpPresenter<LoginContract.LoginView<UserInfo>> {


    private static LoginPresenter instance = null;

    public static LoginPresenter DEFAULT(LoginContract.LoginView<UserInfo> view) {
        if (instance == null) {
            synchronized (GuidePresenter.class) {
                if (instance == null) {
                    instance = new LoginPresenter(view);
                }
            }
        }
        return instance;
    }

    private LoginPresenter(LoginContract.LoginView<UserInfo> view) {
        attachView(view);
    }

    //登录
    public void login(String phone, String password) {
        ModelFactory.getModel(LoginModel.class)
                .loginData(phone, password, new ModelCallback.Http<UserInfo>() {
                    @Override
                    public void onSuccess(UserInfo object) {
                        getView().loginSuccess(object);
                    }

                    @Override
                    public void onError(int code, String desc) {
                        getView().showFail(code, desc);
                    }

                    @Override
                    public void onCancel() {
                        LogUtils.D("登录取消");
                    }
                });
    }

    @Override
    public void destroy() {
        detachView();
    }
}
