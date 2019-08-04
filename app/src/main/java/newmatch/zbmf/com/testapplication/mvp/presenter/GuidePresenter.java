package newmatch.zbmf.com.testapplication.mvp.presenter;

import android.annotation.SuppressLint;
import android.app.Activity;

import java.util.List;

import newmatch.zbmf.com.testapplication.mvp.contract.GuideContract;
import newmatch.zbmf.com.testapplication.mvp.model.GuideModel;
import newmatch.zbmf.com.testapplication.mvp.model.baseModel.ModelFactory;
import newmatch.zbmf.com.testapplication.mvp.model.baseModel.ModelCallback;
import newmatch.zbmf.com.testapplication.mvp.presenter.basePresenter.MvpPresenter;
import newmatch.zbmf.com.testapplication.mvp.YeHiBean.GuideBanner;
import newmatch.zbmf.com.testapplication.utils.LogUtils;

/**
 * Create By Administrator
 * on 2019/8/3
 */
public class GuidePresenter extends MvpPresenter<GuideContract.GuideView<List<GuideBanner>>> {

    @SuppressLint("StaticFieldLeak")
    private static GuidePresenter instance = null;

    public static GuidePresenter DEFAULT(GuideContract.GuideView<List<GuideBanner>> view) {
        if (instance == null) {
            synchronized (GuidePresenter.class) {
                if (instance == null) {
                    instance = new GuidePresenter(view);
                }
            }
        }
        return instance;
    }


    private GuidePresenter(GuideContract.GuideView<List<GuideBanner>> view) {
        attachView(view);
    }

    private boolean isShowLoading;
    private Activity activity;

    //请求Guide数据
    public void guideData(boolean isShowLoading, Activity activity) {
        this.isShowLoading = isShowLoading;
        this.activity = activity;
        if (isShowLoading) {
            showLoading(activity, null);
        }
        ModelFactory.getModel(GuideModel.class)
                .guideData(new ModelCallback.Http<List<GuideBanner>>() {
                    @Override
                    public void onSuccess(List<GuideBanner> object) {
                        getView().showSuccess(object);
                        if (isShowLoading) {
                            showLoadSuccess(activity, null);
                        }
                    }

                    @Override
                    public void onError(int code, String desc) {
                        getView().showFail(code, desc);
                        if (isShowLoading) {
                            showLoadFailed(activity, null);
                        }
                    }

                    @Override
                    public void onCancel() {
                        LogUtils.D("Guide请求取消");
                    }
                });
    }


    @Override
    public void destroy() {
        detachView();
    }

    @Override
    protected void onLoadRetry() {
        super.onLoadRetry();
        //重新加载
        guideData(isShowLoading, activity);


    }
}
