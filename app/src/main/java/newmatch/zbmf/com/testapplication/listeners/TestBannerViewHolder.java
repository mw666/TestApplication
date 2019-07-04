package newmatch.zbmf.com.testapplication.listeners;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.zhouwei.mzbanner.holder.MZViewHolder;

import newmatch.zbmf.com.testapplication.R;

/**
 * Created By pq
 * on 2019/7/4
 */
public class TestBannerViewHolder implements MZViewHolder<Integer> {
    private ImageView mImageView;
    @Override
    public View createView(Context context) {
        // 返回页面布局
        View view = LayoutInflater.from(context).inflate(R.layout.chat_recommend_item_view,null);
        mImageView = (ImageView) view.findViewById(R.id.chatRecommendItemIv);
        return view;
    }

    @Override
    public void onBind(Context context, int position, Integer data) {
        // 数据绑定
        mImageView.setImageResource(data);
    }
}
