package newmatch.zbmf.com.testapplication.presenter.backview;

import newmatch.zbmf.com.testapplication.presenter.presenterIml.BasePresenter;

/**
 * Created by pq
 * on 2018/9/14.
 */

public interface TestView<E,T extends BasePresenter> extends BaseView<T>{
    //回调方法里边传出处理结果
    void resultCallBack(E result);

}
