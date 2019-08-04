package newmatch.zbmf.com.testapplication.mvp.contract;

import newmatch.zbmf.com.testapplication.mvp.IMvpModel;
import newmatch.zbmf.com.testapplication.mvp.IMvpView;
import newmatch.zbmf.com.testapplication.mvp.model.baseModel.ModelCallback;

/**
 * Create By Administrator
 * on 2019/8/3
 */
public interface GuideContract {

    //此层用于接收返回的结果
    interface GuideView<E> extends IMvpView {

        //用于接收返回的结果，可变参数
        void showSuccess(E result);

    }

    //协议请求的格式
    interface GuideModel<E> extends IMvpModel {

        //请求数据
        void guideData(ModelCallback.Http<E> resultCallBack);

    }

}
