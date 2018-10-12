package newmatch.zbmf.com.testapplication.GMClass;

import android.content.ClipboardManager;
import android.content.Context;
import android.widget.TextView;

import newmatch.zbmf.com.testapplication.base.MyApplication;
import newmatch.zbmf.com.testapplication.utils.ToastUtils;

/**
 * Created by **
 * on 2018/10/12.
 * 复制剪切
 */

public class GMCopy <T extends TextView>{

    private String mContent;

    private static GMCopy sGMCopy;
    public static GMCopy instance(){
        if (sGMCopy==null){
            synchronized (GMCopy.class){
                if (sGMCopy==null){
                    sGMCopy=new GMCopy();
                }
            }
        }
        return sGMCopy;
    }

    public String copyContent(T view,Context context){
        //设置长按可复制
        view.setOnLongClickListener(v -> {
            mContent = view.getText().toString();
            return false;
        });
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (mContent!=null){
            cmb.setText(mContent.trim()); //将内容放入粘贴管理器,在别的地方长按选择"粘贴"即可
            ToastUtils.showSingleToast(MyApplication.getInstance(),"复制的内容："+cmb.getText().toString());
            return cmb.getText().toString();//获取粘贴信息
        }else {
            return null;
        }
    }

}
