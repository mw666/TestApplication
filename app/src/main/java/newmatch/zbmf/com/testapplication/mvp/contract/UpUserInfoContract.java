package newmatch.zbmf.com.testapplication.mvp.contract;

import java.io.File;
import java.util.List;

import newmatch.zbmf.com.testapplication.mvp.IMvpModel;
import newmatch.zbmf.com.testapplication.mvp.IMvpView;
import newmatch.zbmf.com.testapplication.mvp.YeHiBean.UserInfo;
import newmatch.zbmf.com.testapplication.mvp.model.baseModel.ModelCallback;

/**
 * Create By Administrator
 * on 2019/8/4
 */
public interface UpUserInfoContract {

    interface UpUserInfoView<E> extends IMvpView {

        void upUserInfoSuccess(E result);
    }

    interface UpUserInfoModel<E> extends IMvpModel{

        void upUserInfoModel(List<File> list, UserInfo userInfo, ModelCallback.Http<E> callBack);
    }
}