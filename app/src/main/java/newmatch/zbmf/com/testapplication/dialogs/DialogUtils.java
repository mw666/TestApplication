package newmatch.zbmf.com.testapplication.dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by **
 * on 2018/10/10.
 * 所有的dialog集中在这里
 */

public class DialogUtils {
    @SuppressLint("StaticFieldLeak")
    private static DialogUtils mDialogUtils;

    public static DialogUtils instance() {
        if (mDialogUtils == null) {
            synchronized (DialogUtils.class) {
                if (mDialogUtils == null) {
                    mDialogUtils = new DialogUtils();
                }
            }
        }
        return mDialogUtils;
    }

    //设置dialog的布局
    private View mView;

    public DialogUtils setView(View view) {
        mView = view;
        return this;
    }

    private Integer mGravity;

    //设置dialog的位置
    public DialogUtils setGravity(Integer gravity) {
        mGravity = gravity;
        return this;
    }

    //设置dialog的动画属性
    private Integer mDialogAnimStyle;

    public DialogUtils setDialogAnimStyle(Integer dialogAnimStyle) {
        mDialogAnimStyle = dialogAnimStyle;
        return this;
    }

    //设置dialog大小的参数
    private Float mDialogW = 1f;

    private DialogUtils setDialogW(Float dialogW) {
        mDialogW = dialogW;
        return this;
    }

    private Float mDialogH;

    private DialogUtils setDialogH(Float dialogH) {
        mDialogH = dialogH;
        return this;
    }

    //设置点击dialog外围是否取消
    private Boolean mIsCancel = false;

    public DialogUtils setIsCancel(Boolean isCancel) {
        mIsCancel = isCancel;
        return this;
    }
    //设置dialog的属性
    private Integer mDialogStyle;
    public DialogUtils setDialogStyle(Integer dialogStyle){
        mDialogStyle=dialogStyle;
        return this;
    }

    public Dialog gMDialog(Context context) {
        Dialog dialog=null;
        if (mDialogStyle!=null){
         dialog= new Dialog(context,mDialogStyle);
        }else {
            dialog=new Dialog(context);
        }
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = (int) (WindowManager.LayoutParams.MATCH_PARENT / mDialogW);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //设置位置
        window.setGravity(mGravity);
        //设置window动画
        if (mDialogAnimStyle!=null){
            window.setWindowAnimations(mDialogAnimStyle);
        }
//        window.getDecorView().setPadding(0, 0, 0, 0);
        //给dialog设置布局
        dialog.setContentView(mView, lp);
        //设置点击外围取消
        dialog.setCanceledOnTouchOutside(mIsCancel);
        dialog.setCancelable(mIsCancel);
        //展示dialog
        dialog.show();
        return dialog;
    }

}
