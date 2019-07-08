package newmatch.zbmf.com.testapplication.views.customViewPager;

import android.content.Context;
import android.view.View;

/**
 * Create By Administrator
 * on 2019/7/7
 */
public interface MZViewHolder<T> {
        /**
         *  创建View
         * @param context
         * @return
         */
        View createView(Context context);

        /**
         * 绑定数据
         * @param context
         * @param position
         * @param data
         */
        void onBind(Context context, int position, T data);
        }