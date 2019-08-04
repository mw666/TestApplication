package newmatch.zbmf.com.testapplication.mvp.contract;

import newmatch.zbmf.com.testapplication.mvp.IMvpModel;
import newmatch.zbmf.com.testapplication.mvp.IMvpView;
import newmatch.zbmf.com.testapplication.mvp.model.baseModel.ModelCallback;

/**
 * Create By Administrator
 * on 2019/8/3
 */
public interface OfficialContract {

    interface OfficialView<E> extends IMvpView {
        //用于接收返回的结果，可变参数
        void officialSuccess(E result);

    }

    interface OfficialModel<E> extends IMvpModel {
        //请求数据
        void officialData(ModelCallback.Http<E> resultCallBack);

    }

}
