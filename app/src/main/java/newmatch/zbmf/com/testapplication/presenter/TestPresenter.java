package newmatch.zbmf.com.testapplication.presenter;

/**
 * Created by pq
 * on 2018/9/14.
 */

public interface TestPresenter extends BasePresenter {
    //这里是属于在P层的逻辑处理方法 --->设置可变参数
    void doLoadData(String ... params);
}
