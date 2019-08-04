package newmatch.zbmf.com.testapplication.mvp.presenter;

import newmatch.zbmf.com.testapplication.mvp.YeHiBean.UserHomeShow;
import newmatch.zbmf.com.testapplication.mvp.contract.HomeUserShowContract;
import newmatch.zbmf.com.testapplication.mvp.model.HomeUserShowModel;
import newmatch.zbmf.com.testapplication.mvp.model.baseModel.ModelCallback;
import newmatch.zbmf.com.testapplication.mvp.model.baseModel.ModelFactory;
import newmatch.zbmf.com.testapplication.mvp.presenter.basePresenter.MvpPresenter;
import newmatch.zbmf.com.testapplication.utils.LogUtils;

/**
 * Create By Administrator
 * on 2019/8/4
 */
public class HomeUserShowPresenter extends
        MvpPresenter<HomeUserShowContract.HomeUserShowView<UserHomeShow>> {


    private static HomeUserShowPresenter instance = null;

    public static HomeUserShowPresenter DEFAULT(
            HomeUserShowContract.HomeUserShowView<UserHomeShow> view) {
        if (instance == null) {
            synchronized (HomeUserShowPresenter.class) {
                if (instance == null) {
                    instance = new HomeUserShowPresenter(view);
                }
            }
        }
        return instance;
    }

    private HomeUserShowPresenter(HomeUserShowContract.HomeUserShowView<UserHomeShow> view) {
        attachView(view);
    }

    //获取首页用户展示的数据
    public void showHomeUserShow(int pageNum, int pageSize, String address, int type) {
        ModelFactory.getModel(HomeUserShowModel.class)
                .homeUserShowData(pageNum, pageSize, address, type
                        , new ModelCallback.Http<UserHomeShow>() {
                            @Override
                            public void onSuccess(UserHomeShow object) {
                                getView().homeUserShowSuccess(object);
                            }

                            @Override
                            public void onError(int code, String desc) {
                                getView().showFail(code, desc);
                            }

                            @Override
                            public void onCancel() {
                                LogUtils.D("获取首页用户的展示数据取消");
                            }
                        });
    }


    @Override
    public void destroy() {
        detachView();
    }
}
