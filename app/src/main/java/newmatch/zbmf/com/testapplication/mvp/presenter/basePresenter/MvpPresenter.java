
package newmatch.zbmf.com.testapplication.mvp.presenter.basePresenter;

import android.app.Activity;
import android.support.annotation.UiThread;
import android.view.View;

import com.billy.android.loading.Gloading;

import newmatch.zbmf.com.testapplication.mvp.IMvpPresenter;
import newmatch.zbmf.com.testapplication.mvp.IMvpView;
import newmatch.zbmf.com.testapplication.mvp.proxy.MvpViewProxy;


/**
 * Presenter基础实现
 *
 * @param <V>
 */
public abstract class MvpPresenter<V extends IMvpView> implements IMvpPresenter<V> {

    protected V mView;

    //View代理对象
    protected MvpViewProxy<V> mMvpViewProxy;

    /**
     * 获取view
     *
     * @return
     */
    @UiThread
    public V getView() {
        return mView;
    }

    /**
     * 判断View是否已经添加
     *
     * @return
     */
    @UiThread
    public boolean isViewAttached() {
        return mView != null;
    }

    /**
     * 绑定View
     *
     * @param view
     */
    @UiThread
    @Override
    public void attachView(V view) {
        mMvpViewProxy = new MvpViewProxy<V>();
        mView = (V) mMvpViewProxy.newProxyInstance(view);
    }

    /**
     * 移除View
     */
    @Override
    public void detachView() {
        if (mMvpViewProxy != null) {
            mMvpViewProxy.detachView();
        }
    }

    /*以下是加载动画的配置*/
    //绑定加载数据状态的View
    protected Gloading.Holder mHolder;

    /**
     * make a Gloading.Holder wrap with current activity by default
     * override this method in subclass to do special initialization
     *
     * @see
     */
    protected void initLoadingStatusViewIfNeed(Activity activity, View view) {
        if (mHolder == null) {
            //bind status view to activity root view by default
            if (activity != null) {
                mHolder = Gloading.getDefault().wrap(activity).withRetry(this::onLoadRetry);
            } else if (view != null) {
                mHolder = Gloading.getDefault().wrap(view).withRetry(this::onLoadRetry);
            }
        }
    }

    //在子类中重写这个方法执行重复的任务
    protected void onLoadRetry() {
        // override this method in subclass to do retry task
    }

    //展示加载
    public void showLoading(Activity activity, View view) {
        if (activity != null) {
            initLoadingStatusViewIfNeed(activity, null);
        } else if (view != null) {
            initLoadingStatusViewIfNeed(null, view);
        }
        mHolder.showLoading();
    }

    //加载成功
    public void showLoadSuccess(Activity activity, View view) {
        if (activity != null) {
            initLoadingStatusViewIfNeed(activity, null);
        } else if (view != null) {
            initLoadingStatusViewIfNeed(null, view);
        }
        mHolder.showLoadSuccess();
    }

    //加载失败
    public void showLoadFailed(Activity activity, View view) {
        if (activity != null) {
            initLoadingStatusViewIfNeed(activity, null);
        } else if (view != null) {
            initLoadingStatusViewIfNeed(null, view);
        }
        mHolder.showLoadFailed();
    }

    //加载的数据为空
    public void showEmpty(Activity activity, View view) {
        if (activity != null) {
            initLoadingStatusViewIfNeed(activity, null);
        } else if (view != null) {
            initLoadingStatusViewIfNeed(null, view);
        }
        mHolder.showEmpty();
    }

}
