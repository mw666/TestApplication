package newmatch.zbmf.com.testapplication.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import newmatch.zbmf.com.testapplication.listeners.OnceClickListener;
import newmatch.zbmf.com.testapplication.presenter.presenterIml.BasePresenter;

/**
 * Created by **
 * on 2018/9/10.
 */

public abstract class BaseFragment extends Fragment{

    private Bundle savedInstanceState;
    private View mView;

    protected abstract Integer layoutId();
    protected abstract void initView();
    protected abstract void initData();
    protected abstract BasePresenter initPresenter();
    //点击事件
    protected abstract void onViewClick(View view);
    //是否全屏，让视图内容浸入状态栏
    protected abstract Boolean setViewEnterStatuBar();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(layoutId(), container, false);
        this.savedInstanceState=savedInstanceState;
        Boolean isEnter = setViewEnterStatuBar();
        //设置状态栏的通用特性
        initAppBar(isEnter);
        BasePresenter basePresenter = initPresenter();//该处返回BasePresenter
        initData();
        initView();
        return mView;
    }

    //外传mView
    public View getView(){
        return mView;
    }

    //获取保存数据的bundle
    public Bundle getSaveBundle() {
        return savedInstanceState;
    }

    //给控件绑定点击事件
    @SuppressWarnings("unchecked")
    public <T extends View> T bindViewWithClick(int resID, boolean isBindClick) {
        View view = bindView(resID);
        if (isBindClick) {
            view.setOnClickListener(clickListener);
        }
        return (T) view;
    }

    //获取布局控件
    @SuppressWarnings("unchecked")
    protected <T extends View> T bindView(int resourcesId) {
        try {
            return (T) mView.findViewById(resourcesId);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected OnceClickListener clickListener = new OnceClickListener() {
        @Override
        public void onNoDoubleClick(View v) {
            onViewClick(v);
        }
    };

    private void initAppBar(Boolean isEnter){
             if (isEnter){
                 //设置全屏，视图内容浸入状态栏
                 getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
             }
    }


}
