package newmatch.zbmf.com.testapplication.dialogs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.listeners.DialogCallBack;

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

    public DialogUtils setDialogStyle(Integer dialogStyle) {
        mDialogStyle = dialogStyle;
        return this;
    }

    //设置是否有边距
    private Boolean mHasMargin = false;

    public DialogUtils setHasMargin(Boolean hasMargin) {
        mHasMargin = hasMargin;
        return this;
    }
    private Integer mRes;
    public DialogUtils setDialogDecoeViewBg(Integer res){
        mRes=res;
        return this;
    }

    public Dialog gMDialog(Activity activity, Context context) {
        AlertDialog.Builder builder;
        AlertDialog alertDialog;
        if (mDialogStyle != null) {
            builder = new AlertDialog.Builder(context, mDialogStyle);
        } else {
            builder = new AlertDialog.Builder(context);
        }
        alertDialog = builder.create();
        Window window = alertDialog.getWindow();
        assert window != null;
        WindowManager.LayoutParams lp = window.getAttributes();
        if (mHasMargin) {
            lp.width = (int) (WindowManager.LayoutParams.MATCH_PARENT / mDialogW);
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        } else {
            WindowManager windowManager = activity.getWindowManager();
            Display defaultDisplay = windowManager.getDefaultDisplay();
            Point point = new Point();
            defaultDisplay.getSize(point);
            lp.width = point.x;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        }
        window.getDecorView().setPadding(0, 0, 0, 0);
        //从Android 4.1开始向上兼容，对下不兼容
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (mRes==null){
                window.getDecorView().setBackground(context.getResources().getDrawable(R.drawable.dialog_top_bg));
            }else {
                window.getDecorView().setBackground(context.getResources().getDrawable(mRes));
            }
        }
        //设置位置
        window.setGravity(mGravity);
        //设置window动画
        if (mDialogAnimStyle != null) {
            window.setWindowAnimations(mDialogAnimStyle);
        }
        window.setAttributes(lp);
        //给dialog设置布局
        alertDialog.setView(mView);
        //设置点击外围取消
        alertDialog.setCanceledOnTouchOutside(mIsCancel);
        alertDialog.setCancelable(mIsCancel);
        //展示dialog
        alertDialog.show();
        return alertDialog;
    }

    //无边距
    public void showAlertDialog(Context context, Activity activity) {
        WindowManager windowManager = activity.getWindowManager();
        Display defaultDisplay = windowManager.getDefaultDisplay();
        Point outSize = new Point();
        defaultDisplay.getSize(outSize);
        AlertDialog.Builder builder;
        if (mDialogStyle != null) {
            builder = new AlertDialog.Builder(context, mDialogStyle);
        } else {
            builder = new AlertDialog.Builder(context);
        }
        AlertDialog dialog = builder.create();
        dialog.setView(mView);
        Window win = dialog.getWindow();
        assert win != null;
        View decorView = win.getDecorView();
        decorView.setPadding(0, 0, 0, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            decorView.setBackground(context.getResources().getDrawable(R.drawable.dialog_top_bg));
        }
        if (mGravity != null) {
            win.setGravity(mGravity);
        }
        if (mDialogAnimStyle != null) {
            win.setWindowAnimations(mDialogAnimStyle);
        }
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = outSize.x;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        win.setAttributes(lp);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    private DialogCallBack mDialogCallBack;
    public DialogUtils setDialogCallBack(DialogCallBack dialogCallBack){
        mDialogCallBack=dialogCallBack;
        return this;
    }

    public void showNormalAlertDialog(Context context,Integer msg){
        AlertDialog.Builder builder;
//        if (mDialogStyle != null) {
//            builder = new AlertDialog.Builder(context, mDialogStyle);
//        } else {
            builder = new AlertDialog.Builder(context);
//        }
        builder.setMessage(msg)
                .setPositiveButton(context.getString(R.string.confirm), (dialog, which) -> {
                    if (mDialogCallBack!=null){
                        mDialogCallBack.positiveClick(dialog);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(context.getString(R.string.cancel), (dialog, which) -> {
                    if (mDialogCallBack!=null){
                        mDialogCallBack.negativeClick(dialog);
                        dialog.dismiss();
                    }
                })
                .show();
    }
}
