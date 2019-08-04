package newmatch.zbmf.com.testapplication.mvp.contract;

import newmatch.zbmf.com.testapplication.mvp.IMvpModel;
import newmatch.zbmf.com.testapplication.mvp.IMvpView;
import newmatch.zbmf.com.testapplication.mvp.model.baseModel.ModelCallback;

/**
 * Create By Administrator
 * on 2019/8/4
 */
public interface ResetPassWordContract {

    interface ResetPassWordView<E> extends IMvpView {
        //用于接收返回的结果
        void resetPassWordSuccess(E result);
    }

    interface ResetPassWordModel<E> extends IMvpModel {
        //请求数据
        void resetPassWordData(String appkey,
                               String phone,
                               String code,
                               String userId,
                               String password,
                               ModelCallback.Http<E> resultCallBack);

    }
}
