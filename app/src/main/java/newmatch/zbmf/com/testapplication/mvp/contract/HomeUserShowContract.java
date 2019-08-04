package newmatch.zbmf.com.testapplication.mvp.contract;

import newmatch.zbmf.com.testapplication.mvp.IMvpModel;
import newmatch.zbmf.com.testapplication.mvp.IMvpView;
import newmatch.zbmf.com.testapplication.mvp.model.baseModel.ModelCallback;

/**
 * Create By Administrator
 * on 2019/8/4
 */
public interface HomeUserShowContract {

    interface HomeUserShowView<E> extends IMvpView {
        //用于接收返回的结果
        void homeUserShowSuccess(E result);
    }

    interface HomeUserShowModel<E> extends IMvpModel {
        //请求数据   type 0:精华  1:新人
        void homeUserShowData(int pageNum,int pageSize,String address,int type,
                              ModelCallback.Http<E> resultCallBack);

    }

}
