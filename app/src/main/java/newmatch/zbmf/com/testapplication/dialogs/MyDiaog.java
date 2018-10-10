package newmatch.zbmf.com.testapplication.dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.component.PLog;
import newmatch.zbmf.com.testapplication.listeners.OnceClickListener;

/**
 * Created by **
 * on 2018/9/29.
 * 封装一个简单的DialogFragment
 * <p>
 * 参考简书：https://www.jianshu.com/p/526fcf3e8db3
 */

public abstract class MyDiaog extends DialogFragment {

    private AlertDialog.Builder mBuilder;
    private View mView;
    private AlertDialog mAlertDialog;
//    private Integer mDialogStyle;

    //初始化设置dialog的View
    protected abstract Integer initView();

    //是否显示确定按钮
    protected abstract Boolean isShowPositivieBtn();

    //是否显示取消按钮
    protected abstract Boolean isShowNegativeBtn();

    //设置dialog的显示位置
    protected abstract Integer dialogGravity();

    //绑定View获取控件
    protected abstract void bindView(View view);
    //控件的点击事件设置
    protected abstract void viewOnClick(View view);

    //设置dialog的标题和提示信息
    protected abstract String initTitle();

    protected abstract String initMsg();
    //设置dialog的属性
//    protected abstract Integer setDialogStyle();

    //提供对外的方法
    private PositiveBtnClick mPositiveBtnClick;

    public void setPositiveBtnClick(PositiveBtnClick positiveBtnClick) {
        this.mPositiveBtnClick = positiveBtnClick;
    }

    private NegativeBtnClick mNegativeBtnClick;

    public void setNegativeBtnClick(NegativeBtnClick negativeBtnClick) {
        this.mNegativeBtnClick = negativeBtnClick;
    }

    //确定按钮的接口
    public interface PositiveBtnClick {
        void dialogFgPositiveBtnClick();
    }

    //取消按钮的接口
    public interface NegativeBtnClick {
        void dialogFgNegativeBtnClick();
    }

    /**
     * DialogFragment当前几个方法的执行顺序
     * 1.onCreate()
     * 2.onCreateDialog()
     * 3.setupDialog()
     * 4.onCreateView()
     * 5.onStart()
     */

    private Integer viewInteger;
    private Boolean showPositivieBtn;
    private Boolean showNegativeBtn;
    private String title;
    private String tipMsg;
    private Integer mGravity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PLog.LogD("执行MyDialog  onCreate()方法");
        //获取得到各种参数

        //获取子类设置进来的View的资源
        viewInteger = initView();
        //设置dialog的属性
//        mDialogStyle = setDialogStyle();
        //获取是否显示dialog的确定按钮
        showPositivieBtn = isShowPositivieBtn();
        //获取是否显示dialog的取消按钮
        showNegativeBtn = isShowNegativeBtn();
        //获取设置的dialog标题
        title = initTitle();
        //获取设置的dialog的提示信息
        tipMsg = initMsg();
        //获取dialog的显示位置
        mGravity = dialogGravity();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //这里返回null，让Fragment作为一个control
        PLog.LogD("执行MyDialog  onCreateView()方法");

        return null;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        PLog.LogD("执行MyDialog  onCreateDialog()方法");
        //根据参数建立alertDialog
        mBuilder = new AlertDialog.Builder(getActivity());
        if (!TextUtils.isEmpty(title)) {
            mBuilder.setTitle(title);
        }
        if (!TextUtils.isEmpty(tipMsg)) {
            mBuilder.setMessage(tipMsg);
        }
        if (viewInteger != null) {
            mView = LayoutInflater.from(getActivity()).inflate(viewInteger, null);
            mBuilder.setView(viewInteger);
        }
        //我在这里配置dialog的确定按钮和取消按钮的点击事件---》并对外回调
        if (showNegativeBtn) {
            //取消
            mBuilder.setNegativeButton(R.string.cancel, (dialog1, which) -> {
                if (mNegativeBtnClick != null) mNegativeBtnClick.dialogFgNegativeBtnClick();
            });
        }
        if (showPositivieBtn) {
            //确定
            mBuilder.setPositiveButton(R.string.confirm, (dialog12, which) -> {
                if (mPositiveBtnClick != null) mPositiveBtnClick.dialogFgPositiveBtnClick();
            });
        }
        mAlertDialog = mBuilder.create();
        mAlertDialog.show();
        if (mGravity!=null){
            setDialogGravity(mGravity);
        }
        return mAlertDialog;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        PLog.LogD("执行MyDialog  setupDialog()方法");
        //上面建立好dialog，这里可以进一步的配置


    }

    private void setDialogGravity(Integer gravity){
        //设置AlertDialog的位置和大小
//        View decorView = mAlertDialog.getWindow().getDecorView();
//        ViewGroup.LayoutParams layoutParams = decorView.getLayoutParams();
//        layoutParams.width= WindowManager.LayoutParams.MATCH_PARENT;
//        layoutParams.height=WindowManager.LayoutParams.WRAP_CONTENT;
//        decorView.setLayoutParams(layoutParams);

//        WindowManager.LayoutParams lp = mAlertDialog.getWindow().getAttributes();
//        lp.width=WindowManager.LayoutParams.MATCH_PARENT;
//        lp.height=WindowManager.LayoutParams.WRAP_CONTENT;
//        mAlertDialog.getWindow().setAttributes(lp);
//        mAlertDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
//        mAlertDialog.getWindow().setGravity(gravity);

        WindowManager windowManager = getActivity().getWindowManager();
        Display defaultDisplay = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = mAlertDialog.getWindow().getAttributes();
        lp.width=defaultDisplay.getWidth();
        mAlertDialog.getWindow().setAttributes(lp);
        mAlertDialog.getWindow().setGravity(gravity);
    }

    @Override
    public void onStart() {
        super.onStart();
        PLog.LogD("执行MyDialog  onStart()方法");
        //这里的view来自onCreateView,上面返回的null，所以这里返回的是null
//        View view = getView();

        //这里可以进行控件的获取
        bindView(mView);
    }

    @SuppressWarnings("unchecked")
    protected <T extends View>T bindViewByIdWithClick(Integer id,Boolean isClick){
        View view = mView.findViewById(id);
        if (isClick){
            view.setOnClickListener(mClickListener);
        }
        return (T)view;
    }

    protected OnceClickListener mClickListener=new OnceClickListener() {
        @Override
        public void onNoDoubleClick(View v) {
            viewOnClick(v);
        }
    };

}
