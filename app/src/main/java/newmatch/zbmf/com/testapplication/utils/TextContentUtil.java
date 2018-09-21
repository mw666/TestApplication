package newmatch.zbmf.com.testapplication.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Created by **
 * on 2018/9/21.
 */

public class TextContentUtil {

    public static void showOrHideClearIv(EditText et, ImageView clearIv){
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()>0){
                    clearIv.setVisibility(View.VISIBLE);
                }else {
                    clearIv.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}
