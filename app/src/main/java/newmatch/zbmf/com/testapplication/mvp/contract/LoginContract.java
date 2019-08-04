package newmatch.zbmf.com.testapplication.mvp.contract;

import newmatch.zbmf.com.testapplication.mvp.IMvpModel;
import newmatch.zbmf.com.testapplication.mvp.IMvpView;
import newmatch.zbmf.com.testapplication.mvp.model.baseModel.ModelCallback;

/**
 * Create By Administrator
 * on 2019/8/4
 */
public interface LoginContract {

    interface LoginView<E> extends IMvpView {
        //用于接收返回的结果，可变参数
        void loginSuccess(E result);

    }

    interface LoginModel<E> extends IMvpModel {
        //请求数据
        void loginData(String phone,String password,ModelCallback.Http<E> resultCallBack);

    }
}
