package newmatch.zbmf.com.testapplication.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;

/**
 * Created by **
 * on 2018/9/20.
 */

public class MyNestedScrollView extends NestedScrollView{

    NestedScrollInterface mNestedScrollInterface;

    public MyNestedScrollView(@NonNull Context context) {
        super(context);
    }

    public MyNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
    }

    public void setNestedScrollInterface(NestedScrollInterface nsi){
        this.mNestedScrollInterface=nsi;
    }

    public static interface NestedScrollInterface{
        void scroll(int l, int t, int oldl, int oldt);
    }
}
