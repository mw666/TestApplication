package newmatch.zbmf.com.testapplication.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.callback.CommentCallBack;
import newmatch.zbmf.com.testapplication.listeners.OnceClickListener;

/**
 * Created by **
 * on 2018/3/30.
 */

public class BottomEditDialog extends Dialog {
    private Context mContext;
    private EditText mInput_remark;
    private CommentCallBack mCommentCallBack;
    private ImageView mSendComment;

    public BottomEditDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public BottomEditDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    protected BottomEditDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }

    public BottomEditDialog getDialog() {
        View dialogView = LayoutInflater.from(mContext).inflate(R.layout.bottom_edit_dailog, null);
        //获得dialog的window窗口
        Window window = getWindow();
        //设置dialog在屏幕底部
        assert window != null;
        window.setGravity(Gravity.BOTTOM);
        //设置dialog弹出时的动画效果，从屏幕底部向上弹出
//        window.setWindowAnimations(R.style.bottomDialogStyle);
        window.getDecorView().setPadding(0, 0, 0, 0);
        //获得window窗口的属性
        android.view.WindowManager.LayoutParams lp = window.getAttributes();
        //设置窗口宽度为充满全屏
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //设置窗口高度为包裹内容
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //将设置好的属性set回去
        window.setAttributes(lp);
        //将自定义布局加载到dialog上
        this.setContentView(dialogView);
        mInput_remark = dialogView.findViewById(R.id.input_remark);
        mSendComment = dialogView.findViewById(R.id.sendComment);
        return this;
    }

    public BottomEditDialog showI() {
        if (!this.isShowing()) {
            this.show();
        }
        return this;
    }

    public void dissI() {
        if (this.isShowing()) {
            this.dismiss();
        }
    }

    public void setOnAddClick(final CommentCallBack commentCallBack) {
        mCommentCallBack=commentCallBack;
        mSendComment.setOnClickListener(new OnceClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (commentCallBack!=null){
                    commentCallBack.commentCallBack(mInput_remark.getText().toString());
                }
                dissI();
            }
        });
    }
}
