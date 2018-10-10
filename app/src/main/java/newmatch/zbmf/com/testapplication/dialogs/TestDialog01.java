package newmatch.zbmf.com.testapplication.dialogs;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.EditText;

import newmatch.zbmf.com.testapplication.R;

/**
 * Created by **
 * on 2018/9/29.
 * 这是测试的一个带EditText的DialogFragment
 */

public class TestDialog01 extends MyDiaog {

    private EditText mCommentEt;
    private Integer mGravity;

    public TestDialog01(){}

    @SuppressLint("ValidFragment")
    public TestDialog01(Integer gravity) {
        mGravity = gravity;
    }

    @Override
    protected Integer initView() {
        return R.layout.input_comment_dialog_view;
    }

    @Override
    protected Boolean isShowPositivieBtn() {
        return true;
    }

    @Override
    protected Boolean isShowNegativeBtn() {
        return false;
    }

    @Override
    protected Integer dialogGravity() {
        return mGravity;
    }

    @Override
    protected void bindView(View view) {
        mCommentEt = bindViewByIdWithClick(R.id.commentEt, false);

    }

    @Override
    protected void viewOnClick(View view) {

    }

    @Override
    protected String initTitle() {
        return "这是一个测试的dialog标题";
    }

    @Override
    protected String initMsg() {
        return "这是一个测试的dialog的提示信息";
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //这是里可以 销毁view
        mCommentEt = null;//赋值为null，促进GC的回收

    }
}
