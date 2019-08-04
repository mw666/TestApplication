package newmatch.zbmf.com.testapplication.mvp.presenter;

import java.io.File;
import java.util.List;

import newmatch.zbmf.com.testapplication.mvp.YeHiBean.EmptyData;
import newmatch.zbmf.com.testapplication.mvp.YeHiBean.UserInfo;
import newmatch.zbmf.com.testapplication.mvp.contract.UpUserInfoContract;
import newmatch.zbmf.com.testapplication.mvp.model.UpUserInfoModel;
import newmatch.zbmf.com.testapplication.mvp.model.baseModel.ModelCallback;
import newmatch.zbmf.com.testapplication.mvp.model.baseModel.ModelFactory;
import newmatch.zbmf.com.testapplication.mvp.presenter.basePresenter.MvpPresenter;
import newmatch.zbmf.com.testapplication.utils.LogUtils;

/**
 * Create By Administrator
 * on 2019/8/4
 */
public class UpUserInfoPresenter extends MvpPresenter<UpUserInfoContract.UpUserInfoView<EmptyData>> {


    private static UpUserInfoPresenter instance = null;

    public static UpUserInfoPresenter DEFAULT(
            UpUserInfoContract.UpUserInfoView<EmptyData> view) {
        if (instance == null) {
            synchronized (UpUserInfoPresenter.class) {
                if (instance == null) {
                    instance = new UpUserInfoPresenter(view);
                }
            }
        }
        return instance;
    }

    private UpUserInfoPresenter(UpUserInfoContract.UpUserInfoView<EmptyData> view) {
        attachView(view);
    }

    //上传用户信息
    public void upUserInfo(List<File> files, UserInfo userInfo) {
        ModelFactory.getModel(UpUserInfoModel.class)
                .upUserInfoModel(files, userInfo, new ModelCallback.Http<EmptyData>() {
                    @Override
                    public void onSuccess(EmptyData object) {
                        getView().upUserInfoSuccess(object);
                    }

                    @Override
                    public void onError(int code, String desc) {
                        getView().showFail(code, desc);
                    }

                    @Override
                    public void onCancel() {
                        LogUtils.D("上传用户信息取消");
                    }
                });
    }

    @Override
    public void destroy() {
        detachView();
    }
}
