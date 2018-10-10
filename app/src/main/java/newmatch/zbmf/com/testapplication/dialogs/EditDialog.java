package newmatch.zbmf.com.testapplication.dialogs;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import newmatch.zbmf.com.testapplication.R;

/**
 * Created by **
 * on 2018/9/28.
 * 测试的dialog
 */

public class EditDialog extends BaseDialogFragment{



    @Override
    public DialogStyleEnum initStyleNum() {
        return DialogStyleEnum.STYLE_NO_TITLE;
    }

    @Override
    public DialogThemeEnum initThemeNum() {
        return DialogThemeEnum.Theme_Holo_Light_Panel;
    }

    @Override
    public View initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.input_comment_dialog_view, null);
        EditText commentEt = view.findViewById(R.id.commentEt);



        return view;
    }
}
