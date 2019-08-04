package newmatch.zbmf.com.testapplication.mvp.presenter;

import java.util.List;

import newmatch.zbmf.com.testapplication.mvp.YeHiBean.OfficialHomeBanner;
import newmatch.zbmf.com.testapplication.mvp.contract.OfficialContract;
import newmatch.zbmf.com.testapplication.mvp.model.OfficialBannerModel;
import newmatch.zbmf.com.testapplication.mvp.model.baseModel.ModelCallback;
import newmatch.zbmf.com.testapplication.mvp.model.baseModel.ModelFactory;
import newmatch.zbmf.com.testapplication.mvp.presenter.basePresenter.MvpPresenter;
import newmatch.zbmf.com.testapplication.utils.LogUtils;

/**
 * Create By Administrator
 * on 2019/8/3
 */
public class OfficialBannerPresenter extends
        MvpPresenter<OfficialContract.OfficialView<List<OfficialHomeBanner>>> {

    private static OfficialBannerPresenter instance = null;

    public static OfficialBannerPresenter DEFAULT(
            OfficialContract.OfficialView<List<OfficialHomeBanner>> view) {
        if (instance == null) {
            synchronized (OfficialBannerPresenter.class) {
                if (instance == null) {
                    instance = new OfficialBannerPresenter(view);
                }
            }
        }
        return instance;
    }

    private OfficialBannerPresenter(OfficialContract.OfficialView<List<OfficialHomeBanner>> view) {
        attachView(view);
    }

    //获取首页官方轮播的数据
    public void getOfficialHomeData() {
        ModelFactory.getModel(OfficialBannerModel.class)
                .officialData(new ModelCallback.Http<List<OfficialHomeBanner>>() {
                    @Override
                    public void onSuccess(List<OfficialHomeBanner> object) {
                        getView().officialSuccess(object);
                    }

                    @Override
                    public void onError(int code, String desc) {
                        getView().showFail(code, desc);
                    }

                    @Override
                    public void onCancel() {
                        LogUtils.D("首页官方轮播数据请求取消");
                    }
                });
    }

    @Override
    public void destroy() {
        detachView();
    }
}
