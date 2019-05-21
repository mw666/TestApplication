package newmatch.zbmf.com.testapplication.dialogs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.MessageFormat;

import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.callback.DialogActCallBack;
import newmatch.zbmf.com.testapplication.callback.EtCallBack;
import newmatch.zbmf.com.testapplication.listeners.DialogCallBack;
import newmatch.zbmf.com.testapplication.utils.ToastUtils;

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

    public static void showEtDialog(Activity activity, Context context, String tips, View view,
                                    int res, boolean touchCancel, EtCallBack callBack) {
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
        TextView tip = view.findViewById(R.id.tip);
        EditText newNickEt = view.findViewById(R.id.newNickEt);
        TextView btn = view.findViewById(R.id.btn);
        tip.setText(tips);
        newNickEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void afterTextChanged(Editable editable) {
                if (newNickEt.getText().length() > 0) {
                    btn.setBackground(ContextCompat.getDrawable(context,
                            R.drawable.login_btn1_bg_pressed));
                }
            }
        });
        btn.setOnClickListener(view1 -> {
            if (newNickEt.getText().length() > 0 && callBack != null) {
                callBack.etContent(newNickEt.getText().toString(), alertDialog);
            }
        });
        alertDialog.show();
    }

    public static void showPayDialog(Activity activity, Context context, View view, boolean touchCancel
            , int res, String title, EtCallBack callBack) {
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
        TextView payDialogTitle = view.findViewById(R.id.payDiaLogTitle);
        EditText rmbEt = view.findViewById(R.id.rmbEt);
        TextView allRmb = view.findViewById(R.id.allRmb);
        Button tiXianBtn = view.findViewById(R.id.tiXianBtn);
        if (!TextUtils.isEmpty(title))
            payDialogTitle.setText(title);
        rmbEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void afterTextChanged(Editable editable) {
                if (rmbEt.getText().length() > 0) {
                    tiXianBtn.setTextColor(Color.WHITE);
                    tiXianBtn.setBackground(ContextCompat.getDrawable(context,
                            R.drawable.login_btn1_bg_pressed));
                } else {
                    tiXianBtn.setTextColor(ContextCompat.getColor(context, R.color.black));
                    tiXianBtn.setBackground(ContextCompat.getDrawable(context,
                            R.drawable.add_friend_et_bg));
                }
            }
        });
        allRmb.setOnClickListener(v -> {
            //提现所有金额
            ToastUtils.showSquareTvToast(context, "提现所有金额");
        });
        tiXianBtn.setOnClickListener(v -> {
            String rmb = rmbEt.getText().toString().trim();
            if (callBack != null)
                callBack.etContent(rmb, alertDialog);
        });
        alertDialog.show();
    }

    public static void showVipDialog(Activity activity, Context context, View view, boolean touchCancel
            , int res, String title, String vipContentTitle,String vipPrice, EtCallBack callBack) {
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
        TextView vipDiaLogTitle = view.findViewById(R.id.vipDiaLogTitle);
        TextView vipRmbTitle = view.findViewById(R.id.vipRmbTitle);
        TextView vipRmb = view.findViewById(R.id.vipRmb);
        Button vipPositionBtn = view.findViewById(R.id.vipPositionBtn);
        if (!TextUtils.isEmpty(title))
            vipDiaLogTitle.setText(title);
        if (!TextUtils.isEmpty(vipContentTitle))
            vipRmbTitle.setText(vipContentTitle);
        if (!TextUtils.isEmpty(vipPrice))
            vipRmb.setText(MessageFormat.format("{0}元", vipPrice));
        vipPositionBtn.setOnClickListener(v -> {
            if (callBack != null)
                callBack.etContent(vipPrice, alertDialog);
        });
        alertDialog.show();
    }
}
