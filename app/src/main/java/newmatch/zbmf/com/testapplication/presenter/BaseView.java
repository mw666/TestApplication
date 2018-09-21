package newmatch.zbmf.com.testapplication.presenter;

/**
 * Created by **
 * on 2018/9/14.
 */

public interface BaseView<T extends BasePresenter> {

    //传入具体的presenter
    void setPresenter(T presenter);
}
