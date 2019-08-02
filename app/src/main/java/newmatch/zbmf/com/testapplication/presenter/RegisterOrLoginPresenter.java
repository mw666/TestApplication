package newmatch.zbmf.com.testapplication.presenter;

import newmatch.zbmf.com.testapplication.entity.RegisterBean;
import newmatch.zbmf.com.testapplication.presenter.backview.YeHiView;
import newmatch.zbmf.com.testapplication.presenter.presenterIml.YeHiPresenter;

/**
 * Created by pq
 * on 2018/11/15.
 */

public class RegisterOrLoginPresenter implements YeHiPresenter {

    private YeHiView<RegisterBean,RegisterOrLoginPresenter> mBaseView;
    public RegisterOrLoginPresenter(YeHiView<RegisterBean,RegisterOrLoginPresenter> mView){
        this.mBaseView=mView;
        mView.setPresenter(this);
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void doLoadData(String... params) {

    }

    //注册
    public void register(String appkey,String phone,String zone,String code){
//        YeHiRequests sweetRequests = RetrofitSingleton.getInstance().create(YeHiRequests.class);
//        sweetRequests.register(appkey,phone,zone,code)
//                .subscribeOn(Schedulers.io())//指定发布的线程
//        .observeOn(AndroidSchedulers.mainThread())//指定接受结果的线程
//        .doOnError(RetrofitSingleton::disposeFailureInfo)//错误处理
//        .compose(RxUtil.io())//重用，管理生命周期
//        .subscribe(registerBean -> mBaseView.resultCallBack(registerBean));//将结果传回主线程界面
    }

    //登录
    public void login(String... params){

    }
}
