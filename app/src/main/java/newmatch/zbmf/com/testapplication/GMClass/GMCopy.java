package newmatch.zbmf.com.testapplication.GMClass;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.Objects;

import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.base.MyApplication;
import newmatch.zbmf.com.testapplication.component.PLog;
import newmatch.zbmf.com.testapplication.utils.GetUIDimens;
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

    @SuppressLint("ClickableViewAccessibility")
    public void/*GMCopy<T>*/ copyGetXY(T view,Context context,View parent){
        view.setOnTouchListener((v, event) -> {
            float rawX = event.getRawX();
            float rawY = event.getRawY();
            setPop(context,(int) rawX-65, (int) rawY-115,parent,view);
            return false;
        });
//        return this;
    }

    //设置popWindow
    public void setPop(Context context, int x, int y, View parent, T  userAccount){
        PopupWindow popupWindow = new PopupWindow(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView tv = new TextView(context);
        tv.setText(context.getString(R.string.copy));
        tv.setPadding(9,5,9,7);
        tv.setTextSize(GetUIDimens.pxToSp(context,GetUIDimens.dpToPx(context,13)));
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(context.getResources().getColor(R.color.white));

        tv.setOnClickListener(v -> {
            String copyUserAccount1 = userAccount.getText().toString();
            ClipboardManager cmb = (ClipboardManager) Objects.requireNonNull(context)
                    .getSystemService(Context.CLIPBOARD_SERVICE);
            cmb.setText(copyUserAccount1.trim()); //将内容放入粘贴管理器,在别的地方长按选择"粘贴"即可
            ToastUtils.showSingleToast(MyApplication.getInstance(),"复制的内容："+cmb.getText().toString());
            popupWindow.dismiss();
        });
        popupWindow.setContentView(tv);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.copy_bg_view));
        popupWindow.setFocusable(true);//设置获取焦点
        popupWindow.setTouchable(true);//设置可触摸
        popupWindow.setOutsideTouchable(true);//设置非popWindow区域可触摸
//        //设置点击隐藏popWindow
//        ColorDrawable colorDrawable = new ColorDrawable(0X00000000);
//        popupWindow.setBackgroundDrawable(colorDrawable);
        userAccount.setOnLongClickListener(v -> {
            //设置popWindow展示的位置
            popupWindow.showAtLocation(parent,Gravity.NO_GRAVITY,x,y);
            return false;
        });
    }

}
