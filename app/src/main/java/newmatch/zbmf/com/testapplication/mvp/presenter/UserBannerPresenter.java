package newmatch.zbmf.com.testapplication.mvp.presenter;

import java.util.List;

import newmatch.zbmf.com.testapplication.mvp.YeHiBean.UserBanner;
import newmatch.zbmf.com.testapplication.mvp.contract.UserBannerContract;
import newmatch.zbmf.com.testapplication.mvp.model.UserBannerModel;
import newmatch.zbmf.com.testapplication.mvp.model.baseModel.ModelCallback;
import newmatch.zbmf.com.testapplication.mvp.model.baseModel.ModelFactory;
import newmatch.zbmf.com.testapplication.mvp.presenter.basePresenter.MvpPresenter;
import newmatch.zbmf.com.testapplication.utils.LogUtils;

/**
 * Create By Administrator
 * on 2019/8/3
 */
public class UserBannerPresenter extends
        MvpPresenter<UserBannerContract.UserBannerView<List<UserBanner>>> {

    private static UserBannerPresenter instance = null;

    public static UserBannerPresenter DEFAULT(
            UserBannerContract.UserBannerView<List<UserBanner>> view) {
        if (instance == null) {
            synchronized (UserBannerPresenter.class) {
                if (instance == null) {
                    instance = new UserBannerPresenter(view);
                }
            }
        }
        return instance;
    }

    private UserBannerPresenter(UserBannerContract.UserBannerView<List<UserBanner>> view) {
        attachView(view);
    }

    //获取用户的轮播数据
    public void getUserBanner() {
        ModelFactory.getModel(UserBannerModel.class)
                .userBannerData(new ModelCallback.Http<List<UserBanner>>() {
                    @Override
                    public void onSuccess(List<UserBanner> object) {
                        getView().userBannerSuccess(object);
                    }

                    @Override
                    public void onError(int code, String desc) {
                        getView().showFail(code, desc);
                    }

                    @Override
                    public void onCancel() {
                        LogUtils.D("用户轮播数据取消");
                    }
                });
    }

    @Override
    public void destroy() {
        detachView();
    }
}
