package newmatch.zbmf.com.testapplication.listeners;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.assist.GlideUtil;
import newmatch.zbmf.com.testapplication.interfaces.MZViewHolder;

/**
 * Created by **
 * on 2018/3/20.
 */

public class BannerViewHolder implements MZViewHolder<Integer> {
    private ImageView mImageView;
    //    private TextView mMTv;
    private int imgs;
    private BannerPageClickListener mBannerPageClickListener;

    public BannerViewHolder(int imgs) {
        this.imgs = imgs;
    }

    public void setBannerClickListener(BannerPageClickListener bannerClickListener) {
        this.mBannerPageClickListener = bannerClickListener;
    }

    @Override
    public View createView(Context context) {
        // 返回页面布局文件
        View view = LayoutInflater.from(context).inflate(R.layout.chat_recommend_item_view, null);
        mImageView = (ImageView) view.findViewById(R.id.chatRecommendItemIv);
//        mMTv = (TextView) view.findViewById(R.id.banner_tv);
        return view;
    }

    @Override
    public void onBind(Context context, final int position, Integer data/*, Integer text*/) {
        // 数据绑定
        GlideUtil.loadCircleImage(context, R.drawable.loading1, data, mImageView);
//        mMTv.setText(text);
        mImageView.setOnClickListener(v -> {
            int i = position % imgs;
            if (mBannerPageClickListener != null) {
                mBannerPageClickListener.onPageClick(v, i);
            }
        });
    }
}