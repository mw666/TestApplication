package newmatch.zbmf.com.testapplication.dialogs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by **
 * on 2018/9/28.
 * 基础的DialogFragment
 */

public abstract class BaseDialogFragment extends DialogFragment {

    //初始化dialog的样式和主题
    private int style = DialogFragment.STYLE_NORMAL, theme = 0;

    //传递标记
    public abstract DialogStyleEnum initStyleNum();

    public abstract DialogThemeEnum initThemeNum();

    public abstract View initView();


    /**
     * 在onCreate()方法中控制对话框的样式，在onCreateView()方法中控制对话框的布局
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //接收标记
        DialogStyleEnum initStyleNum = initStyleNum();
        DialogThemeEnum initThemeNum = initThemeNum();
        switch (initStyleNum) {
            case STYLE_NO_FRAME:
                style = DialogFragment.STYLE_NO_FRAME;
                break;
            case STYLE_NO_INPUT:
                style = DialogFragment.STYLE_NO_INPUT;
                break;
            case STYLE_NO_TITLE:
                style = DialogFragment.STYLE_NO_TITLE;
                break;
            case STYLE_NORMAL:
                style = DialogFragment.STYLE_NORMAL;
                break;
            default:
                style = STYLE_NORMAL;
                break;
        }
        switch (initThemeNum) {
            case Theme_Holo:
                theme = android.R.style.Theme_Holo;
                break;
            case Theme_Holo_Light:
                theme = android.R.style.Theme_Holo_Light;
                break;
            case Theme_Holo_Light_Dialog:
                theme = android.R.style.Theme_Holo_Light_Dialog;
                break;
            case Theme_Holo_Light_Panel:
                theme = android.R.style.Theme_Holo_Light_Panel;
                break;
            default:
                theme = android.R.style.Theme_Holo;
                break;
        }
        //设置dialog的样式和主题风格
        setStyle(style, theme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return initView();
    }


}
