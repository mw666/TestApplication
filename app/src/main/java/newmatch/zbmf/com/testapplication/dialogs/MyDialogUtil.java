package newmatch.zbmf.com.testapplication.dialogs;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.callback.DialogActCallBack;
import newmatch.zbmf.com.testapplication.listeners.DialogCallBack;

/**
 * Created by **
 * on 2018/9/11.
 */

public class MyDialogUtil {
    private static MyDialogUtil sMyDialogUtil;

    public static MyDialogUtil getInstance() {
        if (sMyDialogUtil == null) {
            synchronized (MyDialogUtil.class) {
                if (sMyDialogUtil == null) {
                    sMyDialogUtil = new MyDialogUtil();
                }
            }
        }
        return sMyDialogUtil;
    }

    private static DialogCallBack mDialogCallBack;

    public MyDialogUtil setDialogCallBack(DialogCallBack dialogCallBack) {
        mDialogCallBack = dialogCallBack;
        return this;
    }

    public void showPermissionDialog(Context context, String permissionTip) {
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setMessage(permissionTip)
                .setPositiveButton(context.getString(R.string.resume), (dialog, which) -> {
                    //确定，调用确定的回调
                    if (mDialogCallBack != null) {
                        mDialogCallBack.positiveClick(dialog);
                    }
                    dialog.dismiss();
                })
                .setNegativeButton(context.getString(R.string.cancel), (dialog, which) -> {
                    if (mDialogCallBack != null) {
                        mDialogCallBack.negativeClick(dialog);
                    }
                    dialog.dismiss();
                    //退出应用
                    //activity.onBackPressed();
                })
                .create();
        alertDialog.show();
    }

    //展示权限的对话框
    public static void showPermissionAlert(Activity activity, Context context, String tips, View view,
                                           int res, boolean touchCancel, DialogActCallBack callBack) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.dialogTheme);
        AlertDialog alertDialog = builder.create();
        alertDialog.setView(view);
        Window window = alertDialog.getWindow();
        if (window != null) {
            window.getDecorView().setPadding(0, 0, 0, 0);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                window.getDecorView().setBackground(context.getResources().getDrawable(res));
            }
            WindowManager manager = activity.getWindowManager();
            Display display = manager.getDefaultDisplay();
            android.view.WindowManager.LayoutParams lp = alertDialog.getWindow()
                    .getAttributes();
            lp.gravity = Gravity.CENTER;
            lp.width = display.getWidth() * 4 / 5;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            alertDialog.getWindow().setAttributes(lp);
            alertDialog.setCancelable(touchCancel);
            alertDialog.setCanceledOnTouchOutside(touchCancel);
            window.setWindowAnimations(R.style.alertDialogStyle01);
        }
        //设置window动画
        TextView init_permissions_tv = view.findViewById(R.id.init_permissions_tv);
        Button pushOutAppBtn = view.findViewById(R.id.pushOutAppBtn);
        Button agreeBtn = view.findViewById(R.id.agreeBtn);
        if (!TextUtils.isEmpty(tips)) {
            init_permissions_tv.setText(tips);
        }
        pushOutAppBtn.setOnClickListener(view1 -> {
            if (callBack != null) {
                callBack.cancelActCallBack(alertDialog);
            }
        });
        agreeBtn.setOnClickListener(view1 -> {
            if (callBack != null) {
                callBack.positionActCallBack(alertDialog);
            }
        });
        alertDialog.show();
    }
}
