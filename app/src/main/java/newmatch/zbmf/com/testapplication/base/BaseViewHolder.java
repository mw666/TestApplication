package newmatch.zbmf.com.testapplication.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by **
 * on 2018/9/13.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder{
    private View mView;
    public BaseViewHolder(View itemView) {
        super(itemView);
        this.mView=itemView;
    }
    //获取布局控件
    @SuppressWarnings("unchecked")
    protected <T extends View> T bindView(int resourcesId) {
        try {
            return (T) mView.findViewById(resourcesId);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return null;
    }
}
