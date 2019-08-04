package newmatch.zbmf.com.testapplication.mvp.contract;

import newmatch.zbmf.com.testapplication.mvp.IMvpModel;
import newmatch.zbmf.com.testapplication.mvp.IMvpView;
import newmatch.zbmf.com.testapplication.mvp.model.baseModel.ModelCallback;

/**
 * Create By Administrator
 * on 2019/8/4
 */
public interface UserAllInfoContract {

    interface UserAllInfoView<E> extends IMvpView {
        //用于接收返回的结果
        void userAllInfoContract(E result);
    }

    interface UserAllInfoModel<E> extends IMvpModel {
        //请求数据
        void userAllInfoContract(int userid,
                                  ModelCallback.Http<E> resultCallBack);

    }


}
