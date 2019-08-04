package newmatch.zbmf.com.testapplication.mvp.presenter;

import newmatch.zbmf.com.testapplication.mvp.YeHiBean.UserAllInfo;
import newmatch.zbmf.com.testapplication.mvp.contract.UserAllInfoContract;
import newmatch.zbmf.com.testapplication.mvp.model.UserAllInfoModel;
import newmatch.zbmf.com.testapplication.mvp.model.baseModel.ModelCallback;
import newmatch.zbmf.com.testapplication.mvp.model.baseModel.ModelFactory;
import newmatch.zbmf.com.testapplication.mvp.presenter.basePresenter.MvpPresenter;
import newmatch.zbmf.com.testapplication.utils.LogUtils;

/**
 * Create By Administrator
 * on 2019/8/4
 */
public class UserAllInfoPresenter extends
        MvpPresenter<UserAllInfoContract.UserAllInfoView<UserAllInfo>> {

    private static UserAllInfoPresenter instance = null;

    public static UserAllInfoPresenter DEFAULT(
            UserAllInfoContract.UserAllInfoView<UserAllInfo> view) {
        if (instance == null) {
            synchronized (UpUserInfoPresenter.class) {
                if (instance == null) {
                    instance = new UserAllInfoPresenter(view);
                }
            }
        }
        return instance;
    }

    private UserAllInfoPresenter(UserAllInfoContract.UserAllInfoView<UserAllInfo> view) {
        attachView(view);
    }

    //获取用户的所有信息
    public void getUserAllInfo(int userId) {
        ModelFactory.getModel(UserAllInfoModel.class)
                .userAllInfoContract(userId, new ModelCallback.Http<UserAllInfo>() {
                    @Override
                    public void onSuccess(UserAllInfo object) {
                        getView().userAllInfoContract(object);
                    }

                    @Override
                    public void onError(int code, String desc) {
                        getView().showFail(code, desc);
                    }

                    @Override
                    public void onCancel() {
                        LogUtils.D("获取该用户的所有信息取消");
                    }
                });
    }

    @Override
    public void destroy() {
        detachView();
    }
}
