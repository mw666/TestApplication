package newmatch.zbmf.com.testapplication.mvp.presenter;

import newmatch.zbmf.com.testapplication.mvp.YeHiBean.YeHiLaunch;
import newmatch.zbmf.com.testapplication.mvp.contract.LaunchContract;
import newmatch.zbmf.com.testapplication.mvp.model.LaunchModel;
import newmatch.zbmf.com.testapplication.mvp.model.baseModel.ModelCallback;
import newmatch.zbmf.com.testapplication.mvp.model.baseModel.ModelFactory;
import newmatch.zbmf.com.testapplication.mvp.presenter.basePresenter.MvpPresenter;
import newmatch.zbmf.com.testapplication.utils.LogUtils;

/**
 * Create By Administrator
 * on 2019/8/3
 */
public class LaunchPresenter extends MvpPresenter<LaunchContract.LaunchView<YeHiLaunch>> {

    private static LaunchPresenter instance = null;

    public static LaunchPresenter DEFAULT(LaunchContract.LaunchView<YeHiLaunch> view) {
        if (instance == null) {
            synchronized (GuidePresenter.class) {
                if (instance == null) {
                    instance = new LaunchPresenter(view);
                }
            }
        }
        return instance;
    }

    private LaunchPresenter(LaunchContract.LaunchView<YeHiLaunch> view) {
        attachView(view);
    }

    //获取启动页数据
    public void getLaunchData() {
        ModelFactory.getModel(LaunchModel.class)
                .launchData(new ModelCallback.Http<YeHiLaunch>() {
                    @Override
                    public void onSuccess(YeHiLaunch object) {
                        getView().launchSuccess(object);
                    }

                    @Override
                    public void onError(int code, String desc) {
                        getView().showFail(code, desc);
                    }

                    @Override
                    public void onCancel() {
                        LogUtils.D("启动页请求取消了");
                    }
                });
    }


    @Override
    public void destroy() {
        detachView();
    }
}
