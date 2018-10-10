package newmatch.zbmf.com.testapplication.GMClass;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;

/**
 * Created by **
 * on 2018/10/10.
 * 设置RecyclerView的LayoutManager，同意放在这个类里边
 */

public class GMRvSetLayoutManager {

    /**
     * 给recyclerView设置竖直方向的LayoutManager
     * @param context
     * @param rv
     */
    public static void setLinearLayoutManager(Context context,RecyclerView rv){
        rv.setLayoutManager(new LinearLayoutManager(context,
                OrientationHelper.VERTICAL,
                false));
    }

    /**
     * 给RecyclerView设置网格布局
     * @param context
     * @param rv
     */
    public static void setGridLayoutManager(Context context,RecyclerView rv,Integer spanCount){
       rv.setLayoutManager(new GridLayoutManager(context,spanCount,
               OrientationHelper.VERTICAL,
               false));
    }

}
