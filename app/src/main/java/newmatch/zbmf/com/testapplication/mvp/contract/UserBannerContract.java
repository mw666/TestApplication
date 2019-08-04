package newmatch.zbmf.com.testapplication.mvp.contract;

import newmatch.zbmf.com.testapplication.mvp.IMvpModel;
import newmatch.zbmf.com.testapplication.mvp.IMvpView;
import newmatch.zbmf.com.testapplication.mvp.model.baseModel.ModelCallback;

/**
 * Create By Administrator
 * on 2019/8/3
 */
public interface UserBannerContract {

    interface UserBannerView<E> extends IMvpView{
        //用于接收返回的结果
        void userBannerSuccess(E result);
    }

    interface UserBannerModel<E> extends IMvpModel {
        //请求数据
        void userBannerData(ModelCallback.Http<E> resultCallBack);

    }


}
